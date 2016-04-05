$.jBox.setDefaults({
			tipDefaults : {
				top : 0
			}
		});
$(document).ready(function(){
	$.ajaxSetup({
		error : function(jqXHR, textStatus, errorThrown) {
			crt_util.showMessage(jqXHR, false);
			crt_util.hideMask();
			crt_util.buttonEnable();
		},
		complete : function(jqXHR, textStatus) {
			var s = jqXHR.getResponseHeader("sessionstatus");
			if (s == "timeout") {
				//crt_util.loginWin();
				window.document.location.href = "../../login/toLogin";
				return false;
			}
		}
	});
	
	var t = '[data-toggle="jBox-win"],[data-toggle="jBox-view"],[data-toggle="jBox-submit"],[data-toggle="jBox-bill"],[data-toggle="jBox-edit"],[data-toggle="jBox-remove"],[data-toggle="jBox-update"],[data-toggle="jBox-close"],[data-toggle="jBox-close-refresh"],[data-toggle="jBox-refresh"],[data-toggle="jBox-call"],[data-toggle="jBox-query"],[data-toggle="jBox-download"],[data-toggle="page-submit"],[data-toggle="jBox-clear-form"],[data-toggle="jBox-menuResource-close"]';

	$(document).on('click', t, function(e) {
		var $t = $(this);
		var toggle = $t.attr('data-toggle');
		var fn = $t.attr('data-fn');
		var form = $t.attr('data-form') || '#query-form';
		var grid = $t.attr('data-grid') || '#grid-table';
		var url = $t.attr('href');
		var w = $t.attr('jBox-width');
		var h = $t.attr('jBox-height');
		var t = $t.attr('title') || $t.text();
		var type = $t.attr('data-type');
		var callback = $t.attr('call-back');
		
		if (toggle == 'jBox-close') {
			$.jBox.close();
		}else if(toggle=='jBox-close-refresh'){
			$.jBox.refresh = true;
			crt_util.closejBox('jbox-win');
		}else if(toggle == 'jBox-menuResource-close'){
			$('[data-toggle="jBox-audit-userfunctioncheck"]').removeAttr("disabled");
			$('[data-toggle="jBox-audit-userfunctionnocheck"]').removeAttr("disabled");
			$.jBox.close();
		}else if (toggle == 'jBox-refresh') {
			document.location.reload();
		} else if (toggle == 'jBox-query') {
			crt_util.queryForm(form, grid);
		} else if (toggle == 'jBox-submit') {
			var fn = fn ? eval(fn) : crt_util.submitForm;
			fn.call(this, form,callback);
		} else if (toggle == 'page-submit') {
			var fn = fn ? eval(fn) : crt_util.submitPageForm;
			fn.call(this, form);
		}  else if (toggle == 'jBox-download') {
			window.open(url);
		} else if (toggle == 'jBox-bill') {
			var fn = fn ? eval(fn) : queryForm.submitBill;
			fn.call(this, form, grid);
		} else if (toggle == 'jBox-edit' || toggle == 'jBox-view') {
			var ids = $(grid).jqGrid('getGridParam', 'selarrrow');
			if (ids.length <= 0) {
				$.jBox.tip('请选择一条记录！');
				return;
			} else if (ids.length > 1) {
				$.jBox.tip('选择记录不能超过一条！');
				return;
			}
			url = url + '?id=' + ids[0];
			if (type == 'tab') {
				var tab = top.$('#home-tabs').ajaxTab($(this), t, url);
				if (tab !== false) {
					$('iframe', tab.data('target')).data('grid', $(grid));
				}
			} else {
				crt_util.showjBox({
							width : w,
							height : h,
							title : t,
							url : url,
							grid : grid
						});
			}

		} 
		else if (toggle == 'jBox-remove') {
			var ids = $(grid).jqGrid('getGridParam', 'selarrrow');
			if (ids.length <= 0) {
				$.jBox.tip('请选择一条记录！');
				return;
			}
			$.jBox.confirm("确认要删除该数据?", "提示", function(v){
				if(v == 'ok'){
					crt_util.showMask('正在删除数据，请稍等...');
					$.ajax({
						url : url,
						type :'post',
						dataType : 'json',
						data : 'id=' + ids,
						success : function(data, status, jqXHR) {
							crt_util.hideMask();
							crt_util.showMessage(jqXHR,
									data, function(s) {
										if (s) {
											if(data.success==true){
												$.jBox.tip('删除成功！','success');
											}else{
												$.jBox.tip('删除失败！','error');
											}
											crt_util.refreshGrid(grid);
										}
							});
						}
					});
				}
			});
		} 
		else if (toggle == 'jBox-update') {
			var ids = $(grid).jqGrid('getGridParam', 'selarrrow');
			if (ids.length <= 0) {
				$.jBox.tip('请选择一条记录！');
				return;
			}
			$.jBox.confirm("确认要解锁该数据?", "提示", function(v){
				if(v == 'ok'){
					crt_util.showMask('正在解锁数据，请稍等...');
					$.ajax({
						url : url,
						type :'post',
						dataType : 'json',
						data : 'id=' + ids,
						success : function(data, status, jqXHR) {
							crt_util.hideMask();
							crt_util.showMessage(jqXHR,
									data, function(s) {
										if (s) {
											top.$.jBox.tip('成功！','success');
											crt_util.refreshGrid(grid);
										}
									});
						}
					});
				}
			});
		}
		else if (toggle == 'jBox-win') {
			crt_util.showjBox({
						width : w,
						height : h,
						title : t,
						url : url,
						grid : grid
					});
		}else if (toggle == 'jBox-call' && fn) {
			eval(fn).call(this);
		}else if(toggle == 'jBox-clear-form'){
			crt_util.clearForm(form, grid);
		}
		return false;
	})
	
	
});