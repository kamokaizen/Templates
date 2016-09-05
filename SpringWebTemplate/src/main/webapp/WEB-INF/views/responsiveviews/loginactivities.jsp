<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><spring:message code="header" /></title>

<!-- favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<%=request.getContextPath()%>/resources/images/welcomefavicon.ico" />

<!-- Bootstrap Core CSS -->
<link
	href="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Timeline CSS -->
<link
	href="<%=request.getContextPath()%>/resources/bootstraptemplate/dist/css/timeline.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link
	href="<%=request.getContextPath()%>/resources/bootstraptemplate/dist/css/sb-admin-2.css"
	rel="stylesheet">

<!-- Morris Charts CSS -->
<link
	href="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/morrisjs/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link href="<%=request.getContextPath()%>/resources/css/home.css"
	rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<div id="wrapper">
		<input type="hidden" id="userIdValue" value='${userId}' />

		<!-- Navigation -->
		<%@ include file="/WEB-INF/views/navigation.jsp" %>

		<div id="page-wrapper">
			<br>
			<div class="row">
				<div class="alert alert-info alert-dismissible" role="alert"  hidden="hidden" id="successDeleteDismissible">
				  <button type="button" class="close" data-dissmiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong id="successDeleteDismissibleStrong"></strong>
				</div>
				<div class="col-lg-12">
					<div class="chat-panel panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-exchange fa-fw"></i>
							<spring:message code="activityList" />
							<div class="btn-group pull-right">
								<button type="button"
									class="btn btn-default btn-xs dropdown-toggle"
									data-toggle="dropdown">
									<i class="fa fa-chevron-down"></i>
								</button>
								<ul class="dropdown-menu slidedown">
									<li><a href="javascript:refreshuserActivities()"> <i
											class="fa fa-refresh fa-fw"></i> <spring:message
												code="button_refresh" />
									</a></li>
								</ul>
							</div>
						</div>
						<!-- /.panel-heading -->
						<div class="input-group" style="margin: 5px">
							<spring:message code="button_filter" var="button_filter" />
							<input id="searchActivityTextField" type="text"
								class="form-control input-sm" placeholder="${button_filter}" />
							<span class="input-group-btn">
								<button class="btn btn-info btn-sm" id="btn-chat">
									<i class="fa fa-search" aria-hidden="true"></i>
								</button>
							</span>
						</div>

						<div class="btn-group btn-group-sm hiddenLowResolution" style="margin: 5px">
							<a href="#" name="user_activity_delete_button"
								class="btn btn-info btn-sg"><i
								class="glyphicon glyphicon-trash"></i> <span> <spring:message
										code="button_delete" /></span></a>
						</div>
						<div class="panel-body">
							<ul id="user_activities_listview_component" class="chat">
							</ul>
						</div>
						<!-- /.panel-body -->
						<div class="panel-footer">
							<spring:message code="button_loading" var="button_loading" />
							<button type="button" id="user_activity_pager_button"
								style="display: block; width: 100%;"
								data-loading-text="${button_loading}"
								class="btn btn-info btn-sm">
								<span class="glyphicon glyphicon-circle-arrow-down"></span>
								<spring:message code="button_more" />
							</button>
						</div>
						<!-- /.panel-footer -->
					</div>
					<!-- /.panel .chat-panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>

		<div id="smallModal" class="modal fade">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<spring:message code="button_close" />
						</button>
					</div>
				</div>
			</div>
		</div>

    	<div class="modal fade" id="activity-confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	                    <a class="btn btn-danger btn-ok" onclick="deleteActivity();"><spring:message code="button_delete"/></a>
	                </div>
	            </div>
	        </div>
    	</div>	

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/raphael/raphael-min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/morrisjs/morris.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/js/morris-data.js"></script>

	<!-- Custom Theme JavaScript -->
	<script
		src="<%=request.getContextPath()%>/resources/bootstraptemplate/dist/js/sb-admin-2.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/impl/loginactivities.js"></script>
</body>

</html>
