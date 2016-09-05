getTotalVictimNumber();
getTotalNotificationNumber();

function getTotalVictimNumber() {
	$.ajax({
		type : 'GET',
		url : './victim/count',
		data : '',
		dataType : 'json',
		success : function(count) {
			$('#totalVictimDiv').html(count);
		},
		error : function() {
		}
	});
}

function getTotalNotificationNumber() {
	$.ajax({
		type : 'GET',
		url : './cheat/count',
		data : '',
		dataType : 'json',
		success : function(count) {
			$('#totalNotificationDiv').html(count);
		},
		error : function() {
		}
	});
}