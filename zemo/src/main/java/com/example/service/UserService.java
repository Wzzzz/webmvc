package com.example.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.SessionDAO;
import com.example.DAO.UserDAO;
import com.example.model.Session;
import com.example.model.User;

@Service
public class UserService {
	   @Autowired
	   private UserDAO userDAO;
	   @Autowired
	   private SessionDAO sessionDAO;
  
	   /*根据用户ID随机生成ticket，并加在数据库中，返回一个ticket*/ 
	   public String addTicket(int userId){
	   Session session = new Session();   
	   session.setUserId(userId);
	   Date now = new Date();
	   now.setTime(3600*24+now.getTime());
	   session.setExpired(now);
	   session.setStatus(0);
	   session.setTicket(UUID.randomUUID().toString());  
	   sessionDAO.addSession(session);
	   return session.getTicket();
	   } 
   
	   /*登录*/
	   public Map<String, Object> login(String username, String password){
			Map<String, Object> map = new HashMap<String, Object>(); 
			if(username == ""){
				map.put("msg","用户名不能为空");	
			    return map;
			}
			if(password == ""){
				map.put("msg","密码不能为空");	
			    return map;
			}
	      User user = userDAO.selectByName(username);	
		   if(user == null){
		       map.put("msg","用户名不存在");	
			   return map;   
		   }
		  if(!password .equals(user.getPassword())){ 
			  map.put("msg","密码错误");	
			   return map;    
		  }
		  String ticket = addTicket(user.getId());
		  map.put("ticket", ticket);
		  map.put("user", user);
		  return map;
	   }
	   /*注册*/		    
	   public Map<String, Object> register(String username, String password){
			Map<String, Object> map = new HashMap<String, Object>(); 
			if(username == ""){
				map.put("msg","用户名不能为空");	
			    return map;
			}
			if(password == ""){
				map.put("msg","密码不能为空");	
			    return map;
			}
	       User user = userDAO.selectByName(username);	
		   if(user != null){
		       map.put("msg","用户名已经被注册");	
			   return map;   
		   }  
		   user = new User();
		   Date date = new Date();
		   user.setName(username);
		   user.setPassword(password);
		   user.setDatetime(date);
		   user.setInformation("");
		   userDAO.addUser(user);
		   user.setId(userDAO.selectByName(username).getId());
		   String ticket = addTicket(user.getId());
		   map.put("ticket", ticket);
		   map.put("user", user);
		   return map;
		}
	   /*更新信息*/	
	   public Map<String, Object> update(User user2, String message){ 
		   Map<String, Object> map = new HashMap<String, Object>(); 	
		   userDAO.updateMessage(message,user2.getId());
		   User user = userDAO.selectByName(user2.getName());
		   map.put("user", user);
		   return map;
	   }
      /*退出*/
      public void logout(String ticket){
    	 sessionDAO.updateStatus(ticket,1); 
      }
		
	}