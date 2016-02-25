var Validator = {
		validateIp : function(ipAddress) {
			
		},
		
		validateUsername : function(username) {
			
		},
		
		validateName : function(name) {
			return "Valid\n";
		},
		
		validatePhoneNumber : function(phoneNumber) {
			
		},
		
		validateEmailAddress : function(email) {
			
		},
		
		validatePassword : function(password) {
			
		},
		
		validateInteger : function(value) {
			
		},
		
		validateString : function(value) {
			
		},
		
		checkValues : function(object) {
			var errorMessages = "";
			
			for (key in object) {
				errorMessages += ValidateFunctions[key](object[key]);
			}
			
			return errorMessages;
		}	
};




