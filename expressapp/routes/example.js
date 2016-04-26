var express = require('express');
var router = express.Router();
var async = require('async');
var dbWorker = require('../workers/mysql_worker');
// var redis = require('redis');
// var redisClient;
var log4js = require('log4js');
var log = log4js.getLogger("example");

// try {
//     redisClient = redis.createClient(global.conf.redis); //CREATE REDIS CLIENT    
// }
// catch (err) {
//     log.error(err);
// }

/* GET users listing. */
router.get('/test', function(req, res, next) {
    res.send('respond with a resource');
});

router.get('/asyncExample', function(req, res, next) {
    async.waterfall([
        // First function, goes to db and get user..
        function(callback) {
            dbWorker.getUser(function(status, rows) {
                if (status) {
                    if (rows && rows.length > 0) {
                        var user = rows[0];
                        callback(null, "First Waterfall method called: " + JSON.stringify(user));
                    }
                    else {
                        callback({ err: "Something is wrong!" }, user);
                    }
                }
                else {
                    callback({ err: "Something is wrong!" }, user);
                }
            }, "Default_user");
        },
        // Second function, add example string to result
        function(data, callback) {
            callback(null, "Second Waterfall method called: " + JSON.stringify(data));
        }],
        // Final function, send message to client.  
        function(err, result) {
            if (err) {
                res.status(500).send('Async waterfall response with error: ' + JSON.stringify(err));
                res.end();
            }
            else {
                res.status(200).send('Async waterfall final response: ' + result);
                res.end();
            }
        });
});

// // GET KEY'S VALUE
// router.get('/redis/get/:key', function(req, response) {
//     redisClient.get(req.params.key, function(error, val) {
//         if (error !== null) {
//             log.error("error: " + error);
//         }
//         else {
//             response.send("The value for this key is " + val);
//         }
//     });
// });

// //SET KEY'S VALUE
// router.get('/redis/set/:key/:value', function(req, response) {
//     redisClient.set(req.params.key, req.params.value, function(error, result) {
//         if (error !== null) {
//             log.error("error: " + error);
//         }
//         else {
//             response.send("The value for '" + req.params.key + "' is set to: " + req.params.value);
//         }
//     });
// });

module.exports = router;
