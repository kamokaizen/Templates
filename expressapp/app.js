var express = require('express');
var session = require('express-session');
var path = require('path');
var fs = require('fs');
var favicon = require('serve-favicon');
var helmet = require("helmet");
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var async = require("async");
var csrf = require('csurf');
var redis = require('redis');
var redisStore = require('connect-redis')(session);
var log4js = require('log4js');
log4js.configure('./config/log4js.json');
var log = log4js.getLogger("app");

/**
 * make a log directory, just in case it isn't there.
 */
try {
  fs.mkdirSync('./log');
} catch (e) {
  if (e.code != 'EEXIST') {
    log.error("Could not set up log directory, error was: ", e);
  }
}

if (process.env.APP_ENV) {
    global.conf = require('./config/config.' + process.env.APP_ENV + '.json');
    log.info("Configuration is set for " + process.env.APP_ENV);
} else {
    global.conf = require('./config/config.default.json');
    log.info("Configuration is set for default.json");
}

var redisClient = redis.createClient(global.conf.redis); //CREATE REDIS CLIENT

redisClient.on('connect', function () {
    log.info('Redis server connection is established.');
});

redisClient.on("error", function (err) {
    log.info("Could not connect to Redis Server, Error " + err);
});

var authentication = require('./routes/authentication');
var exampleRouter = require('./routes/example');

var app = express();

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(log4js.connectLogger(log4js.getLogger("http"), { level: 'auto' }));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(helmet());
app.use(session({ secret: global.conf.redis.sessionSecretKey, store: new redisStore({ client: redisClient }), resave: false, saveUninitialized: false, cookie: { maxAge: global.conf.redis.sessionTimeout * 60000 } }));
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
    var p = path.join(__dirname, "public/templates/views", 'index.html');
    res.sendFile(p);
});

app.listen(8070);
log.info("Server is listening on port 8070");
log.info("Server started at " + new Date());

module.exports = app;
