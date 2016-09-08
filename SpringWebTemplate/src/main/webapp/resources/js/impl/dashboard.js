getTotalVictimNumber();
getTotalNotificationNumber();

$('.close').click(function() { $('.alert').hide(); })

function getTotalVictimNumber() {
	makeServiceCall('GET', './victim/count', '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
			$('#totalVictimDiv').html(jsonData);
		}
	});
}

function getTotalNotificationNumber() {
	makeServiceCall('GET', './cheat/count', '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
			$('#totalNotificationDiv').html(jsonData);
		}
	});
}