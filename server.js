var express = require('express');
var cors = require('cors');
var app = express();

// Use CORS for all routes
app.use(cors());

app.get('/user/all', function (req, res) {
  res.json({ msg: 'This is CORS-enabled for all origins!' });
});
app.get('/user/signin', function (req, res) {
  res.json({ msg: 'This is CORS-enabled for all origins!' });
});

app.listen(8080, function () {
  console.log('CORS-enabled web server listening on port 8080');
});
