angular.module('domeshield').factory('Authentication', function (CallService, $cookies, $rootScope) {

	return {
		login: function (url, params, callback) {
			CallService.post(url, params, callback);
		},
		logout: function (url, params, callback) {
			CallService.post(url, params, callback);
		},
        changePassword: function (url, params, callback) {
            CallService.post(url, params, callback);
        },
        getAnalyzeToken: function () {
            var token = $cookies.get("analyzeToken");
			if (token) {
			    return token;
			}
            return "token-undefined";
		},
		getAuthenticatedAccount: function () {
			if (!$cookies.getObject("authenticatedAccount")) {
			    return;
			}

			return $cookies.getObject("authenticatedAccount");
		},
		getDnsAddresses: function() {
			var addresses = $cookies.get("dnsAddresses");
			if (addresses) {
			    return addresses;
			}
            return [];
		},
		isAuthenticated: function () {
			return !!$cookies.getObject("authenticatedAccount");
		},
		isAdmin: function () {
			var account = this.getAuthenticatedAccount();
			if(account && account.isAdmin)
				return true;
			
			return false;
		},
		setAuthenticatedAccount: function (account) {
			var cookieAccount = {};
			cookieAccount.username = account;
			
			$cookies.put("authenticatedAccount", JSON.stringify(cookieAccount));
		},
		setAnalyzeToken: function (token) {
			$cookies.put("analyzeToken", token);
		},
		setDnsAddresses: function (addresses) {
			$cookies.put("dnsAddresses", addresses);
		},
		unauthenticate: function () {
			delete $cookies.remove("authenticatedAccount");
		}
	};
});