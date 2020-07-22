/*
*
*
*       Complete the API routing below
*
*
*/

'use strict';

var expect = require('chai').expect;
var mongo = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectID;
var runner            = require('../test-runner');

const CONNECTION_STRING = process.env.DB; //MongoClient.connect(CONNECTION_STRING, function(err, db) {});

module.exports = function (app) {

  mongo.connect(CONNECTION_STRING,{ useUnifiedTopology: true }, (err, client) => {
    if(err) return console.error(err);
    else {
        console.log("Successfully connected");
        let db = client.db('ggDB');
        app.route('/api/issues/:project')
      
            .get(function (req, res){
              var project        = req.params.project;
              var fieldsToQuery  = Object.keys(req.query);
              if(req.query['open'] == 'true') req.query.open  = true;
              if(req.query['open'] == 'false') req.query.open = false; 
          
              db.collection('issue_tracker').findOne({project_name: project}, (err, result) => {
                if(err) return console.error(err);
                if(!result) res.json({});
                else if(fieldsToQuery.length > 0)
                {
                  let array = result.project_data;  
                  array = array.filter( obj => {
                       let flag = true;
                       fieldsToQuery.forEach(field => {
                          if(obj[field] != req.query[field]) flag = false; });
                       return flag;
                  });
                  
                  res.json(array);
                }
                else res.json(result.project_data);
              });
            })
      
            .post(function (req, res){
              var project      = req.params.project;
              let project_data = {_id: new ObjectId()}; 
              project_data = Object.assign(project_data, req.body);
              project_data.created_on = new Date();
              project_data.updated_on = new Date();
              project_data.open       = true;
              
              db.collection('issue_tracker')
                .findOneAndUpdate({project_name: project},
                                  {$setOnInsert: {project_name: project},

                                   $push: {project_data: project_data}},
                                  {upsert: true, returnOriginal: false},
                                  (err, doc) => {
                                       if(err) return console.error(err);
                                       console.log('saved');
                                       res.json(project_data);
                            });           
            })

            .put(function (req, res){
              var project      = req.params.project;
              if(Object.keys(req.body).length == 0)
                  res.send('no updated field sent');
          
              var id           = req.body._id          
              db.collection('issue_tracker')
                .findOne({project_name: project},                    
                         (err, result) => {
                          if(err) return console.error(err);
                   // Finding out the array to update using id from input.
                          result = result.project_data.filter(ele => (ele._id == id));
                          result[0].updated_on  = new Date();
                
                 // Updating result array with the fields to be updated.(except id)
                          let fieldsToUpdate = Object.keys(req.body).filter(ele => req.body[ele] != ''); 
                          fieldsToUpdate.forEach(field => result[0][field] = req.body[field]);
                
                          result[0]._id  = new ObjectId(id); //'id' has to be objectID
                          result[0].open = (req.body.open === 'true') ? true:false;
                
                //Updating array
                  db.collection('issue_tracker')
                     .update({project_name: project,"project_data._id": result[0]._id },
                             { $set: { "project_data.$": result[0] } },
                             { returnOriginal: false },
                             (err, result) => {
                                if(err) console.log('Cannot update : ' + id);
                                console.log('Successfully updated');
                                res.send('successfully updated');
                  })
                
                });
                                             
            })

            .delete(function (req, res){
              var project = req.params.project;
              if(!req.body._id) res.send('_id error');
          
              var id      = new ObjectId(req.body._id);
              db.collection('issue_tracker')
                .update({project_name: project},
                        { $pull: {project_data: { _id: id} } },
                        (err, result) => {
                    if(err) console.log('Could not delete' + id);
                    res.send('deleted ' + id);
              })
            });
      
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
      
      //404 Not Found Middleware
      app.use(function(req, res, next) {
        res.status(404)
          .type('text')
          .send('Not Found');
      });
    }
  });
    
};
