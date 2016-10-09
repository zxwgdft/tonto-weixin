package com.tonto.weixin.core.message.response.common;

import java.util.List;

import com.tonto.weixin.core.message.response.ResponseConstant;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.model.Article;

public class NewsResponseMessage extends ResponseMessage{
	
	// 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<Article> Articles;
    
    public NewsResponseMessage()
    {
    	MsgType=ResponseConstant.RESP_MESSAGE_TYPE_NEWS;
    }
    
    
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	} 
	
}
