angular.module('domeshield').controller('OverviewController', function (Authentication, $rootScope, $scope, CallService) {

    $scope.init = function () {
        $scope.loadIframe();
    };


    $scope.loadIframe = function () {
        $('#indexIframe').attr('src', Authentication.getAnalyzeToken());
    };

});