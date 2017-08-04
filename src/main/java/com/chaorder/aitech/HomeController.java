package com.chaorder.aitech;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Handles requests for the chaorder home page.
 */
@Controller
public class HomeController {
	/* logger declaration */
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * @return login
	 * 		Render the home page of chaorder Q&A engine
	 */
	@RequestMapping(value = "/chaorder_home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		String username = (String)request.getSession().getAttribute("username");
		if(username != null && username.length() !=0 ){
			model.addAttribute("usernmae", username);
			return "ChaorderHome";
		} else {
			return "redirect:/login";
		}
	}
	
	/*
	 * @return login
	 * 		user logout
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus sessionStatus){
		sessionStatus.setComplete();
		return "redirect:/login";
	}
}
