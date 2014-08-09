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
<title>创力 | 系统管理 - 部门管理</title>
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
					部门管理
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
								部门管理
							</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-6">
					<div class="portlet blue box">
						<div class="portlet-title">
							<div class="caption">
								部门树
							</div>						
						</div>
						<div class="portlet-body">
			           		<div id="tree_cl" class="tree-demo">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="portlet blue box">
						<div class="portlet-title">
							<div class="caption">
								部门管理
							</div>
						</div>
						<div class="portlet-body form">
							<!-- BEGIN FORM-->
							<form action="#" id="form_cl" class="form-horizontal">
								<div class="form-body">
										<div class="form-group">
										<label class="control-label col-md-3">名称
										<span class="required">
											 *
										</span>
										</label>
										<div class="col-md-8">
											<input type="hidden" id="id" name="id"/>
											<input type="text" id="name" name="name" data-required="1" class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">序号
										<span class="required">
											 *
										</span>
										</label>
										<div class="col-md-8">
											<input id="sortNo" name="sortNo" type="text" class="form-control" maxlength="5"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">备注
										</label>
										<div class="col-md-8">
											<input type="text" id="remark" name="remark" data-required="1" class="form-control"/>
										</div>
									</div>
								</div>
								<div class="form-actions fluid">
									<div class="col-md-offset-3 col-md-9">
										<button type="submit" class="btn blue" onclick="javascript:Cl.action='create';">增加</button>
										<button type="submit" class="btn blue" onclick="javascript:Cl.action='update';">修改</button>
										<button type="button" class="btn blue" onclick="javascript:Department.remove();">删除</button>
										<button type="button" class="btn blue" onclick="javascript:Department.clear();">取消</button>
									</div>
								</div>
							</form>
							<!-- END FORM-->
						</div>
					</div>
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
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${IncPath}/assets/plugins/jstree/dist/jstree.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="${IncPath}/assets/scripts/core/app.js"></script>
<script src="${BasePath}/scripts/custom/cl.js"></script>
<script src="${BasePath}/scripts/custom/department.js"></script>
<script>
    jQuery(document).ready(function() {       
       // initiate layout and plugins
       App.init();
       Cl.initModal();
       TreeCl.init();
       FormCl.init();
    });
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
