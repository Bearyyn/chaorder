package com.chaorder.aitech;


import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chaorder.aitech.IndexData;
import com.chaorder.aitech.mapping.UserMapper;
import com.chaorder.aitech.mapping.ZyzbMapper;
import com.chaorder.aitech.pojo.User;
import com.chaorder.aitech.pojo.Zyzb;
import com.chaorder.validationCode.ValidationCode;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Handles requests for the chaorder Login page.
 */
@Controller
public class LoginController {
	/* logger declaration */
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	/* validation code in session */
	private static final String VAL_CODE = "login_validation_code";
	/* config setting */
	private static final ResourceBundle SETTING = ResourceBundle.getBundle("Config");
	/* mongodb config */
	private static String MONGO_ADDR = SETTING.getString("mongodb_addr");
	private static String MONGO_UNAME = SETTING.getString("mongodb_uname");
	private static String MONGO_PWD = SETTING.getString("mongodb_pwd");
	private static String MONGO_USR_DB = SETTING.getString("mongodb_user_db");
	private static List<MongoCredential> CREDENTIALS = new ArrayList<MongoCredential>(){
		private static final long serialVersionUID = 1L;
		{
		   add(
				MongoCredential.createScramSha1Credential(
					MONGO_UNAME, 
				    MONGO_USR_DB, 
				    MONGO_PWD.toCharArray()
				)
			);
		}
	};
	
	private static List<ServerAddress> ADDRS = new ArrayList<ServerAddress>(){
		private static final long serialVersionUID = 1L;
		{
			add(
				new ServerAddress(MONGO_ADDR,27017)
			);
		}
	};
	
	/**
	 * 验证码刷新
	 */
	@RequestMapping(value = "/refresh_val_code", method = RequestMethod.GET)
	public void refreshValCode(HttpServletRequest request, HttpServletResponse response) {
		ValidationCode createCode = new ValidationCode();
		try {
			JSONObject valCode = createCode.createValidationCode();
			/* 验证码内容存在session中，直接覆盖原来的 */
			request.getSession().setAttribute(VAL_CODE, (String)valCode.get("code"));		
			/* 验证码地址返回 */
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print((String)valCode.get("address"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ;
	}
	
	
	/**
	 * 登录界面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
   
		/* 获取指数数据显示给前端 */
		IndexData indexData = new IndexData();
		List<Map<String,String>> indexList = indexData.getIndex();
		
		model.addAttribute("indexList", indexList);
		/* 生成验证码 */
		ValidationCode createCode = new ValidationCode();
		try {
			JSONObject valCode = createCode.createValidationCode();
			/* 验证码内容存在session中 */
			request.getSession().setAttribute(VAL_CODE, (String)valCode.get("code"));	
			/* 验证码地址注入前端 */
			model.addAttribute("valCodeAddress", (String)valCode.get("address"));
		} catch (Exception e) {
			logger.error(e.getMessage());;
		}
		return "ChaorderLogin";
	}
	
	/**
	 * 验证用户名密码
	 * @param username
	 * @param password
	 * @return True
	 * 		If username & password are correct
	 * @return False
	 * 		Otherwise
	 * @exception
	 */
	public Boolean usr_pwd_validation(String username, String password) {
		try{
			/* connect to MongoDb & authenticate */
            MongoClient mongoClient = new MongoClient(ADDRS, CREDENTIALS);
            MongoDatabase database = mongoClient.getDatabase("AiTech");
            
            /* select the password of given username */
            MongoCollection<Document> collection = database.getCollection("User");
            Document mongo_usr_info = collection.find(Filters.eq("username", username)).first();
            mongoClient.close();
            
            /* compare the pwd */
            if (mongo_usr_info == null){
            	return false;
            } else {
            	JSONObject mongo_usr_info_json = new JSONObject(mongo_usr_info.toJson());
            	String register_time = mongo_usr_info_json.getString("register_time");
            	
            	/* MD5 Encryption algorithm */
	            MessageDigest MD5 = MessageDigest.getInstance("MD5");
	            MD5.update((register_time + password).getBytes());
	            String md5_pwd = new BigInteger(1, MD5.digest()).toString(16);
	            
	            /* compare the encrypted text with password */
	            if ( md5_pwd.equals(mongo_usr_info_json.getString("password"))){
	            	return true;
	            } else {
	            }
            }
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			
		}
		return false;
	}
	
	/*
	 * 验证登录信息
	 * @return void
	 * 	通过response向前端输出验证结果(JSONObject)
	 *   accessGranted: 1 成功; 0 用户名密码错误 ;-1 验证码错误; 
	 */
	@RequestMapping(value = "/login_check", method = RequestMethod.POST)
	public void loginCheck(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String valcode = request.getParameter("valcode");
		JSONObject resp = new JSONObject();
		/* 验证用户名密码 */
		if( (username != null && username.length() != 0) &&
			(password != null && password.length() != 0) &&
			(valcode != null && valcode.length() !=0 )){
			/* 判断验证信息 */
			if(valcode.equals(request.getSession().getAttribute(VAL_CODE))){
				if(usr_pwd_validation(username, password)){
					request.getSession().setAttribute("username", username);
					resp.put("accessGranted", 1);
				}else{
					resp.put("accessGranted", 0);
				}
				
			} else {
				resp.put("accessGranted", -1);
			}
		}
		/* 返回前端 */
		try {
			response.getWriter().print(resp.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			
		}
		return ;
	}
}
