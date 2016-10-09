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

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<body>
	<button id="onMenuShareAppMessage">分享给朋友</button>
</body>
<jsp:include page="../common/jssdk.jsp"></jsp:include>

<script type="text/javascript">
	
		wx.ready(function() {
			var shareData = {
			    title: '微信JS-SDK Demo',
			    desc: '微信JS-SDK,帮助第三方为用户提供更优质的移动web服务',
			    link: 'http://demo.open.weixin.qq.com/jssdk/',
			    imgUrl: 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0'
			  };
			  wx.onMenuShareAppMessage(shareData);
		
			document.querySelector('#onMenuShareAppMessage').onclick = function() {
				wx.getLocation({
			    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			    success: function (res) {
		
			        alert("你的位置："+res.latitude+"/"+res.longitude+"/"+res.speed+"/"+res.accuracy);
			        
			        
			        
			    }
			});
			};

		});
		
		wx.error(function(res){
			alert("配置错误");
		});
</script>

</html>
