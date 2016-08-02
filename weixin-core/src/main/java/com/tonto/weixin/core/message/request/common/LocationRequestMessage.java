package com.tonto.weixin.core.message.request.common;

import com.tonto.weixin.core.message.request.RequestConstant;

public class LocationRequestMessage extends CommonRequestMessage{
	
	//地理位置纬度
	private double Location_X;
	//地理位置经度
	private double Location_Y;
	//地图缩放等级
	private int Scale;
	//地理位置信息
	private String Label;
	
	public LocationRequestMessage()
	{
		MsgType=RequestConstant.REQ_MESSAGE_TYPE_LOCATION;
	}

	public double getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(double location_X) {
		Location_X = location_X;
	}

	public double getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(double location_Y) {
		Location_Y = location_Y;
	}

	public int getScale() {
		return Scale;
	}

	public void setScale(int scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	
}
