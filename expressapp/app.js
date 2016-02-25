var express = require('express');
var session = require('express-session');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var helmet = require("helmet");
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

if (process.env.APP_ENV) {
    global.conf = require('./config/config.' + process.env.APP_ENV + '.json');
    console.log("Configuration is set for " + process.env.APP_ENV);
} else {
    global.conf = require('./config/config.default.json');
    console.log("Configuration is set for default.json");
}

var authentication = require('./routes/authentication');
var routes = require('./routes/index');
var users = require('./routes/users');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

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

app.use('/', authentication);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});


module.exports = app;
