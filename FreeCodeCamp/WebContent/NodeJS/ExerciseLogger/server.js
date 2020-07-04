const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const dateformat = require('dateformat');
const cors = require('cors')

const mongoose = require('mongoose');
mongoose.connect(process.env.DB_URI, { useNewUrlParser: true, useUnifiedTopology: true });

var schema = mongoose.Schema;

var userschema = new schema({
  username: String
});

var exerciseschema = new schema({
  userId: String,
  description: String,
  duration: Number,
  date: { type: Date, default: Date.now }
});

var User = mongoose.model("User", userschema);
var Log = mongoose.model("Log", exerciseschema);

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
  let date = null;
  
  if(req.body.date.length != 0) date = new Date(req.body.date);
  
  let logobj = { userId: userId, description: description,
                     duration: parseInt(duration) };
  
  if(date != null) logobj.date = date;
  
  let exercise = new Log(logobj);
  
  save(exercise).then( (doc) => {
      
      let name = "";
      User.find({ _id: userId })
          .select('username')
          .exec( (err, result) => {

                  if(err) return console.error(err);
                  else name = result[0].username;
                  
                  let formattedDate = dateformat(doc.date, "ddd mmm dd yyyy");
                  console.log(typeof doc.date);
                  
                  let exerciselog = { _id: doc.userId, username: name, 
                                       date: formattedDate, duration: doc.duration,
                                       description: doc.description,
                                       };

                  res.json(exerciselog);   
           });  
     });
});

app.get("/api/exercise/log", (req, res) => {
  
  let userId = req.query.userId;
  let limit = parseInt(req.query.limit);
  
  let loglimit = (limit == NaN) ? 0 : limit;
      
  Log.find({userId: userId}, (err, docs) => {
    
    if(err) return console.error(err);

    let count = docs.length;
    User.find({_id: userId}).select('username').exec( (err, doc) => {
        
        if(err) return console.error(err);
        let name = doc[0].username;      
        let logs = docs.map( (log) => ({ description: log.description,
                                         duration: log.duration,
                                         date: log.date }) );
       
        let custom_sort = (a, b) => (b.date.getTime()) - (a.date.getTime());       
        let sortedLogs = logs.sort(custom_sort);        
        let resultLogs = sortedLogs.map( (log) => ({ description: log.description,
                                               duration: log.duration,
                                               date: log.date.toDateString() }) );
      
        if(logs.length > loglimit && loglimit != 0)
        {
            let toRemove = logs.length - loglimit;
            resultLogs.splice(0, toRemove);
        }
      
        res.json({ userId: userId, username: name, log: resultLogs, count: count });
      
       });
  });
  
});

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
