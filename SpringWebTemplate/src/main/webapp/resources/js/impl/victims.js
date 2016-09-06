var notificationUsers = null;

/* Map contains [USER_ID][IMAGE_BASE64] */
var userImageMap = new Object();
/* Map contains [TYPE_ID][IMAGE_BASE64]*/
var typeImageMap = new Object();

var selectedUserId = 0;
var notificationUsersPageNumber = 1;
var searchPageNumber = 1;
var searchMode = false;

var files = [];
var fileChanged = false;

var tempSelectedNotificationUserLiItem = null;

var nameerrorHtml = $('#nameerrorValue').val();
var surnameerrorHtml = $('#surnameerrorValue').val();
var usernameerrorHtml = $('#usernameerrorValue').val();
var emailerrorHtml = $('#emailerrorValue').val();
var imageerrorHtml = $('#imageerrorValue').val();

$('.close').click(function() { $('.alert').hide(); })

$('#nameField').tooltip({
	'trigger' : 'focus',
	'title' : nameerrorHtml,
	'html' : true
});
$('#surnameField').tooltip({
	'trigger' : 'focus',
	'title' : surnameerrorHtml,
	'html' : true
});
$('#usernameField').tooltip({
	'trigger' : 'focus',
	'title' : usernameerrorHtml,
	'html' : true
});
$('#emailField').tooltip({
	'trigger' : 'focus',
	'title' : emailerrorHtml,
	'html' : true
});
$('#imageField').tooltip({
	'trigger' : 'focus',
	'title' : imageerrorHtml,
	'html' : true
});

getVictims();

$('body').on('click','a[name="notification_user_delete_button"]',function() {
	$("#user-confirm-delete").modal('show');
});

