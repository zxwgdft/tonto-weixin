package com.tonto.weixin.core.service.menu;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonto.weixin.core.service.WeChatHttpClient;
import com.tonto.weixin.core.service.WeChatService;
import com.tonto.weixin.core.service.WeChatServiceException;
import com.tonto.weixin.core.service.token.WeChatAccessTokenService;

/**
 * 
 * 设置微信公众号菜单服务
 * 
 * @author TontoZhou
 * 
 */
public class SetMenuService implements WeChatService {

	private static final String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

	// 菜单的文件地址，相对目录
	String menuPath = "menu.json";

	/**
	 * <p>设置公众号菜单，默认使用{@code menuPath}文件</p>
	 * <p>菜单应该是一个json，例如：</p>
	 * 
	 *  {                                         
	 *	   	 "button":[
	 *	     {
	 *	          "type":"click",
	 *	          "name":"今日歌曲",
	 *	          "key":"TODAY_MUSIC"
	 *	      },
	 *	      {
	 *	           "name":"菜单",
	 *	           "sub_button":[
	 *	           {	
	 *	               "type":"view",
	 *	               "name":"搜索",
	 *	               "url":"http://www.soso.com/"
	 *	            },
	 *	            {
	 *	               "type":"view",
	 *	               "name":"主页",
	 *	               "url":"http://154713i0y8.iask.in/tonto/wx/test/test"
	 *	            }]
	 *	       }]
	 *	 }
	 */
	public void setMenu(){
		setMenu(SetMenuService.class.getClassLoader().getResourceAsStream(menuPath));
	}

	/**
	 * 
	 * 设置公众号菜单
	 * 
	 * @param input
	 */
	public void setMenu(InputStream input) {
		
		HttpPost post = new HttpPost(url + WeChatAccessTokenService.getAccessToken());

		InputStreamEntity postEntity = new InputStreamEntity(input,
				ContentType.create("text/plain", "UTF-8"));
		
		post.setEntity(postEntity);
		CloseableHttpResponse response = null;
		try{
			
			response = WeChatHttpClient.sendRequest(post);
			HttpEntity responseEntity = response.getEntity();
			Result result = new ObjectMapper().readValue(
					responseEntity.getContent(), Result.class);

			if (result.errcode == 0)
			{
				return;
			}

			throw new Exception(result.errmsg);

		} catch (Exception e) {
			throw new WeChatServiceException("设置微信公众号菜单失败！",e);
		} 
		finally{
			
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	static class Result {

		int errcode;
		String errmsg;

		public int getErrcode() {
			return errcode;
		}

		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	
	public static void main(String[] args){
		
		new SetMenuService().setMenu();
	}
	
}
