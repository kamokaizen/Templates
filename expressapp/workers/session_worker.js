var _ = require('underscore');
var guid = require('guid');
var log4js = require('log4js');
var log = log4js.getLogger("session_worker");

function getNewSession(uname, uid) {
    var now = new Date();
    var userSession = {
        username: uname,
        user_id: uid,
        login_timestamp: now.getTime()
    };
    log.info("Session is created for user: " + userSession.username);
    return userSession;
}

function removeSession(session) {
    var username = session.userData.username;
    session.destroy(function(err) {
        log.info("Session is deleted for user:  " + username);
    });
}

function doSessionValidation(cb, session) {
    var now = new Date();
    // difference between session expire time and now must be negative to be session alive.
    // if value is positive then user must be redirect to login and requesting relogin..
    var invalidTimeLimit = now.getTime() - session.cookie.expires.getTime();

    if (invalidTimeLimit < 0 && session.userData) {
        cb(true);
    } else {
        cb(false);
    }
}

exports.validateSession = function(cb, session) {
    doSessionValidation(cb, session);
};

exports.getNewSession = function(uname, uid) {
    return getNewSession(uname, uid);
};

exports.removeSession = function(session) {
    removeSession(session);
};
