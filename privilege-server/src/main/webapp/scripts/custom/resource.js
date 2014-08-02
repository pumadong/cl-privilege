var selectedResourceNode;

var ResourceTree = function () {

     var ajaxResourceTree = function() {

        $("#resource_tree").jstree({
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
                		return 'getResourceTree.do';
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
            "state" : { "key" : "resourceTree" },
            "plugins" : [ "state", "types" ]
        })
        .on('select_node.jstree', function (e, data) {
        	var id = data.node.id;
        	//只处理菜单资源，不处理模块
        	if(!isNaN(id))
        	{
        		loadNodeData(id);
        	} else {
        		clearInputValue();
        	}
        });
    
    }

    return {
        //main function to initiate the module
        init: function () {
    		ajaxResourceTree();
        }
    };

}();

var ResourceForm = function () {

    var handleValidation = function() {
        	// for more info visit the official plugin documentation: 
            // http://docs.jquery.com/Plugins/Validation

            var form1 = $('#resource_form');
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
                    url: {
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
		$("#url").attr("value",node.url);
		$("#url").blur();
		$("#sortNo").attr("value",node.sortNo);
		$("#sortNo").blur();
		$("#remark").attr("value",node.remark);
		
		selectedResourceNode = node;
	});
}

//增加节点
function addMenuNode(){
	
	var ref = $('#resource_tree').jstree(true),sel = ref.get_selected(true);
	
	if(!sel.length) {
		alert('请选择一个节点！');
		return false; 
	}
	sel = sel[0];
	
	var newNodeText = $('#name').val();

	if(newNodeText == sel.text){
		alert('子级名称不能与父级相同！');
		return false;
	}	
	
	var url="add.do";
	var data={
		"name":$("#name").val(),
		"url":$("#url").val(),
		"moduleFlag":sel.li_attr.flag,
		"remark": $("#remark").val(),
		"sortNo" : $("#sortNo").val(),
		"parentId":isNaN(sel.id) ? 0 : sel.id
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
				flag:sel.li_attr.flag,
				sortNo:node.sortNo,
				icon: "fa fa-briefcase icon-success"
			};
			
			//找出根据sortNo，节点应该插入的位置pos
			var sons = ref.get_json(sel,{no_children:false,no_state:true});
			var pos = 0;
			for(i=0;i<sons.children.length;i++)
			{
				if(parseInt(node.sortNo) > parseInt(sons.children[i].li_attr.sortNo))
				{
					pos = i+1;
				}
			}
			
			ref.create_node(sel, nodeData,pos+'');	
			ref.set_icon(sel,"fa fa-folder icon-warning icon-lg");
			ref.open_node(sel);
			
			clearInputValue();
		}
	});
}

//修改节点数据
function updateMenuNode(){
	
	var ref = $('#resource_tree').jstree(true),sel = ref.get_selected(true);
	
	if(!sel.length) {
		alert('请选择一个节点！');
		return false; 
	}
	sel = sel[0];
	
	if($("#id").val() == null || $("#id").val() == ""){
		alert('请选择要修改的节点!');
		return ;
	}
	
	var data={
		"name":$("#name").val(),
		"url": $("#url").val(),
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
		
		var node = eval("("+result+")");

		ref.rename_node(sel,node.name)
		
		if(selectedResourceNode.sortNo != node.sortNo)
		{
			//找出根据sortNo，节点应该插入的位置pos
			var parent = ref.get_node(ref.get_parent(sel));
			var sons = ref.get_json(parent,{no_children:false,no_state:true});
			var pos = 0;
			for(i=0;i<sons.children.length;i++)
			{
				if(parseInt(node.sortNo) > parseInt(sons.children[i].li_attr.sortNo))
				{
					pos = i+1;
				}
			}
			ref.move_node(sel,parent,pos+'');
		}
		
		clearInputValue();
		
		alert("修改成功");
	});	
}

//删除节点
function removeMenuNode(){

	var ref = $('#resource_tree').jstree(true),sel = ref.get_selected(true);
	
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
		"id":sel.id
	};

	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');

		if(result == "success"){			
			
			//如果父节点是叶子了，给个新样式
			var parent = ref.get_node(ref.get_parent(sel));
			var sons = ref.get_json(parent,{no_children:false,no_state:true});
			if(sons.children.length == 1)
			{
				ref.set_icon(parent,"fa fa-briefcase icon-success");
			}
			
			ref.delete_node(sel);
			
			clearInputValue();
			alert("删除成功");
			return ;
		}else{
			alert("删除失败,被使用的菜单不允许删除");
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
	$("#url").attr("value","");
	$("#url").blur();
	$("#remark").val("");
	$("#sortNo").attr("value","");
	$("#sortNo").blur();
	
}