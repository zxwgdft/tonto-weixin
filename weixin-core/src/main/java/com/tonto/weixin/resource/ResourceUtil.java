package com.tonto.weixin.resource;


/**
 * 资源文件工具类
 * @author	TontoZhou
 * @date	2014-10-15
 */
public class ResourceUtil {
	/**
	 * 取得图片存放路径
	 * @param chartname
	 * @return
	 */
	public static String getImagePath(String imageName)
	{
		return ResourceConfig.PATH_IMAGE+imageName;
	}
	
	
	
}
