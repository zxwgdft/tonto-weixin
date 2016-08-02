(function($) {
	$.namespace2win('tonto.loupan');

	var tonto_loupan_Selector = function(btn,config) {
		
		var _self = this;
		var r=new Date().getTime();
		
		_self.config={
			allBtn:false,
			showBtn:false,
			showBtnCaret:true,
			loaded:false
		}
		
		$.extend(_self.config,config);
		config=_self.config;
		
		var container=$("<div class='dropdown-menu'><div class='loupan-selector-main'><div align='right'>"+
			"<button class='close' id='closeBtn"+r+"' style='float:none' type='button'>×</button></div><li class='divider'></li>" +
			"<div class='row loupan-selector-search'><label for='' class='col-md-1 form-control-static text-right' style='width:auto'>名称</label>" +
			"<div class='col-md-3' style='width:auto'><input id='loupan_name"+r+"' class='form-control' type='text' placeholder='请输入楼盘名称'/></div>" +
			"<label for='' style='width:auto' class='col-md-1 form-control-static text-right'>地区</label>" +		
			"<div class='col-md-3' style='width:200px'><input type='hidden' id='loupan_district_code"+r+"'>"+
			"<button type='button' class='btn btn-info btn-select' id='selectDistrictToSearchBtn"+r+"'>全部<span class='caret'></span></button></div>" +	
			"<div class='col-md-4' style='width:auto' align='right'><button id='search_btn"+r+"' class='btn btn-primary btn-md' type='button''>" +
			"<span class='glyphicon glyphicon-search'></span>查询</button></div></div>" +
			"<li class='divider'></li><div class='loupan-selector-list' id='lopan_list_div"+r+"'></div></div>");
		
		
		$(btn).after(container);
		
		_self.element = {
			selectBtn : $(btn),
			searchBtn : $("#search_btn"+r),
			dropdownDiv : container,
			listDiv:$("#lopan_list_div"+r),
			nameInput : $("#loupan_name"+r),
			districtInput : $("#loupan_district_code"+r),
			closeBtn:$("#closeBtn"+r)
		}
		
		var ele=_self.element;
		
		
		_self.addEventListener("checked",function(item){
			if(config.showBtn)
			{
				ele.selectBtn.html(item.loupanName+(config.showBtnCaret?"<span class='caret'></span>":""));	
			}
		});
		
		if(config.allBtn)
		{
			/*ele.allBtn=$("<button class='btn btn-primary btn-md' style='margin-left:10px;' type='button'>全部</button>");
			ele.allBtn.on("click",function(){
				_self.distribute("checked", {loupanName:'全部'});
				_self.toggle();
			});
			ele.searchBtn.after(ele.allBtn);*/
		}
		
		_self.districtSelector = new tonto.district.District("#selectDistrictToSearchBtn"+r,
				{single:false,showBtn:true});
		_self.districtSelector.addEventListener("checked", function(obj) {
			ele.districtInput.val(obj.key==-1?"":obj.key);
		});
		
		ele.selectBtn.on("click",function(){
			_self.toggle();
		});
		
		ele.searchBtn.on("click",function(){
			var data={};
			var lname=$.trim(ele.nameInput.val());
			var code=ele.districtInput.val();
			
			if(lname)
				data.loupanName=lname;
			if(code)
				data.districtCode=code*1;
			_self.searchData=data;
			_self.skip(1);
		});
		
		ele.closeBtn.on("click",function(){
			ele.dropdownDiv.hide();
		});
		
		_self.currentPage=1;
		
	}
	
	tonto_loupan_Selector.prototype = new tonto.event.Dispatcher();
	tonto_loupan_Selector.prototype.toggle=function(){
		var _self=this;
		_self.element.dropdownDiv.toggle();
		if(!_self.config.loaded)
		{
			_self.skip(_self.currentPage);
			_self.config.loaded=true;
		}
	};
	tonto_loupan_Selector.prototype.show=function(){
		var _self=this;
		_self.element.dropdownDiv.show();
		if(!_self.config.loaded)
		{
			_self.skip(_self.currentPage);
			_self.config.loaded=true;
		}
	};
	tonto_loupan_Selector.prototype.hide=function(){
		var _self=this;
		_self.element.dropdownDiv.hide();
		if(!_self.config.loaded)
		{
			_self.skip(_self.currentPage);
			_self.config.loaded=true;
		}
	};
	tonto_loupan_Selector.prototype.skip=function(pageNo)
	{
		var _self=this,pageNo=pageNo||1,ele=_self.element;	
		_self.currentPage = pageNo;	
		
		ele.listDiv.loadJsonContent($.getRootPath()+"/loupan/page/json/" + pageNo,_self.searchData,function(json){
				if(json.status==1)
				{
					json=json.result;
					var r=new Date().getTime();
					ele.listDiv.append("<table class='table table-condensed table-hover'><thead><tr>"
						+ "<th style='text-align: center'>楼盘名称</th><th style='text-align: center'>所在城市</th>"
						+ "</tr></thead><tbody id='loupanTbody"+r+"'></tbody></table><div align='center' id='loupanPager"+r+"'></div>");
					var $tbody=$("#loupanTbody"+r);
					
					var list=json.result;
					
					for(var i=0;i<list.length;i++)
					{
						var l=list[i];
						var tr=$("<tr align='center' style='cursor:pointer'><td>"+l.loupanName+"</td><td>"+l.fullDistrict+"</td><tr>");
						(function(a){
							tr.on("click",function(){
								_self.distribute("checked", a);
								_self.toggle();
							});
						})(l)
						
						
						$tbody.append(tr);
					}

					var page_options = {
		            	currentPage: json.pageNum,
		            	totalPages: json.pages,
		            	pageUrl: function(type, page, current){               
		            		return "javascript:void(0)";
		            	},
		            	onPageClicked: function(event, originalEvent, type,page){
		            		_self.skip(page);
		            	}
			        }
					$("#loupanPager"+r).bootstrapPaginator(page_options);
				}
				else
				{
					var msg="保存出错！";
					msg+=json.msg||"";
					$.alertError("获取楼盘信息出错！" + msg);
					return;
				}
		});
	}
		
	tonto.loupan.Selector = tonto_loupan_Selector;
})(jQuery);