angular.module('domeshield').controller('NetworkController', function($rootScope, $scope, CallService) {
	
	$scope.deleteMessage = "Do you want to delete this network?";
	$scope.ipPattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(/([0-9]|[1-2][0-9]|3[0-2]))?$";
	$scope.init = function() {
		$scope.errorMessage = "";
		
		$scope.rules = [];
		$scope.action = "add";
		$scope.selectedRule = {};
		
		$scope.getRules();
	};
	
	$scope.validate = function () {
        if ($scope.selectedRule.name == undefined || $scope.selectedRule.name == "" || $scope.selectedRule.name.length > 100)
            $scope.settingsForm.pName.$setDirty();

        if ($scope.selectedRule.remark && $scope.selectedRule.remark.length > 250)
            $scope.settingsForm.pRemark.$setDirty();
        
        if ($scope.selectedRule.ip == "")
            $scope.settingsForm.pIp.$setDirty();
        
        return $scope.settingsForm.pName.$valid || $scope.settingsForm.pRemark.$valid;
    };
    
	$scope.toggleAction = function(action, object) {
		$scope.addedDomain = "";
		$scope.action = action;
		$scope.selectedRule = angular.copy(object);	
		
		if (action == "add") {
			$scope.selectedRule.name = "";
			$scope.selectedRule.remark = "";
			$scope.selectedRule.ip = "";
		}
		
		$scope.settingsForm.$setPristine();
        $scope.settingsForm.pName.$setPristine();
        $scope.settingsForm.pRemark.$setPristine();
        $scope.settingsForm.pIp.$setPristine();
        
		if(action == "add")
			$scope.selectedRule.connection_status = "UP";
	};
	
	$scope.isIpUnique = function() {
		if($scope.selectedRule.ip) {
			var selectedIp = $scope.selectedRule.ip.indexOf("/") == -1 ? $scope.selectedRule.ip + "/24" : $scope.selectedRule.ip;
			var usedData = _.findWhere($scope.rules, {ip : selectedIp});
			if (usedData && usedData.id != $scope.selectedRule.id)
				return false;
		}
		return true;
	} ;
	
	$scope.getRules = function() {
		CallService.get("/api/network/all", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = data;
			}
		});
	};
	
	$scope.saveRule = function() {
		if ($scope.validate()) {
			$scope.selectedRule.ip = $scope.selectedRule.ip.indexOf("/") == -1 ? $scope.selectedRule.ip + "/24" : $scope.selectedRule.ip;
			CallService.post("/api/network/" + $scope.action, $scope.selectedRule, function(err, data) {
				if (err) {
					// Handle Error
				}else{
					$('#createNetwork').modal('hide');
					
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
		CallService.post("/api/network/delete", {networkId: $scope.selectedRule.id, networkIp: $scope.selectedRule.ip}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = _.without($scope.rules, _.findWhere($scope.rules, {id: $scope.selectedRule.id}));
			}
		}, true);
	};
});