angular.module('domeshield').factory('CallService', function ($http, $rootScope, inform) {
	
	var timeout = 60 * 5 * 1000;
    function completeRequest(data, err, status, callback, heartbeatFlag) {
    	if (err && !err.status && (status == 401 || status == 403 ))
			window.location = "/login";
		else {
			err = status == -1 ? { result: false, message: 'Something went wrong. Please try again.' } : err;
			if (err) {
	            var message = (err.message) ? err.message : "Something went wrong during service call";
	            
	            if(typeof message === 'string') {
	            	inform.add(message, {
		                ttl: 5000, type: 'danger'
		            });
	            } else {
	            	_.each(message, function(msg) {
		            	inform.add(msg, {
			                ttl: 5000, type: 'danger'
			            });
		            });
	            }   
	        }
	        
	        if (err) {
	            callback(err, null);
	        } else {
	        	callback(null, data);
	        }
	        
	        if(heartbeatFlag)
	        	return;
	        
	        $rootScope.postLoadingFlag = false;
	        $rootScope.postLoadingDeleteFlag = false;
		}
    };
    
	function checkLogin (err, status, callback) {
		if (!err.status && (status == 401 || status == 403 ))
			window.location = "/login";
		else
			callback(err, null);
	};

	return {
		get: function (url, params, callback, heartbeatFlag) {
			$http.get(url, {
				params: params,
				timeout: timeout
			}).success(function (data, status) {
				completeRequest(data, null, status, callback, heartbeatFlag);
			}).error(function (err, status) {
				completeRequest(null, err, status , callback, heartbeatFlag);
			});
		},
		post: function (url, data, callback, deleteFlag) {
			$rootScope.postLoadingFlag = true;
			
			if(deleteFlag)
				$rootScope.postLoadingDeleteFlag = true;
			
			$http.post(url, data, {timeout: timeout})
			.success(function (data, status) {
				completeRequest(data, null, status, callback);
			}).error(function (err, status) {
				completeRequest(null, err, status , callback);
			});
		}
	};
});
