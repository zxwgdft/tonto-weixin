<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>     
    <title>测试</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<meta name="viewport" content="width=device-width, initial-scale=1">    
	
	<script src="${path}/js/wx/wx.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	
	<script type="text/javascript">
		wx.config({
		    debug: true,  // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: weixin.config.appId, // 必填，公众号的唯一标识
		    timestamp: 1470640885,  // 必填，生成签名的时间戳
		    nonceStr: '3d98a743-1bff-4008-b696-edaac7e0ac1a', // 必填，生成签名的随机串
		    signature: '2C0EBC23809426EF2B15608CB16D92A9C28AA2B4',// 必填，签名，见附录1
		    jsApiList: ["onMenuShareTimeline",
						"onMenuShareAppMessage",
						"onMenuShareQQ",
						"onMenuShareWeibo",
						"onMenuShareQZone",
						"startRecord",
						"stopRecord",
						"onVoiceRecordEnd",
						"playVoice",
						"pauseVoice",
						"stopVoice",
						"onVoicePlayEnd",
						"uploadVoice",
						"downloadVoice",
						"chooseImage",
						"previewImage",
						"uploadImage",
						"downloadImage",
						"translateVoice",
						"getNetworkType",
						"openLocation",
						"getLocation",
						"hideOptionMenu",
						"showOptionMenu",
						"hideMenuItems",
						"showMenuItems",
						"hideAllNonBaseMenuItem",
						"showAllNonBaseMenuItem",
						"closeWindow",
						"scanQRCode",
						"chooseWXPay",
						"openProductSpecificView",
						"addCard",
						"chooseCard",
						"openCard"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		wx.ready(function(){
			alert("验证成功");
		});
		
		wx.error(function(res){
			alert(res);
		});
		
	</script>
  </head>
  
  <body>
    This is my JSP page.士大夫士大夫士大夫 <br>
  </body>
</html>