$('body').on('click', 'a[name="notification_user_update_button"]', function() {
	resetVictimModal();
	resetValues();
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
	resetVictimModal();
	resetValues();
	$("#newVictimModal").modal('show');
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

$('body').on('click', 'span[name="user_image_preview_button"]', function() {
	previewUserImage(selectedUserId);
});

$('body').on("change",'input[name="notificationUserimage"]',function(event) {
	files = event.target.files;
	fileChanged = true;
});

$('body').on("change",'input[name="createNotificationUserimage"]',function(event) {
	files = event.target.files;
	fileChanged = true;
});

$("#notification_users_listview_component").on("click", "li", function(e) {
	e.preventDefault();
	selectedUserId = $(this).attr("id");
	$('#notification_users_listview_component li').removeClass('selected');
    $(this).addClass('selected');
});

$('#notification_user_pager_button').on('click', function() {
	var $btn = $(this).button('loading');

	if(searchMode){
		searchPageNumber = searchPageNumber + 1;
		searchVictims($("#searchVictimTextField").val().toLowerCase());
	}
	else{
		notificationUsersPageNumber = notificationUsersPageNumber + 1;
		getVictims();	
	}
	
	setTimeout(function() {
		$btn.button('reset');
	}, 500);
});

$('#searchVictimTextField').keyup(function() {
	var valThis = $(this).val().toLowerCase();
	$('#notification_users_listview_component>li').each(function() {
		var text = $(this).find('div>div').text().toLowerCase();
		(text.indexOf(valThis) == 0) ? $(this).show() : $(this).hide();
	});
});

$('#btn-victim-search').on('click', function() {
	var $btn = $(this).button('loading');
	resetSearch();
	searchVictims($("#searchVictimTextField").val().toLowerCase());
	setTimeout(function() {
		$btn.button('reset');
	}, 500);
});

$('#search-button').on('click', function() {
	var $btn = $(this).button('loading');
	resetSearch();
	searchVictims($("#searchTextField").val().toLowerCase());
	setTimeout(function() {
		$btn.button('reset');
	}, 500);
});

function getVictims() {
	$.ajax({
		type : 'GET',
		url : './get?pn=' + notificationUsersPageNumber,
		data : '',
		dataType : 'json',
		success : function(jsonData) {
			if (notificationUsers == null) {
				notificationUsers = jsonData;
				$('#notification_users_listview_component').empty();
				setVictims(notificationUsers.pageResult);
			} else {
				notificationUsers.pageResult = $.merge(
						notificationUsers.pageResult, jsonData.pageResult);
				notificationUsers.page = jsonData.page;
				notificationUsers.totalPage = jsonData.totalPage;
				setVictims(jsonData.pageResult);
			}
		},
		error : function() {
		}
	});
}

function searchVictims(searchText) {
	$.ajax({
		type : 'GET',
		url : './search?text=' + searchText + '&pn=' + searchPageNumber,
		data : '',
		dataType : 'json',
		success : function(jsonData) {			
			if (notificationUsers == null) {
				notificationUsers = jsonData;
				$('#notification_users_listview_component').empty();
				setVictims(notificationUsers.pageResult);
			} else {
				notificationUsers.pageResult = $.merge(notificationUsers.pageResult, jsonData.pageResult);
				notificationUsers.page = jsonData.page;
				notificationUsers.totalPage = jsonData.totalPage;
				setVictims(jsonData.pageResult);
			}
		},
		error : function() {
		}
	});
}

function setVictims(results) {
	$
			.each(
					results,
					function(i, user) {
						var imageComponentId = "user_img_"
								+ user.notificationUserId;

						$('#notification_users_listview_component')
								.append(
										'<li class="left clearfix" id='
												+ user.notificationUserId
												+ '>'
												+ '<a href="#">'
												+ '<span class="chat-img pull-left" name="user_image_preview_button"><img id="'
												+ imageComponentId
												+ '" src="" alt="" class="img-circle" width="50" height="50"/></span>'
												+ '<div class="chat-body clearfix">'
												+ '<div class="header">'
												+ '<strong class="primary-font">'
												+ user.notificationUsername
												+ ' '
												+ user.notificationUsersurname
												+ '</strong> <small class="pull-right text-muted">'
												+ user.notificationUserusername
												+ '</small>'
												+ '</div>'
												+ '<table>'
												+ '<tr>'
												+ '<td>'
												+ '<small class="pull-right text-muted">'
												+ user.notificationUseremail
												+ '</small>'
												+ '</td>'
												+ '<tr>'
												+ '</table>'
												+ '</div>' + '</a>' + '</li>');

						getImageForUser(user.notificationUserId,
								imageComponentId);
					});
}

function refreshVictims() {
	resetVictims();
	getVictims();
}

function resetVictims() {
	notificationUsers = null;
	notificationUsersPageNumber = 1;
	searchPageNumber = 1;
	searchMode = false;
	$('#notification_users_listview_component').empty();
	$('#searchVictimTextField').val('');
}

function resetSearch(){
	searchMode = true;
	notificationUsers = null;
	searchPageNumber = 1;
	$('#notification_users_listview_component').empty();
}

function getImageForUser(userId, imageComponentId) {
	var image = getImage(userId);
	if (image != null) {
		$('#' + imageComponentId).fadeOut(
				200,
				function() {
					$('#' + imageComponentId).attr('src',
							'data:image/png;base64,' + image);
				}).fadeIn(400);
	} else {
		$
				.ajax({
					type : 'GET',
					url : './image/get?uid=' + userId,
					data : '',
					dataType : 'json',
					success : function(jsonData) {
						pushImage(jsonData.userId,
								jsonData.imageBase64);
						$('#' + imageComponentId)
								.fadeOut(
										200,
										function() {
											$('#' + imageComponentId)
													.attr(
															'src',
															'data:image/png;base64,'
																	+ jsonData.imageBase64);
										}).fadeIn(400);
					},
					error : function() {
					}
				});
	}
}

function deleteUser() {
	$("#user-confirm-delete").modal('hide');
	setTimeout(
			function() {
				$
						.ajax({
							type : 'GET',
							url : './delete?uid=' + selectedUserId,
							data : '',
							dataType : 'json',
							success : function(jsonData) {
								  $('#successDeleteDismissible').show();
								  $('#successDeleteDismissibleStrong').html(jsonData.reason);
								  refreshVictims();
							},
							error : function() {
							}
						});
			}, 100);
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
          url : "./update",
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
          url : "./create",
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
}

function showDialog(header, content) {
	$('#smallModal').find('.modal-body').empty();
	$('#smallModal').find('.modal-header').empty();
	$('#smallModal').find('.modal-header').append(
			'<h4 class="modal-title">' + header + '</h4>');
	$('#smallModal').find('.modal-body').append(content);
	$("#smallModal").modal('show');
}

function previewUserImage(id) {
	var image = getImage(id);
	$('#smallModal').find('.modal-header').empty();
	$('#smallModal').find('.modal-body').empty();
	$('#smallModal').find('.modal-header').append(
			'<h4 class="modal-title">Profil Önizleme</h4>');
	$('#smallModal')
			.find('.modal-body')
			.append(
					'<div align="center"><img src="data:image/png;base64,'
							+ image
							+ '" style=display: block;margin-left: auto;margin-right: auto;"/></div>');
	$("#smallModal").modal('show');
}

function getUser(id) {
	var returnedUser = null;
	$.each(notificationUsers.pageResult, function(i, user) {
		if (user.notificationUserId == id) {
			returnedUser = user;
		}
	});
	return returnedUser;
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