var fs = require('fs');
var log4js;

exports.initializeLogger = function(logPath, logConfigPath) {
    /**
     * make a log directory, just in case it isn't there.
     */
    try {
        fs.mkdirSync(logPath);
    } catch (e) {
        if (e.code != 'EEXIST') {
            log.error("Could not set up log directory, error was: ", e);
        }
    }

    log4js = require('log4js');
    log4js.configure(logConfigPath);
}

exports.getHttpLogger = function(){
    return log4js.connectLogger(log4js.getLogger("http"), { level: 'auto' });
}

exports.getLogger = function(loggerName) {
    return log4js.getLogger(loggerName);
}
