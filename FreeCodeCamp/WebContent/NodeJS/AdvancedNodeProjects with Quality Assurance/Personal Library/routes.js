/*
*
*
*       Complete the API routing below
*       
*       
*/

'use strict';

var expect   = require('chai').expect;
var mongo    = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectId;
var runner   = require('../test-runner');
const MONGODB_CONNECTION_STRING = process.env.DB;

module.exports = function (app) {
  
 mongo.connect(MONGODB_CONNECTION_STRING, {useUnifiedTopology: true}, (err, client) => {
   
   if(err) return console.error(err);
   else
   {  
      console.log("Succesfully connected");
      let db = client.db('ggDB');
     
      app.route('/api/books')
        .get(function (req, res){
          //response will be array of book objects
          //json res format: [{"_id": bookid, "title": book_title, "commentcount": num_of_comments },...]
         db.collection('books').find({}).toArray((err, result) => {
           if(err) return console.error(err);
           let responseObj = result.map(ele => ({title: ele.title, _id: ele._id, commentcount: ele.comments.length }) );
           res.json(responseObj);
         });
       
        })

        .post(function (req, res){
          var title = req.body.title;
          if(!title) res.send('missing title');
          else{
          db.collection('books').insertOne({
            title: title,
            comments: []
          }, (err, result) => {
            if(err) return console.error(err);
            res.json({_id: result.insertedId, title: title});
          })
          //response will contain new book object including atleast _id and title
        }})

        .delete(function(req, res){
          //if successful response will be 'complete delete successful'
          db.collection('books').deleteMany({}, (err, result) => {
            if(err) return console.error(err);
            res.send('complete delete successful');
          })
        });



      app.route('/api/books/:id')
        .get(function (req, res){
          var bookid = req.params.id;
          //json res format: {"_id": bookid, "title": book_title, "comments": [comment,comment,...]}
          db.collection('books')
            .findOne({_id: new ObjectId(bookid)},
                     (err, result) => {
            if(err) return console.error(err);
            if(!result) res.send('no book exists');
            else res.json(result);
          })
        })

        .post(function(req, res){
          var bookid = req.params.id;
          var comment = req.body.comment;
          //json res format same as .get
          db.collection('books')
            .findOneAndUpdate({_id: new ObjectId(bookid)},
                              { $push: {comments: comment} },
                              { returnOriginal: false},
                              (err, result) => {
            if(err) return console.log(err);
            let responseObj = {_id: bookid, title: result.value.title,
                                comments: result.value.comments };
            res.json(responseObj);
          })
        })

        .delete(function(req, res){
          var bookid = req.params.id;
          //if successful response will be 'delete successful'
          db.collection('books')
            .deleteOne({_id: new ObjectId(bookid)},
                       (err, result) => {
              if(err) return console.error(err);
              res.send('delete successful');
          })
        });

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
          }, 1500);
        }
      });
    }
 }); 
};
