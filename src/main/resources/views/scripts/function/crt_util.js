crt_util={
		showMask : function(msg, el, m) {
			var el = $(document.body) || el;
			var msg = $('<div class="x-masked-text"><i class="fa-spinner fa-spin orange bigger-125"></i>'
				+ msg + '</div>').appendTo(el);
			var top = (el.outerHeight() - msg.outerHeight()) / 2;
			msg.css({
					left : (el.outerWidth() - msg.outerWidth()) / 2,
					top : top < 150 ? 150 : top
				});
		},
		hideMask : function() {
			$('div.x-masked-text').remove();
		},
		jqGrid : function(q, p) {
			$(q).jqGrid($.extend({
						height : 'auto',
						width:'100%',
						datatype : 'json',
						jsonReader : {
							root:"records",
					   		total:"totalpage",
					   		page: "currentpage",
					   		records:"totalrecord",
					   		repeatitems: false
						},
						loadComplete : function() {
							var table = this;
							setTimeout(function(){
								//styleCheckbox(table);
								
								//updateActionIcons(table);
								crt_util.updatePagerIcons(table);
								//enableTooltips(table);
							}, 0);
						},
						ajaxGridOptions : {
							method : 'post'
						},
						multiselect : true,
						viewrecords : true,
						rownumWidth : 40,
						mtype:'post',
						type:'post',
						rownumbers : true,
						rowNum : 10,
						pager : '#grid-pager',
						rowList : [10,20,30],
						altRows : false,
						prmNames:{nd:null},
						multiboxonly : false
//						,autowidth : true
					}, p));
		},
		showjBox : function(o) {// 打开弹出窗口
			var c = $.extend({
						id : 'jbox-win',
						border : 0,
						persistent : true,
						buttons : {},
						closed : function() {
							if ($.jBox.refresh === true) {
								crt_util.refreshGrid(o.grid || '#grid-table');
								delete $.jBox.refresh;
							}
						}
					}, o);
			var width = parseFloat(c.width || crt_constant.JBOX_WIDTH);
			var height = parseFloat(c.height || crt_constant.JBOX_HEIGHT);
			c.width = width;
			c.height = height;
			$.jBox.open("iframe:" + o.url, c.title || '', width, height, c);
		},
		closejBox : function(token) {// 关闭窗口
			parent.$.jBox.close(token);
		},
		refreshGrid : function(grid,queryForm) {// 刷新grid
			//$(grid).trigger("reloadGrid");
			$(grid).jqGrid('setGridParam',{  
			    datatype:'json',  
			    postData:crt_util.formObjectJson(queryForm), //发送数据  
			    page:1  
			}).trigger("reloadGrid"); //重新载入
		},
		showMessage : function(jqXHR, data, fn) {
			var s = false;
			//alert(JSON.stringify(data));
			if (jqXHR.status == 404) {
				bootbox.alert('请求地址不存在！');
			} else if (data && data.success === false) {
				var msg = ['错误代码：' + data.code + ',' + data.msg];
				if (data.stackTrace) {
					msg.push('<a data-toggle="collapse" class="error-collapsed" href="#error-panel-body" title="详细信息"><i class="fa-chevron-down"></i></a>');
					msg.push('<div id="error-panel-body" class="panel-collapse collapse"><pre>'
									+ data.msg + '</pre></div>');
				}
				
				bootbox.alert(msg.join(''));
				crt_util.closejBox('jbox-win');
			} else if (data === false) {
				bootbox.alert('系统错误！');
			} else if (jqXHR.getResponseHeader("sessionstatus") == 'timeout') {
				$.jBox.tip('登录超时，请重新登录！');
			} else {
				s = true;
			}

			if (typeof fn == 'function') {
				fn.call(this, s, data);
			}

		},
		buttonDisabled : function() {// 禁用按钮
			$('.btn.btn-danger').addClass('disabled').attr('disabled', true);
		},
		buttonEnable : function() {// 启用
			$('.btn.btn-danger').removeClass('disabled').attr('disabled', false);
		},
		formObjectJson : function(form) {
			var json = {};
			var a = $(form).serializeArray();
			for (var i = 0; i < a.length; i++) {
				if(null!=json[a[i].name]&&json[a[i].name].length>0){
					json[a[i].name] = (json[a[i].name]+","+a[i].value);
				}else{
				json[a[i].name] = a[i].value;
				}
			}
			return json;
		},
		queryForm : function(form, jqgrid) { // 查询列表
			$(jqgrid).jqGrid('setGridParam', {
						page : 1,
						datatype:'json',
						type:'post',
						mtype:'post',
						postData : crt_util.formObjectJson(form)
					}).trigger("reloadGrid");
		},
		//情况form
		clearForm :function(form,jqgrid){
            //清空form
			$(form).clearForm();
			$(jqgrid).jqGrid('setGridParam', {
				start : 1,
				datatype:'json',
				type:'post',
				mtype:'post',
				postData : crt_util.formObjectJson(form)
			}).trigger("reloadGrid");
		},
		submitForm : function(form,functionName) {
			if (crt_util.validate(form)) {
				try{
					if(functionName && typeof(functionName)!="undefined"){
						var result = eval(functionName+"();");
						if(result==false){return false;}
					}
				}catch(e){console.warn(e);}
				crt_util.buttonDisabled();
				crt_util.showMask('数据保存中....');
				$.ajax({
							url : $(form).attr('action'),
							type : 'post',
							dataType : 'json',
							data : crt_util.formObjectJson(form),
							success : function(data, status, jqXHR) {
								crt_util.hideMask();
								crt_util.buttonEnable();
								crt_util.showMessage(jqXHR, data, function(s) {
									        if(s){
												$.jBox.tip('保存成功！', 'success');
												$.jBox.refresh = true;
												crt_util.closejBox('jbox-win');
									        }
											
								});
							}
				});
			}
		},
		submitPageForm : function(form,rules) {
			if (crt_util.validate(form,rules)) {
				crt_util.showMask('数据保存中....');
				crt_util.buttonDisabled();
				$(form)[0].submit();
			}
		},
		ajaxSubmitForm : function(form,rules,callback) {
			if (crt_util.validate(form,rules)) {
				crt_util.showMask('数据保存中....');
				crt_util.buttonDisabled();
				$.ajax({
					url : $(form).attr('action'),
					type : 'post',
					dataType : 'json',
					data : crt_util.formObjectJson(form),
					success : function(data, status, jqXHR) {
						crt_util.hideMask();
						crt_util.buttonEnable();
						crt_util.showMessage(jqXHR, data,callback);
					}
				});
			}
		},
		validate : function(f, o) {
			var p = $.extend({
						meta: "validate",
						errorElement : 'div',
						errorClass : 'help-block',
						highlight : function(e) {
							$(e).closest('.form-group').removeClass('has-info')
									.addClass('has-error');
						},
						success : function(e) {
							$(e).closest('.form-group').removeClass('has-error')
									.addClass('has-info');
							$(e).remove();
						},
						errorPlacement : function(error, element) {
							if (element.parent().is('.input-group')) {
								error.insertAfter(element.parent());
							} else {
								error.insertAfter(element);
							}
						}
					}, o);
			var v = $(f).validate(p);
			return v.form();
		},
		validateNewForm:function(f, o){
			var p = $.extend({
				meta: "validate",
				errorElement : 'div',
				errorClass : 'help-block',
				highlight : function(e) {
					$(e).closest('.form-group').removeClass('has-info')
							.addClass('has-error');
				},
				success : function(e) {
					$(e).closest('.form-group').removeClass('has-error')
							.addClass('has-info');
					$(e).remove();
				},
				errorPlacement : function(error, element) {
					if (element.parent().is('.input-group')) {
						error.insertAfter(element.parent());
					} else {
						error.insertAfter(element);
					}
				}
			}, o);
			var v = $(f).validate(p);
			return v.form();
		},
		getValidateNew:function(o){
			var p = $.extend({
				meta: "validate",
				errorElement : 'div',
				errorClass : 'help-block',
				highlight : function(e) {
					$(e).closest('.form-group').removeClass('has-info')
							.addClass('has-error');
				},
				success : function(e) {
					$(e).closest('.form-group').removeClass('has-error')
							.addClass('has-info');
					$(e).remove();
				},
				errorPlacement : function(error, element) {
					if (element.parent().is('.input-group')) {
						error.insertAfter(element.parent());
					} else {
						error.insertAfter(element);
					}
				}
			}, o);
			return p;
		},
		loginWin : function() {
			top.$.jBox.open("iframe:/login/toLoginWin", "用户登录", 450, 285, {
						id : 'loginBox',
						border : 0,
						persistent : true,
						iframeScrolling : 'no',
						buttons : {}
					});
		},
		updatePagerIcons:function(table){
			var replacement = 
			{
				'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
				'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
				'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
				'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
			};
			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
				
				if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
			})
		}
		
}

