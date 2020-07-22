var passport = require('passport');
var LocalStrategy = require('passport-local');
var bcrypt = require('bcrypt');
var ObjectID = require('mongodb').ObjectID;

module.exports = (app, db) => {
  
  passport.serializeUser((user, done) => {
      console.log("Serialized User");
      done(null,user._id);
    });
  
  //user object attaches to req. req.user
  passport.deserializeUser((id, done) => {   
    db.collection('users')
      .findOne({ _id: new ObjectID(id) }, (err, user) =>
                              {
                                  console.log("Deserialized");
                                  done(null, user);      
                              })   
  });
    
  //Local strategy means authenticating username and password using a local DB.
  //Can also be delegated to facebook,twitter,github through passport.
  passport.use(new LocalStrategy( (username, password, done) => {  
    
    db.collection('users').findOne({username: username}, (err, doc) => {      
      console.log('User ' + username + ' attempted to log in.');
      if(err) return done(err);
      else if(!doc) return done(null, false);
      else if(!bcrypt.compareSync(password, doc.password)) return done(null, false);
      else return done(null, doc);     
    })
    
  }));
  
}
 