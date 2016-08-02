
(function($){
	//自然数
	$.validator.addMethod("naturalNumber", function(value, element) {return this.optional(element) || (/^[1-9]\d{0,9}$/.test(value));}, "请输入大于0小于9999999999的整数");
	//身份证
	$.validator.addMethod("identity", function(value, element) {   
	    var id = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	      
	    if(!id.test(value))
	    	return this.optional(element)||false;
	    
	    var y=value.substring(6,10)*1;
	    var m=value.substring(10,12)*1-1;
	    var d=value.substring(12,14)*1;
	    
	    var date=new Date(y,m,d);        	
	    
	    return this.optional(element) || (date.getFullYear()==y&&date.getMonth()==m&&date.getDate()==d);
	}, "身份证格式错误");
	//邮编
	$.validator.addMethod("zip", function(value, element) {return this.optional(element) || (/^[0-9]\d{5}$/.test(value));}, "邮编格式错误");
	//手机
	$.validator.addMethod("cellphone", function(value, element) {return this.optional(element) || (/^1[3|5|7|8|]\d{9}$/.test(value));}, "手机号码格式错误");
	//电话（包括手机和座机）
	$.validator.addMethod("phone", function(value, element) {return this.optional(element) || (/((^\d{3,4}-?)?\d{7,8}$)|(^1[3|5|7|8|]\d{9}$)/.test(value));}, "电话号码格式错误");
	$.validator.addMethod("englishName", function(value, element) {return this.optional(element) || (/^\w+$/.test(value));}, "请输入英文名称");
	//日期
	$.validator.addMethod("date", function(value, element) {       
	    var r=value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	    if(r==null)    
	    	return this.optional(element);
	    
	    var d=new Date(r[1],r[3]-1,r[4]);       
	    return this.optional(element) || (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	}, "日期格式不正确");
	//大于
	$.validator.addMethod("large-than", function(value, element ,$name) { 
		if($name)
		{	
			var minVal=$($name).val();
			if(minVal)
			{
				if(("string" === typeof(minVal)&& /\d+\.?\d*/.test(minVal))||"number" === typeof(minVal))		
					return this.optional(element)||value>=minVal*1;
				else
					return true;
			}
		}
		return true;
	}, "输入值不能小于最小值");
	$.validator.addMethod("maxEngLength", function(value, element,maxlength) {return this.optional(element) || (value.replace(/[^\x00-\xff]/g, 'xx').length<=maxlength);}, "输入长度不能超过{0}");
	$.validator.addMethod("minEngLength", function(value, element,minlength) {return this.optional(element) || (value.replace(/[^\x00-\xff]/g, 'xx').length>=minlength);}, "输入长度不能少于{0}");

	$.extend($.validator.messages, {
	    required: "该字段不能为空",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
		minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
		rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("输入值不能大于 {0}"),
		min: jQuery.validator.format("输入值不能小于 {0}")
	});
	
	$.validator.setDefaults({
		ignore:"",
		showErrors: function(map, list) {		
			this.currentElements.each(function(){
				var p=$(this).parent()
				if(p.hasClass("input-group"))
				{
					p=p.parent();
				}
				p.parent().removeClass("has-error");                  
				p.find("span.error-msg").remove();
			});					
			$.each(list, function(index, error) {
				var msg=(error.method=="required"&&error.element.placeholder)||error.message;					
				var p=$(error.element).parent();
				if(p.hasClass("input-group"))
				{
					p=p.parent();
				}
				p.append("<span class='error-msg'>"+msg+"</span>");
				p.parent().addClass("has-error");                
			});			
		} 
	});

	$.extend($.fn,{
		createValidate:function(config){			
			for(var i=0;this.length>i;i++)
			{
				var target=$(this[i]);
				
				var name=target.attr("name");
				var rules=config&&config.rules&&config.rules[name]||{};
				var messages=config&&config.messages&&config.messages[name]||{};
				
				
				var type=target.attr("data-type");
				if(type)
				{
					rules[type]=true;
				}
					
				if(target.hasClass("required"))
					rules.required=true;
				
				var area=target.attr("data-area");
				if(area)
				{
					var numbers=area.split(",");
					if(numbers.length>0)
					{
						if(numbers[0])
							rules.min=numbers[0]*1;
						if(numbers.length>1)
						{
							if(numbers[1])
								rules.max=numbers[1]*1;
						}							
					}
					
				}	
			
				var length=target.attr("data-length");
				if(length)
				{
					var lengths=length.split(",");
					if(lengths)
					{
						if(lengths.length>0)
						{
							if(lengths[0])
								rules.minlength=lengths[0]*1;							
							if(lengths.length>1)
							{
								if(lengths[1])
									rules.maxlength=lengths[1]*1;
							}						
						}					
					}	
				}
				
				var length=target.attr("data-eng-length");
				if(length)
				{
					var lengths=length.split(",");
					if(lengths)
					{
						if(lengths.length>0)
						{
							if(lengths[0])
								rules.minEngLength=lengths[0]*1;
							if(lengths.length>1)
							{
								if(lengths[1])
									rules.maxEngLength=lengths[1]*1;
							}
						}
						
					}	
				}
				
				var equalTo=target.attr("equalTo");
				if(equalTo)
				{
					rules["equalTo"]=equalTo;
				}
				
				var largeThan=target.attr("large-than");
				if(largeThan)
				{
					rules["largeThan"]=largeThan;
				}
				
				rules.messages=messages;
				target.rules("add",rules);
				
			}	
		},
		createFormValidate:function(config){
			this.validate();
			this.find
				("input[type='text']:enabled,input[type='password']:enabled,input[type='hidden']:enabled,select:enabled,textfield:enabled")
					.createValidate(config);
			
		}
	});
})(jQuery);







