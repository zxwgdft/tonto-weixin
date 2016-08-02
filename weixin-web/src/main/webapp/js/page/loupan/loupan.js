$.model("loupan",function(){
	var loupan=new tonto.model("loupan");

	loupan.searchDistrict = new tonto.district.District("#selectDistrictToSearchBtn",
			{single:false,showBtn:true});
	loupan.searchDistrict.addEventListener("checked", function(obj) {
		$("#searchDistrictCode").val(obj.key==-1?"":obj.key);
	});	
		
	$("#searchLoupanBtn").on("click",function(){
		var searchDto={};
		var lname=$.trim($("#searchloupanName").val());
		var code=$("#searchDistrictCode").val();
		if(lname)
			searchDto.loupanName=lname;
		if(code)
			searchDto.districtCode=code*1;
		loupan.config.searchDto=searchDto;
		loupan.skip(1);
	});	

	
	$("#addLoupanBtn").on("click",loupan.add);
		
	loupan.skip(1);
	return loupan;
});