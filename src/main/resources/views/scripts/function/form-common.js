function FormUtils(){
	var saveUrl;
	var updateUrl;
	var queryUrl;
	var deleteUrl;
	var saveTitle;
	var editTitle;

	this.fnSave=function(url,title){
		this.saveUrl=url;
		this.saveTitle=title;
	};
	this.fnUpdate=function(url,title){
		this.updateUrl=url;
		this.editTitle=title;
	};
	this.fnDelete=function(url){
		this.deleteUrl=url;
	};
	this.getSaveUrl=function(){
		return this.saveUrl;
	};
	this.getUpdateUrl=function(){
		return this.updateUrl;
	};
	this.getDeleteUrl=function(){
		return this.deleteUrl;
	};
	this.getSaveTitle=function(){
		return this.saveTitle;
	};
	this.getEditTitle=function(){
		return this.editTitle;
	};
}
var formUtil = new FormUtils();
var url;
function add(){
    $('#dlg').dialog('open').dialog('setTitle',formUtil.getSaveTitle());
    $('#fm').form('clear');
    url = formUtil.getSaveUrl();
};
function edit(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle',formUtil.getEditTitle());
        $('#fm').form('load',row);
        url = formUtil.getUpdateUrl()+'?id='+row.id;
    }
};
function save(){
    $('#fm').form('submit',{
       url: url,
       onSubmit: function(){
           return $(this).form('validate');
       },
       success: function(result){
            var result = eval('('+result+')');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
               });
            } else {
                $('#dlg').dialog('close');        // close the dialog
                $('#dg').datagrid('reload');    // reload the user data
            }
        }
   });
};
function destroy(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('确认','你确定你想删除这条记录吗?',function(r){
            if (r){
               $.post(formUtil.getDeleteUrl(),{id:row.id},function(result){
                   if (result.success){
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.errorMsg
                        });
                   }
               },'json');
            }
       });
    }
};
