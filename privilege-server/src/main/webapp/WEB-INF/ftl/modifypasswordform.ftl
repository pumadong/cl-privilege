<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">修改密码</h4>
</div>

<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN FORM-->
			<form action="#" id="form_cl_mp" class="form-horizontal">
				<div class="form-body">
					<div class="form-group">
						<label class="control-label col-md-3">旧密码
						<span class="required">
							 *
						</span>
						</label>
						<div class="col-md-8">
							<input type="hidden" id="id" name="id"/>
							<input type="text" id="oldpassword" name="oldpassword" data-required="1" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">新密码
						<span class="required">
							 *
						</span>
						</label>
						<div class="col-md-8">
							<input type="password" id="password" name="password" data-required="1" class="form-control"/>
						</div>
					</div>				
					<div class="form-group">
						<label class="control-label col-md-3">确认新密码
						<span class="required">
							 *
						</span>
						</label>
						<div class="col-md-8">
							<input type="text" id="confirmpassword" name="confirmpassword" data-required="1" class="form-control"/>
						</div>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn default" data-dismiss="modal">关闭</button>
	<button type="button" class="btn blue" onclick="javascript:$('#form_cl_mp').submit();">保存</button>
</div>

<script>
    jQuery(document).ready(function() {       
       Cl.initModifyPassword("${BasePath}/controller/modifypassword.do");
    });
</script>