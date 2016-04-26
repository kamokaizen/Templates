var logWorker = require('./workers/log_worker');
// initialize the logger, first param is the logging path, second param is configuration file path of log4js
logWorker.initializeLogger('./log', './config/log4js.json');
var log = logWorker.getLogger('app');

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
var redisClient;

if (process.env.APP_ENV) {
    global.conf = require('./config/config.' + process.env.APP_ENV + '.json');
    log.info("Configuration is set for " + process.env.APP_ENV);
} else {
    global.conf = require('./config/config.default.json');
    log.info("Configuration is set for default.json");
}

try {
    redisClient = redis.createClient(global.conf.redis); //CREATE REDIS CLIENT    
}
catch (err) {
    log.error(err);
}

redisClient.on('connect', function () {
    log.info('Redis server connection is established.');
});

redisClient.on("error", function (err) {
    log.info("Could not connect to Redis Server, Error " + err);
});

var authentication = require('./routes/authentication');
var exampleRouter = require('./routes/example');
var queueWorker = require('./workers/queue_worker');
var scheduleWorker = require('./workers/schedule_worker');

var app = express();

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logWorker.getHttpLogger());
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

app.listen(global.conf.listenPort);
log.info("Server is listening on port " + global.conf.listenPort);
log.info("Server started at " + new Date());

// start queue with empty jobs
queueWorker.startQueue(null, function (status, error) {
    if (!status) { log.error("Error:" + error); }
});

// This job works at every 55 th minutes of hour
var job = scheduleWorker.createHourlyScheduledJob(55, function () {
    console.log('Hourly Task is run');
});

module.exports = app;
