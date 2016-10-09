package com.tonto.weixin.core.message.response.common;

import com.tonto.weixin.core.message.response.ResponseConstant;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.model.Music;

public class MusicResponseMessage extends ResponseMessage {

	private Music Music;

	public MusicResponseMessage() {
		MsgType = ResponseConstant.RESP_MESSAGE_TYPE_MUSIC;
	}

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
