var bcrypt = require('bcryptjs');
var log4js = require('log4js');
var log = log4js.getLogger("hash_worker");

exports.createPassword = function (passwd) {
    // Creating hash and salt
    var salt = bcrypt.genSaltSync(10);
    return bcrypt.hashSync(passwd, salt);
};

exports.checkPassword = function (hash, passwd) {
    return bcrypt.compareSync(passwd, hash);
};