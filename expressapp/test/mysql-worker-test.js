global.conf = require('../config/config.test.json');

var assert = require('assert');
var mysql = require('mysql');
var sinon = require('sinon');
var log4js = require('log4js');
var log = log4js.getLogger("mysql-worker-test");

var mysqlWorker = require('../workers/mysql_worker');

describe('User Crud Operations Test', function () {

    var expectedUser = {
        username: "testUser",
        uid: "850681",
        password: "Password",
        role: 0
    };

    before(function (done) {
        // runs before all tests in this block
        mysqlWorker.deleteUser(function (status, rows) {
            if (status) log.info('testUser is deleted from db before all tests run.');
            done();
        }, expectedUser.username);
    });

    after(function () {
        // runs after all tests in this block
    });

    beforeEach(function () {
        // runs before each test in this block
    });

    afterEach(function () {
        // runs after each test in this block
    });

    // test cases
    it('should insert user', function (done) {
        mysqlWorker.insertUser(function (status, rows) {
            if (status) log.info('testUser is inserted into db.');
            assert.equal(status, true);
            done();
        }, expectedUser.username, expectedUser.uid, expectedUser.password, 0);
    });

    it('should get user', function (done) {
        mysqlWorker.getUser(function (status, rows) {
            if (status) log.info('testUser is retrieved from db.');
            assert.equal(status, true);
            assert.equal(expectedUser.username, rows[0].username);
            done();
        }, expectedUser.username);
    });

    it('should delete user', function (done) {
        mysqlWorker.deleteUser(function (status, rows) {
            if (status) log.info('testUser is deleted from db.');
            assert.equal(status, true);
            done();
        }, expectedUser.username);
    });
});