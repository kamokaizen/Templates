var notifications=null;

/* Map contains [USER_ID][IMAGE_BASE64]*/
var userImageMap = new Object();

/* Map contains [TYPE_ID][IMAGE_BASE64]*/
var typeImageMap = new Object();

/* Map contains [STATE_ID][IMAGE_BASE64]*/
var stateImageMap = new Object();

var selectedNotificationId=0;
var notificationsPageNumber=1;
var searchPageNumber = 1;
var searchMode = false;

$('.close').click(function() { $('.alert').hide(); })

$("#notifications_listview_component").on("click", "li", function(e){
	e.preventDefault();
	selectedNotificationId = $(this).attr("id");
	$('#notifications_listview_component li').removeClass('selected');
    $(this).addClass('selected');
});

$('body').on('click', 'a[name="notification_preview_button"]', function() {
    previewEmail(selectedNotificationId);
});

$('body').on('click', 'a[name="notification_send_button"]', function() {
	checkSentStatus(selectedNotificationId);
});

$('body').on('click', 'a[name="notification_delete_button"]', function() {
	$("#notification-confirm-delete").modal('show');
});

$('body').on('click', 'a[name="notification_update_button"]', function() {
	var notification = getNotificationItem(selectedNotificationId);
	
	if(notification != null && notification != undefined){
		resetValues();
		
	    $('#updatenotificationUserLabel').html(notification.notificationUserString);
	    $('#updatenotificationMailLabel').html(notification.notificationEmailString);
	    getImageForUser(notification.notificationUser,'updatenotificationUserImage');
	    getTypeImageForUser(notification.notificationType,'updatenotificationTypeImage');
		
	    $('#updatenotificationDate').val(notification.notificationDate);
	    $('#updatenotificationLocation').val(notification.notificationLocation);
	    $('#updatenotificationIP').val(notification.notificationIp);
	    $('#updatenotificationOsSelection').val(notification.notificationOs);
	    
		$("#updateNotificationModal").modal('show');
	}
});

$('body').on('click', 'span[name="user_image_preview_button"]', function() {
    previewUserImage(selectedNotificationId);
});

$('body').on('click', 'span[name="notification_state_button"]', function() {
	createNotificationStatePanel(selectedNotificationId);
});

$('#notifications_pager_button').on('click', function () {
    var $btn = $(this).button('loading');
    
	if(searchMode){
		searchPageNumber = searchPageNumber + 1;
		searchNotifications($("#searchNotificationTextField").val().toLowerCase());
	}
	else{
	    notificationsPageNumber = notificationsPageNumber+1;
	    getNotifications();	
	}
    
    setTimeout(function () {
        $btn.button('reset');
    }, 500);
});

$('#searchNotificationTextField').keyup(function(){
	   var valThis = $(this).val().toLowerCase();
	    $('#notifications_listview_component>li').each(function(){
			var text =$(this).find('div>table').text().toLowerCase();
	     	(text.indexOf(valThis) == 0) ? $(this).show() : $(this).hide();            
	   });	
});

$('#btn-notification-search').on('click', function() {
	var $btn = $(this).button('loading');
	resetSearch();
	searchNotifications($("#searchNotificationTextField").val().toLowerCase());
	setTimeout(function() {
		$btn.button('reset');
	}, 500);
});

$('#search-button').on('click', function() {
	var $btn = $(this).button('loading');
	resetSearch();
	searchNotifications($("#searchTextField").val().toLowerCase());
	setTimeout(function() {
		$btn.button('reset');
	}, 500);
});

getNotifications();

function getNotifications(){
	makeServiceCall('GET', './get?pn=' + notificationsPageNumber, '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
			  if(notifications == null){
				  notifications = jsonData;
				  $('#notifications_listview_component').empty();
				  setNotifications(notifications.pageResult);
			  }
			  else{
				  notifications.pageResult = $.merge(notifications.pageResult,jsonData.pageResult);
				  notifications.page = jsonData.page;
				  notifications.totalPage = jsonData.totalPage;
				  setNotifications(jsonData.pageResult);
			  }
		}
	});
}

