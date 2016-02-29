var bcrypt = require('bcryptjs');

exports.createPassword = function (passwd) {
    // Creating hash and salt
    var salt = bcrypt.genSaltSync(10);
    return bcrypt.hashSync(passwd, salt);
};

exports.checkPassword = function (hash, passwd) {
    return bcrypt.compareSync(passwd, hash);
};