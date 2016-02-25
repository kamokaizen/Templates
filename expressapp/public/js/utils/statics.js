var Statics = {
		POLLER_TIMEOUT			   : 30000,
		POLLER_DELAY			   : 3000,
		DEFAULT_SELECTED_UNIT_TYPE : "O",
		DEAFULT_SELECTED_UNIT_ID   : -1,
		DEFAULT_SELECTED_UNIT_NAME : "Default",
		DEAFULT_SELECTED_ORG_ID    : -1,

		DELETE_MESSAGE			   : "Do you want to delete this %val%?",
		START_MESSAGE 			   : "Do you want to start this %val%?",
		STOP_MESSAGE 			   : "Do you want to stop this %val%?",
		CUSTOMER_WARN_MESSAGE      : "There is no available customer in the system, instance cannot be created",
		HOST_WARN_MESSAGE          : "There is no saved host in the system, instance cannot be created",
		REJECT_MESSAGE			   : "Do you want to reject this host?",
		
		INSTANCE_PENDING_MESSAGE   : "Host is unreachable. Operation is pending.",
		UNKNOWN_ERROR_MESSAGE      : "Unexpected error. Please contact the system administrator for assistance."
}

var KeyLabelMap = {
		"host_id"       : "Host",
	    "customer_id"   : "Customer", 
		"customer_name" : "Customer Name",
		"customer_username" : "Username",
		"customer_phone" : "Phone Number",
		"customer_email" : "Email Address",
	    "customer_address" : "Address",
		"host_name" : "Host Name",
		"host_address" : "Address",
		"public_ip" : "Ip Address",
		"instance_name" : "Instance Name",
		"file_size" : "File Size",
		"zip_depth" : "Zip Depth",
	    "email" : "Email",
	    "username":"Username",
	    "password":"New Password",
	    "confirm_password" : "Confirm Password",
	    "phone_number" : "Phone Number",
	    "address" : "Address",
	    "cam_license" : "Cam License",
		"mssp_token" : "Network Token"
}

var ValidateFunctions = {
		"ipAddr" : Validator.validateIp,
		"userName" : Validator.validateUsername,
		"name"	: Validator.validateName,
		"phoneNumber" : Validator.validatePhoneNumber,
		"email" 	: Validator.validateEmailAddress,
		"password"  : Validator.validatePassword			
}