function searchNotifications(searchText) {
	makeServiceCall('GET', './search?text=' + searchText + '&pn=' + searchPageNumber, '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
			if (notifications == null) {
				notifications = jsonData;
				$('#notifications_listview_component').empty();
				setNotifications(notifications.pageResult);
			} else {
				  notifications.pageResult = $.merge(notifications.pageResult,jsonData.pageResult);
				  notifications.page = jsonData.page;
				  notifications.totalPage = jsonData.totalPage;
				  setNotifications(jsonData.pageResult);
			}
		}
	});
}

function deleteNotification(){
	$("#notification-confirm-delete").modal('hide');
	setTimeout(function(){
		makeServiceCall('GET', './delete?nid=' + selectedNotificationId, '', 'json', function(err, jsonData){
			if(err){
				$('#failDismissible').show();
				$('#failDismissibleStrong').html(err);
			}
			else{
				  $('#successDeleteDismissible').show();
				  $('#successDeleteDismissibleStrong').html(jsonData.reason);
				  refreshNotifications();
			}
		});
		}, 100);
}

function updateNotification(){
    var formData = new FormData();
    formData.append("notificationId", selectedNotificationId);
    formData.append("notificationOs", $('#updatenotificationOsSelection').val());
    formData.append("notificationDate", $('#updatenotificationDate').val());
    formData.append("notificationLocation", $('#updatenotificationLocation').val());
    formData.append("notificationIp", $('#updatenotificationIP').val());
	
	makeMultipartFormDataCall("POST", "./update", formData, function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
			  resetModal();
			  $( "#updateNotificationStatusField" ).show();
			  
			  if(jsonData.status == false){
				  $('#updateNotificationStatusField').text('Notifikasyon güncellenemedi!');
				  $('#updateNotificationStatusField').addClass('alert-danger');
				  
				  if(jsonData.errors.notificationState != null){
					  $('#updateNotificationStatusField').text(jsonData.errors.notificationState);
				  }
				  
				  if(jsonData.errors.notificationDate != null){
					  $('#updatenotificationDate').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationDate , 'html': false});
					  $('#updatenotificationDate').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationLocation != null){
					  $('#updatenotificationLocation').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationLocation , 'html': false});
					  $('#updatenotificationLocation').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationIp != null){
					  $('#updatenotificationIP').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationIp , 'html': false});
					  $('#updatenotificationIP').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationOs != null){
					  $('#updatenotificationOsSelection').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationOs , 'html': false});
					  $('#updatenotificationOsSelection').closest('.form-group').addClass('has-error');
				  }
			  }
			  else{
				  resetModal();
				  resetValues();
				  $("#updateNotificationModal").modal('hide');
				  $('#successNotificationUpdateDismissible').show();
				  refreshNotifications();
			  }
		}
	});
}

function updateRandomNotification(){
	  $('#updatenotificationLocation').val(chance.city() + ", " +chance.country({ full: true }));
	  $('#updatenotificationIP').val(chance.ip());
	  $("#updatenotificationOsSelection").val(chance.integer({min: 0, max: 4}));
}

