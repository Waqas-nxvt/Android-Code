package com.c.connectone.bo;

import org.json.JSONObject;

public class UserBO {

	private int id;
	private String firstName;
	private String lastName;
	private String emailAdress;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmailAdress() {
		return emailAdress;
	}
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	
	
	public UserBO getUserObvject(JSONObject userObject) {
		UserBO user = new UserBO();
		user.setId(Integer.parseInt(userObject.optString("user_id")));
		user.setFirstName(userObject.optString("user_fname"));
		user.setLastName(userObject.optString("user_lname"));
		user.setEmailAdress(userObject.optString("user_email"));
		return user;
	}
	

	
}
