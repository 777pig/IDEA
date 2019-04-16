package com.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.bean.token;

@Service
public class login_token {
	
	J_Util j=J_Util.getInstance(); 
	//插入token
	@Test
	public void text() {
		J_Util j=J_Util.getInstance();
		j.insert("token",new token("bb2") );
	}
	public List<token> getAlltoken (String token){
		return j.get_entitys(token.class, "select * from token where token =?", token);
	}
	/**
	 * 删除token
	 */
	public void remove_token(String token ) {
		j.update("delete from token where token=?", token);
	}
	public boolean select_token(String token) {
		try {
			 com.bean.token c2=j.get_entity(token.class, "select * from token where token=?", token);
			 return c2.getUrl()!=null;
		 }catch (Exception e) {
			e.printStackTrace();
		}	
			return false;
	}
	//查询token有没有
	public void insert_token(token t) {
		j.insert("token",t);
	}
	
}
