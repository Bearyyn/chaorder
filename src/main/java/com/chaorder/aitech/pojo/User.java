package com.chaorder.aitech.pojo;

import java.io.Serializable;

public class User implements Serializable {  
	  
    public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
    public User() {
		this.name = "";
		this.password = "";
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}  
	
	private String name;  
    private String password;
}  