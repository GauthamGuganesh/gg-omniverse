const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const dateformat = require('dateformat');
const cors = require('cors')

const mongoose = require('mongoose');
mongoose.connect(process.env.DB_URI, { useNewUrlParser: true, useUnifiedTopology: true });

var schema = mongoose.Schema;

var userschema = new schema({
  username: String,
  log: [{ description: String,
          duration: Number,
          date: { type: Date, default: Date.now }
        }]
});

var User = mongoose.model("User", userschema);

app.use(cors())

app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())


app.use(express.static('public'))
app.get('/', (req, res) => {
  res.sendFile(__dirname + '/views/index.html')
});

let save = async (doc) =>
{
  let savedvalue = await doc.save();
  return savedvalue;
}

app.post("/api/exercise/new-user", (req, res) => {
  
  //The name present in HTML form in 'views'.
  let user = new User({ username: req.body.username });
  save(user).then( (saveduser) => res.json({ "username": saveduser.username,
                                             "_id": saveduser._id, }));
  
});

app.post("/api/exercise/add", (req, res) => {
  
  let userId = req.body.userId;
  let description = req.body.description;
  let duration = req.body.duration; 
  let date;
  
  if(!req.body.date){
    date = new Date();
  }
  else{
    date = new Date(req.body.date);
  }
  
  let logobj = { description: description,
                 duration: parseInt(duration),
                 date: date.toDateString() };
    
  User.findById(userId, (err, doc) => {
        
        if(err) return console.error(err);
        doc.log.push(logobj);
        doc.save();
        res.json({ _id: doc._id, username: doc.username,
                   description: description, duration: parseInt(duration),
                   date: date.toDateString() });
    
    
    });
  
  });

app.get("/api/exercise/log", (req, res) => {
  
  let userId = req.query.userId;
  let limit = parseInt(req.query.limit);
  
  let loglimit = (limit == NaN) ? 0 : limit;
  
  User.findById(userId, (err, doc) => {
    
      if(err) return console.error(err);
    
      let sortedLogs = sortLogs(doc.log);     
      let logs = sortedLogs.map( (log) => ({ _id: log._id, description: log.description, 
                                         duration: log.duration,
                                         date: log.date.toDateString() }) );
      
      if(loglimit != 0 && loglimit < sortedLogs.length) logs = logs.slice(0, loglimit);
    
      let result = { userId: doc._id, username: doc.username, 
                     log: logs, count: logs.length };
    
      res.json(result);
  
  });
  
});

function sortLogs(logs)
{
  let sortedExercises = logs.sort((a,b) => a.date - b.date)
  return sortedExercises;
}

app.get("/api/exercise/users", (req, res) => {
  
  User.find() //Empty search object means will fetch everything
      .select('username _id')
      .exec( (err, result) => {
                  if(err) return console.error(err);
                  res.json(result);  
      });
});

// Not found middleware
app.use((req, res, next) => {
  return next({status: 404, message: 'not found'})
})

// Error Handling middleware
app.use((err, req, res, next) => {
  let errCode, errMessage

  if (err.errors) {
    // mongoose validation error
    errCode = 400 // bad request
    const keys = Object.keys(err.errors)
    // report the first validation error
    errMessage = err.errors[keys[0]].message
  } else {
    // generic or custom error
    errCode = err.status || 500
    errMessage = err.message || 'Internal Server Error'
  }
  res.status(errCode).type('txt')
    .send(errMessage)
})

const listener = app.listen(process.env.PORT || 3000, () => {
  console.log('Your app is listening on port ' + listener.address().port)
})