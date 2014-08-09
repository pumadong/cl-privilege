<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">分配权限</h4>
</div>

<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN TREE-->
       		<div id="tree_cl" class="tree-demo">
			</div>
			<!-- END TREE-->
		</div>
	</div>
</div>

<div class="modal-footer">
	<input type="hidden" id="id" name="id" value="${id}"/>
	<button type="button" class="btn default" data-dismiss="modal">关闭</button>
	<button type="button" class="btn blue" onclick="javascript:Role.assign();">保存</button>
</div>

<script>
	jQuery(document).ready(function() {       
	   TreeCl.init("${id}");
	});
</script>