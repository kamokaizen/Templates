angular.module('domeshield').controller('CategoryController', function($rootScope, $scope, CallService) {
	
	$scope.deleteMessage = "Do you want to delete this rule?";
	$scope.init = function() {
		$scope.errorMessage = "";
		
		$scope.rules = [];
		$scope.categories = [];
		$scope.action = "add";
		$scope.selectedRule = {};
		$scope.selectedCategories = [];
		
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
			item.ticked = false;
		});
		
		if ($scope.action == 'update') {
			var selectedCategories = $scope.selectedRule.object_id;
			$scope.categories =_.each($scope.categories, function(item) {
				if(item.hasOwnProperty("category_id") && selectedCategories.indexOf(String(item.category_id)) != -1)
					item.ticked = true;
			});
		}
	};
	
	$scope.getRules = function() {
		CallService.get("/api/rules/category/all", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = data;
				$scope.rules = _.each($scope.rules , function(item) {
					item.object_id = item.object_id.split(","); 
				});
			}
		});
	};
	
	$scope.createCategories = function(categories) {
		_.each(categories, function(category) {
			for (var i = 0; i < category.length; i++) {
				var subCategory = category[i];
				if(i == 0)
					$scope.categories.push({name:subCategory.name, msGroup: true});
				
				$scope.categories.push({name:subCategory.category_name, category_id: subCategory.category_id ,ticked: false});
			}
			$scope.categories.push({msGroup: false});		
		});
	};
	
	$scope.getCategories = function() {
		CallService.get("/api/rules/urlgroups/0", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.createCategories(data);
			}
		});
	};
	
	$scope.saveRule = function() {
		
		if($scope.validate() && $scope.selectedCategories.length != 0) {
			$scope.selectedRule.object_id = _.map($scope.selectedCategories, function(obj) { return String(obj.category_id); }); 
			
			CallService.post("/api/rules/category/" + $scope.action, $scope.selectedRule, function(err, data) {
				if (err) {
					// Handle Error
				}else{
					$('#createCategoryRule').modal('hide');
					
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
		CallService.post("/api/rules/category/delete", {ruleId: $scope.selectedRule.id}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = _.without($scope.rules, _.findWhere($scope.rules, {id: $scope.selectedRule.id}));
			}
		}, true);
	};
});