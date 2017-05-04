package com.example.model;

import java.util.Date;

public class Comment {
   private int id;
   private int userId;
   private Date creatDate;
   private String userName;
   private String content;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public Date getCreatDate() {
	return creatDate;
}
public void setCreatDate(Date creatDate) {
	this.creatDate = creatDate;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}


}
