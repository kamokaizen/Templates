var express = require('express');
var session = require('express-session');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var helmet = require("helmet");
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var async = require("async");
var csrf = require('csurf');

if (process.env.APP_ENV) {
    global.conf = require('./config/config.' + process.env.APP_ENV + '.json');
    console.log("Configuration is set for " + process.env.APP_ENV);
} else {
    global.conf = require('./config/config.default.json');
    console.log("Configuration is set for default.json");
}

var authentication = require('./routes/authentication');
var exampleRouter = require('./routes/example');

var app = express();

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(helmet());

// session settings
var sessionExpireTime = 60 * 20 * 1000;
app.use(session({ secret: '!seSsion_Scrt_Ky!', resave: true, saveUninitialized: true, cookie: { maxAge: sessionExpireTime } }));
app.use(csrf());

app.use(function (req, res, next) {
    var token = req.csrfToken();
    res.cookie('XSRF-TOKEN', token);
    res.cookie('csrftoken', token);
    res.locals.csrfToken = token;
    next();
});

app.use('/', authentication);
app.use('/api', exampleRouter);

// Returns index html on each get request.
app.get('*', function (req, res) {
    console.log(req.path)
    var p = path.join(__dirname, "public/templates/views", 'index.html');
    res.sendFile(p);
});

module.exports = app;
