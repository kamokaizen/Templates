angular.module('domeshield').controller('BWController', function($rootScope, $scope, CallService) {
	
	$scope.deleteMessage = "Do you want to delete this rule?";
	$scope.domainPattern = new RegExp(/^([a-z0-9]+(-[a-z0-9]+)*\.)+[a-z]{2,}$/);
	$scope.init = function() {
		$scope.errorMessage = "";
		$scope.addedDomain = "";
		$scope.rules = [];
		$scope.categories = [];
		$scope.action = "add";
		$scope.selectedRule = {};
		
		$scope.getRules();
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
		$scope.addedDomain = "";
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
        $scope.settingsForm.domainname.$setPristine();
        
		if(action == "add") {
			$scope.selectedRule.domain_list = [];
			$scope.selectedRule.list_type = 0;
		}
			
	};
	
	$scope.getRules = function() {
		CallService.get("/api/rules/bwList/all", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = data;
				_.each($scope.rules, function(item) {
					item.domain_list = item.domain_list.split(",");
				})
			}
		});
	};
	
	$scope.saveRule = function() {
		if($scope.validate() && $scope.selectedRule.domain_list.length != 0) {
			CallService.post("/api/rules/bwList/" + $scope.action, $scope.selectedRule, function(err, data) {
				if (err) {
					// Handle Error
				}else{
					$('#createBWList').modal('hide');
					
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
		CallService.post("/api/rules/bwList/delete", {ruleId: $scope.selectedRule.id}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = _.without($scope.rules, _.findWhere($scope.rules, {id: $scope.selectedRule.id}));
			}
		}, true);
	};
	
	$scope.deleteDomain = function(domainName) {
		$scope.selectedRule.domain_list = _.without($scope.selectedRule.domain_list, domainName);
	};
	
	$scope.addDomain = function(args) {
		if (args.currentTarget.className.indexOf("disabled") !== -1) {
            return;
        }
		
		if($scope.selectedRule.domain_list.indexOf($scope.addedDomain) == -1 && $scope.addedDomain != "")
			$scope.selectedRule.domain_list.push($scope.addedDomain);
		
		$scope.addedDomain = "";
		$scope.settingsForm.domainname.$setPristine();
	};
});