var TreeCl = function () {
     var ajaxTreeCl = function() {
        $("#tree_cl").jstree({
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
        		Resource.load(id);
        	} else {
        		Resource.clear();
        	}
        });
    
    }
    return {
        //main function to initiate the module
        init: function () {
    		ajaxTreeCl();
        }
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
                if(Cl.action == 'create')
      			{
      				Resource.add();
      			} else
      			{
      				if(Cl.action == 'update')
      				{
      					Resource.update();
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
        }
    };
}();

var Resource = function(){
	return {
		load: function(id){
			var url = "get.do";
			var data={
				"id":id
			};	
			Cl.ajaxRequest(url,data,function(result){
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				//如果获取数据为空  则清空数据
				if(result.length == ""){
					Resource.clear();
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
				Cl.selected = node;
			});
		},
		//增加节点
		add: function(){	
			var ref = $('#tree_cl').jstree(true),sel = ref.get_selected(true);	
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
			Cl.ajaxRequest(url,data,function(result){
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
					Resource.clear();			
					alert("添加成功");
				}
			});
		},
		//修改节点数据
		update: function(){	
			var ref = $('#tree_cl').jstree(true),sel = ref.get_selected(true);	
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
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result.length == ""){
					alert("修改失败");
					return ;			
				}		
				var node = eval("("+result+")");
				ref.rename_node(sel,node.name)		
				if(Cl.selected.sortNo != node.sortNo)
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
				Resource.clear();		
				alert("修改成功");
			});	
		},
		//删除节点
		remove: function(){
			var ref = $('#tree_cl').jstree(true),sel = ref.get_selected(true);	
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
			Cl.ajaxRequest(url,data,function(result){
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
					Resource.clear();
					alert("删除成功");
					return ;
				}else{
					alert("删除失败,被使用的菜单不允许删除");
				}
			});
		},
		//清除输入框内容
		clear: function(){
			var validator = $( "#form_cl" ).validate();
			validator.resetForm();
			
			//$("#id").attr("value","");
			$("#name").attr("value","");
			$("#name").blur();
			$("#url").attr("value","");
			$("#url").blur();
			$("#remark").val("");
			$("#sortNo").attr("value","");
			$("#sortNo").blur();
		}
	};
}();