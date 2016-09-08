var userActivities=null;

/* Map contains [TYPE_ID][IMAGE_BASE64]*/
var typeImageMap = new Object();

var userActivityPageNumber=1;
var selectedActivityId=0;

$('.close').click(function() { $('.alert').hide(); })

$("#user_activities_listview_component").on("click", "li", function(e){
	e.preventDefault();
	selectedActivityId = $(this).attr("id");
	$('#user_activities_listview_component li').removeClass('selected');
    $(this).addClass('selected');
});

$('body').on('click', 'a[name="user_activity_delete_button"]', function() {
	$("#activity-confirm-delete").modal('show');
});

$('body').on('click', 'span[name="user_image_preview_button"]', function() {
    previewUserImage(selectedActivityId);
});

$('#user_activity_pager_button').on('click', function () {
    var $btn = $(this).button('loading');
    
    userActivityPageNumber = userActivityPageNumber+1;
    getuserActivities();
    
    setTimeout(function () {
        $btn.button('reset');
    }, 500);
});

$('#searchActivityTextField').keyup(function(){		
	   var valThis = $(this).val().toLowerCase();
	    $('#user_activities_listview_component>li').each(function(){
	    	var text = $(this).find('div>table').text().toLowerCase();
	     	(text.indexOf(valThis) == 0) ? $(this).show() : $(this).hide();            
	   });
});

getuserActivities();

function getuserActivities(){
	makeServiceCall('GET', './get?pn=' + userActivityPageNumber, '', 'json', function(err, jsonData){
		if(err){
			$('#failDismissible').show();
			$('#failDismissibleStrong').html(err);
		}
		else{
		  if(userActivities == null){
			  userActivities = jsonData;
			  $('#user_activities_listview_component').empty();
			  setuserActivities(userActivities.pageResult);
		  }
		  else{
			  userActivities.pageResult = $.merge(userActivities.pageResult,jsonData.pageResult);
			  userActivities.page = jsonData.page;
			  userActivities.totalPage = jsonData.totalPage;
			  setuserActivities(jsonData.pageResult);
		  }
		}
	});
}

function deleteActivity(){
	$("#activity-confirm-delete").modal('hide');
	setTimeout(function(){
		makeServiceCall('GET', './delete?aid=' + selectedActivityId, '', 'json', function(err, jsonData){
			if(err){
				$('#failDismissible').show();
				$('#failDismissibleStrong').html(err);
			}
			else{
				$('#successDeleteDismissible').show();
				$('#successDeleteDismissibleStrong').html(jsonData.reason);
				refreshuserActivities();
			}
		});
	}, 100);
}

function setuserActivities(results){
	$.each( results, function(i, activity) {
		var imageComponentId = "user_activity_img_" + activity.userActivityId;
		
		$('#user_activities_listview_component').append(		
		'<li class="left clearfix" id='+ activity.userActivityId +'>' +
		'<span class="chat-img pull-left" name="user_image_preview_button"><img id="'+ imageComponentId +'" src="" alt="" class="img-circle" width="50" height="50" /></span>' +
		'<div class="chat-body clearfix">' +
			'<div class="header">' +
				 '<strong class="primary-font">'+ activity.userActivityType +'</strong>' +
			'</div>' +
			'<table>' +
			'<tr>' +
				'<td>' +
					'<small class="pull-left text-muted">'+ activity.user +'</small></br>' +
					'<small class="pull-left text-muted">'+ activity.userEmail +'</small></br>' +
					'<small class="pull-left text-muted">'+ activity.userActivityDate +'</small></br>' +
					'<small class="pull-left text-muted">'+ activity.userIp +'</small></br>' +
					'<small class="pull-left text-muted">'+ activity.userAgent +'</small></br>' +
					'<small class="pull-left text-muted">'+ activity.userLocation +'</small></br>' +
				'</td>' +
			'<tr>' +
			'</table>' +
		'</div>'+
		'</li>');
		
		getImageForType(activity.userActivityTypeId, imageComponentId);
	});
}

function refreshuserActivities(){
	resetuserActivities();
	getuserActivities();
}

function resetuserActivities(){
	userActivities = null;
	userActivityPageNumber = 1;
	selectedActivityId = 0;
	$('#user_activities_listview_component').empty();
}

function getImageForType(userId, imageComponentId){
	var image = getImage(userId);
	if(image != null){		
		$('#'+ imageComponentId).fadeOut(200, function() {
			$('#'+ imageComponentId).attr('src', 'data:image/png;base64,'+ image);
        }).fadeIn(400);
	}
	else{
		makeServiceCall('GET', './image/get?tid=' + userId, '', 'json', function(err, jsonData){
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

function previewUserImage(id){
	var userActivity = getUserActivityItem(id);
	var image = getImage(userActivity.userActivityTypeId);
	$('#smallModal').find('.modal-header').empty();
	$('#smallModal').find('.modal-body').empty();
	$('#smallModal').find('.modal-header').append('<h4 class="modal-title">Profil Önizleme</h4>');
	$('#smallModal').find('.modal-body').append('<div align="center"><img src="data:image/png;base64,'+ image +'" style=display: block;margin-left: auto;margin-right: auto;"/></div>');
	$("#smallModal").modal('show');
}

function getUserActivityItem(id){
	var returnedActivity = null;
	$.each( userActivities.pageResult, function(i, activity) {
		if(activity.userActivityId == id){
			returnedActivity = activity;
		}
	});
	return returnedActivity;
}

function showDialog(header, content){
	  $('#smallModal').find('.modal-body').empty();
	  $('#smallModal').find('.modal-header').empty();
	  $('#smallModal').find('.modal-header').append('<h4 class="modal-title">'+ header +'</h4>');
	  $('#smallModal').find('.modal-body').append(content);
	  $("#smallModal").modal('show');
}

function getImage(userId) {
    return typeImageMap[userId];
}

function pushImage(userId, value) {
    typeImageMap[userId] = value;
}