function setNotifications(results){	
	$.each( results, function(i, notification) {
		var imageComponentId = "notificaiton_user_img_" + notification.notificationId;
		var typeComponentId = "notificaiton_type_img_" + notification.notificationId;
		var stateComponentId = "notificaiton_state_img_" + notification.notificationId;
		
		$('#notifications_listview_component').append(		
		'<li id='+ notification.notificationId +' class="left clearfix">' +
		'<a href="#">' +
		'<span class="chat-img pull-left" name="user_image_preview_button">'+
		'<table>' +
			'<tr>' +
				'<td>' +
					'<img id="'+ imageComponentId +'" src="" class="img-circle" width="50" height="50" />'+
				'</td>' +
			'<tr>' +
			'<tr>' +
				'<td>' +
					'<img id="'+ typeComponentId +'" style="margin-top:5px;"  src="" class="img-circle" width="25" height="25" />'+
				'</td>' +
			'<tr>' +
		'</table>' +
		'</span>' +
		'<div class="chat-body clearfix">' +
			'<div>' +
				 '<span class="chat-img pull-left" name="notification_state_button"><img id="'+ stateComponentId +'" src="" class="img-circle" width="20" height="20" /></span>' +
				 '<strong class="primary-font" style="margin-left:5px;">'+ notification.notificationActionString +'</strong>' +
			'</div>' +
			'<div class="pull-left">' +
			'<table>' +
				'<tr>' +
					'<td>' +
						'<small class="text-muted">'+ notification.notificationUserString +'</small></br>' +
						'<small class="text-muted">'+ notification.notificationLocation +'</small></br>' +
						'<small class="text-muted">'+ notification.notificationIp +'</small></br>' +
						'<small class="text-muted">'+ notification.notificationOsString +'</small></br>' +
						'<small class="text-muted">'+ notification.notificationDate +'</small></br>' +
					'</td>' +
				'<tr>' +
			'</table>' +
			'</div>' +
		'</div>'+
		'</a>' +
		'</li>');
		
		getImageForUser(notification.notificationUser, imageComponentId);
		getTypeImageForUser(notification.notificationType, typeComponentId);
		getStateImageForUser(notification.notificationState, stateComponentId);
	});
}

function createNotificationStatePanel(notificationId){
	var notificationItem = getNotificationItem(notificationId);
	makeServiceCall('GET', './state/get', '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
		  var stateDiv = '<div class="btn-group.btn-group-justified" role="group" aria-label="">';
		  stateDiv = stateDiv + '<h3 align="center"><span id="stateLabel" class="label label-danger">Mevcut Durum: '+  notificationItem.notificationStateString +'</span></h3>';
		  $.each( states, function(i, state) {
			  if(state.value == notificationItem.notificationState ){
				  stateDiv = stateDiv + '<button onClick=changeNotificationState("'+ state.value +'","' + state.name + '") class="btn btn-primary btn-lg btn-block"><span class="glyphicon glyphicon-ok-circle"></span> '+ state.name  +'</button>'; 
			  }
			  else{
				  stateDiv = stateDiv + '<button onClick=changeNotificationState("'+ state.value +'","' + state.name + '") class="btn btn-default btn-lg btn-block"><span class="glyphicon glyphicon-ok-circle"></span> '+ state.name  +'</button>';
			  }
		  });
		  stateDiv = stateDiv + '</div>';
		  showDialog('Notifikasyon Durumu', stateDiv);
		}
	});
}

function changeNotificationState(stateId, stateValue){
	makeServiceCall('GET', './state/change?nid=' +selectedNotificationId + '&sid=' + stateId, '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
		  if(response.status == true){
			  $('#stateLabel').text('Mevcut Durum: '+ jsonData);	  
		  }
		}
	});
}

