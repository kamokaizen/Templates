var Utils = {
		
		getErrorMessage : function(data) {
				var errorMessage = "";
				var messages = data["message"];
				
				for(key in messages) {
					var subMessages = messages[key];
					if(KeyLabelMap[key])
						errorMessage += KeyLabelMap[key] + ": ";
					
					for (var i = 0; i < subMessages.length; i++)
						errorMessage += subMessages[i] + "\n";
				}
				
				return errorMessage;
		},
		
		editGUIMessage 	: function(message, replaceVal) {
			return message.replace("%val%", replaceVal);
		}
	
};