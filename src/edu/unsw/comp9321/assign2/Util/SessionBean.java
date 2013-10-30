package edu.unsw.comp9321.assign2;

/*
 * SessionBeam tracks user session information.
 */
public class SessionBean {
	String username;
	String role;
	
	public SessionBean(String user, String role) {
		username = user;
		this.role = role;
	}
	
	public String getUser() {
		return username;
	}
	
	public String getRole() {
		return role;
	}
}
