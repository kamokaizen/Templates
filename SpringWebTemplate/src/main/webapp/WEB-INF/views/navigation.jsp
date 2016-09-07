<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
	<div>
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> 
			<span class="icon-bar"></span> 
			<span class="icon-bar"></span> 
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="${contextPath}/home/default"><span><spring:message code="header" /></span></a>
		<!-- /.navbar-top-links -->
		<ul class="nav navbar-top-links navbar-right pull-right">
			<!-- /.dropdown -->
			<li class="dropdown" id="menuUserProfile">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 
						<i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-user">
					<li>
						<a href="#">
							<i class="fa fa-users fa-fw"></i> <spring:message code="menu_profile" />
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-gear fa-fw"></i> <spring:message code="menu_settings" />
						</a>
					</li>
					<li>
						<a href="${contextPath}/loginactivity/default">
							<i class="fa fa-keyboard-o fa-fw"></i> <spring:message code="menu_login_activities" />
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<c:url var="logoutUrl" value="/logout"/>
						<form class="form-signin" method="post" action="${logoutUrl}">
							<a href="#" onclick="$(this).closest('form').submit()">
								<i class="fa fa-sign-out fa-fw"></i> <spring:message code="menu_logout" />
							</a>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					</li>
				</ul>
				<!-- /.dropdown-user -->
			</li>
			<!-- /.dropdown -->
			<li class="dropdown" id="menuUserLinks">	
				<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 
						<i class="fa fa-list-ul fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-user">
					<li>
						<a href="${contextPath}/dashboard/default">
							<i class="fa fa-dashboard fa-fw"></i> <spring:message code="menu_dashboard" />
						</a>
					</li>
					<li>
						<a href="${contextPath}/victim/default">
							<i class="fa fa-user fa-fw"></i> <spring:message code="menu_victims" />
						</a>
					</li>
					<li>
						<a href="${contextPath}/cheat/default">
							<i class="fa fa-mobile fa-fw"></i> <spring:message code="menu_notifications" />
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-users fa-fw"></i> <spring:message code="menu_profile" />
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-gear fa-fw"></i> <spring:message code="menu_settings" />
						</a>
					</li>
					<li>
						<a href="${contextPath}/loginactivity/default">
							<i class="fa fa-keyboard-o fa-fw"></i> <spring:message code="menu_login_activities" />
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<c:url var="logoutUrl" value="/logout"/>
						<form class="form-signin" method="post" action="${logoutUrl}">
							<a href="#" onclick="$(this).closest('form').submit()">
								<i class="fa fa-sign-out fa-fw"></i> <spring:message code="menu_logout" />
							</a>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					</li>
				</ul> 
			</li>
		</ul>
		<!-- /.navbar-top-links -->
	</div>
	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li class="sidebar-search">
					<div class="input-group custom-search-form">
						<spring:message code="button_search_placeholder" var="button_search_placeholder" />
						<input type="text" class="form-control" placeholder="${button_search_placeholder}" id="searchTextField"> 
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" id="search-button">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div> <!-- /input-group -->
				</li>
				<li class="sidebar-menuitem">
					<a href="${contextPath}/dashboard/default">
						<i class="fa fa-dashboard fa-fw"></i> <spring:message code="menu_dashboard" />
					</a>
				</li>
				<li class="sidebar-menuitem">
					<a href="${contextPath}/victim/default">
						<i class="fa fa-user fa-fw"></i> <spring:message code="menu_victims" />
					</a>
				</li>
				<li class="sidebar-menuitem">
					<a href="${contextPath}/cheat/default">
						<i class="fa fa-mobile fa-fw"></i> <spring:message code="menu_notifications" />
					</a>
				</li>
			</ul>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-header -->
	<!-- /.navbar-static-side -->
</nav>