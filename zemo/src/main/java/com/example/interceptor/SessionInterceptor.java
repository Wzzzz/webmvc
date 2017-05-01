package com.example.interceptor;


import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.DAO.SessionDAO;
import com.example.DAO.UserDAO;
import com.example.model.Session;
import com.example.model.User;
import com.example.model.UserHolder;

@Component
public class SessionInterceptor implements HandlerInterceptor{

	@Autowired
	SessionDAO sessionDAO; 
	@Autowired
	UserDAO userDAO;
	@Autowired
	UserHolder userHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler)
			throws Exception {
		String ticket = null;
		if(httpServletRequest.getCookies()!=null){
		  for(Cookie cookie : httpServletRequest.getCookies()){	
			if(cookie.getName().equals("ticket")){
			ticket = cookie.getValue();
			break;
			}
		  }	
		}
		if(ticket != null){
		  Session session = sessionDAO.selectByTicket(ticket);
		  if(session == null || session.getExpired().before(new Date())|| session.getStatus()!=0){
			return true;
		  }
		  User user = userDAO.selectById(session.getUserId());
		  userHolder.setUser(user);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView != null){
	    modelAndView.addObject("user", userHolder.getUser());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex)
			throws Exception {
	    userHolder.clear();
		
	}

	
	
	
}
