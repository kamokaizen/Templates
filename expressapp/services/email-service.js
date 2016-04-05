process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = '0';
var nodemailer = require('nodemailer');
var log4js = require('log4js');
var log = log4js.getLogger("email-service");

// create reusable transporter object using the default SMTP transport
var transporter = nodemailer.createTransport(global.conf.email);

// verify connection configuration
transporter.verify(function(error, success) {
    if (error) {
        log.info('Mail Server connection could not be established with user credentials: ' + global.conf.email.auth.user);
        log.error(error);
    } else {
        log.info('Mail Server connection is accomplished with user credentials: ' + global.conf.email.auth.user);
    }
});

exports.sendMail = function(to, cc, html, text, subject, attachments, cb) {

    var mailOptions = {
        from: global.conf.email.address, // sender address
        cc: cc,
        to: to, // list of receivers
        subject: subject,
        text: text,
        html: html,
        attachments: attachments // [{filename, path}]
    };

    // send mail with defined transport object
    transporter.sendMail(mailOptions, function(error, info) {
        if (error) {
            log.error(error);
            cb(false, error);
        }
        else {
            log.info('Email is sent to : ' + to);
            cb(true, info);
        }
    });
};