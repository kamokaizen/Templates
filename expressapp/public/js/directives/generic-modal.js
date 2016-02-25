angular.module('domeshield').directive("modalGeneric", function() {	
	
	var basePath = "/js/partials/"
	
	return{
        scope:false,  
        templateUrl: function(elem,attrs) {
            return basePath + attrs.templatehtml
        }
    }
});
