package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.CommentDAO;
import com.example.model.Comment;
import com.example.model.User;

@Service
public class CommentService {
	@Autowired
	CommentDAO commentDAO;
	public List<Comment> getLatestComment(int offset,int limit){
	  return commentDAO.selectLatestComment(offset,limit); 
	}
	
	public void addComment(String comme,User user){
	   Comment comment = new Comment();
	   Date date = new Date();
	   comment.setCreatDate(date);
	   comment.setContent(comme);
	   comment.setUserId(user.getId());
	   comment.setUserName(user.getName());
	   commentDAO.addComment(comment);
	}
	
}
