package com.example.controller;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.DAO.SessionDAO;
import com.example.DAO.UserDAO;
import com.example.model.Comment;
import com.example.model.User;
import com.example.model.UserHolder;
import com.example.service.CommentService;
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
	@Autowired
	CommentService commentService;
	
	
	
	@RequestMapping(path = {"/","index"},method = {RequestMethod.GET}) 
	public String home(Model model,
			           HttpServletRequest request){
		if(request.getCookies()!=null){
		List<Comment> commentlist = commentService.getLatestComment(0,4);
		Comment comment0 = commentlist.get(0);
		Comment comment1 = commentlist.get(1);
		Comment comment2 = commentlist.get(2);
		Comment comment3 = commentlist.get(3);
		model.addAttribute("comment0",comment0);
		model.addAttribute("comment1",comment1);
		model.addAttribute("comment2",comment2);
		model.addAttribute("comment3",comment3);
	    return "home";}
		else  return "login";
	}

	@RequestMapping(path = {"/update/"}, method = {RequestMethod.POST}) 
	public String update(Model model,
            @RequestParam("message") String message,
	        HttpServletRequest request)
	        {
		    if(request.getCookies()!=null){
		    User user = userHolder.getUser();
		    Map<String, Object> map = userService.update(user, message);
            userHolder.setUser((User)map.get("user"));
			return "redirect:/";   	
		    }
		    else return "login";
		    }
	
	@RequestMapping(path = {"/commen/"}, method = {RequestMethod.POST}) 
	public String reg(Model model,
                      @RequestParam("comme") String comme,
                      HttpServletRequest request
			          ){
		if(request.getCookies()!=null){
		User user = userHolder.getUser();
		commentService.addComment(comme,user);
		return "redirect:/"; 
		}
		else return "login";
}
	
	
	@RequestMapping(path = {"/out/"}, method = {RequestMethod.POST}) 
	public String out(Model model,
			          @CookieValue("ticket")  String ticket,
			          HttpServletRequest request){
	 if(request.getCookies()!=null){ 
		userService.logout(ticket);}
	  return "login";
	}
  

}
