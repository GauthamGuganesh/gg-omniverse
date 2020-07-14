var passport = require('passport');
var bcrypt = require('bcrypt');

module.exports = (app, db) => {
  
  app.route("/").get((req, res) => {
  //Change the response to render the Pug template
  res.render(process.cwd() + '/views/pug/index', 
              {title: 'Home Page', message: 'Please login', 
               showLogin: true, showRegistration: true}
            );
  });
  
  //authenticating user using passport middleware
  //passport.authenticate invokes the Strategy implemented
  app.post("/login", passport.authenticate('local', { failureRedirect: '/' }), (req, res) => {
     res.redirect("/profile");
  });
  
  app.route("/register").post(//arg1    
     (req, res, next) => {
     console.log("1) - REGISTER");
     db.collection('users').findOne( {username: req.body.username}, (err, doc) => { 
       if(err) next(err);
       else if(doc) res.redirect('/');
       else
       {
         let hash = bcrypt.hashSync(req.body.password, 12); //Hashing password
         db.collection('users').insertOne({
           username: req.body.username,
           password: hash
         }, (err, result) => {
              if(err) res.redirect('/');
              next(null, result);
         })
       }
     })}, 
        //arg2                    
        passport.authenticate('local', {failureRedirect: '/'}), 
        //arg3
        (req, res, next) => {
        console.log("2) Authenticated USER");
        res.redirect('/profile');
      });
  
  //Middleware checking for authentication if user enters /profile link directly.
  function ensureAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
      return next();
    }
    res.redirect('/');
  };

  app.get('/profile', ensureAuthenticated, (req,res) => {
      console.log("3) Ensuring authentication");
      console.log(req.user);
      console.log(req.session.passport.user);
      res.render(process.cwd() + '/views/pug/profile', { username: req.user.username });
 });
    
  app.get('/logout', (req, res) => {
      req.logout();
      res.redirect('/');
  });
  
  // When any other path is requested, throw 404.
  app.use( (req, res, next) => {
      res.status(404);
      res.type('text');
      res.send('Not Found');
  })
  
}