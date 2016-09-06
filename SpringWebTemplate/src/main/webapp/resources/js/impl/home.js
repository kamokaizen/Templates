var notificationUsers=null;
var notifications=null;
/* Map contains [USER_ID][IMAGE_BASE64]*/
var userImageMap = new Object();
/* Map contains [TYPE_ID][IMAGE_BASE64]*/
var typeImageMap = new Object();
/* Map contains [STATE_ID][IMAGE_BASE64]*/
var stateImageMap = new Object();

var selectedUserId=0;
var notificationUsersPageNumber=1;
var selectedNotificationId=0;
var notificationsPageNumber=1;

var searchModeVictim = false;
var searchPageNumberVictim = 1;

var searchPageNumberNotification = 1;
var searchModeNotification = false;

var files = [];
var fileChanged = false;

var tempSelectedNotificationLiItem = null;
$('.close').click(function() { $('.alert').hide(); })

	getNotificationUsers();
	setTimeout(function () {
		getNotifications();
    }, 500);
	    
	$("#notification_users_listview_component").on("click", "li", function(e){
		e.preventDefault();
		selectedUserId = $(this).attr("id");
		$('#notification_users_listview_component li').removeClass('selected');
	    $(this).addClass('selected');
		resetNotifications();
		getNotifications();
	});
	
	$("#notifications_listview_component").on("click", "li", function(e){
		e.preventDefault();
		selectedNotificationId = $(this).attr("id");
		$('#notifications_listview_component li').removeClass('selected');
	    $(this).addClass('selected');
	});
				
	$('body').on('click', 'a[name="notification_user_delete_button"]', function() {
		$("#user-confirm-delete").modal('show');
	});
	
	$('body').on('click', 'a[name="notification_user_update_button"]', function() {
		var user = getUser(selectedUserId);
		var image = getImage(selectedUserId);
				
	    $('#updateNameField').val(user.notificationUsername);
	    $('#updateSurnameField').val(user.notificationUsersurname);
	    $('#updateUsernameField').val(user.notificationUserusername);
	    $('#updateEmailField').val(user.notificationUseremail);
	    $("#userthumbnail").attr('src', 'data:image/png;base64,' + image +'');
		
		$("#updateVictimModal").modal('show');
	});
	
	$('body').on('click', 'a[name="notification_user_new_button"]', function() {
		$("#newVictimModal").modal('show');
	});
	
	$('body').on('click', 'a[name="notification_delete_button"]', function() {
		$("#notification-confirm-delete").modal('show');
	});
		
	$('body').on('click', 'a[name="notification_new_button"]', function() {
		var user = getUser(selectedUserId);
		
		if(user != null && user != undefined){
			resetValues();
			
		    $('#notificationUserLabel').html(user.notificationUsername + " " + user.notificationUsersurname);
		    $('#notificationMailLabel').html(user.notificationUseremail);
		    getImageForUser(selectedUserId,'notificationUserImage');
		    getTypeImageForUser(user.notificationType,'notificationTypeImage');
			
			$("#newNotificationModal").modal('show');
		}
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
	    previewUserImage(selectedUserId);
	});
	
	$('body').on('click', 'span[name="notification_user_image_preview_button"]', function() {
		var notification = getNotificationItem(selectedNotificationId);
	    previewUserImage(notification.notificationUser);
	});
		
	$('body').on('click', 'span[name="notification_state_button"]', function() {
		createNotificationStatePanel(selectedNotificationId);
	});
	
	$('body').on("change",'input[name="notificationUserimage"]',function(event) {
		files = event.target.files;
		fileChanged = true;
    });
	
	$('body').on("change",'input[name="createNotificationUserimage"]',function(event) {
		files = event.target.files;
		fileChanged = true;
    });
	
	$('#notification_user_pager_button').on('click', function () {
	    var $btn = $(this).button('loading');
	    
		if(searchModeVictim){
			searchPageNumberVictim = searchPageNumberVictim + 1;
			searchVictims($("#searchVictimTextField").val().toLowerCase());
		}
		else{
		    notificationUsersPageNumber = notificationUsersPageNumber+1;
		    getNotificationUsers();
		}
	    
	    setTimeout(function () {
            $btn.button('reset');
        }, 500);
	  });
		
	$('#notifications_pager_button').on('click', function () {
	    var $btn = $(this).button('loading');
	    
		if(searchModeNotification){
			searchPageNumberNotification = searchPageNumberNotification + 1;
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

	$('#searchVictimTextField').keyup(function(){
	   var valThis = $(this).val().toLowerCase();
	    $('#notification_users_listview_component>li').each(function(){
	    	var text =$(this).find('div>div').text().toLowerCase();
	     	(text.indexOf(valThis) == 0) ? $(this).show() : $(this).hide();            
	   });	
	});
	
	$('#btn-victim-search').on('click', function() {
		var $btn = $(this).button('loading');
		resetVictimSearch();
		searchVictims($("#searchVictimTextField").val().toLowerCase());
		setTimeout(function() {
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
		resetNotificationSearch();
		searchNotifications($("#searchNotificationTextField").val().toLowerCase());
		setTimeout(function() {
			$btn.button('reset');
		}, 500);
	});
			
	$('#search-button').on('click', function() {
		var $btn = $(this).button('loading');
		resetNotificationSearch();
		resetVictimSearch();
		searchNotifications($("#searchTextField").val().toLowerCase());
		searchVictims($("#searchTextField").val().toLowerCase());
		setTimeout(function() {
			$btn.button('reset');
		}, 500);
	});
	
function getSelectedNotification(notificationId){
    var len = notifications.length;
    for (var i = 0; i< len; i++) {
    	if(notifications[i].notificationId == notificationId){
    		return notifications[i];
    	}
    }
}

function getNotifications(){
	$.ajax({
		  type: 'GET',
		  url: './cheat/get?uid=' + selectedUserId + '&pn=' + notificationsPageNumber,
		  data: '',
		  dataType: 'json',
		  success: function(jsonData) {
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
		  },
		  error: function() {
		  }
	});
}

function searchNotifications(searchText) {
	$.ajax({
		type : 'GET',
		url : './cheat/search?text=' + searchText + '&pn=' + searchPageNumberNotification,
		data : '',
		dataType : 'json',
		success : function(jsonData) {			
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
		},
		error : function() {
		}
	});
}

function getNotificationUsers(){
	$.ajax({
		  type: 'GET',
		  url: './victim/get?pn='+notificationUsersPageNumber,
		  data: '',
		  dataType: 'json',
		  success: function(jsonData) {
			  if(notificationUsers == null){
				  notificationUsers = jsonData;
				  $('#notification_users_listview_component').empty();
				  setNotificationUsers(notificationUsers.pageResult);
			  }
			  else{
				  notificationUsers.pageResult = $.merge(notificationUsers.pageResult,jsonData.pageResult);
				  notificationUsers.page = jsonData.page;
				  notificationUsers.totalPage = jsonData.totalPage;
				  setNotificationUsers(jsonData.pageResult);
			  }
		  },
		  error: function() {
		  }
	});
}

function searchVictims(searchText) {
	$.ajax({
		type : 'GET',
		url : './victim/search?text=' + searchText + '&pn=' + searchPageNumberVictim,
		data : '',
		dataType : 'json',
		success : function(jsonData) {			
			if (notificationUsers == null) {
				notificationUsers = jsonData;
				$('#notification_users_listview_component').empty();
				setNotificationUsers(notificationUsers.pageResult);
			} else {
				notificationUsers.pageResult = $.merge(notificationUsers.pageResult, jsonData.pageResult);
				notificationUsers.page = jsonData.page;
				notificationUsers.totalPage = jsonData.totalPage;
				setNotificationUsers(jsonData.pageResult);
			}
		},
		error : function() {
		}
	});
}

function refreshVictims(){
	resetNotificationUsers();
	getNotificationUsers();
}

function refreshNotifications(){
	resetNotifications();
	getNotifications();
}

function setNotificationUsers(results){
	$.each( results, function(i, user) {		
		var imageComponentId = "user_img_" + user.notificationUserId;
				
		$('#notification_users_listview_component').append(		
				'<li class="left clearfix" id='+ user.notificationUserId +'>' +
				'<a href="#">' +
				'<span class="chat-img pull-left" name="user_image_preview_button"><img id="'+ imageComponentId +'" src="" alt="" class="img-circle" width="50" height="50"/></span>' +
				'<div class="chat-body clearfix">' +
					'<div class="header">' +
						 '<strong class="primary-font">'+ user.notificationUsername + ' '+ user.notificationUsersurname +'</strong> <small class="pull-right text-muted">'+ user.notificationUserusername +'</small>' +
					'</div>' +
					'<table>' +
						'<tr>' +
							'<td>' +
								'<small class="pull-right text-muted">'+ user.notificationUseremail +'</small>' +
							'</td>' +
						'<tr>' +
					'</table>' +
				'</div>'+
				'</a>' +
				'</li>');
		
		getImageForUser(user.notificationUserId, imageComponentId);
	});
}

function setNotifications(results){	
	$.each( results, function(i, notification) {
		var imageComponentId = "notificaiton_user_img_" + notification.notificationId;
		var typeComponentId = "notificaiton_type_img_" + notification.notificationId;
		var stateComponentId = "notificaiton_state_img_" + notification.notificationId;
				
		$('#notifications_listview_component').append(		
				'<li id='+ notification.notificationId +' class="left clearfix">' +
				'<a href="#">' +
				'<span class="chat-img pull-left" name="notification_user_image_preview_button">'+
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

function previewUserImage(id){
	var image = getImage(id);
	$('#smallModal').find('.modal-header').empty();
	$('#smallModal').find('.modal-body').empty();
	$('#smallModal').find('.modal-header').append('<h4 class="modal-title">Profil Önizleme</h4>');
	$('#smallModal').find('.modal-body').append('<div align="center"><img src="data:image/png;base64,'+ image +'" style=display: block;margin-left: auto;margin-right: auto;"/></div>');
	$("#smallModal").modal('show');
}

function showDialog(header, content){
	  $('#smallModal').find('.modal-body').empty();
	  $('#smallModal').find('.modal-header').empty();
	  $('#smallModal').find('.modal-header').append('<h4 class="modal-title">'+ header +'</h4>');
	  $('#smallModal').find('.modal-body').append(content);
	  $("#smallModal").modal('show');
}

function deleteUser(){
	$("#user-confirm-delete").modal('hide');
	setTimeout(function(){
		$.ajax({
			  type: 'GET',
			  url: './victim/delete?uid=' + selectedUserId,
			  data: '',
			  dataType: 'json',
			  success: function(jsonData) {
				  $('#successDeleteDismissible').show();
				  $('#successDeleteDismissibleStrong').html(jsonData.reason);
				  refreshVictims();
			  },
			  error: function() {
			  }
		});
		}, 100);
}

function deleteNotification(){
	$("#notification-confirm-delete").modal('hide');
	setTimeout(function(){
		$.ajax({
			  type: 'GET',
			  url: './cheat/delete?nid=' + selectedNotificationId,
			  data: '',
			  dataType: 'json',
			  success: function(jsonData) {
				  $('#successDeleteDismissible').show();
				  $('#successDeleteDismissibleStrong').html(jsonData.reason);
				  refreshNotifications();
			  },
			  error: function() {
			  }
		});
		}, 100);
}

function createNotificationStatePanel(notificationId){
	var notificationItem = getNotificationItem(notificationId);
	$.ajax({
		  type: 'GET',
		  url: './cheat/state/get',
		  data: '',
		  dataType: 'json',
		  success: function(states) {
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
		  },
		  error: function() {
		  }
	});
}

function changeNotificationState(stateId, stateValue){
	$.ajax({
		  type: 'GET',
		  url: './cheat/state/change?nid=' +selectedNotificationId + '&sid=' + stateId,
		  data: '',
		  dataType: 'json',
		  success: function(response) {
			  if(response.status == true){
				  $('#stateLabel').text('Mevcut Durum: '+ stateValue);	  
			  }
		  },
		  error: function() {
		  }
	});
}

function updateUser(){
    var oMyForm = new FormData();
    oMyForm.append("notificationUserId", selectedUserId);
    oMyForm.append("notificationUsername", $('#updateNameField').val());
    oMyForm.append("notificationUsersurname", $('#updateSurnameField').val());
    oMyForm.append("notificationUserusername", $('#updateUsernameField').val());
    oMyForm.append("notificationUseremail", $('#updateEmailField').val());
    if(files && files[0] != null || files && files[0] != undefined){
        oMyForm.append("notificationUserimage", files[0]);	
    }
    oMyForm.append("notificationUserimageChanged", fileChanged);
    
    $.ajax({
          url : "./victim/update",
          data : oMyForm,
          type : "POST",
          cache: false,
          contentType: false,
          processData: false,
		  success: function(jsonData) {
			  resetVictimModal();
			  $( "#updateStatusField" ).show();
			  
			  if(jsonData.status == false){
				  $('#updateStatusField').text('Düzenleme Hatası');
				  $('#updateStatusField').addClass('alert-danger');
				  
				  if(jsonData.errors.notificationUsername != null){
					  $('#updateNameField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUsername , 'html': false});
					  $('#updateNameField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUsersurname != null){
					  $('#updateSurnameField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUsersurname , 'html': false});
					  $('#updateSurnameField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUserusername != null){
					  $('#updateUsernameField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUserusername , 'html': false});
					  $('#updateUsernameField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUseremail != null){
					  $('#updateEmailField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUseremail , 'html': false});
					  $('#updateEmailField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUserimage != null){
					  $('#notificationUserimage').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUserimage , 'html': false});
					  $('#notificationUserimage').closest('.form-group').addClass('has-error');
				  }
			  }
			  else{				  
				  resetVictimModal();
				  resetValues();
				  $("#updateVictimModal").modal('hide');
				  $('#successUserUpdateDismissible').show();
				  refreshVictims();
			  }
		  },
		  error: function() {
		  }
      });
}

function createUser(){
    var oMyForm = new FormData();
    oMyForm.append("notificationUsername", $('#createNameField').val());
    oMyForm.append("notificationUsersurname", $('#createSurnameField').val());
    oMyForm.append("notificationUserusername", $('#createUsernameField').val());
    oMyForm.append("notificationUseremail", $('#createEmailField').val());
    if(files && files[0] != null || files && files[0] != undefined){
        oMyForm.append("notificationUserimage", files[0]);	
    }
    
    $.ajax({
          url : "./victim/create",
          data : oMyForm,
          type : "POST",
          cache: false,
          contentType: false,
          processData: false,
		  success: function(jsonData) {
			  resetVictimModal();
			  $( "#createStatusField" ).show();
			  
			  if(jsonData.status == false){
				  $('#createStatusField').text('Kullanıcı oluşturulamadı!');
				  $('#createStatusField').addClass('alert-danger');
				  
				  if(jsonData.errors.notificationUsername != null){
					  $('#createNameField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUsername , 'html': false});
					  $('#createNameField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUsersurname != null){
					  $('#createSurnameField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUsersurname , 'html': false});
					  $('#createSurnameField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUserusername != null){
					  $('#createUsernameField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUserusername , 'html': false});
					  $('#createUsernameField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUseremail != null){
					  $('#createEmailField').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUseremail , 'html': false});
					  $('#createEmailField').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationUserimage != null){
					  $('#createNotificationUserimage').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationUserimage , 'html': false});
					  $('#createNotificationUserimage').closest('.form-group').addClass('has-error');
				  }
			  }
			  else{
				  resetVictimModal();
				  resetValues();
				  $("#newVictimModal").modal('hide');
				  $('#successUserDismissible').show();
				  refreshVictims();
			  }
		  },
		  error: function() {
		  }
      });
}

function createNotification(){
    var oMyForm = new FormData();
    oMyForm.append("notificationUser", selectedUserId);
    oMyForm.append("notificationOs", $('#notificationOsSelection').val());
    oMyForm.append("notificationLocation", $('#notificationLocation').val());
    oMyForm.append("notificationIp", $('#notificationIP').val());
		
    $.ajax({
        url : "./cheat/new",
        data : oMyForm,
        type : "POST",
        cache: false,
        contentType: false,
        processData: false,
		  success: function(jsonData) {
			  resetVictimModal();
			  $( "#createnotificationStatusField" ).show();
			  
			  if(jsonData.status == false){
				  $('#createnotificationStatusField').text('Notifikasyon oluşturulamadı!');
				  $('#createnotificationStatusField').addClass('alert-danger');
				  
				  if(jsonData.errors.notificationLocation != null){
					  $('#notificationLocation').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationLocation , 'html': false});
					  $('#notificationLocation').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationIp != null){
					  $('#notificationIP').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationIp , 'html': false});
					  $('#notificationIP').closest('.form-group').addClass('has-error');
				  }
				  if(jsonData.errors.notificationOs != null){
					  $('#notificationOsSelection').tooltip({'trigger':'focus', 'title': jsonData.errors.notificationOs , 'html': false});
					  $('#notificationOsSelection').closest('.form-group').addClass('has-error');
				  }
			  }
			  else{
				  resetVictimModal();
				  resetValues();
				  $("#newNotificationModal").modal('hide');
				  $('#successNotificationDismissible').show();
				  refreshNotifications();
			  }
		  },
		  error: function() {
		  }
    });
}

function createRandomNotification(){
	  $('#notificationLocation').val(chance.city() + ", " +chance.country({ full: true }));
	  $('#notificationIP').val(chance.ip());
	  $("#notificationOsSelection").val(chance.integer({min: 0, max: 4}));
}

function updateNotification(){
    var oMyForm = new FormData();
    oMyForm.append("notificationId", selectedNotificationId);
    oMyForm.append("notificationOs", $('#updatenotificationOsSelection').val());
    oMyForm.append("notificationDate", $('#updatenotificationDate').val());
    oMyForm.append("notificationLocation", $('#updatenotificationLocation').val());
    oMyForm.append("notificationIp", $('#updatenotificationIP').val());
		
    $.ajax({
        url : "./cheat/update",
        data : oMyForm,
        type : "POST",
        cache: false,
        contentType: false,
        processData: false,
		  success: function(jsonData) {
			  resetVictimModal();
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
				  resetVictimModal();
				  resetValues();
				  $("#updateNotificationModal").modal('hide');
				  $('#successNotificationUpdateDismissible').show();
				  refreshNotifications();
			  }
		  },
		  error: function() {
		  }
    });
}

function updateRandomNotification(){
	  $('#updatenotificationLocation').val(chance.city() + ", " +chance.country({ full: true }));
	  $('#updatenotificationIP').val(chance.ip());
	  $("#updatenotificationOsSelection").val(chance.integer({min: 0, max: 4}));
}

function resetValues(){
	  $('#createNameField').val('');
	  $('#createSurnameField').val('');
	  $('#createUsernameField').val('');
	  $('#createEmailField').val('');
	  $('#createNotificationUserimage').val('');
	  
	  $('#updateNameField').val('');
	  $('#updateSurnameField').val('');
	  $('#updateUsernameField').val('');
	  $('#updateEmailField').val('');
	  
	  $('#notificationLocation').val('');
	  $('#notificationIP').val('');
	  $("#notificationOsSelection").val("-1");
	  
	  $('#updatenotificationDate').val('');
	  $('#updatenotificationLocation').val('');
	  $('#updatenotificationIP').val('');
	  $("#updatenotificationOsSelection").val("-1");
	  
	  files = null;
	  fileChanged = false;
}

function resetVictimModal(){	  
	  $('#createNameField').closest('.form-group').removeClass('has-error');
	  $('#createSurnameField').closest('.form-group').removeClass('has-error');
	  $('#createUsernameField').closest('.form-group').removeClass('has-error');
	  $('#createEmailField').closest('.form-group').removeClass('has-error');
	  $('#createNotificationUserimage').closest('.form-group').removeClass('has-error');
	  
	  $('#createNameField').tooltip("destroy");
	  $('#createSurnameField').tooltip("destroy");
	  $('#createUsernameField').tooltip("destroy");
	  $('#createEmailField').tooltip("destroy");
	  $('#createNotificationUserimage').tooltip("destroy");
	  $('#createStatusField').hide();
	  	  
	  $('#updateNameField').closest('.form-group').removeClass('has-error');
	  $('#updateSurnameField').closest('.form-group').removeClass('has-error');
	  $('#updateUsernameField').closest('.form-group').removeClass('has-error');
	  $('#updateEmailField').closest('.form-group').removeClass('has-error');
	  $('#notificationUserimage').closest('.form-group').removeClass('has-error');
	  
	  $('#updateNameField').tooltip("destroy");
	  $('#updateSurnameField').tooltip("destroy");
	  $('#updateUsernameField').tooltip("destroy");
	  $('#updateEmailField').tooltip("destroy");
	  $('#notificationUserimage').tooltip("destroy");
	  
	  $('#notificationLocation').closest('.form-group').removeClass('has-error');
	  $('#notificationIP').closest('.form-group').removeClass('has-error');
	  $('#notificationOsSelection').closest('.form-group').removeClass('has-error');
	  $('#createnotificationStatusField').hide();
	  
	  $('#notificationLocation').tooltip("destroy");
	  $('#notificationIP').tooltip("destroy");
	  $('#notificationOsSelection').tooltip("destroy");
	  
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

function resetNotifications(){
	notifications = null;
	notificationsPageNumber = 1;
	selectedNotificationId = 0;
	searchModeNotification = false;
	searchPageNumberNotification = 1;
	$('#searchNotificationTextField').val('');
	$('#notifications_listview_component').empty();
}

function resetNotificationSearch(){
	notifications = null;
	searchModeNotification = true;
	searchPageNumberNotification = 1;
	$('#notifications_listview_component').empty();
}

function resetNotificationUsers(){
	notificationUsers = null;
	notificationUsersPageNumber = 1;
	selectedUserId = 0;
	searchModeVictim = false;
	searchPageNumberVictim = 1;
	$('#searchVictimTextField').val('');
	$('#notification_users_listview_component').empty();
}

function resetVictimSearch(){
	notificationUsers = null;
	searchModeVictim = true;
	searchPageNumberVictim = 1;
	$('#notification_users_listview_component').empty();
}

function getImageForUser(userId, imageComponentId){
	var image = getImage(userId);
	if(image != null){		
		$('#'+ imageComponentId).fadeOut(200, function() {
			$('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ image);
        }).fadeIn(400);
	}
	else{
		$.ajax({
			  type: 'GET',
			  url: './victim/image/get?uid=' + userId,
			  data: '',
			  dataType: 'json',
			  success: function(jsonData) {
				  pushImage(jsonData.userId, jsonData.imageBase64);
				  $('#'+ imageComponentId).fadeOut(200, function() {
					  $('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ jsonData.imageBase64);
			        }).fadeIn(400);
			  },
			  error: function() {
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
		$.ajax({
			  type: 'GET',
			  url: './cheat/type/image/get?tid=' + typeId,
			  data: '',
			  dataType: 'json',
			  success: function(jsonData) {
				  pushTypeImage(jsonData.userId, jsonData.imageBase64);
				  $('#'+ imageComponentId).fadeOut(200, function() {
					  $('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ jsonData.imageBase64);
			        }).fadeIn(400);
			  },
			  error: function() {
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
		$.ajax({
			  type: 'GET',
			  url: './cheat/state/image/get?sid=' + stateId,
			  data: '',
			  dataType: 'json',
			  success: function(jsonData) {
				  pushStateImage(jsonData.userId, jsonData.imageBase64);
				  $('#'+ imageComponentId).fadeOut(200, function() {
					  $('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ jsonData.imageBase64);
			        }).fadeIn(400);
			  },
			  error: function() {
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


function getUser(id){
	var returnedUser = null;
	$.each( notificationUsers.pageResult, function(i, user) {
		if(user.notificationUserId == id){
			returnedUser = user;
		}
	});
	return returnedUser;
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