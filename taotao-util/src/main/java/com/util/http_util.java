package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

public class http_util { 

	public static void main(String[] args) throws Exception, Exception {
		new http_util().client();
	}
	
//	@Test
	public void client() throws Exception, IOException {
		final String urlt="http://192.168.43.42:8456";
		HashMap<String, String>h=new HashMap<String, String>();
		h.put("s", "s");
		postMap(urlt,h);
	System.out.println("发送完毕");
	}
	
	/**
     * 发送post请求，参数用map接收
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
    public static  String postMap(String url,Map<String,String> map) {
    	System.out.println(url+"发送url");
    	
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                org.apache.http.HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch  (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    private static  String entityToString(org.apache.http.HttpEntity entity) throws IOException {
        String result = null;
        if(entity != null)
        {
            long lenth = entity.getContentLength();
            if(lenth != -1 && lenth < 2048)
            {
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }
    /**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    public static String postJson(String url,String jsonString)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                org.apache.http.HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
	/**
	 * 发送一个请求
	 */
	public static String sendGet(String urlt, String data)  {
			try {
		URL url =new URL(urlt);
		 //url工具类用于代理发送请求和数据
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection. setRequestMethod ("POST");
		 connection. setDoOutput (true);
		 connection. setDoInput (true);
		 connection. setRequestProperty("Content-Type", "text/html; charset=utf8");
		 
		 OutputStream os = connection.getOutputStream();
		 os.write(data.getBytes ("utf-8"));
		 os.flush ();
		 connection.connect();
		  os = connection.getOutputStream();
		 os.write(data.getBytes ("utf-8"));
		 os.flush ();
		 connection.connect();
		 
		 
		 //只有当响应码是200的时候才可以开输入流获取响应的内容
		 int responseCode = connection.getResponseCode();
		 if (responseCode==200) 
			 {
			 InputStream is = connection. getInputStream();
			 
			 String string=InputStreamTOString(is,"utf-8");
			 System.out.println( "响应内容 "+string );
			//直接打印出
			 	return string;
			 }
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
    /**
     * 将InputStream转换成某种字符编码的String
     *
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in, String encoding) {
        String string = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        try {
            while ((count = in.read(data, 0, 1024)) != -1)
                outStream.write(data, 0, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = null;
        try {
            string = new String(outStream.toByteArray(), encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }
	
	/**
	 * 客户端用来监听请求
	 */

	public void service() throws Exception {
		System.out.println("监听端口");
		 ServerSocket ss = new ServerSocket(8011);
		 Socket c=ss.accept();
		 
		 InputStream out=c.getInputStream();
		 System.out.println(out.available());

		 OutputStream in=c.getOutputStream();
		 in.write("测试数据".getBytes());
		 in.flush();
		 in.close();
	}
	
}