function getImageForUser(userId, imageComponentId){
	var image = getImage(userId);
	if(image != null){		
		$('#'+ imageComponentId).fadeOut(200, function() {
			$('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ image);
        }).fadeIn(400);
	}
	else{
		makeServiceCall('GET', './image/get?uid=' + userId, '', 'json', function(err, jsonData){
			if(err){
				$('#failDismissible').show();
				$('#failDismissibleStrong').html(err);
			}
			else{
			  pushImage(jsonData.userId, jsonData.imageBase64);
			  $('#'+ imageComponentId).fadeOut(200, function() {
				  $('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ jsonData.imageBase64);
		        }).fadeIn(400);
			}
		});
	}	
}

function getTypeImageForUser(typeId, imageComponentId){
	var image = getTypeImage(typeId);
	if(image != null){		
		$('#'+ imageComponentId).fadeOut(200, function() {
			$('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ image);
        }).fadeIn(400);
	}
	else{
		makeServiceCall('GET', './image/type/get?tid=' + typeId, '', 'json', function(err, jsonData){
			if(err){
				$('#failDismissible').show();
				$('#failDismissibleStrong').html(err);
			}
			else{
			  pushTypeImage(jsonData.userId, jsonData.imageBase64);
			  $('#'+ imageComponentId).fadeOut(200, function() {
				  $('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ jsonData.imageBase64);
		        }).fadeIn(400);
			}
		});
	}	
}

function getStateImageForUser(stateId, imageComponentId){
	var image = getStateImage(stateId);
	if(image != null){		
		$('#'+ imageComponentId).fadeOut(200, function() {
			$('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ image);
        }).fadeIn(400);
	}
	else{
		makeServiceCall('GET', './image/state/get?sid=' + stateId, '', 'json', function(err, jsonData){
			if(err){
				$('#failDismissible').show();
				$('#failDismissibleStrong').html(err);
			}
			else{
			  pushStateImage(jsonData.userId, jsonData.imageBase64);
			  $('#'+ imageComponentId).fadeOut(200, function() {
				  $('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ jsonData.imageBase64);
		        }).fadeIn(400);
			}
		});
	}	
}

function getImage(userId) {
    return userImageMap[userId];
}

function pushImage(userId, value) {
    userImageMap[userId] = value;
}

function getTypeImage(userId) {
    return typeImageMap[userId];
}

function pushTypeImage(userId, value) {
	typeImageMap[userId] = value;
}

function getStateImage(stateId) {
    return stateImageMap[stateId];
}

function pushStateImage(stateId, value) {
	stateImageMap[stateId] = value;
}

function getNotificationItem(id){
	var returnedNotification = null;
	$.each( notifications.pageResult, function(i, notification) {
		if(notification.notificationId == id){
			returnedNotification = notification;
		}
	});
	return returnedNotification;
}

function showDialog(header, content){
	  $('#smallModal').find('.modal-body').empty();
	  $('#smallModal').find('.modal-header').empty();
	  $('#smallModal').find('.modal-header').append('<h4 class="modal-title">'+ header +'</h4>');
	  $('#smallModal').find('.modal-body').append(content);
	  $("#smallModal").modal('show');
}

function refreshNotifications(){
	resetNotifications();
	getNotifications();
}

function resetNotifications(){
	notifications = null;
	notificationsPageNumber = 1;
	selectedNotificationId = 0;
	searchPageNumber = 1;
	searchMode = false;
	$('#notifications_listview_component').empty();
	$('#searchNotificationTextField').val('');
}

function resetSearch(){
	searchMode = true;
	notifications = null;
	searchPageNumber = 1;
	$('#notifications_listview_component').empty();
}

function resetValues(){	  
	  $('#updatenotificationDate').val('');
	  $('#updatenotificationLocation').val('');
	  $('#updatenotificationIP').val('');
	  $("#updatenotificationOsSelection").val("-1");
}

function resetModal(){	  	  
	  $('#updatenotificationOsSelection').tooltip("destroy");
	  $('#updatenotificationDate').tooltip("destroy");
	  $('#updatenotificationLocation').tooltip("destroy");
	  $('#updatenotificationIP').tooltip("destroy");
	  
	  $('#updatenotificationOsSelection').closest('.form-group').removeClass('has-error');
	  $('#updatenotificationDate').closest('.form-group').removeClass('has-error');
	  $('#updatenotificationLocation').closest('.form-group').removeClass('has-error');
	  $('#updatenotificationIP').closest('.form-group').removeClass('has-error');
	  $('#updateNotificationStatusField').hide();
}

function previewUserImage(id){
	var notification = getNotificationItem(id);
	var image = getImage(notification.notificationUser);
	$('#smallModal').find('.modal-header').empty();
	$('#smallModal').find('.modal-body').empty();
	$('#smallModal').find('.modal-header').append('<h4 class="modal-title">Profil Önizleme</h4>');
	$('#smallModal').find('.modal-body').append('<div align="center"><img src="data:image/png;base64,'+ image +'" style=display: block;margin-left: auto;margin-right: auto;"/></div>');
	$("#smallModal").modal('show');
}