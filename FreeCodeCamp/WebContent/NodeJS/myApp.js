
var express = require('express');
var bodyParser = require('body-parser');
var app = express(); // initiating server instance.

var filePath =  __dirname + '/views/index.html';
var assetPath = __dirname + '/public';

app.use((req, resp, next) => {
  console.log(req.method + " " + req.path + " - " + req.ip);
  next();
});

app.use(bodyParser.urlencoded({extended: false}));

app.get("/", (req, res) => res.sendFile(filePath));
app.use("/", express.static(assetPath));

app.get("/json", (req, res) => {

  if(process.env.MESSAGE_STYLE == 'uppercase')
  {
    res.json({"message" : 'Hello json'.toUpperCase()});
  }
  else
  {
    res.json({"message" : 'Hello json'});
  }

});

app.get("/now", (req, resp, next) => {

  req.time = new Date().toString();
  next();
},
  (req, resp) => {

  resp.json({time: req.time});

});

app.get("/:word/echo", (req, resp) => {
  resp.json({echo : req.params.word});
});

let queryHandler = (req, resp) => {

  resp.json({name : req.query.first + " " + req.query.last});

};

let bodyHandler = (req, resp) => {

  resp.json({name : req.body.first + " " + req.body.last});

};

app.route("/name").get(queryHandler).post(bodyHandler);

app.listen(3000); // To make server actively listen.
