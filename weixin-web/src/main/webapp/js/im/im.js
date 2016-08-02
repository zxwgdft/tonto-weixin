(function($) {
	
	$.namespace2win('tonto.im');
	
	Easemob.im.config = {
	    xmppURL: 'im-api.easemob.com',
	    apiURL: 'http://a1.easemob.com',
	    appkey: "tonto-zhou#tonto2im",
	    https : true,
	    multiResources: false
		
	}
	
	var conn = new Easemob.im.Connection({
				multiResources : Easemob.im.config.multiResources,
				https : Easemob.im.config.https,
				url : Easemob.im.config.xmppURL
			});
	
	tonto.im.connection = conn;
	
	tonto.im.login = function(user){
		conn.open({
			apiUrl : Easemob.im.config.apiURL,
			user : user,
			pwd : "1988gdft",
			//连接时提供appkey
			appKey : Easemob.im.config.appkey
		}); 
	
	};
	
	tonto.im.handleOpen = function(){
		
	};
	
	tonto.im.handleClosed = function(){
		
	}
	
	tonto.im.handleTextMessage = function(message){
		
		alert(message);
		
	};
	
	
	
	// 初始化连接
	conn.listen({
		// 当连接成功时的回调方法
		onOpened : function() {
			tonto.im.handleOpen(conn);
		},
		// 当连接关闭时的回调方法
		onClosed : function() {
			tonto.im.handleClosed();
		},
		// 收到文本消息时的回调方法
		onTextMessage : function(message) {
			tonto.im.handleTextMessage(message);
		},
		// 收到表情消息时的回调方法
		onEmotionMessage : function(message) {
			tonto.im.handleEmotion(message);
		},
		// 收到图片消息时的回调方法
		onPictureMessage : function(message) {
			tonto.im.handlePictureMessage(message);
		},
		// 收到音频消息的回调方法
		onAudioMessage : function(message) {
			tonto.im.handleAudioMessage(message);
		},
		// 收到透传消息的回调方法
		onCmdMessage : function(message) {
			tonto.im.handleCmdMessage(message);
		},
		// 收到位置消息的回调方法
		onLocationMessage : function(message) {
			tonto.im.handleLocationMessage(message);
		},
		// 收到文件消息的回调方法
		onFileMessage : function(message) {
			tonto.im.handleFileMessage(message);
		},
		// 收到视频消息的回调方法
		onVideoMessage : function(message) {
			tonto.im.handleVideoMessage(message);
		},
		// 收到联系人订阅请求的回调方法
		onPresence : function(message) {
			tonto.im.handlePresence(message);
		},
		// 收到联系人信息的回调方法
		onRoster : function(message) {
			tonto.im.handleRoster(message);
		},
		// 收到群组邀请时的回调方法
		onInviteMessage : function(message) {
			tonto.im.handleInviteMessage(message);
		},
		// 异常时的回调方法
		onError : function(message) {
			tonto.im.handleError(message);
		}
	});
	
})(jQuery);