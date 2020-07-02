// server.js
// where your node app starts

// init project
var express = require('express');
var app = express();

// enable CORS (https://en.wikipedia.org/wiki/Cross-origin_resource_sharing)
// so that your API is remotely testable by FCC 
var cors = require('cors');
app.use(cors({optionSuccessStatus: 200}));  // some legacy browsers choke on 204

// http://expressjs.com/en/starter/static-files.html
app.use(express.static('public'));

// http://expressjs.com/en/starter/basic-routing.html
app.get("/", function (req, res) {
  res.sendFile(__dirname + '/views/index.html');
});


// your first API endpoint... 
app.get("/api/timestamp/:date_string?", function (req, res) {
  
  let date_value = req.params.date_string;
  if(date_value == undefined)
  {
    let currentDate = new Date();
    res.json({ "unix": currentDate.getTime(), "utc": currentDate.toUTCString() });  
  }
  
  else if(date_value.indexOf("-") == -1)
  {
      let resultDate = new Date(parseInt(date_value));
      res.json({ "unix": resultDate.getTime(), "utc": resultDate.toUTCString() });
  }
  
  else
  {
    let resultDate = new Date(date_value);
    
    if(resultDate == 'Invalid Date') res.json({ "error" : "Invalid Date" });
    else res.json({ "unix": resultDate.getTime(), "utc": resultDate.toUTCString() });
  }
  
});



// listen for requests :)
var listener = app.listen(process.env.PORT, function () {
  console.log('Your app is listening on port ' + listener.address().port);
});