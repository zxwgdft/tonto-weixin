$.namespace2win('tonto.model');
tonto.model=function(name,config){
	
	var _self=this;
	_self.name=name;
	
	/**
	 * addUrl,updateUrl在get模式下为调用页面输入，post为保存页面输入
	 * searchUrl后会拼接pageNo+pageSize
	 * url都用/结尾
	 * 
	 * 这里的url都是ajax请求，form提交或其他都不需要在这里处理
	 */
	_self.config={
		searchUrl:name+"/page/",
		pageSize:20,
		currentPage:1,
		searchDto:{},
		
		pageContent:"#listDiv",
		
		addUrl:name+"/add/",
		updateUrl:name+"/update/",
		deleteUrl:name+"/delete/ajax/",
	
		updateForm:"#updateForm",
		updateModal:"#updateModal",
		addForm:"#addForm",
		addModal:"#addModal"		
	}
	$.extend(_self.config,config);
	
	var _config=_self.config;
	
	//跳转数据页，访问url格式为searchUrl+pageNo+pageSize,以/分隔
	_self.skip=function(pageNo,pageSize,fun) {	
		_config.currentPage = pageNo;
		_config.pageSize=pageSize?pageSize:20;
		$(_config.pageContent).loadContent(_config.searchUrl+_config.currentPage+"/" + _config.pageSize,_config.searchDto,fun);
	}
	//获取add输入页面
	_self.add=function(){
		$(_config.addModal).modal({
			backdrop:"static",
			show:true,
			remote:_config.addUrl
		});
	}
	
	//update输入页面每次都重新请求获取
	$(_config.updateModal).on("hidden.bs.modal", function() {
	    $(_config.updateModal).removeData("bs.modal");
	})
	//获取update输入页面
	_self.update=function(id){
		$(_config.updateModal).modal({
			backdrop:"static",
			show:true,
			remote:_config.updateUrl+id
		});	
	}
	
	_self.saveAdd=function(){
		$(_config.addForm).formAjaxSubmit(function(){
			$.alertSuccess("保存成功",function(){
				$(_config.addModal).modal("hide");
				_self.skip(_config.currentPage);
			});			
		});
	}
	
	_self.saveUpdate=function(){
		$(_config.updateForm).formAjaxSubmit(function(){
			$.alertSuccess("修改成功",function(){
				$(_config.updateModal).modal("hide");
				_self.skip(_config.currentPage);
			});	
		});
	}
	
	
	_self.remove=function(id)
	{
		$.alertConfirm("确定删除？",function(result){
			if(result)
			{
				$.getRequest(_config.deleteUrl + id, function() {
						$.alertSuccess("删除成功!",function(){
							_self.skip(_config.currentPage);
						});						
				});
			}
		});
	}
}

