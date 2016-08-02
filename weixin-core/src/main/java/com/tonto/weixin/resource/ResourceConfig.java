package com.tonto.weixin.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author	TontoZhou
 * @date	2014-10-15
 */
public class ResourceConfig {
	
	private static Logger logger=Logger.getLogger(ResourceConfig.class);
	private static Properties props=new Properties();
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	 
	private static final String default_path="";
	
	public static String PATH_IMAGE;	
	public static void init() throws Exception
	{
		InputStream input=null;
		
		try{
			//获取类根目录，toURI()转换20%成空格       
	    	//String path = ResourceConfig.class.getResource("/").toURI().getPath();
			
	    	input = ResourceConfig.class.getClassLoader().getResourceAsStream("resource.properties");;
	
	        props.load(input);        
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("读取resource.properties文件失败!");
			props=null;
		}
		finally
        {
        	if(input!=null)
        	{
        		try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		
        	} 	
        }
				
		String image=null;
    	
            	
    	if(props!=null)
    	{  			
    		image=formatPath(props.getProperty("imagePath")); 	     	   
     	    image=checkFileAndCreate("图片存放",image)?image:null;	    	    	  	      		
    	}	    
			
        //为null则启用缺省路径
        if(image==null)
        {
        	image=default_path+"/image";
        	image=checkFileAndCreate("缺省图片存放",image)?image:null;
        }
       
        
        PATH_IMAGE=image;
       
        if(PATH_IMAGE==null)
        {
        	throw new Exception("无法得到资源存放路径");
        }
        
        logger.info("图片存放路径"+PATH_IMAGE);
	}
	
	private static String formatPath(String path)
    {
    	if(path!=null)
    	{
    		//替换所有斜杠
    		
    		path=path.replaceAll("\\\\\\\\", "/");
    		path=path.replaceAll("\\\\", "/");
    		
    		
    		if(!path.endsWith("/"))
    			path+="/";
    	}
    	return path;
    }
    
    private static boolean checkFileAndCreate(String fileTitle,String filepath)
	{
    	try{
	    	File file=new File(filepath);
	    	
			if(file.exists())
			{
				if(!file.isDirectory())
				{
					logger.error(fileTitle+"路径错误！路径不是文件夹："+filepath);
					return false;
				}	
			}
			else
			{
				logger.info(fileTitle+"目录不存在，开始创建目录："+filepath);
				file.mkdirs();
				logger.info(fileTitle+"目录创建完成");
			}
			return true;
    	}
    	catch(Exception e)
    	{
    		logger.error(fileTitle+"路径错误:"+filepath);
    		return false;
    	}
	}
    
	public static Properties getProps() {
		return props;
	}
   
}
