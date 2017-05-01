package com.example.controller;



import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.DAO.SessionDAO;
import com.example.DAO.UserDAO;
import com.example.model.User;
import com.example.model.UserHolder;
import com.example.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionDAO sessionDAO;	
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	UserHolder userHolder;
	
	@RequestMapping(path = {"/"},method = {RequestMethod.GET}) 
	public String home(Model model){
	       return "login";		
	}
		
	@RequestMapping(path = {"/update/"}, method = {RequestMethod.POST}) 
	public String update(Model model,
            @RequestParam("message") String message)
	        {		
		    User user = userHolder.getUser();
		    Map<String, Object> map = userService.update(user, message);
            userHolder.setUser((User)map.get("user"));
			return "home";   	
   }

	@RequestMapping(path = {"/out/"}, method = {RequestMethod.POST}) 
	public String out(Model model,
			          @CookieValue("ticket")  String ticket){
       userService.logout(ticket);
	   return "login";
	}
  

}
