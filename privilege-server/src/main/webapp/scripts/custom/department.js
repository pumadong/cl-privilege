var DepartmentTree = function () {

     var ajaxDepartmentTree = function() {

        $("#department_tree").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                //不允许多选
                "multiple":false,
                // so that create works
                "check_callback" : true,
                'data' : {
                    'url' : function (node) {
                		return 'getDepartmentTree.do';
                    },
                    'data' : function (node) {
                    	return { 'parent' : node.id };
                    }
                }
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder icon-warning icon-lg"
                },
                "file" : {
                    "icon" : "fa fa-file icon-warning icon-lg"
                }
            },
            "state" : { "key" : "departmentTree" },
            "plugins" : [ "state", "types" ]
        })
        .on('select_node.jstree', function (e, data) {
            //alert(data.selected);
        	loadNodeData(data.instance.get_node(data.selected).id);
        });;
    
    }

    return {
        //main function to initiate the module
        init: function () {
    		ajaxDepartmentTree();
        }
    };

}();

var DepartmentForm = function () {

    var handleValidation = function() {
        	// for more info visit the official plugin documentation: 
            // http://docs.jquery.com/Plugins/Validation

            var form1 = $('#department_form');
            var error1 = $('.alert-danger', form1);
            var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    name: {
                        minlength: 2,
                        required: true
                    },
                    sortNo: {
                    	maxlength:5,
                        required: true,
                        number: true
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
                    if(button == 'create')
          			{
          				addMenuNode();
          			} else
          			{
          				if(button == 'update')
          				{
          					updateMenuNode();
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
                "stylesheets": ["assets/plugins/bootstrap-wysihtml5/wysiwyg-color.css"]
            });
        }
    }

    return {
        //main function to initiate the module
        init: function () {

            handleWysihtml5();
            handleValidation();

        }

    };

}();

//操作按钮：create/update
var button = "";

function loadNodeData(nodeid){
	var url = "get.do";
	var data={
		"id":nodeid
	};
	
	ajaxRequest(url,data,function(result){

		result = result.replace(/(^\s*)|(\s*$)/g,'');

		//如果获取数据为空  则清空数据
		if(result.length == ""){
			clearInputValue();
			return ;
		}

		var node = eval("("+result+")");
		
		$("#id").attr("value",node.id);
		$("#name").attr("value",node.name);
		$("#name").blur();		
		$("#sortNo").attr("value",node.sortNo);
		$("#sortNo").blur();
		$("#remark").attr("value",node.remark);
	});
}

//增加节点
function addMenuNode(){
	
	var ref = $('#department_tree').jstree(true),sel = ref.get_selected(true);
	
	if(!sel.length) {
		alert('请选择一个节点！');
		return false; 
	}
	sel = sel[0];
	
	var newNodeText = $('#name').val();

	if(newNodeText == $(sel).attr("text")){
		alert('子目录名称不能与父级相同！');
		return false;
	}	
	
	var url="add.do";
	var data={
		"name":$("#name").val(),
		"remark": $("#remark").val(),
		"sortNo" : $("#sortNo").val(),
		"parentId":$(sel).attr("id")
	};
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result.length == ""){
			alert("增加失败");
			return ;
			
		}
		var node = eval("("+result+")");
		
		if(node.id){
			var nodeData = {
				id:node.id,
				text:node.name,
				icon: "fa fa-briefcase icon-success"
			};
			
			ref.create_node(sel, nodeData,"last");	
			ref.set_icon(sel,"fa fa-folder icon-warning icon-lg");
			ref.open_node(sel);
			
			clearInputValue();
		}
	});
}

//修改节点数据
function updateMenuNode(){
	
	if(!confirm("确认修改?"))return 
	
	
	if($("#id").val() == null || $("#id").val() == ""){
		alert('请选择你要修复的部门!');
		return ;
	}
	
	var data={
		"name":$("#name").val(),
		"remark": $("#remark").val(),
		"sortNo" : $("#sortNo").val(),
		"id":$("#id").val()
	};
	
	var url="update.do";
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result.length == ""){
			alert("修改失败");
			return ;			
		}
		
		var resultNode = eval("("+result+")");
		var ref = $('#department_tree').jstree(true),sel = ref.get_selected(true);
		ref.rename_node(sel,resultNode.name)
		
		clearInputValue();
		
		alert("修改成功");
	});	
}

//删除节点
function removeMenuNode(){

	var ref = $('#department_tree').jstree(true),sel = ref.get_selected(true);
	
	if(!sel.length) {
		alert('请选择要删除的节点！');
		return false; 
	}
	sel = sel[0];
	
	if(!ref.is_leaf(sel)){
		alert("只有叶子结点才能删除！");
		return false;
	}
	
	if(!confirm("确认删除?")) return;
	
	var url = "delete.do";
	var data={
		"id":$(sel).attr("id")
	};

	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');

		if(result == "success"){			
			ref.delete_node(sel);			
			clearInputValue();
			alert("删除成功");
			return ;
		}else{
			alert("删除失败,被使用的部门不允许删除");
		}
	});
}

//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: false,
		  success: callback
	});
}

//清除输入框内容
function clearInputValue(){
	$("#id").attr("value","");
	$("#name").attr("value","");
	$("#name").blur();
	$("#remark").val("");
	$("#sortNo").attr("value","");
	$("#sortNo").blur();
	
}