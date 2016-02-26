var app = angular.module('domeshield', ['ui.router', 'ngRoute', 'ngCookies', 'inform']);

app.config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state('overview', {
            url: "/overview",
            templateUrl: "../templates/views/overview.html",
            controller: "OverviewController"
        });
    	
    // For any unmatched url, send to /home
    $urlRouterProvider.otherwise("/overview");
});

app.config(function ($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });

    $locationProvider.hashPrefix('!');

});

app.run(function ($rootScope, $location, $http, $cookies, CallService, Authentication) {
    $http.defaults.xsrfHeaderName = 'XSRF-TOKEN';
    $http.defaults.xsrfCookieName = 'csrftoken';
});
