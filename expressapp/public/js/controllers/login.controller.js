angular.module('domeshield').controller('LoginController', function(Authentication, $rootScope, $location, $scope) {
	
	$scope.errorMessage = "";
	
	$scope.init = function() {
		$scope.loginPreloaderFlag = false;
	};
	
	$scope.getMessage = function() {
		return $scope.errorMessage;
	};
	
	$scope.login = function() {
		$scope.errorMessage = "";
		$scope.loginPreloaderFlag = true;
		Authentication.login("/signin", {username: $scope.username, password: $scope.password}, 
				function (err, data) {
                    data = err ? err : data;
					if((data && !data.result) || (err && !err.result))  {
						$scope.errorMessage = data.message;
						$scope.loginPreloaderFlag = false;
					} else {
						Authentication.setAuthenticatedAccount($scope.username);
                        Authentication.setAnalyzeToken(data.analyzeToken);    
                        Authentication.setDnsAddresses(data.dnsAddresses);    
						window.location = data.redirect;
					}
				}
		);
	};
	
	$scope.logout = function() {
		Authentication.logout("/signout", {}, 
				function (err, data) {
					if(err) {
						console.error('Cannot Logout');
					}
					else {
						Authentication.unauthenticate();
					    window.location = '/';
					}
				}
		);
	};
	
    $scope.resetModel = function() {
        $scope.errorMessage = "";
    };
	
    /******** Modal Close Event ********/
	$scope.resetModalParams = function(modalId)
    {
		 $scope.resetModel();
    	//$scope.idFunctionMap[modalId]();
    };

});