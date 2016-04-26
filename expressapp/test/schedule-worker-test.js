global.conf = require('../config/config.test.json');

var assert = require('assert');
var sinon = require('sinon');
var log4js = require('log4js');
var log = log4js.getLogger("schedule-worker-test");

var scheduleWorker = require('../workers/schedule_worker');

// TODO complete the test case implementation
describe('Schedule worker Functional Tests', function () {

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
    it('should setup hourly cron job', function (done) {
        done();
    });

    it('should setup daily cron job', function (done) {
        done();
    });

    it('should setup weekly cron job', function (done) {
        done();
    });

    it('should setup monthly cron job', function (done) {
        done();
    });
});