var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res, next) {
    res.send('respond with a resource');
});

router.get('/list', function (req, res, next) {
    var users = ['serif', 'kamo', 'duygu'];
    var usersJson =  {t1:"t1",t2:"t2"};
    res.render('user', { title: "User List", name: "kamo", users: users, userJson: usersJson });
});

module.exports = router;
