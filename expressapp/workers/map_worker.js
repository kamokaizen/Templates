var HashMap = require('hashmap');
var log4js = require('log4js');
var log = log4js.getLogger("map_worker");
// HashMap keeps 
// key = uid, value  = json object 
// [uid, {}]
var exampleMap;

exports.initializeMap = function(cb) {
    initializeMap(cb);
}

exports.getMap = function(cb) {
    cb(exampleMap);
}

exports.getValue = function(key, cb) {
    getValueFromMap(key, cb);
}

exports.setValue = function(key, value) {
    setValueIntoMap(key, value);
}

exports.deleteValue = function(key) {
    deleteValueFromMap(key);
}

exports.getAllValues = function(cb) {
    getAllValues(cb);
}

exports.getAllKeys = function(cb) {
    getAllKeys(cb);
}

function initializeMap(cb) {
    try {
        exampleMap = new HashMap();
        cb(null, exampleMap);
    }
    catch (err) {
        log.error("Map could not be created: " + err);
        cb(err, null);
    }
}

function getValueFromMap(key, cb) {
    try {
        cb(null, exampleMap.get(key));
    }
    catch (err) {
        log.error("Value with key " + key + " could not be getting from map: " + err);
        cb(err, null);
    }
}

function setValueIntoMap(key, value) {
    try {
        exampleMap.set(key, value);
        cb(null, true);
    }
    catch (err) {
        log.error("Value with key " + key + " could not be added into map: " + err);
        cb(err, false);
    }
}

function deleteValueFromMap(key) {
    try {
        exampleMap.remove(key);
        cb(null, true);
    }
    catch (err) {
        log.error("Value with key " + key + " could not be removed from map: " + err);
        cb(err, false);
    }
}

function getAllValues(cb) {
    try {
        cb(null, exampleMap.values());
    }
    catch (err) {
        log.error("Values could not be getting from map error: " + err);
        cb(err, null);
    }
}

function getAllKeys() {
    try {
        cb(null, exampleMap.keys());
    }
    catch (err) {
        log.error("Keys could not be getting from map error: " + err);
        cb(err, null);
    }
}