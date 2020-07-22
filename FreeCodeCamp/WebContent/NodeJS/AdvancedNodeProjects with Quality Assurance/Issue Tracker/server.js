'use strict';

var express     = require('express');
var bodyParser  = require('body-parser');
var helmet      = require('helmet');
var expect      = require('chai').expect;
var cors        = require('cors');

var apiRoutes         = require('./routes/api.js');
var fccTestingRoutes  = require('./routes/fcctesting.js');

var app = express();

app.use('/public', express.static(process.cwd() + '/public'));

app.use(helmet.xssFilter());
app.use(cors({origin: '*'})); //For FCC testing purposes only



app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));



//Sample front-end
app.route('/:project/')
  .get(function (req, res) {
    res.sendFile(process.cwd() + '/views/issue.html');
  });

//Index page (static HTML)
app.route('/')
  .get(function (req, res) {
    res.sendFile(process.cwd() + '/views/index.html');
  });

//Routing for API 
apiRoutes(app);

//For FCC testing purposes
fccTestingRoutes(app);  
    

//Start our server and tests!


module.exports = app; //for testing
