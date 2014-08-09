<!-- BEGIN HEADER -->
<div class="header navbar navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="header-inner">
		<!-- BEGIN LOGO -->
		<a class="navbar-brand" href="${BasePath}">
			<img src="${IncPath}/assets/img/logo.png" alt="logo" class="img-responsive"/>
		</a>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<img src="${IncPath}/assets/img/menu-toggler.png" alt=""/>
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<ul class="nav navbar-nav pull-right">
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<img alt="" src="${IncPath}/assets/img/<#if user.gender==true>avatar3_small.jpg<#else>avatar2_small.jpg</#if>"/>
					<span class="username">
						 ${user.fullname?default("")}
					</span>
					<i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a href="javascript:;" id="trigger_fullscreen">
							<i class="fa fa-arrows"></i> 全屏
						</a>
					</li>
					<li>
						<a href="javascript:Cl.showModalWindow(Cl.modalName,'${BasePath}/controller/modifypasswordform.do');" id="modify_password">
							<i class="fa fa-arrows"></i> 修改密码
						</a>
					</li>
					<li>
						<a href="${BasePath}/controller/logout.do">
							<i class="fa fa-key"></i> 退出
						</a>
					</li>
				</ul>
			</li>
			<!-- END USER LOGIN DROPDOWN -->
		</ul>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->