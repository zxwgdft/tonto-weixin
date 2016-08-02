$.model("customer",function(){
	var customer=new tonto.model("customer");
	$("#addCustomerBtn").on("click",customer.add);
	customer.skip(1);
	return customer;
});
