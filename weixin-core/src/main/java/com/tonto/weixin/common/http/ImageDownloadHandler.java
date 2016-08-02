package com.tonto.weixin.common.http;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HttpContext;

import com.tonto.common.util.http.ResponseHandler;

public class ImageDownloadHandler implements ResponseHandler{
	
	private String filepath;
	
	public ImageDownloadHandler(String filepath)
	{
		this.filepath=filepath;
	}
	
	@Override
	public void handle(HttpEntity entity, HttpContext context) {
		if(entity==null)
		{
			throw new RuntimeException("Entity can't be null!");
		}
		
		FileOutputStream output=null;
		
		try {
			output=new FileOutputStream(filepath);
		
		    final InputStream instream = entity.getContent();
		    try{
	            int i = (int)entity.getContentLength();
	            if (i < 0) {
	                i = 4096;
	            }
	            final byte[] tmp = new byte[4096];
	            int l;
	            while((l = instream.read(tmp)) != -1) {
	            	output.write(tmp, 0, l);
	            }
	        } catch (IOException e) {
				e.printStackTrace();
			} finally {
				instream.close();			
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(output!=null)
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
