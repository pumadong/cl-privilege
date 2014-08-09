var Cl = function() {	
    return {
    	action: "",
    	selected: {},
    	tableName: "datatable_cl",
    	modalName: "modal_cl",
    	formName: "form_cl",
    	treeName: "tree_cl",
    	ajaxRequest: function (url,reqParam,callback) {
	    	$.ajax({
	  		  type: 'POST',
	  		  url: url,
	  		  data: reqParam,
	  		  cache: false,
	  		  success: callback
	    	});
        },
        refreshDataTable: function(name) {
        	var oTable = $('#'+name).dataTable();
        	oTable.fnDraw();
        },
        updateDataRow: function(tableName,id,idindex,url) {
        	var data={
        		"id":id
        	};
        	Cl.ajaxRequest(url,data,function(result){
        		if(!result) return ;        		
        		//result = result.replace(/(^\s*)|(\s*$)/g,'');
        		var oTable = $('#'+tableName).dataTable();
        		var nNodes = oTable.fnGetNodes();
        		for(var i=0;i<nNodes.length;i++) {
        			if(nNodes[i].cells[idindex].innerText == id)
        			{
        				var result = eval("("+result+")");
        				oTable.fnUpdate( result, i,undefined,false);
        				break;
        			}
        		}
        	});
        },
        deleteDataRow: function(tableName,id,idindex) {
    		var oTable = $('#'+tableName).dataTable();
    		var nNodes = oTable.fnGetNodes();
    		if(nNodes.length == 1)
    		{
    			oTable.fnDraw();
    			return;
    		}
    		for(var i=0;i<nNodes.length;i++) {
    			if(nNodes[i].cells[idindex].innerText == id)
    			{
    				oTable.fnDeleteRow(i);
    				break;
    			}
    		}
        },
        initModal: function() {
            // general settings
            $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner = 
              '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
                '<div class="progress progress-striped active">' +
                  '<div class="progress-bar" style="width: 100%;"></div>' +
                '</div>' +
              '</div>';
            $.fn.modalmanager.defaults.resize = true;
            $("<div id=\"modal_cl\" class=\"modal fade\" tabindex=\"-1\"></div>").appendTo($('body'));
        },
        showModalWindow: function(modalName,url) {
        	var $modal = $('#'+modalName);
        	$('body').modalmanager('loading');
            setTimeout(function(){
            	$modal.load(url, '', function(){
            		$modal.modal();
            	});
            }, 1000);
        },
        hideModalWindow: function(modalDiv) {
        	var $modal = $('#'+modalDiv);
        	$modal.modal("hide")
        },
        initModifyPassword: function(url)
        {
            var handleValidation = function() {
            	// for more info visit the official plugin documentation: 
                // http://docs.jquery.com/Plugins/Validation
                var form1 = $('#form_cl_mp');
                var error1 = $('.alert-danger', form1);
                var success1 = $('.alert-success', form1);
                form1.validate({
                    errorElement: 'span', //default input error message container
                    errorClass: 'help-block', // default input error message class
                    focusInvalid: false, // do not focus the last invalid input
                    ignore: "",
                    rules: {
                        oldpassword: {
                            minlength: 2,
                            required: true
                        },
                        password: {
                            minlength: 2,
                            required: true
                        },
                        confirmpassword: {
                            minlength: 2,
                            required: true,
                            equalTo: "#password"
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
        				modify();
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
            var modify = function() {
				var data={
					"oldpassword":$("#oldpassword").val(),
					"password": $("#password").val()
				};
				Cl.ajaxRequest(url,data,function(result){
					if(!result) return ;						
					result = result.replace(/(^\s*)|(\s*$)/g,'');
					if(result == "success"){						
						alert("修改成功");
						Cl.hideModalWindow(Cl.modalName);
					} else {
						alert("旧密码输入错误");
						return ;			
					}
				});
            }
            
            handleWysihtml5();
            handleValidation();
        }
    };
}();