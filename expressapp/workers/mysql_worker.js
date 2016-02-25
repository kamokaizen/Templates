var mysql = require('mysql');
var us = require('underscore');

var pool = mysql.createPool({
    connectionLimit: global.conf.mysql.connectionLimit,
    host: global.conf.mysql.host,
    port: global.conf.mysql.port,
    user: global.conf.mysql.user,
    password: global.conf.mysql.password,
    database: global.conf.mysql.database,
    debug: false,
    multipleStatements: true
});

function getConnection(cb, query, groupBy) {
    pool.getConnection(function (err, connection) {
        if (typeof connection === 'undefined') {
            console.error("Error in connection database");
            cb(false, []);
            return;
        }

        if (err) {
            connection.release();
            console.error("Error in connection database");
            cb(false, []);
        }

        console.log('connected to db as id ' + connection.threadId);

        var errorCallback = function (err) {
            console.error("Error in connection database");
            try {
                cb(false, []);
            } catch (error) {
                console.error('callback error after connection lost in mysql connection : ' + error);
            }
        };

        connection.on('error', errorCallback);

        connection.query(query,
            function (err, rows, fields) {
                connection.removeListener('error', errorCallback);
                connection.release();
                if (!err) {
                    if (groupBy) {
                        var grouped = us.groupBy(rows, function (d) {
                            return d[groupBy];
                        });

                        rows = grouped;
                    }

                    cb(true, rows);
                } else {
                    console.log(err);
                    cb(false, []);
                }
            });
    });
}

exports.insertUser = function (callbackFunc, username, uid, customerId, msspToken, role) {
    var query = mysql.format("INSERT into users (uid,username,mssp_cid,mssp_ntoken,role,isdeleted) VALUES (?,?,?,?,?,0);", [uid, username, customerId, msspToken, role]);
    getConnection(callbackFunc, query);
}

exports.deleteUser = function (callbackFunc, username) {
    var query = mysql.format("DELETE FROM users WHERE username=?;", username);
    getConnection(callbackFunc, query);
}

exports.getUser = function (callbackFunc, username) {
    var query = mysql.format("SELECT * FROM users WHERE isdeleted=0 AND username=?;", username);
    getConnection(callbackFunc, query);
};

exports.getUser = function (callbackFunc, username) {
    var query = mysql.format("SELECT * FROM users WHERE isdeleted=0 AND username=?;", username);
    getConnection(callbackFunc, query);
};