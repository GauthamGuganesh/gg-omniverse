'use strict';

var express = require('express');
var cors = require('cors');
var formidable = require('formidable');

// require and use "multer"...

var app = express();

app.use(cors());
app.use('/public', express.static(process.cwd() + '/public'));

app.get('/', function (req, res) {
     res.sendFile(process.cwd() + '/views/index.html');
  });

app.post('/api/fileanalyse', function(req, res)
{
  let form = new formidable.IncomingForm();
  form.parse(req);
  
  form.on('file', (name, file) => {
    
    console.log(name);
    console.log(file);
    
    res.json({ filesize: file.size});
  })
      
});

app.listen(process.env.PORT || 3000, function () {
  console.log('Node.js listening ...');
});
