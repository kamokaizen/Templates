angular.module('domeshield').controller('MasterPageController', function (Authentication, $rootScope, $scope, $state, $interval, inform, CallService) {
    
	$scope.init = function () {
        $scope.state = $state;
        $scope.username = Authentication.getAuthenticatedAccount().username;
    };

    $rootScope.inform = inform;
    
    $scope.$on('$destroy', function () {
        // Make sure that the interval is destroyed too
    });
});