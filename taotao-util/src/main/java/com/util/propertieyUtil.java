package com.util;

import java.util.ResourceBundle;

public class propertieyUtil {
	
public static final String TAOTAO_SYSTEM1_LOGINOUT=loginout();
	
	public static String loginout() {
		return ResourceBundle.getBundle("url").getString("taotao-loginout");
	}
	
	public static String getHostUrl() {
		return ResourceBundle.getBundle("url").getString("taotao-sso");
	} 
	
	//
	public static String getVerifyUrl() {
		return ResourceBundle.getBundle("url").getString("taotao-sytem1-verify");
	} 
	public static String getVerify_errorUrl() {
		return ResourceBundle.getBundle("url").getString("token-verify-error");
	}


		
}
