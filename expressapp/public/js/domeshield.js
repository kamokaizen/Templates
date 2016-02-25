var app = angular.module('domeshield', ['ui.router', 'ngRoute', 'ngCookies', 'inform']);

app.config(function($stateProvider, $urlRouterProvider){
	
	
    $stateProvider
    	/*
        .state('analyze', {
    		url: "/analyze",
    		templateUrl: "../templates/views/analyze.html",
            controller: "AnalyzeController"
    	})//*/
	    .state('overview', {
			url: "/overview",
			templateUrl: "../templates/views/overview.html",
	        controller: "OverviewController"
		})
    	.state('setup', {
    		url: "/setup",
    		templateUrl: "../templates/views/setup.html"
    	})
    	.state('configure', {
    		url: "",
    		templateUrl: "../templates/views/configure/configure.html"
    	})
    	.state('configure.policy', {
    		url: "/policy",
    		templateUrl: "../templates/views/configure/configure.policy.html",
    		controller: "PolicyController"
    	})
    	.state('configure.securityRules', {
    		url: "/securityRules",
    		templateUrl: "../templates/views/configure/configure.securityRules.html",
    		controller: "SecurityController"
    	})
    	.state('configure.categoryRules', {
    		url: "/categoryRules",
    		templateUrl: "../templates/views/configure/configure.categoryRules.html",
    		controller: "CategoryController"
    	})
    	.state('configure.bwLists', {
    		url: "/bwLists",
    		templateUrl: "../templates/views/configure/configure.bwLists.html",
    		controller: "BWController"
    	})
    	.state('configure.network', {
    		url: "/network",
    		templateUrl: "../templates/views/configure/configure.network.html",
    		controller: "NetworkController"
    	})
    	
    	// For any unmatched url, send to /analyze
    	$urlRouterProvider.otherwise("/overview")
});

app.config(function( $locationProvider){  
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
