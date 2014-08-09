var DataTableCl = function () {
    var handleUser = function() {        
    	var grid = new Datatable();
        grid.init({
            src: $("#datatable_cl"),
            onSuccess: function(grid) {
                // execute some code after table records loaded
            },
            onError: function(grid) {
                // execute some code on network or other general error  
            },
            dataTable: {  // here you can define a typical datatable settings from http://datatables.net/usage/options
            	"iDisplayLength": 20,
                "bServerSide": true, // server side processing
                "sAjaxSource": "getUserDataTables.do", // ajax source
                "bSort": false,
                "aoColumnDefs" : [{  // 隐藏第一列
                    'bVisible' : false,
                    'aTargets' : [ 0 ]
                }],
                "aaSorting": [[ 1, "asc" ]] // set first column as a default sort by asc
            }
        });
    }
    return {
        //main function to initiate the module
        init: function () {
    		handleUser();
        },
        tableName: "datatable_cl"
    };
}();

var FormCl = function () {
    var handleValidation = function() {
		// for more info visit the official plugin documentation: 
	    // http://docs.jquery.com/Plugins/Validation
	    var form1 = $('#form_cl');
	    var error1 = $('.alert-danger', form1);
	    var success1 = $('.alert-success', form1);	
	    form1.validate({
	        errorElement: 'span', //default input error message container
	        errorClass: 'help-block', // default input error message class
	        focusInvalid: false, // do not focus the last invalid input
	        ignore: "",
	        rules: {
	            username: {
	                minlength: 2,
	                required: true
	            },
	            password: {
	                minlength: 2,
	                required: true
	            },
	            fullname: {
	                minlength: 2,
	                required: true
	            },
		        gender: {
		            required: true
		        },
		        isAdmin: {
		            required: true
		        },
		        departmentId: {
		            required: true
		        }
	        },
            messages: { // custom messages for radio buttons and checkboxes
	        	gender: {
                    required: "请选择性别."
                },
                is_admin: {
                    required: "请选择类型."
                }
            },
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.attr("data-error-container")) { 
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) { 
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.radio-inline').size() > 0) { 
                    error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-inline').size() > 0) { 
                    error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            },
	        invalidHandler: function (event, validator) { //display error alert on form submit              
	            success1.hide();
	            error1.show();
	            App.scrollTo(error1, -200);
	        },	
	        highlight: function (element) { // hightlight error inputs
	            $(element)
	                .closest('.form-group').addClass('has-error'); // set error class to the control group
	        },	
	        unhighlight: function (element) { // revert the change done by hightlight
	            $(element)
	                .closest('.form-group').removeClass('has-error'); // set error class to the control group
	        },	
	        success: function (label) {
	            label
	                .closest('.form-group').removeClass('has-error'); // set success class to the control group
	        },	
	        submitHandler: function (form) {
	            //验证通过后执行操作
	            if(Cl.action == 'create')
	  			{
	  				User.add();
	  			} else
	  			{
	  				if(Cl.action == 'update')
	  				{
	  					User.update();
	  				}
	  			}
	        }
	    });
    }
    var handleWysihtml5 = function() {
        if (!jQuery().wysihtml5) {            
            return;
        }
        if ($('.wysihtml5').size() > 0) {
            $('.wysihtml5').wysihtml5({
                "stylesheets": ["http://127.0.0.1/privilege_inc/assets/plugins/bootstrap-wysihtml5/wysiwyg-color.css"]
            });
        }
    }
    return {
        //main function to initiate the module
        init: function () {
            handleWysihtml5();
            handleValidation();
        },
        formName: "form_cl"
    };
}();

