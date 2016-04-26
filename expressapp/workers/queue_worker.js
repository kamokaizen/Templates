var queueFramework = require('queue');
var logHelper = require('./log_worker');
var log = logHelper.getLogger('queue_worker');
var queue = queueFramework({ concurrency: global.conf.queue.concurrentProcessLength, timeout: global.conf.queue.timeout });

exports.startQueue = function (jobs, callback) {
    startQueue(jobs, callback);
}

exports.stopQueue = function (callback) {
    stopQueue(callback);
}

exports.endQueue = function (callback) {
    endQueue(callback);
}

exports.addJobIntoQueue = function (job, callback) {
    addJobIntoQueue(job, callback);
};

exports.removeJobFromQueue = function (callback) {
    removeJobFromQueue(callback);
}

exports.getQueueLength = function (callback) {
    getQueueLength(callback);
}

// callback, if passed, will be called when the queue empties or when an error occurs.
function startQueue(jobs, callback) {
    try {
        queue.start(jobs);
        // scheduled queue is starting...callback called with status: true and no error
        log.info("Queue is started.");
        callback(true, null);
    }
    catch (err) {
        callback(false, err);
    }
}

// Stops the queue. can be resumed with queue.start().
function stopQueue(callback) {
    try {
        queue.stop();
        // queue is stopping...callback called with status: true and no error
        log.info("Queue is stopped.");
        callback(true, null);
    }
    catch (err) {
        callback(false, err);
    }
}

function endQueue(callback) {
    try {
        queue.end();
        log.info("Queue is ended.");
        callback(true, null);
    }
    catch (err) {
        callback(false, err);
    }
}

function addJobIntoQueue(job, callback) {
    try {
        queue.push(job);
        // callback called with status: true and no error
        callback(true, null);
    }
    catch (err) {
        // callback called with status: false and error
        callback(false, err);
    }
}

function removeJobFromQueue(callback) {
    try {
        queue.pop();
        // callback called with status: true and no error
        callback(true, null);
    }
    catch (err) {
        // callback called with status: false and error
        callback(false, err);
    }
}

function getQueueLength(callback) {
    try {
        callback(true, queue.length);
    }
    catch (err) {
        callback(false, err);
    }
}

// use the timeout feature to deal with jobs that  
// take too long or forget to execute a callback 
queue.on('timeout', function (next, job) {
    log.warn('job timed out:', job.toString().replace(/\n/g, ''));
    next();
});

// After a job executes its callback.
queue.on('success', function (result, job) {
    log.info('job finished processing:', job.toString().replace(/\n/g, ''));
});

// After a job passes an error to its callback.
queue.on('error', function (err, job) {
    log.error('error occured during working job: ' + err);
    log.error('job passes an error:', job.toString().replace(/\n/g, ''));
});