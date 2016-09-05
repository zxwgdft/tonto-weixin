<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	wx.config({
	    debug: true,  // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${jsSign.appid}', // 必填，公众号的唯一标识
	    timestamp: ${jsSign.timestamp},  // 必填，生成签名的时间戳
	    nonceStr: '${jsSign.noncestr}', // 必填，生成签名的随机串
	    signature: '${jsSign.sign}',// 必填，签名，见附录1
	    jsApiList: ["onMenuShareTimeline",
					"onMenuShareAppMessage",
					"onMenuShareQQ",
					"onMenuShareWeibo",
					"onMenuShareQZone",
					] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
</script>
