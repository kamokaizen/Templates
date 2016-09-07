
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="tr" dir="ltr">
<head>
<meta charset="utf-8">
<title><spring:message code="header" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/bootstrap/dist/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/welcome.css" rel="stylesheet">
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/welcomefavicon.ico" />

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">
		<form class="form-signin" method="post" action="login">
			<h2 class="form-signin-heading" id="welcomeHeader"><spring:message code="header" /></h2>
			<c:choose>
				<c:when test="${loginFailed}">
					<div class="alert alert-danger" role="alert">
						<spring:message code="loginError" />
					</div>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>

			<spring:message code="login_username" var="login_username" />
			<label for="inputEmail" class="sr-only"><spring:message code="login_username" /></label> 
			<input name="username" id="inputEmail" class="form-control" placeholder="${login_username}" required autofocus style="margin-bottom: 5px;">
			<spring:message code="login_password" var="login_password" />
			<label for="inputPassword" class="sr-only"><spring:message code="login_password" /></label> 
			<input type="password" name="password" id="inputPassword" class="form-control" placeholder="${login_password}" required style="margin-bottom: 5px;">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button class="btn btn-lg btn-primary btn-block" type="submit">
				<spring:message code="login_signin" />
			</button>
		</form>
	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/bootstraptemplate/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
</body>
</html>
