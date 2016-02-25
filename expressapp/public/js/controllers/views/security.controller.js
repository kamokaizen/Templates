angular.module('domeshield').controller('SecurityController', function($rootScope, $scope, CallService) {
	
	$scope.deleteMessage = "Do you want to delete this rule?";
	$scope.init = function() {
		$scope.errorMessage = "";
		
		$scope.rules = [];
		$scope.categories = [];
		$scope.action = "add";
		$scope.selectedRule = {};
		
		$scope.getRules();
		$scope.getCategories();
		
	};
	
	$scope.nextClick = function() {
		if($scope.validate()) {
			$('.nav-tabs > .active').next('li').find('a').trigger('click');
		}
	};
	
	$scope.validate = function () {
        if ($scope.selectedRule.name == undefined || $scope.selectedRule.name == "" || $scope.selectedRule.name.length > 100)
            $scope.settingsForm.pName.$setDirty();

        if ($scope.selectedRule.remark.length > 250)
            $scope.settingsForm.pRemark.$setDirty();
        
        return $scope.settingsForm.pName.$valid || $scope.settingsForm.pRemark.$valid;
    };
    
	$scope.toggleAction = function(action, object) {
		$scope.displayWarning = true;
		$scope.action = action;
		$scope.selectedRule = angular.copy(object);
		
		$('.nav-tabs li').first().find('a').trigger('click');
		
		if (action == "add") {
			$scope.selectedRule.name = "";
			$scope.selectedRule.remark = "";
		}
		
		$scope.settingsForm.$setPristine();
        $scope.settingsForm.pName.$setPristine();
        $scope.settingsForm.pRemark.$setPristine();
        
        
		if (action != "delete")
			$scope.updateCategories();
	};
	
	$scope.updateCategories = function() {
		$scope.categories = _.each($scope.categories, function(item) {
			item.selected = false;
		});
		
		if ($scope.action == 'update') {
			var selectedCategories = $scope.selectedRule.object_id;
			$scope.categories =_.each($scope.categories, function(item) {
				if(selectedCategories.indexOf(String(item.id)) != -1)
					item.selected = true;
			});
		}
	};
	
	$scope.getRules = function() {
		CallService.get("/api/rules/security/all", {}, function(err, data) {
			if (err) {
				$scope.errorMessage = data.message;
			}else{
				$scope.rules = data;
			}
		});
	};
	
	$scope.getCategories = function() {
		CallService.get("/api/rules/security/objects/all", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.categories = data;
			}
		});
	};
	
	$scope.saveRule = function() {
		
		if ($scope.validate()) {
			$scope.selectedRule.object_id = _.map(_.filter($scope.categories, function(item){
													return item.selected == true;
												}), function(obj) { return String(obj.id); }); 
			
			CallService.post("/api/rules/security/" + $scope.action, $scope.selectedRule, function(err, data) {
				if (err) {
					// Handle Error
				}else{
					$('#createSecurityRule').modal('hide');
					
					if ($scope.action == "add") {
						var insertRule = angular.copy($scope.selectedRule);
						insertRule.id = data;
						$scope.rules.push(insertRule);
					}
					else {
						_.extend(_.findWhere($scope.rules, { id: $scope.selectedRule.id }), $scope.selectedRule);
					}
				}
			});
		}
	};
	
	$scope.deleteRule = function() {
		CallService.post("/api/rules/security/delete", {ruleId: $scope.selectedRule.id}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = _.without($scope.rules, _.findWhere($scope.rules, {id: $scope.selectedRule.id}));
			}
		}, true);
	};
});