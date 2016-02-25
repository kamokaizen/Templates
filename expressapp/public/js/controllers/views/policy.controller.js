angular.module('domeshield').controller('PolicyController', function($rootScope, $scope, CallService) {
	
	$scope.deleteMessage = "Do you want to delete this policy?";
	$scope.init = function() {
		$scope.errorMessage = "";
		
		$scope.rules = [];
		$scope.selectedNetworks = [];
		$scope.action = "add";
		$scope.selectedRule = {};
		$scope.requirements = {};
		$scope.availableNetworks = [];
		$scope.selectedSecurityRule = [];
		$scope.selectedCategoryRule = [];
		$scope.bwFlag = false;
		$scope.canCreateFlag = false;
		
		$scope.defaultRule = {name: "None", id:-1};
		
		$scope.getRules();
		$scope.getRequirements();
	};
	
	$scope.nextClick = function() {
		if($scope.validate()) {
			$('.nav-tabs > .active').next('li').find('a').trigger('click');
		}
	};
	
	$scope.validate = function () {
       if ($scope.selectedRule.remark && $scope.selectedRule.remark.length > 250)
            $scope.settingsForm.pRemark.$setDirty();
        
        return $scope.settingsForm.pRemark.$valid;
    };
    
    $scope.bwSelected = function() {
    	if($scope.requirements.bwlists) {
    		for(var i = 0; i < $scope.requirements.bwlists.length; i++) {
        		var item = $scope.requirements.bwlists[i];
        		if (item.selected) return true;
        	}
    	}
    	
    	return false;
    	
    };
    
    
    $scope.canCreate = function() {
    	if($scope.bwFlag && !$scope.bwSelected()) {
    		$scope.canCreateFlag = $scope.bwSelected();
    	} else {
    		if(($scope.selectedSecurityRule.length == 0 || ($scope.selectedSecurityRule[0] && $scope.selectedSecurityRule[0].id == -1)) && 
    		   ($scope.selectedCategoryRule.length == 0 || ($scope.selectedCategoryRule[0] && $scope.selectedCategoryRule[0].id == -1)) && 
    		   !$scope.bwSelected())
    			$scope.canCreateFlag = false;
    		else
    			$scope.canCreateFlag = true;
    	}
    };
    
    $scope.getNetworkName = function(id) {
    	var network = _.findWhere($scope.requirements.networks, {id: id}); 
    	
    	if (network)
    		return network.name;
    	
    	return "";
    };
    
	$scope.updateNetworks = function() {
		var usedNetworkIds = _.map($scope.rules, function(item){ return item.policy_networks });
		
		$scope.availableNetworks = _.filter($scope.requirements.networks, function(item){
			if(usedNetworkIds.indexOf(item.id) == -1 || item.id == $scope.selectedRule.policy_networks)
				return item;
		});
		
		$scope.requirements.networks = _.each($scope.requirements.networks, function(item) {
			item.ticked = false;
		});
		
		if ($scope.action == 'update') {
			var selectedNetwork = $scope.selectedRule.policy_networks;
			var item = _.findWhere($scope.requirements.networks, { id: selectedNetwork }); 
			
			if (item)
				item.ticked = true;
		}
	};
	
	$scope.updateSRule = function() {
		$scope.requirements.srules = _.each($scope.requirements.srules, function(item) {
			item.ticked = false;
		});
		
		if ($scope.action == 'update') {
			var selectedSecurityRule = $scope.selectedRule.security_rule_id;
			var item = _.findWhere($scope.requirements.srules, { id: selectedSecurityRule });
			
			if (item)
				item.ticked = true;
		}
	};
	
	$scope.updateCRule = function() {
		$scope.requirements.crules = _.each($scope.requirements.crules, function(item) {
			item.ticked = false;
		});
		
		if ($scope.action == 'update') {
			var selectedCategoryRule = $scope.selectedRule.category_rule_id;
			var item = _.findWhere($scope.requirements.crules, { id: selectedCategoryRule });
			
			if (item)
				item.ticked = true;
		}
	};
	
	$scope.updateBWList = function() {
		$scope.requirements.bwlists = _.each($scope.requirements.bwlists, function(item) {
			item.selected = false;
		});
		
		if ($scope.action == 'update') {
			var selectedBWList = $scope.selectedRule.policy_bw_list;
			$scope.requirements.bwlists =_.each($scope.requirements.bwlists, function(item) {
				if(selectedBWList.indexOf(item.id) != -1)
					item.selected = true;
			});
		}
	};
	
	$scope.updateRequirements = function() {
		$scope.updateNetworks();
		$scope.updateSRule();
		$scope.updateCRule();
		$scope.updateBWList();
	};
	
	$scope.disableDropdowns = function() {
		$scope.requirements.srules = _.each($scope.requirements.srules, function(item) {
			item.ticked = false;
		});
		
		$scope.requirements.crules = _.each($scope.requirements.crules, function(item) {
			item.ticked = false;
		});
		
		$scope.canCreate();
	};
	
	$scope.toggleAction = function(action, object) {
		$scope.action = action;
		$scope.selectedRule = angular.copy(object);
		
		$('.nav-tabs li').first().find('a').trigger('click');
		
		if (action == "add") {
			$scope.selectedRule.remark = "";
			$scope.canCreateFlag = false;
		} else
			$scope.canCreateFlag = true;
		
		if (action != "delete")
			$scope.updateRequirements();

		$scope.bwFlag = $scope.selectedRule.security_rule_id && ($scope.selectedRule.security_rule_id == "" || $scope.selectedRule.security_rule_id == -1) && 
						$scope.selectedRule.category_rule_id && ($scope.selectedRule.category_rule_id == "" || $scope.selectedRule.category_rule_id == -1) ? true : false; 
		
		 
	};
	
	$scope.getRequirements = function() {
		CallService.get("/api/policy/requirements", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.requirements = data;
				$scope.requirements.srules.unshift(angular.copy($scope.defaultRule));
				$scope.requirements.crules.unshift(angular.copy($scope.defaultRule));
			}
		});
	};
	
	$scope.getRules = function() {
		CallService.get("/api/policy/all", {}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = data;
			}
		});
	};

	
	$scope.saveRule = function() {
		
		if($scope.validate()) {
			$scope.selectedRule.policy_networks = $scope.selectedNetworks[0].id;//_.map($scope.selectedNetworks, function(obj) { return obj.id; });
            $scope.selectedRule.policy_network_ip = $scope.selectedNetworks[0].ip;
			$scope.selectedRule.security_rule_id = $scope.selectedSecurityRule.length == 0 ? -1 : $scope.selectedSecurityRule[0].id;
			$scope.selectedRule.category_rule_id = $scope.selectedCategoryRule.length == 0 ? -1 : $scope.selectedCategoryRule[0].id;
			$scope.selectedRule.policy_bw_list = _.map(_.filter($scope.requirements.bwlists, function(item) {
														return item.selected == true;
												 }), function(obj) { return obj.id;});
			
			CallService.post("/api/policy/" + $scope.action, $scope.selectedRule, function(err, data) {
				if (err) {
					// Handle Error
				}else{
					$('#addPolicy').modal('hide');
					
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
		CallService.post("/api/policy/delete", {policyId: $scope.selectedRule.id, 
        security_rule_id: $scope.selectedRule.security_rule_id, 
        category_rule_id: $scope.selectedRule.category_rule_id,
        policy_bw_list: $scope.selectedRule.policy_bw_list, 
        policy_network_ip: _.findWhere($scope.requirements.networks, { id: $scope.selectedRule.policy_networks }).ip}, function(err, data) {
			if (err) {
				// Handle error
			}else{
				$scope.rules = _.without($scope.rules, _.findWhere($scope.rules, {id: $scope.selectedRule.id}));
			}
		}, true);
	};
});