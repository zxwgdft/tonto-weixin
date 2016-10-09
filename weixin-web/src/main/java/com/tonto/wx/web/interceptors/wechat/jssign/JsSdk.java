package com.tonto.wx.web.interceptors.wechat.jssign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 标识该动作返回的页面需要调用微信JS SDK功能
 * 
 * @author TontoZhou
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsSdk {

}
