/*
*
*
*       FILL IN EACH FUNCTIONAL TEST BELOW COMPLETELY
*       -----[Keep the tests in the same order!]-----
*       (if additional are added, keep them at the very end!)
*/

var chaiHttp = require('chai-http');
var chai = require('chai');
var assert = chai.assert;
var server = require('../server');

chai.use(chaiHttp);

suite('Functional Tests', function() {
  
    suite('POST /api/issues/{project} => object with issue data', function() {
      
      test('Every field filled in', function(done) {
       chai.request(server)
        .post('/api/issues/test')
        .send({
          issue_title: 'Title',
          issue_text: 'text',
          created_by: 'Functional Test - Every field filled in',
          assigned_to: 'Chai and Mocha',
          status_text: 'In QA'
        })
        .end(function(err, res){
          assert.equal(res.status, 200);
          assert.isNotNull(res.issue_title); 
          assert.isNotNull(res.issue_text);
          assert.isNotNull(res.created_by);
          assert.isNotNull(res.assigned_to);
          assert.isNotNull(res.status_text);
          done();
        });
      });
      
      test('Required fields filled in', function(done) {
        chai.request(server)
            .post('/api/issues/test')
            .send({
            issue_title: 'Title',
            issue_text: 'text',
            created_by: 'Functional Tests'
        })
            .end((err, res) => {
              assert.equal(res.status, 200);
              assert.isNotNull(res.issue_title);
              assert.isNotNull(res.issue_text);
              assert.isNotNull(res.created_by);
              done();
          })
        })
      
      test('Missing required fields', function(done) {
        chai.request(server)
            .post('/api/issues/test')
            .send({
              assigned_to: 'Fucntional test',
              status_text: 'OA'
            })
            .end((err, res) => {
             assert.equal(res.status, 200);
             assert.isUndefined(res.issue_title);
             assert.isUndefined(res.issue_text);
             assert.isUndefined(res.created_by);
            done();
          })
        })
      });
      
    });
    
    suite('PUT /api/issues/{project} => text', function() {
      
      test('No body', function(done) {
        chai.request(server)
            .put('/api/issues/test')
            .end((req, res) => {
          assert.equal(res.status, 200);
          assert.equal(res.text, 'no updated field sent');
          done();
        })            
      
      });
      
      test('One field to update', function(done) {
        chai.request(server)
            .put('/api/issues/test')
            .send({_id: '5f16c96697922a2fb8c45dac', 'open': true})
            .end((req, res) => {
          assert.equal(res.status, 200);
          assert.equal(res.text, 'successfully updated');
          done();
        })           
      });
      
      test('Multiple fields to update', function(done) {
        chai.request(server)
            .put('/api/issues/test')
            .send({_id: '5f16c970c3038e2fe1d11484', 'open': true, 'issue_title': 'Gautham'})
            .end((req, res) => {
          assert.equal(res.status, 200);
          assert.equal(res.text, 'successfully updated');
          done();
        })   
      });
      
    });
    
    suite('GET /api/issues/{project} => Array of objects with issue data', function() {
      
      test('No filter', function(done) {
        chai.request(server)
        .get('/api/issues/test')
        .query({})
        .end(function(err, res){
          assert.equal(res.status, 200);
          assert.isArray(res.body);
          assert.property(res.body[0], 'issue_title');
          assert.property(res.body[0], 'issue_text');
          assert.property(res.body[0], 'created_on');
          assert.property(res.body[0], 'updated_on');
          assert.property(res.body[0], 'created_by');
          assert.property(res.body[0], 'assigned_to');
          assert.property(res.body[0], 'open');
          assert.property(res.body[0], 'status_text');
          assert.property(res.body[0], '_id');
          done();
        });
      });
      
      test('One filter', function(done) {
        chai.request(server)
        .get('/api/issues/test')
        .query({open: true})
        .end(function(err, res){
          assert.equal(res.status, 200);
          assert.isArray(res.body);
          assert.property(res.body[0], 'issue_title');
          assert.property(res.body[0], 'issue_text');
          assert.property(res.body[0], 'created_on');
          assert.property(res.body[0], 'updated_on');
          assert.property(res.body[0], 'created_by');
          assert.property(res.body[0], 'assigned_to');
          assert.equal(res.body[0].open, true);
          assert.property(res.body[0], 'status_text');
          assert.property(res.body[0], '_id');
          done();
        });
      });
      
      test('Multiple filters (test for multiple fields you know will be in the db for a return)', function(done) {
        chai.request(server)
        .get('/api/issues/test')
        .query({open: true, issue_title: 'Title'})
        .end(function(err, res){
          assert.equal(res.status, 200);
          assert.isArray(res.body);
          assert.equal(res.body[0].issue_title, 'Title');
          assert.property(res.body[0], 'issue_text');
          assert.property(res.body[0], 'created_on');
          assert.property(res.body[0], 'updated_on');
          assert.property(res.body[0], 'created_by');
          assert.property(res.body[0], 'assigned_to');
          assert.equal(res.body[0].open, true);
          assert.property(res.body[0], 'status_text');
          assert.property(res.body[0], '_id');
          done();
      });
      
    });
    
    suite('DELETE /api/issues/{project} => text', function() {
      
      test('No _id', function(done) {
        chai.request(server)
            .delete('/api/issues/test')
            .end((req, res) => {
          assert.equal(res.status, 200);
          assert.equal(res.text, '_id error');
          done();
        })
      });
      
      test('Valid _id', function(done) {
        chai.request(server)
            .delete('/api/issues/test')
            .send({
              _id: '5f16ca788d5c02338c4b73ff'
            })
            .end((req, res) => {
          assert.equal(res.status, 200);
          assert.equal(res.text, 'deleted 5f16ca788d5c02338c4b73ff');
          done();
        })
      });
      
    });

});
