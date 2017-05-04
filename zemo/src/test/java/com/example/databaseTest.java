package com.example;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.DAO.CommentDAO;
import com.example.model.Comment;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
public class databaseTest {
	
	@Autowired
	CommentDAO commentDAO;	
	
   @Test
   public void initDatabase() {
    for(int i = 0;i<11;i++){
	   Comment comment = new Comment();
	   Date date = new Date();
	   comment.setCreatDate(date);
	   comment.setUserId(i);
	   comment.setContent("bulalalal");
	   commentDAO.addComment(comment);
		}
    
    System.out.println(commentDAO.selectLatestComment(0,10));
 
	}

}
