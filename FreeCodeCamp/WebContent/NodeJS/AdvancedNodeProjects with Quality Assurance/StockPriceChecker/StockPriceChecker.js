/*
*
*
*       Complete the API routing below
*
*
*/

'use strict';

var expect = require('chai').expect;
var mongo  = require('mongodb').MongoClient;
var fetch  = require('node-fetch');
var runner = require('../test-runner');

const CONNECTION_STRING = process.env.DB; //MongoClient.connect(CONNECTION_STRING, function(err, db) {});

module.exports = function (app) {

  let getStockData = async (stockName) => 
  {
     let stockEndPoint = `https://repeated-alpaca.glitch.me/v1/stock/${stockName}/quote`;         
     //Fetching stock from another source through an AJAX call.
     return fetch(stockEndPoint)
                .then(res => res.json())
                .then(stockdata => ({stock: stockName, price: stockdata.latestPrice }) );    
  } 
  
  let isNewIPAddress = (IPs, realIP) => {
    
    let isNewIP = false;
    
    if(IPs.length == 0) isNewIP = true;
    else isNewIP = IPs.every(ip => (ip != realIP));
    
    return isNewIP;
  }
  
  mongo.connect(process.env.DB, {useUnifiedTopology: true}, (err, client) => {
    if(err) return console.error(err);
    else
    {
       console.log('Successfully connected');
       var db = client.db('ggDB'); 
      
       app.route('/api/stock-prices')
          .get(function (req, res){
         
         let stockName = req.query.stock;
         let realIP = (req.headers['x-forwarded-for']) ? req.headers['x-forwarded-for'].split(',')[0] : req.host ;
         
         // DB table => Stock Name, Likes, IPs: [IP1, IP2, .....];
         let insertStock = db.collection('StockInfo')
                             .findOneAndUpdate({name: stockName},
                                             {$setOnInsert: {name: stockName,
                                                             likes: 0,
                                                             LikedIPs: []} },
                                             {upsert: true, returnOriginal: false}); 
         
         let fetchStock  = getStockData(stockName);
           
         insertStock.then(stock => {
           let IPs = stock.value.LikedIPs;
           let isNewIP = isNewIPAddress(IPs, realIP);
                      
           console.log(realIP + ' ' + isNewIP + ' ' + IPs);
           if(req.query.like) //Only if like = true, we should push IPs and inc likes.
           {                  
             //If newIP push and increment like.            
             if(isNewIP)
             {
               let updateStock = db.collection('StockInfo')
                                   .findOneAndUpdate({name: stockName},
                                                    {$push: { LikedIPs: realIP },
                                                    $inc:  { likes: 1} },                                                             
                                                    {returnOriginal: false});  
               updateStock.then(result => {

                 fetchStock.then(stockdata => {
                   stockdata.likes = result.value.likes;
                   res.json(stockdata);
                 });

               });
             }
             else
             {
               fetchStock.then(stockdata => {
                                      stockdata.likes = stock.value.likes;
                                      res.json(stockdata);
                });
             }
           }
           else //If like = false, return stock info. Don't do anything else.
           {
             fetchStock.then(stockdata => {
                                      stockdata.likes = stock.value.likes;
                                      res.json(stockdata);
              });
           }           
         })        
       });
      
      app.route('/api/stock-prices/multiple')
         .get((req, res) => {
        
        let stockNames = req.query.stock;
        let realIP = (req.headers['x-forwarded-for']) ? req.headers['x-forwarded-for'].split(',')[0] : req.host ;
        
        let bulkWrite = db.collection('StockInfo').bulkWrite([
          {
            updateOne: {
                          "filter": {name: stockNames[0]},
                          "update": {$setOnInsert: {name: stockNames[0],
                                                    likes: 0,
                                                    LikedIPs: [] } },
                          "upsert": true
                       }
          }, 
          {
            updateOne: {
                          "filter": {name: stockNames[1]},
                          "update": {$setOnInsert: {name: stockNames[1],
                                                    likes: 0,
                                                    LikedIPs: [] } },
                          "upsert": true
                       }                                    
          }
        ]);  
        
        let stock1 = getStockData(stockNames[0]);
        let stock2 = getStockData(stockNames[1]);

        let stocks = Promise.all([stock1, stock2]);        
                                                                     
        bulkWrite.then(result => {
            if(req.query.like)
            {
              //Inserts if no stock name found 
              //Updates IP & increments likes if the field LikedIPs don't have the client IP.
              let updateCall = db.collection('StockInfo').updateMany({name: {$in: [stockNames[0], stockNames[1]]}, 
                                                                     LikedIPs: {$nin: [realIP]}  },
                                                                     { $push: {LikedIPs: realIP},
                                                                       $inc:  {likes: 1} });
               if(req.query.like)
               {
                 updateCall.then(result => {
                  stocks.then(values => 
                  {
                      db.collection('StockInfo')
                        .find({name: {$in: [stockNames[0], stockNames[1]]} })
                        .toArray((err, result) => {
                        if(err) return console.error(err);
                        else
                        {
                          let resultStock = [];                                            
                          values[0].rel_likes = result[0].likes - result[1].likes;
                          values[1].rel_likes = result[1].likes - result[0].likes;

                          resultStock.push(values[0]);
                          resultStock.push(values[1]);

                          res.json(resultStock);
                        }
                      });

                  });
                 });
               }
            }
            else
            {
              console.log('here');             
              stocks.then(values => 
              {
                  db.collection('StockInfo')
                    .find({name: {$in: [stockNames[0], stockNames[1]]} })
                    .toArray((err, result) => {
                    if(err) return console.error(err);
                    else
                    {
                      let resultStock = [];                                            
                      values[0].rel_likes = result[0].likes - result[1].likes;
                      values[1].rel_likes = result[1].likes - result[0].likes;
                     
                      resultStock.push(values[0]);
                      resultStock.push(values[1]);
                      
                      res.json(resultStock);
                    }
                  });
                    
              });
            }         
        })
      })
     
      //404 Not Found Middleware
      app.use(function(req, res, next) {
        res.status(404)
          .type('text')
          .send('Not Found');
      });

      //Start our server and tests!
      app.listen(process.env.PORT || 3000, function () {
        console.log("Listening on port " + process.env.PORT);
        if(process.env.NODE_ENV==='test') {
          console.log('Running Tests...');
          setTimeout(function () {
            try {
              runner.run();
            } catch(e) {
              var error = e;
                console.log('Tests are not valid:');
                console.log(error);
            }
          }, 3500);
        }
      });
      
    }  
  });
};
