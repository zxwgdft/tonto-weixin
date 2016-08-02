(function($){
	$.namespace2win('tonto.form.ui');
	var tonto_form_ui_FormUtil=function(){
		this._formatFunMap={
			percent:function(v,format){
				format=format||2;
				return v&&((v*100).toFixed(format*1));
			},
			date:function(v,format){
				format=format||"yyyy-MM-dd";
				if(v)
				{
					//暂不考虑  20150604这种年月日没有分隔符的格式，可自己再写一个判断
					var matchs=v.match(/\d+/g)
					if(matchs)
					{
						var y=matchs[0]||"2000";
						var M=matchs[1]||"01";
						var d=matchs[2]||"01";
						var h=matchs[3]||"00";
						var m=matchs[4]||"00";
						var s=matchs[5]||"00";
						
						format=format.replace(/yyyy/g, y);
						format=format.replace(/MM/g, M);
						format=format.replace(/dd/g, d);
						format=format.replace(/hh/g, h);
						format=format.replace(/mm/g, m);
						format=format.replace(/ss/g, s);
						
						return format;
					}
				}
				return null;
			}
		}		
	}
	tonto_form_ui_FormUtil.prototype.addFormat=function(type,fun){
		this._formatFunMap[type]=fun;
	};
	tonto_form_ui_FormUtil.prototype.removeFormat=function(type){
		delete this._formatFunMap[type];
	};
	
	//为input标签创建单位
	tonto_form_ui_FormUtil.prototype.createInputTagUnit=function(input){	
		var $input=$.toJQuery(input);
		var unit=$input.attr("data-unit");
		if(unit&&$input)
		{
			$input.wrap("<div class='ui-unit-box'></div>");		
			var $span=$("<span class='unit-text'>"+unit+"</span>");
			$span.insertAfter($input);
			$input.css("textAlign","right");												
			var fontsize=$span.css("fontSize")||"12px";	
			
			var fontlen=fontsize.substring(0,fontsize.length-2)*1;
			var fontunit=fontsize.substring(fontsize.length-2);
			var len=Math.round(unit.replace(/[^\x00-\xff]/g, 'xx').length*fontlen/2+fontlen);
			
			$input.css("paddingRight",len+fontunit);
		}
	}
	
	//格式化处理input标签的值
	//如date|yy-MM-dd 或 percent|2,以分隔符分割格式的名称和样式
	tonto_form_ui_FormUtil.prototype.formatInputTagValue=function(input){
		var $input=$.toJQuery(input);
		var formatType=$input.attr("data-format-type");
				
		if(!formatType||!$input)
			return;
		
		var i=formatType.indexOf("|")
		var format=i>=0?formatType.substring(i+1):null;
		var type=formatType.substring(0,i);
				
		var result=this._formatFunMap[type]&&this._formatFunMap[type]($input.val(),format);
		$input.val(result?result:"");	
	}
	
	tonto.form.ui.FormUtil=tonto_form_ui_FormUtil;
	

	var _util=new tonto.form.ui.FormUtil();
	$.extend($.formUtil,_util);
	$.extend($.fn,{
		initFormUI:function(){
			$(this).find("input").each(function(){
				var $input=$(this);
				_util.createInputTagUnit($input);
				_util.formatInputTagValue($input);
			});
		}
	});
})(jQuery);
