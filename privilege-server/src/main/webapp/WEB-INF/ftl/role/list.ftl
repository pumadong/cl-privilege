<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.1.1
Version: 2.0.2
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>创力 | 系统管理 - 角色管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN GENERAL STYLES -->
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<!-- END GENERAL STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/select2/select2-metronic.css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/data-tables/DT_bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/plugins/jstree/dist/themes/default/style.min.css"/>
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">

<#include "../header.ftl" >

<div class="clearfix">
</div>

<!-- BEGIN CONTAINER -->
<div class="page-container">
	
	<#include "../sidebar.ftl" >

	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
					角色管理
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="${BasePath}">
								主页
							</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="${BasePath}">
								配置中心
							</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a>
								角色管理
							</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- Begin: life time stats -->
					<div class="portlet">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-user"></i>角色列表
							</div>						
						</div>
						<div class="portlet-body">
							<div class="table-container">
								<div class="table-actions-wrapper">
									<button class="btn btn-add blue table-group-action-submit" id="role-add" data-toggle="modal" onclick="javascript:Role.add_click();"> 新增</button>
								</div>
								<table class="table table-striped table-bordered table-hover" id="datatable_cl">
								<thead>
								<tr role="row" class="heading">
									<th width="1%">
										<input type="checkbox" class="group-checkable">
									</th>
									<th width="10%">
										 ID
									</th>
									<th width="15%">
										 名称
									</th>
									<th width="15%">
										 备注
									</th>
									<th width="20%">
										 最后操作人
									</th>
									<th width="20%">
										 最后操作日期
									</th>
									<th width="15%">
										 操作
									</th>
								</tr>
								</thead>
								<tbody>
								</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- End: life time stats -->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<#include "../footer.ftl" >

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${IncPath}/assets/plugins/respond.min.js"></script>
<script src="${IncPath}/assets/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${IncPath}/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN GENERAL PLUGINS -->
<script src="${IncPath}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="${IncPath}/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="${IncPath}/assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
<!-- END GENERAL PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${IncPath}/assets/plugins/select2/select2.min.js"></script>
<script src="${IncPath}/assets/plugins/data-tables/jquery.dataTables.js"></script>
<script src="${IncPath}/assets/plugins/data-tables/DT_bootstrap.js"></script>
<script src="${IncPath}/assets/plugins/jstree/dist/jstree.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${IncPath}/assets/scripts/core/app.js"></script>
<script src="${IncPath}/assets/scripts/core/datatable.js"></script>
<script src="${BasePath}/scripts/custom/cl.js"></script>
<script src="${BasePath}/scripts/custom/role.js"></script>
<script>
	jQuery(document).ready(function() {       
	   // initiate layout and plugins
	   App.init();
	   Cl.initModal();
	   DataTableCl.init();
	});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>