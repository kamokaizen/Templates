var schedule = require('node-schedule');
var logHelper = require('./log_worker');
var log = logHelper.getLogger('schedule_worker');

exports.createHourlyScheduledJob = function (minute, task) {
    createHourlyScheduledJob(minute, task);
}

exports.createDailyScheduledJob = function (minute, hour, task) {
    createDailyScheduledJob(minute, hour, task);
}

exports.createWeeklyScheduledJob = function (minute, hour, day, task) {
    createWeeklyScheduledJob(minute, hour, day, task);
}

exports.createMonthlyScheduledJob = function (minute, hour, day, task) {
    createMonthlyScheduledJob(minute, hour, day, task);
}

function createHourlyScheduledJob(minute, task) {
    try {
        var pattern = getHourlySchedulePattern(minute);
        var newJob = schedule.scheduleJob(pattern, task);
        log.info("Hourly scheduled job is configured with pattern: " + JSON.stringify(pattern));
        return newJob;
    }
    catch (err) {
        log.error("Scheduled job could not be created: " + err);
        return null;
    }
}

function createDailyScheduledJob(minute, hour, task) {
    try {
        var pattern = getDailySchedulePattern(minute, hour);
        var newJob = schedule.scheduleJob(pattern, task);
        log.info("Daily scheduled job is configured with pattern: " + JSON.stringify(pattern));
        return newJob;
    }
    catch (err) {
        log.error("Scheduled job could not be created: " + err);
        return null;
    }
}

function createWeeklyScheduledJob(minute, hour, task) {
    try {
        var pattern = getWeeklySchedulePattern(minute, hour, day);
        var newJob = schedule.scheduleJob(pattern, task);
        log.info("Weekly scheduled job is configured with pattern: " + JSON.stringify(pattern));
        return newJob;
    }
    catch (err) {
        log.error("Scheduled job could not be created: " + err);
        return null;
    }
}

function createMonthlyScheduledJob(minute, hour, task) {
    try {
        var pattern = getWeeklySchedulePattern(minute, hour, day);
        var newJob = schedule.scheduleJob(pattern, task);
        log.info("Monthly scheduled job is configured with pattern: " + JSON.stringify(pattern));
        return newJob;
    }
    catch (err) {
        log.error("Scheduled job could not be created: " + err);
        return null;
    }
}

// This pattern returns a schedule to work on each hour in day.
function getHourlySchedulePattern(minute) {
    return minute + ' * * * *';
}

// This pattern returns a schedule to work on each day at current timestamp; hour and minute.
function getDailySchedulePattern(minute, hour) {
    return minute + ' ' + hour + ' * * *';
}

// This pattern returns a schedule to work on once a week at current timestamp; day, hour and minute.
function getWeeklySchedulePattern(minute, hour, day) {
    return minute + ' ' + hour + ' * * ' + day;
}

// This pattern returns a schedule to work on once a month.
function getMonthlySchedulePattern(minute, hour, day) {
    return minute + ' ' + hour + ' ' + day + ' * *';
}