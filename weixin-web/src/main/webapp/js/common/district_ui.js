(function($) {
	$.namespace2win('tonto.district');

	var tonto_district_District = function(btn, config) {
		var _self = this;

		_self.config = {
			map : district_map,
			showBtn:true,
			showBtnCaret :true,
			single : true
		}
		
		$.extend(_self.config,config);
		
		config=_self.config;
		
		map=config.map;
		
		_self.root = {
				key : -1,
				value : '中国',
				children : map
			}
		
		_self.currentDistrictList = map;
		_self.selectedDistrict=null;
		//绑定父节点
		district_on_parent(_self.root);
		
		defaultShow=_self.config.defaultShow;
		
		//创建界面
		var $dropDiv = $("<div class=\"dropdown-menu\" style=\"padding:10px\"></div>");
		var $districtDiv = $("<div style=\"width:400px;white-space:normal\"></div>");
		var $headDiv=$("<div align=\"right\"></div>");
		var $closeBtn=$("<button type=\"button\" class=\"close\" style=\"float:none\">×</button>");
		var $btnDiv = $("<div align=\"right\"></div>");
		var $backBtn = $("<button type=\"button\" class=\"btn btn-primary\">返回上一层</button>");
		
		_self.element = {
			selectBtn : $(btn),
			dropdownDiv : $dropDiv,
			districtDiv : $districtDiv,
			backBtn : $backBtn
		}
		
		var ele=_self.element;
		
		
		_self.addEventListener("checked",function(item){
			if(config.showBtn)
			{
				_self.element.selectBtn.html(item.value+(config.showBtnCaret?" <span class='caret'></span>":""));	
			}
		});
		
		config.container = $districtDiv;

		ele.selectBtn.on("click", function() {
			_self.toggle();
		});
		
		ele.backBtn.on("click", function() {
			_self.back();
		});
		
		$closeBtn.on("click", function(){
			_self.element.dropdownDiv.hide();
		});
		
		single=_self.config.single;
		
		if(!single)
		{
			var $okBtn = $("<button type=\"button\" class=\"btn btn-primary\" style=\"margin-right:10px\">确定</button>");
			$okBtn.on("click", function() {
				var key=$("input[type='radio']:checked").val();
				if(key==-1)
				{						
					_self.distribute("checked", {key:"-1",value:"全部"});
					_self.selectedDistrict=null;
					_self.element.dropdownDiv.hide();
				}
				else
				{
					var obj=district_find_district(_self.root,key);
					_self.selectedDistrict=obj;
					_self.distribute("checked", obj);
					_self.element.dropdownDiv.hide();
				}
				
			});
			
			_self.element.okBtn=$okBtn;	
			$btnDiv.append($okBtn);
		}
		
		$btnDiv.append($backBtn);
		$headDiv.append($closeBtn);
		$dropDiv.append($headDiv).append("<li class=\"divider\"></li>")
			.append($districtDiv).append("<li class=\"divider\"></li>").append($btnDiv);
		$(btn).after($dropDiv);	
		
		_self.showOption();
	}

	function district_on_parent(root) {
		if (root && root.children) {
			var _list = root.children;
			for ( var i = 0; i < _list.length; i++) {
				var o = _list[i];
				o.parent = root;
				if (o.children) {
					district_on_parent(o);
				}
			}
		}
	}

	function district_find_district(d, key) {
		if (d) {
			if (d instanceof Array) {
				for ( var i = 0; i < d.length; i++) {
					var r = district_find_district(d[i], key);
					if (r)
						return r;
				}

			} else {
				if (d.key == key)
					return d;
				if (d.children) {
					var r = district_find_district(d.children, key);
					if (r)
						return r;
				}
			}
		}
		return null;
	}

	tonto_district_District.prototype = new tonto.event.Dispatcher();	
	tonto_district_District.prototype.toggle = function() {
		this.element.dropdownDiv.toggle();
	}
	tonto_district_District.prototype.show = function() {
		this.element.dropdownDiv.show();
	}
	tonto_district_District.prototype.hide = function() {
		this.element.dropdownDiv.hide();
	}
	tonto_district_District.prototype.toDistrict = function(key) {
		var _self = this;
		var district = district_find_district(_self.root, key);
		if (district) {
			_self.currentDistrictList = district.parent
					&& district.parent.children || _self.config.map;			
			_self.distribute("checked", district);
		}
		_self.showOption();
	}
	tonto_district_District.prototype.back = function(backroot) {
		var _self = this;
		if (backroot)
			_self.show(null, _self.config.map);
		else {
			var m = (_self.currentDistrictList[0]
					&& _self.currentDistrictList[0].parent && _self.currentDistrictList[0].parent.parent)
					|| _self.config.map;
			_self.showOption(null, m.children);
		}
	}

	tonto_district_District.prototype.showOption = function(container, list, single) {

		var _self = this, _list = list || _self.currentDistrictList;
		_self.currentDistrictList = _list;
		container = container || _self.config.container;
		single = !single&&single!=false? _self.config.single:single;

		var $con = $(container);
		$con.empty();

		if (_list) {			
			if(single)
			{	
				for ( var i = 0; i < _list.length; i++) {
					var o = _list[i];
					if (o.children) {
						var $s = $("<button type='button' class='btn btn-default btn-sm'><span class='glyphicon glyphicon-plus'></span> "
								+ o.value + "</button>");
						(function(c, l, s) {
							$s.on("click", function() {
								_self.showOption(c, l, s);
							});
						})(container, o.children, single);
						$con.append($s);
					} else {
						var $s = $("<button type='button' class='btn btn-default btn-sm'>"
								+ o.value + "</button>");
						(function(obj) {
							$s.on("click", function() {
								_self.selectedDistrict=obj;
								_self.distribute("checked", obj);
								_self.element.dropdownDiv.hide();
							});
						})(o);
						$con.append($s);
					}
				}
			}
			else
			{
				$div1=$("<div></div>");
				var p=_list[0].parent;
				if(p.key==-1)
					$div1.append("<span class='district-span'><label><input name='district' type='radio' value='-1'>全部</label></span>");
				else
					$div1.append("<span class='district-span'><label><input name='district' type='radio' value='"+ p.key +"'>"+p.value+"</label></span>");
					
				$div2=$("<div></div>");
				var c=0;
				for ( var i = 0; i < _list.length; i++) {
					var o = _list[i];
					if (o.children) {
						var $s = $("<button type='button' class='btn btn-default btn-sm'><span class='glyphicon glyphicon-plus'></span>"
								+ o.value + "</button>");
						(function(c, l, s) {
							$s.on("click", function() {
								_self.showOption(c, l, s);
							});
						})(container, o.children, single);
						$div2.append($s);
						c++;
						
					} else {						
						$div1.append($("<span class='district-span'><label><input name='district' type='radio' value='"+ o.key +"'>"+o.value+"</span>"));						
					}
				}
				$con.append($div1);
				if(c>0)
					$con.append("<li class=\"divider\"></li>").append($div2);
			}
		}
	}

	tonto.district.District = tonto_district_District;
})(jQuery);