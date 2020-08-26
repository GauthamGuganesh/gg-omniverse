'use strict';

var express     = require('express');
var bodyParser  = require('body-parser');
var cors        = require('cors');
var helmet      = require('helmet');
var nocache     = require('nocache');

var apiRoutes         = require('./routes/api.js');
var fccTestingRoutes  = require('./routes/fcctesting.js');


var app = express();

app.use('/public', express.static(process.cwd() + '/public'));

app.use(cors({origin: '*'})); //USED FOR FCC TESTING PURPOSES ONLY!

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

//Security settings
app.use(helmet.hidePoweredBy({setTo: 'PHP 4.2.0'}));
app.use(nocache());

//Index page (static HTML)
app.route('/')
  .get(function (req, res) {
    res.sendFile(process.cwd() + '/views/index.html');
  });

//For FCC testing purposes
fccTestingRoutes(app);

//Routing for API 
apiRoutes(app);  
    

module.exports = app; //for unit/functional testing
