angular.module('domeshield').controller('MasterPageController', function (Authentication, $rootScope, $scope, $state, $interval, inform, CallService) {
    
	$scope.init = function () {
        $scope.state = $state;
        $scope.heartbeat();
        $scope.username = Authentication.getAuthenticatedAccount().username;
        $scope.dnsAddresses = Authentication.getDnsAddresses().split(",");
    };

    $rootScope.inform = inform;
    
    var stop;
    $scope.heartbeat = function () {
        // Don't start a new heartbeat polling
        if (angular.isDefined(stop)) return;

        stop = $interval(function () {
            CallService.get("/api/mssp/heartbeat", {}, function(err, data) {
			if (err) {
				$scope.stopHeartbeat();
			}
		}, true);
        }, 60 * 1000);
    };

    $scope.stopHeartbeat = function () {
        if (angular.isDefined(stop)) {
            $interval.cancel(stop);
            stop = undefined;
        }
    };
    $scope.$on('$destroy', function () {
        // Make sure that the interval is destroyed too
        $scope.stopHeartbeat();
    });
});