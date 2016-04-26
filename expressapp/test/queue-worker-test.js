global.conf = require('../config/config.test.json');

var assert = require('assert');
var sinon = require('sinon');
var log4js = require('log4js');
var log = log4js.getLogger("queue-worker-test");

var queueWorker = require('../workers/queue_worker');

// TODO complete the test case implementation
describe('Queue worker Functional Tests', function () {

    before(function (done) {
        // runs before all tests in this block
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
    it('should start queue', function (done) {
        done();
    });

    it('should stop queue', function (done) {
        done();
    });

    it('should insert job to queue', function (done) {
        done();
    });

    it('should remove job from queue', function (done) {
        done();
    });

    it('should get queue lengt', function (done) {
        done();
    });
});