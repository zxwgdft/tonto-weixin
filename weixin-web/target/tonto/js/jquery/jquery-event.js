
(function($){
	$.namespace2win('tonto.event');
	
	//--------------------
	// 事件分发器
	//--------------------
	var event_Dispatcher=function(){
	
	}
		
	event_Dispatcher.prototype.addEventListener=function(event,callback){
		var map=this.listenerMap||(this.listenerMap={});
		var listeners=map[event]||(map[event]=new Array());
		listeners.push(callback);
	}
	
	event_Dispatcher.prototype.distribute=function(event,data){
		var map=this.listenerMap||(this.listenerMap={});
		var listeners=map[event];
		if(listeners)
		{
			for(var i=0;i<listeners.length;i+=1)
			{
				listeners[i].call(this,data);
			}
		}
	}
	
	tonto.event.Dispatcher=event_Dispatcher;
	
})(jQuery)