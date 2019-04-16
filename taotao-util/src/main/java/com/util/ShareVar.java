package com.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;



public class ShareVar {

	public static Map<String, HttpSession> map=new HashMap<String, HttpSession>();
	public static void record_session( String key, HttpSession value) {
		map.put(key, value);
	}
	
	public static void destory_session(String key) {
		HttpSession value =	map.get(key);
		if(value!=null) {
			try {
			value.removeAttribute("is_login");
				value.invalidate();
			} catch (Exception e) {
			}
			
		}
		
		System.out.println(value+" "+map.toString() );
	}
}
