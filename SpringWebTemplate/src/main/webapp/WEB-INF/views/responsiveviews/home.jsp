<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib  uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><spring:message code="header"/></title>

    <!-- favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/welcomefavicon.ico" />

    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/resources/plugins/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=request.getContextPath()%>/resources/plugins/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="<%=request.getContextPath()%>/resources/plugins/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=request.getContextPath()%>/resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<link href="<%=request.getContextPath()%>/resources/css/home.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">
		<input type="hidden" id="userIdValue" value='${userId}'/>
		<input type="hidden" id="csrfToken" value="${_csrf.token}"/>
		<input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
		
		<!-- Navigation -->
		<%@ include file="/WEB-INF/views/navigation.jsp" %>
		
        <div id="page-wrapper">
        	<br>
        	<div class="row">
       	       	<div class="alert alert-success alert-dismissible" role="alert"  hidden="hidden" id="successUserDismissible">
				  <button type="button" class="close" data-dissmiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong><spring:message code="userCreated" /></strong>
				</div>
				<div class="alert alert-success alert-dismissible" role="alert"  hidden="hidden" id="successUserUpdateDismissible">
				  <button type="button" class="close" data-dissmiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong><spring:message code="newVictimUpdateSuccess" /></strong>
				</div>
				<div class="alert alert-success alert-dismissible" role="alert"  hidden="hidden" id="successNotificationDismissible">
				  <button type="button" class="close" data-dissmiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong><spring:message code="newNotificationSuccess" /></strong>
				</div>
				<div class="alert alert-success alert-dismissible" role="alert"  hidden="hidden" id="successNotificationUpdateDismissible">
				  <button type="button" class="close" data-dissmiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong><spring:message code="updateNotificationSuccess" /></strong>
				</div>
				<div class="alert alert-info alert-dismissible" role="alert"  hidden="hidden" id="successDeleteDismissible">
				  <button type="button" class="close" data-dissmiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong id="successDeleteDismissibleStrong"></strong>
				</div>
                <div class="col-lg-6">
                    <div class="chat-panel panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-user fa-fw"></i>
                            <spring:message code="victimList"/>
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="javascript:refreshVictims()">
                                            <i class="fa fa-refresh fa-fw"></i> <spring:message code="button_refresh"/>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
						<div class="input-group" style="margin:5px">
	                   		<spring:message code="button_filter" var="button_filter"/>
	                        <input id="searchVictimTextField" type="text" class="form-control input-sm" placeholder="${button_filter}" />
	                        <span class="input-group-btn">
	                            <button class="btn btn-info btn-sm" id="btn-victim-search">
	                            	<i class="fa fa-search" aria-hidden="true"></i>
	                            </button>
	                        </span>
	                    </div>
						<div class="btn-group btn-group-sm hiddenLowResolution" style="margin:5px">
								<a href="#" name="notification_user_new_button"  class="btn btn-info btn-sg"><i class="glyphicon glyphicon-plus"></i> <span><spring:message code="button_new"/></span></a>
                    			<a href="#" name="notification_user_delete_button"  class="btn btn-info btn-sg"><i class="glyphicon glyphicon-trash"></i> <span><spring:message code="button_delete"/></span></a>
								<a href="#" name="notification_user_update_button"  class="btn btn-info btn-sg"><i class="glyphicon glyphicon-edit"></i> <span><spring:message code="button_update"/></span></a>
								<a href="#" name="notification_new_button"  class="btn btn-info btn-sg"><i class="glyphicon glyphicon-phone"></i> <span><spring:message code="button_notification"/></span></a>
                    	</div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <ul id="notification_users_listview_component" class="chat"></ul>
                        </div>
                        <!-- /.panel-body -->
                        <div class="panel-footer">
                        	<spring:message code="button_loading" var="button_loading"/>
							<button type="button" id="notification_user_pager_button" style="display: block; width: 100%;" data-loading-text="${button_loading}" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-circle-arrow-down"></span> <spring:message code="button_more"/></button>
                        </div>
                        <!-- /.panel-footer -->
                    </div>
                    <!-- /.panel .chat-panel -->
                </div>
            	<div class="col-lg-6">
                    <div class="chat-panel panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-mobile fa-fw"></i>
                            <spring:message code="notificationList"/>
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="javascript:refreshNotifications()">
                                            <i class="fa fa-refresh fa-fw"></i> <spring:message code="button_refresh"/>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
						<div class="input-group" style="margin:5px">
	                   		<spring:message code="button_filter" var="button_filter"/>
	                        <input id="searchNotificationTextField" type="text" class="form-control input-sm" placeholder="${button_filter}" />
	                        <span class="input-group-btn">
	                            <button class="btn btn-info btn-sm" id="btn-notification-search">
	                            	<i class="fa fa-search" aria-hidden="true"></i>
	                            </button>
	                        </span>
	                    </div>
	                    <div class="btn-group btn-group-sm hiddenLowResolution" style="margin:5px">
	                   		<a href="#" name="notification_delete_button" class="btn btn-info btn-sg"><i class="glyphicon glyphicon-trash"></i> <span><spring:message code="button_delete"/></span></a>
							<a href="#" name="notification_update_button" class="btn btn-info btn-sg"><i class="glyphicon glyphicon-edit"></i> <span><spring:message code="button_update"/></span></a>
	                    </div>
                        <div class="panel-body">
                            <ul class="chat" id="notifications_listview_component">
                            </ul>
                        </div>
                        <!-- /.panel-body -->
                        <div class="panel-footer">
                       		<spring:message code="button_loading" var="button_loading"/>
							<button type="button" id="notifications_pager_button" style="display: block; width: 100%;" data-loading-text="${button_loading}" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-circle-arrow-down"></span> <spring:message code="button_more"/></button>
                        </div>
                        <!-- /.panel-footer -->
                    </div>
                    <!-- /.panel .chat-panel -->
                </div>
                <!-- /.col-lg-4 -->
            <!-- /.row -->
        	</div>
        </div>

		<!-- Modal HTML -->
	    <div id="emailModal" class="modal fade">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                    <h4 class="modal-title">Email <spring:message code="button_preview" var="button_preview"/></h4>
	                </div>
	                <div class="modal-body">
	                    
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="button_close"/></button>
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <div id="smallModal" class="modal fade">
	        <div class="modal-dialog modal-sm">
	            <div class="modal-content">
	            	<div class="modal-header">
		            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		            </div>
	                <div class="modal-body">
	                </div>
	                <div class="modal-footer">
	                	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="button_close"/></button>
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <!-- New Victim Modal -->
	    <div id="newVictimModal" class="modal fade">
	        <div class="modal-dialog modal-sm">
	            <div class="modal-content">
	            	<div class="modal-header">
	            		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	            		<h4 class="modal-title"><spring:message code="newVictim" /></h4>
		            </div>
	                <div class="modal-body">
						<div align="center">
						   <div id="createStatusField" style="display:none;" class="alert" role="alert"></div>
						   <div class="form-group"><input id="createNameField" class="form-control" type="text" name="notificationUsername" path="notificationUsername" placeholder="<spring:message code='placeholder_name' />"/></div>
				 	  	   <div class="form-group"><input id="createSurnameField" class="form-control" type="text" name="notificationUsersurname" path="notificationUsersurname" placeholder="<spring:message code='placeholder_surname' />"/></div>
						   <div class="form-group"><input id="createUsernameField" class="form-control" type="text" path="notificationUserusername" name="notificationUserusername" placeholder="<spring:message code='placeholder_username' />"/></div>
						   <div class="form-group"><input id="createEmailField" class="form-control" type="text" path="notificationUseremail" name="notificationUseremail" placeholder="<spring:message code='placeholder_email' />"/></div>
						   <div class="form-group"><input type="file" name="createNotificationUserimage" id="createNotificationUserimage" /></div>
						   <div class="form-group"><button onClick="createUser()" class="btn btn-info btn-sm btn-block"><span class="glyphicon glyphicon-floppy-save"></span> <spring:message code="button_save" /></button></div>
						</div>								
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <!-- Update Victim Modal -->
	    <div id="updateVictimModal" class="modal fade">
	        <div class="modal-dialog modal-sm">
	            <div class="modal-content">
	            	<div class="modal-header">
	            		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	            		<h4 class="modal-title"><spring:message code="updateVictim" /></h4>
		            </div>
	                <div class="modal-body">
				       <div align="center">
						   <div id="updateStatusField" style="display:none;" class="alert" role="alert"></div>
						   <div class="form-group"><input id="updateNameField" class="form-control" type="text" name="notificationUsername" path="notificationUsername" placeholder="<spring:message code='placeholder_name' />"/></div>
				 	  	   <div class="form-group"><input id="updateSurnameField" class="form-control" type="text" name="notificationUsersurname" path="notificationUsersurname" placeholder="<spring:message code='placeholder_surname' />" /></div>
						   <div class="form-group"><input id="updateUsernameField" class="form-control" type="text" path="notificationUserusername" name="notificationUserusername" placeholder="<spring:message code='placeholder_username' />"/></div>
						   <div class="form-group"><input id="updateEmailField" class="form-control" type="text" path="notificationUseremail" name="notificationUseremail" placeholder="<spring:message code='placeholder_email' />"/></div>
						   <div class="form-group"><img id="userthumbnail" width="200" height="200"/></div>
						   <div class="form-group"><input type="file" name="notificationUserimage" id="notificationUserimage" /></div>
						   <div class="form-group"><button onClick="updateUser()" class="btn btn-info btn-sm btn-block"><span class="glyphicon glyphicon-floppy-save"></span> <spring:message code="button_update" /></button></div>
						</div>							
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <!-- New Notification Modal -->
	    <div id="newNotificationModal" class="modal fade">
	        <div class="modal-dialog modal-sm">
	            <div class="modal-content">
	            	<div class="modal-header">
	            		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	            		<h4 class="modal-title"><spring:message code="newNotification" /></h4>
		            </div>
	                <div class="modal-body">
						<div align="center">
							<div id="createnotificationStatusField" style="display:none;" class="alert" role="alert"></div>
							<div class="form-group">
								<div class="left clearfix">
									<span class="chat-img pull-left" name="user_image_preview_button"><img id="notificationUserImage" src="" alt="" class="img-circle" width="50" height="50"/></span>
									<div class="chat-body clearfix">
										<table style="padding-left:5px;">
											<tr>
												<td><strong id="notificationUserLabel" class="primary-font"></strong></td>
											</tr>
											<tr>
												<td>
													<small id="notificationMailLabel" class="text-muted"></small>
												</td>
												<td style="padding-left:5px;"><img id="notificationTypeImage" src="" class="img-circle" width="25" height="25" /></td>
											<tr>
											<tr>
												<td><button onClick="createRandomNotification()" class="btn btn-info btn-xs btn-block"><span class="glyphicon glyphicon-random"></span> <spring:message code="button_random"/></button></td>
											</tr>
										</table>
									</div>
								</div>
						   	</div>
			   			    <div class="form-group"  style="margin-top:10px;">
								<select id="notificationOsSelection" name="notificationOs" path="notificationOs" class="form-control">
									<option value="-1" selected="1" id="OPTION_162"><spring:message code="chooseNotificationOSType" /></option>
									<c:forEach items="${notificationOsValues}" var="notificationos">
										<option value="${notificationos.value}">${notificationos.key}</option>
									</c:forEach>
								</select>
						    </div>
						   
						    <div class="form-group"><input id="notificationLocation" type="text" class="form-control" path="notificationDto.notificationLocation" name="notificationLocation" placeholder="<spring:message code='chooseNotificationLocation'/>" /></div>
						    <div class="form-group"><input id="notificationIP" type="text" class="form-control" path="notificationDto.notificationIp" name="notificationIp" placeholder="<spring:message code='chooseNotificationIP'/>" /></div>
						    <div class="form-group"><button onClick="createNotification()" class="btn btn-info btn-sm btn-block"><span class="glyphicon glyphicon-floppy-save"></span> <spring:message code="button_save"/></button></div>
						</div>								
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <!-- Update Notification Modal -->
	    <div id="updateNotificationModal" class="modal fade">
	        <div class="modal-dialog modal-sm">
	            <div class="modal-content">
	            	<div class="modal-header">
	            		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	            		<h4 class="modal-title"><spring:message code="updateNotification" /></h4>
		            </div>
	                <div class="modal-body">
						<div align="center">
							<div id="updateNotificationStatusField" style="display:none;" class="alert" role="alert"></div>
							<div class="form-group">
								<div class="left clearfix">
									<span class="chat-img pull-left" name="user_image_preview_button"><img id="updatenotificationUserImage" src="" alt="" class="img-circle" width="50" height="50"/></span>
									<div class="chat-body clearfix">
										<table style="padding-left:5px;">
											<tr>
												<td><strong id="updatenotificationUserLabel" class="primary-font"></strong></td>
											</tr>
											<tr>
												<td>
													<small id="updatenotificationMailLabel" class="text-muted"></small>
												</td>
												<td style="padding-left:5px;"><img id="updatenotificationTypeImage" src="" class="img-circle" width="25" height="25" /></td>
											<tr>
											<tr>
												<td><button onClick="updateRandomNotification()" class="btn btn-info btn-xs btn-block"><span class="glyphicon glyphicon-random"></span> <spring:message code="button_random"/></button></td>
											</tr>
										</table>
									</div>
								</div>
						   	</div>
			   			    <div class="form-group">
								<select id="updatenotificationOsSelection" name="notificationOs" path="notificationOs" class="form-control">
									<option value="-1" selected="1" id="OPTION_162"><spring:message code="chooseNotificationOSType" /></option>
									<c:forEach items="${notificationOsValues}" var="notificationos">
										<option value="${notificationos.value}">${notificationos.key}</option>
									</c:forEach>
								</select>
						    </div>
						    <div class="form-group" style="margin-top:10px;"><input id="updatenotificationDate" type="text" class="form-control" path="notificationDto.notificationDate" name="notificationDate" placeholder="<spring:message code='chooseNotificationDate'/>" /></div>
						    <div class="form-group"><input id="updatenotificationLocation" type="text" class="form-control" path="notificationDto.notificationLocation" name="notificationLocation" placeholder="<spring:message code='chooseNotificationLocation'/>" /></div>
						    <div class="form-group"><input id="updatenotificationIP" type="text" class="form-control" path="notificationDto.notificationIp" name="notificationIp" placeholder="<spring:message code='chooseNotificationIP'/>" /></div>
						    <div class="form-group"><button onClick="updateNotification()" class="btn btn-info btn-sm btn-block"><span class="glyphicon glyphicon-floppy-save"></span> <spring:message code="button_save"/></button></div>
						</div>								
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <div class="modal fade" id="user-confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	            
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                    <h4 class="modal-title" id="myModalLabel"><spring:message code="confirm_delete"/></h4>
	                </div>
	            
	                <div class="modal-body">
	                    <p><spring:message code="delete_message1"/></p>
	                    <p><spring:message code="delete_message2"/></p>
	                    <p class="debug-url"></p>
	                </div>
	                
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="button_cancel"/></button>
	                    <a class="btn btn-danger btn-ok" onclick="deleteUser();"><spring:message code="button_delete"/></a>
	                </div>
	            </div>
	        </div>
    	</div>
    	
	    <div class="modal fade" id="notification-confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	            
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                    <h4 class="modal-title" id="myModalLabel"><spring:message code="confirm_delete"/></h4>
	                </div>
	            
	                <div class="modal-body">
	                    <p><spring:message code="delete_message1"/></p>
	                    <p><spring:message code="delete_message2"/></p>
	                    <p class="debug-url"></p>
	                </div>
	                
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="button_cancel"/></button>
	                    <a class="btn btn-danger btn-ok" onclick="deleteNotification();"><spring:message code="button_delete"/></a>
	                </div>
	            </div>
	        </div>
    	</div>		    
    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="<%=request.getContextPath()%>/resources/plugins/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/plugins/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/plugins/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/plugins/raphael/raphael-min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/plugins/morrisjs/morris.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/js/jquery/sb-admin-2.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery/chance.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/impl/home.js"></script>
</body>

</html>
