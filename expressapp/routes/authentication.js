var express = require('express');
var async = require("async");
var path = require('path');
var router = express.Router();
var sessionWorker = require('../workers/session_worker');
var dbWorker = require('../workers/mysql_worker');
var hashWorker = require('../workers/hash_worker');

function validateSession(cb, session) {
    sessionWorker.validateSession(function (result) {
        cb(result);
    }, session);
}

function redirectToLogin(res) {
    var p = path.join(__dirname, '../public/templates/registration', '/login.html');
    res.status(401).sendFile(p);
}

router.get('/login', function (req, res, next) {
    validateSession(function (isValid) {
        if (isValid) {
            res.redirect("/overview");
        } else {
            console.log("Session is not valid, request is redirected to the login...");
            redirectToLogin(res);
        }
    }, req.session);
});

router.post('/signin', function (req, res, next) {
    var username = req.body.username;
    var password = req.body.password;

    try {
        dbWorker.getUser(function (status, rows) {
            if (status) {
                if (rows && rows.length > 0) {
                    var user = rows[0];
                    if (hashWorker.checkPassword(user.password, password)) {
                        req.session.userData = sessionWorker.getNewSession(username, user.id);
                        res.json({ result: true, redirect: "/overview" });
                        res.end();
                    }
                    else {
                        res.json({ result: false, message: "User credentials is wrong!" });
                    }
                }
                else {
                    res.json({ result: false, message: "User credentials is wrong!" });
                }
            }
            else {
                res.json({ result: false, message: "Something went wrong!" });
            }
        }, username);
    }
    catch (err) {
        res.json({ result: false, message: "Something went wrong!" });
    }
});

router.post('/signout', function (req, res, next) {
    if (req.session.userData) {
        sessionWorker.removeSession(req.session);
        redirectToLogin(res);
    }
});

router.all('*', function (req, res, next) {
    if (req.url == '/login' || req.url == '/signin' || req.url == '/signout') {
        next();
    } else {
        validateSession(function (isValid) {
            if (isValid) {
                next();
            } else {
                console.log("Session is not valid, request is redirected to the login...");
                res.redirect("/login");
            }
        }, req.session);
    }
});

module.exports = router;
