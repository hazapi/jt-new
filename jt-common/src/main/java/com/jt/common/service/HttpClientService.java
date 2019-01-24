package com.jt.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
    @Autowired(required=false)
    private CloseableHttpClient httpClient;   //连接对象

    @Autowired(required=false)
    private RequestConfig requestConfig;      //定义超时的配置
    
    
    /**
     * url = url +"?";
    	if(params != null){
    		
    		for (Map.Entry<String,String> entry
    						: params.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url = url + key + "=" + value + "&"; 
			}
    		
    		url = url.substring(0,url.length()-1);
    	}
     * 完成httpGet操作
     * url:用户传参
     * 用户参数: http://www.baidu.com?key=value&
     * key2=value2     Map<String,String>
     * 字符集编码:utf-8
     */
    public String doGet(String url,
    		Map<String,String> params,
    		String charset){
    	
    	//1.判断字符集编码
    	if(StringUtils.isEmpty(charset)){
    		
    		charset = "UTF-8";
    	}
    	
    	String result = null;
    	try {
    		
	    	//2.是否携带参数  url= ?key=value&key&...
	    	if(params !=null) {
	    		URIBuilder uriBuilder = 
	    				new URIBuilder(url);
	    		
	    		//循环遍历Map
	    		for (Map.Entry<String,String> entry :params.entrySet()) {
					
	    			uriBuilder.addParameter
	    			(entry.getKey(),entry.getValue());
				}
	    		
	    		//将url地址动态获取
	    		url = uriBuilder.build().toString();
	    	}
	    	
	    	//定义请求方式
	    	HttpGet httpGet = new HttpGet(url);
	    	//设定请求时常
	    	httpGet.setConfig(requestConfig);
	    	
	    	//获取响应结果
	    	CloseableHttpResponse httpResponse
	    		=httpClient.execute(httpGet);
	    	
	    	if(httpResponse
	    			.getStatusLine()
	    			.getStatusCode()
	    			== 200){
	    		result = EntityUtils.toString(httpResponse.getEntity(), charset);
	    		
	    	}
    	} catch (Exception e) {
    		
			e.printStackTrace();
			throw new RuntimeException();
		}
    	
    	return result;
    }
    
    //为了用户体验重载方法
    public String doGet(String url,
    		Map<String,String> params){
    	
    	return doGet(url, params, null);
    }
    
    public String doGet(String url){
    	
    	return doGet(url, null, null);
    }
    
    
    /**
     * 1.定义请求方式httpPost
     * 2.将参数进行表单实体封装.
     * 3.发起url请求,获取返回值
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doPost(String url,Map<String,String> params,String charset){
    	String result = null;
    	if(StringUtils.isEmpty(charset)){
    		
    		charset = "UTF-8";
    	}
    	
    	//1.定义请求类型
    	HttpPost post = new HttpPost(url);
    	post.setConfig(requestConfig); //定义链接时长
  
    	try {	
	    	//2.参数封装
	    	if(params != null){
	    		List<BasicNameValuePair> parameters = new ArrayList<>();
	    		//动态获取用户数据
	    		for (Map.Entry<String,String> entry : params.entrySet()) {
	    			parameters.add(
	    			new BasicNameValuePair(entry.getKey(),entry.getValue()));
				}
	    		//封装FORM表单实体对象,作用传递参数
	    		UrlEncodedFormEntity entity = 
	    				new UrlEncodedFormEntity(parameters,charset);
	    		post.setEntity(entity);
	    	}
	    	
	    	//3.发起url请求.
	    	CloseableHttpResponse httpResponse = 
	    			httpClient.execute(post);
	    	//504 访问超时 500 服务器异常 406 浏览器解析参数异常  404 请求没有对应的处理方式
	    	//400 参数提交到后台参数类型错误. 200 请求正常  304 浏览器已缓存
	    	if(httpResponse.getStatusLine().getStatusCode() == 200){
	    		result  = 
	    	EntityUtils.toString(httpResponse.getEntity(),charset);
	    	
	    	}
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
   
    	return result;
    }
    
    public String doPost(String url,Map<String,String> params){
    	
    	return doPost(url, params, null);
    }
    
    public String doPost(String url){
    	
    	return doPost(url, null, null);
    }
}
