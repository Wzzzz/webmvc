package com.example.controller;


import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.model.UserHolder;
import com.example.service.UserService;


@Controller
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	UserHolder userHolder;
	
	@RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET}) 
	public String reglogin(Model model){
       return "login";	
       }	
		
	@RequestMapping(path = {"/login/"}, method = {RequestMethod.POST}) 
	public String login(Model model,
			            @RequestParam("name") String name,
	                    @RequestParam("password") String password, 
	                    HttpServletResponse response){
		
		Map<String, Object> map = userService.login(name, password);	
		if(map.containsKey("ticket")){
			Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
			cookie.setPath("/");
			response.addCookie(cookie);
			userHolder.setUser((User)map.get("user"));
			return "redirect:/";
		 }
      // else if(map.containsKey("msg"))
		 else{
		 model.addAttribute("msg", map.get("msg"));
	     return "login";   	
		 }	
	}
/*	
	@RequestMapping(path = {"/login/"}, method = {RequestMethod.POST}) 
	public ModelAndView login(@RequestParam("name") String name,
	                    @RequestParam("password") String password, 
	                    HttpServletResponse response){
		ModelAndView model = new ModelAndView("home");
		ModelAndView model2 = new ModelAndView("login");
		Map<String,String> map = userService.login(name, password);	
		if(map.containsKey("ticket")){
			Cookie cookie = new Cookie("ticket",map.get("ticket"));
			cookie.setPath("/");
			response.addCookie(cookie);
			model.addAllObjects(map);
			return model;
		 }
      // else if(map.containsKey("msg"))
		 else{
		 model2.addAllObjects(map);
	     return model2;   	
		 }	
	}
*/	
	
		
	@RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST}) 
	public String reg(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("password") String password,
                      HttpServletResponse response
			          ){
	   Map<String,Object> map = userService.register(name, password);
	   if(map.containsKey("ticket")){
			Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
			cookie.setPath("/");
			response.addCookie(cookie);
			userHolder.setUser((User)map.get("user"));
			return "redirect:/";
		 }
//	   else if(map.containsKey("msg"))
	   else{
	   model.addAttribute("msg", map.get("msg"));
	   return "login";
	   }
  }
}
