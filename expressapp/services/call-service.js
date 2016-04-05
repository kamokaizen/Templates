var https = require('https');
var http = require('http');
var querystring = require('querystring');
var log4js = require('log4js');
var log = log4js.getLogger("call-service");

exports.requestToService = requestToService;

function requestToService(host, port, method, path, headers, data, isSecureCall, callback) {
    var header_opt = {};

    if (headers) {
        header_opt = headers;
        if (data) {
            header_opt['Content-Length'] = Buffer.byteLength(data);
        }
    } else {
        header_opt = data ? {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(data)
        } : null;
    }

    var options = {
        host: host,
        method: method,
        path: path,
        rejectUnauthorized:global.conf.rejectUnauthorized,
        headers: header_opt
    };
    if (port) {
        options.port = port;
    }

    // TODO change control from headers to protocol

    var apiReq = isSecureCall ? https.request(options) : http.request(options);
    apiReq.on('response', function(apiRes) {
        var chunks = [];
        var statusCode = apiRes.statusCode;

        apiRes.on('data', function(chunk) {
            chunks.push(chunk);
        });

        apiRes.on('end', function() {
            // TODO  Generalize for mssp_service
            var resData = Buffer.concat(chunks).toString();
            // TODO  If condition should change
            // TODO  Generalize for mssp_service
            if (statusCode == 200 && resData != "") {
                callback(null, resData);
            } else if (statusCode == 302) {
                callback(null, querystring.parse(apiRes.headers.location)["?session_id"])
            } else {
                callback(resData, null)
            }
        });
    });

    apiReq.on('error', function(err) {
        log.error(err)
        callback(err, null);
    });

    if(data){
        apiReq.write(data);
        // console.log(apiReq.output.toString());
    }
    apiReq.end();
}
