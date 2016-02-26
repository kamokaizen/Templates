var express = require('express');
var router = express.Router();
var async = require('async');
var dbWorker = require('../workers/mysql_worker');

/* GET users listing. */
router.get('/test', function (req, res, next) {
    res.send('respond with a resource');
});

router.get('/asyncExample', function (req, res, next) {
    async.waterfall([
        // First function, goes to db and get user..
        function (callback) {
            dbWorker.getUser(function (status, rows) {
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
        function (data, callback) {
            callback(null, "Second Waterfall method called: " + JSON.stringify(data));
        }],
        // Final function, send message to client.  
        function (err, result) {
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

module.exports = router;
