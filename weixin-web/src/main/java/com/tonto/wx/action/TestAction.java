package com.tonto.wx.action;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.action.common.CommonProcessAction;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.NewsResponseMessage;
import com.tonto.weixin.core.message.response.common.model.Article;

@Component
public class TestAction extends CommonProcessAction{
	
	@Override
	public ResponseMessage execute(Session session,
			RequestMessage message, ProcessContext context) {
		
//		String str=((TextRequestMessage)message).getContent().trim();			
//		TextResponseMessage response=new TextResponseMessage();	
//		response.setContent("您发送了："+str);
		
		NewsResponseMessage response = new NewsResponseMessage();
		
		Article article = new Article();
		article.setDescription("一张好图");
		article.setTitle("好消息");
		article.setPicUrl("http://154713i0y8.iask.in/tonto/image/house.png");           
		article.setUrl("http://154713i0y8.iask.in/tonto/wx/test/test");
		
		ArrayList<Article> list = new ArrayList<Article>();
		list.add(article);
		
		response.setArticles(list);
		response.setArticleCount(1);
		return response;
	}

}
