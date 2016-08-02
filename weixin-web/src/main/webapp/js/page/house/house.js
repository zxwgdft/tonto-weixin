$.model("house",function(){	
	var house=new tonto.model("house");
	
	house.loupanSearchSelector=new tonto.loupan.Selector("#selectLoupanToSearchBtn",{allBtn:true,showBtn:true});
	house.loupanSearchSelector.addEventListener("checked",function(item){
		house.config.searchDto.loupanId=item.id;
	});
	house.searchDistrict = new tonto.district.District("#selectDistrictToSearchBtn",{single:false,showBtn:true});
	house.searchDistrict.addEventListener("checked", function(obj) {			
		house.config.searchDto.districtCode=obj.key==-1?undefined:obj.key;
	});		
	
	
	$("#searchTypeSelect").on("change",function(){
		if($("#searchTypeSelect").val()=="选择地区")
		{
			$("#searchDistrictDiv").show();
			$("#searchLoupanDiv").hide();	
		}
		else
		{
			$("#searchDistrictDiv").hide();
			$("#searchLoupanDiv").show();	
		}
	});
	$("#searchTypeSelect").change();
	
	
	$("#searchHouseBtn").on("click",function(){
		
		if($("#searchTypeSelect").val()=="选择地区")
		{
			house.config.searchDto.loupanId=undefined;
		}
		else
		{
			house.config.searchDto.districtCode=undefined;
		}
		house.skip(1);
	});

	
	$("#addHouseBtn").on("click",house.add);
	
	house.skip(1);
	return house;
});