var User = function(){
	return {
		/**
		 * 点击增加按钮
		 */
		add_click: function() {
	    	Cl.action='create';
	    	Cl.showModalWindow(Cl.modalName,"addform.do");
		},
		/**
		 * 点击修改按钮
		 */
		update_click: function(id) {
			Cl.action='update';	
			Cl.showModalWindow(Cl.modalName,"updateform.do?id="+id);
		},
		/**
		 * 点击分配权限按钮按钮
		 */
		assign_click: function(id) {
			Cl.action='assign';	
			Cl.showModalWindow(Cl.modalName,"assignform.do?id="+id);
		},
		/**
		 * 增加角色
		 */
		add: function(){
			var url="add.do";
			var data={
				"username":$("#username").val(),
				"password":$("#password").val(),
				"fullname":$("#fullname").val(),
				"gender":$('input[name="gender"]:checked').val(),
				"isAdmin":$('input[name="isAdmin"]:checked').val(),
				"departmentId":$("#departmentId").val()
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){								
					Cl.hideModalWindow(Cl.modalName);
					Cl.refreshDataTable(DataTableCl.tableName);
					alert("增加成功");
				} else {
					alert("增加失败");
					return ;			
				}
			});
		},
		/**
		 * 修改角色
		 */
		update: function(){
			var url="update.do";
			var data={
				"id":$("#id").val(),
				"username":$("#username").val(),
				"fullname":$("#fullname").val(),
				"gender":$('input[name="gender"]:checked').val(),
				"isAdmin":$('input[name="isAdmin"]:checked').val(),
				"departmentId":$("#departmentId").val()
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){			
					Cl.hideModalWindow(Cl.modalName);
					Cl.updateDataRow(DataTableCl.tableName,data.id,0,'getUserDataRow.do');
					alert("修改成功");
				} else {
					alert("修改失败");
					return ;			
				}
			});
		},
		/**
		 * 删除角色
		 */
		remove: function(id){
			if(!confirm("确定要删除该角色")){
				return;
			}	
			var url="delete.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.deleteDataRow(DataTableCl.tableName,data.id,0);
					alert("删除成功");
				} else {
					alert("被用户使用的角色不允许删除");
					return ;			
				}
			});
		},
		/**
		 * 用户分配角色
		 */
		assign: function(){
			var selectedStr = "";
			var i = 0;
			$("#multi_role").find("option:selected").each(function(){
				if(i==0)
				{
					selectedStr = $(this).val();
				} else {
					selectedStr = selectedStr + "," + $(this).val();
				}
				i++;
			});
			if(selectedStr == ""){
				alert("没有选择任何角色");
				return;
			}	
			var url="assign.do";
			var data={
				"id":$("#id").val(),
				"selectedStr":selectedStr
			};	
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.hideModalWindow(Cl.modalName);
					alert("分配角色成功");			
				} else {
					alert("分配角色失败");
					return ;			
				}
			});
		},
		/**
		 * 复位密码
		 */
		resetpass: function(id){
			if(!confirm("确定要重置密码为root吗")){
				return;
			}	
			var url="resetpass.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					alert("重置成功");
				} else {
					alert("重置失败");
					return ;			
				}
			});
		},
		/**
		 * 锁定用户
		 */
		lock: function(id){
			if(!confirm("确定要锁定用户")){
				return;
			}	
			var url="lock.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.updateDataRow(DataTableCl.tableName,data.id,0,'getUserDataRow.do');
					alert("锁定成功");
				} else {
					alert("锁定失败");
					return ;			
				}
			});
		},
		/**
		 * 解锁用户
		 */
		unlock: function(id){
			if(!confirm("确定要解锁用户")){
				return;
			}	
			var url="unlock.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.updateDataRow(DataTableCl.tableName,data.id,0,'getUserDataRow.do');
					alert("解锁成功");
				} else {
					alert("解锁失败");
					return ;			
				}
			});
		}
	};
}();

/**
 * 查询参数的处理，每个功能的DataTable都要处理自己的查询条件，并向服务器提交
 * 如果使用了DataTables控件，则都要定义这个函数
 */
var aoDataHandler = function(aoData) {
	//页面的查询条件
	//aoData.push( { "name": "name", "value": "" } );
}