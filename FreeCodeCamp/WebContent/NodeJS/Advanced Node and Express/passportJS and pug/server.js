"use strict";

const express = require("express");
const fccTesting = require("./freeCodeCamp/fcctesting.js");
const auth = require("./auth.js");
const routes = require("./routes.js");
var session = require('express-session');
var passport = require('passport');

//MongoDB
var mongo = require('mongodb').MongoClient;

const app = express();

fccTesting(app); //For FCC testing purposes
app.use("/public", express.static(process.cwd() + "/public"));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

//Creating session and using it in passport.
app.use( session({ secret: process.env.SESSION_SECRET, // SESSION_SECRET is a random value that is used to hash your cookie.
                   resave: true,
                   saveUninitialized: true
                  }) );
app.use(passport.initialize());
app.use(passport.session());
app.set('view engine', 'pug');

mongo.connect(process.env.DB, { useUnifiedTopology: true }, (err, client) => {
  
  if(err) return console.error(err);
  
  else //Mounting all middleware only if db connection is successfull
  {
    console.log("Sucessfully connected");
    var db = client.db('ggDB'); //mongo change from version 3 and above.
    
    
    auth(app, db); //using separate modules for auth and routes.
    routes(app, db);

    app.listen(process.env.PORT || 3000, () => {
       console.log("Listening on port " + process.env.PORT);    
    });
    
  }
  
})

