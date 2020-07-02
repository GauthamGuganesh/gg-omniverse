'use strict';

var express = require('express');
var mongo = require('mongodb');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var dns = require('dns');
var urlparser = require('url');
var cors = require('cors');

var app = express();

// Basic Configuration 
var port = process.env.PORT || 3000;

/** this project needs a db !! **/ 
mongoose.connect(process.env.DB_URI);

var schema = mongoose.Schema;
var urlschema = new schema({
  
  "original_url": {
                    type: String,
                    required: true
                  },
  
  "short_url":   {
                    type: String,
                    required: true
                 }
  
});

var URL = mongoose.model("URL", urlschema);

app.use(cors());

/** this project needs to parse POST bodies **/
app.use(bodyparser.urlencoded({extended: false}));

app.use('/public', express.static(process.cwd() + '/public'));

app.get('/', function(req, res){
  res.sendFile(process.cwd() + '/views/index.html');
});

let findDoc = async (searchObj) =>
{  
  let value = await URL.find(searchObj, (err, result) => {
      
    if(err) return console.error(err);
    else if(result.length > 0) return result;
    else return null;
    
  }); 
  
  return value;
}

// your first API endpoint... 
app.post("/api/shorturl/new", function (req, res) 
{
  let url = urlparser.parse(req.body.url);
  
  if(url.protocol == null || url.host == null) 
    res.json({ "error": "invalid URL" });
  else
  {
    dns.lookup(url.host, (err, addresses, family) => {
    
      if(err) res.json({ "error": "invalid URL" });
      else
      {
        findDoc({"original_url": url.host}).then((doc) => {
          if(doc.length == 0) 
          {
            let urlrecord = new URL({ "original_url": url.host, "short_url": 1 });
            urlrecord.save().then( (doc) => res.json({"original_url": doc.original_url, "short_url": doc.short_url}));
          }
          else
          {
            let result = doc[0];
            res.json({ "original_url": result.original_url, "short_url": result.short_url});
          }
        }); 
        
      }
      
    });
  }          

});

app.get("/api/shorturl/1", (req, res) => {
  
  console.log("inside shortened url");
  findDoc({"short_url": "1"}).then((doc) => {

    if(doc.length == 0) 
    {
      res.json({ "error": "Invalid URL" });
    }
    else
    {
      let redirectAddr = doc[0].original_url;
      console.log("redirecting to " + redirectAddr);
      res.redirect("https://"+redirectAddr);
    }
  });
  
});

app.listen(port, function () {
  console.log('Node.js listening ...');
});