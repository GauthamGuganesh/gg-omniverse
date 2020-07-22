'use strict';

const express     = require('express');
const session     = require('express-session');
const bodyParser  = require('body-parser');
const fccTesting  = require('./freeCodeCamp/fcctesting.js');
const auth        = require('./app/auth.js');
const routes      = require('./app/routes.js');
const mongo       = require('mongodb').MongoClient;
const passport    = require('passport');
const passportSocketIo = require('passport.socketio');
const cookieParser= require('cookie-parser')
const app         = express();
const http        = require('http').Server(app);
const io          = require('socket.io')(http);
const cors = require('cors');
const sessionStore= new session.MemoryStore();

app.use(cors());
fccTesting(app); //For FCC testing purposes

app.use('/public', express.static(process.cwd() + '/public'));
app.use(cookieParser());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.set('view engine', 'pug')

app.use(session({
  secret: process.env.SESSION_SECRET,
  resave: true,
  saveUninitialized: true,
  key: 'express.sid',
  store: sessionStore,
}));


mongo.connect(process.env.DATABASE, (err, db) => {
    if(err) console.log('Database error: ' + err);
    else 
    {
      console.log("successfully connected")
      auth(app, db);
      routes(app, db);

      http.listen(process.env.PORT || 3000);


      //start socket.io code  
      //Authenticating users establishing socket connection.
      io.use(passportSocketIo.authorize({
          cookieParser: cookieParser,
          key: 'express.sid',
          secret: process.env.SESSION_SECRET,
          store: sessionStore
        }
      ));
		
	  var currentUsers = 0;
      
      io.on('connection', socket => {
        console.log('A user has connected');
        //Now after authenticating user object available in socket.request.user
        currentUsers++;
        
        io.emit('user', {name: socket.request.user.name, currentUsers, connected: true});
        
        //Receiving a chat message and broadcasting to all clients.
        socket.on('chat message', message => {
          io.emit('chat message', {name: socket.request.user.name, message});
        })
        
        //socket.on because we are listening for a connected socket to disconnect.
        //If many sockets connected we have to listen to each one of them for disconnect.
        socket.on('disconnect', () => {
          console.log('A user has disconnected');
          
          //Cookie parsed, then user info deserialized and attached to socket obj.
          console.log('user ' + socket.request.user.name + ' connected');
          --currentUsers;
          
        io.emit('user', {name: socket.request.user.name, currentUsers, connected: false});
        });
        
      });

      
      //end socket.io code
  
    }
});
