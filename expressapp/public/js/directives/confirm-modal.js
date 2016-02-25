angular.module('domeshield').directive("modalConfirm", function() {
    return {
        restrict:"EA",
        transclude: true,
        scope: {
        	'id'      : '=',
            'message' : '=',
            'action'  : '&'
        },
        templateUrl: '/js/partials/confirmationModal.html',

        link: function (scope, element, attrs) {
            	return;

            },
    	};
});
