package com.chaorder.aitech;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndexData{
	/* logger declaration */
	private static final Logger logger = LoggerFactory.getLogger(IndexData.class);
	/* Index List */
	private static final List<String> indexList = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("s_sz399001");
			add("int_sh000001");
	        add("int_dji");
	        add("int_nasdaq");
	        add("int_hangseng");
	        add("int_nikkei");
	        add("b_FSSTI");
		}
	};
	/* Sina Index Web Api */
	private static final String sinaIndexApi = "http://hq.sinajs.cn/list=";
	
	/**
	 * Get Request
	 * @param url
	 * 		web site/api url
	 * @param param
	 * 		parameters of get request
	 * @return result
	 */
	public String sendGet(String url, String param){
		String result = "";
        BufferedReader in = null;
        try {
        	String urlNameString = url;
        	// 有参数的话加在后面
        	if (param != "" && param != null)
        		urlNameString += "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"gbk"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("指数获取get请求出现异常: " + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
	}
	
	/**
	 * 获取指数数据信息，来源: sina财经接口 
	 * 数据列表:
	 * '深证指数'(中国深圳),'上证指数'(中国上海),'道琼斯指数'(美国),'纳斯达克指数'(美国)
	 * '恒生指数'(中国香港),'日经指数'(日本东京),'海峡富时指数'(新加坡)
	 * @param None
	 * @return indexObjectList
	 * @exception
	 */
	public List<Map<String,String>> getIndex(){
		List<Map<String,String>> indexObjectList = new ArrayList<Map<String,String>>();
		for(String index:indexList){
			String data = sendGet(sinaIndexApi+index, null);
			Map<String,String> indexObject = new HashMap<String, String>();
			
			/* 切割字符串，分别获取名称，当日指数，涨跌情况 */
			String[] dataSplit = data.split(",");
			indexObject.put("name", (dataSplit[0].split("\""))[1]);
			indexObject.put("index", dataSplit[1]);
			indexObject.put("change", dataSplit[2]);
			
			indexObjectList.add(indexObject);
		}
		
		return indexObjectList;
	}
}
