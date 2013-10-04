package edu.unsw.comp9321.assign2;

import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Admin {
	ConnectionManager cm;
	Connection c;
	
	public Admin() {
		cm = new ConnectionManager();
		c = cm.getConnection();
	}
	
	public void adminFunction(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("admin called");
		if (request.getParameter("haltAuction") != "") {
			System.out.println("*Halt id: --" +request.getParameter("haltAuction") + "--");
			haltAuction(request.getParameter("haltAuction"));
		}
		else if (request.getParameter("removeItem") != "") {
			removeItem(request.getParameter("removeItem"));
		}
		else if (request.getParameter("removeUser") != "") {
			//System.out.println("halt not selected");
			removeUser(request.getParameter("removeUser"));
		}
	}
	
	private void haltAuction(String id) {
		String update = "UPDATE auction " +
						"SET status='ended' " +
						"WHERE id=?";
		System.out.println("halt auction is: " + id);
		try {
			PreparedStatement ps = c.prepareStatement(update);
			ps.setInt(1, Integer.parseInt(id));
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("auction halted");
			}
		}
		catch (SQLException s) {
			s.printStackTrace();
		}
		
	}
	
	private void removeItem(String item) {
		String update = "DELETE FROM auction " +
						"WHERE id=?";
		System.out.println("remove Item: " + item);
		//runUpdate(update);
		try {
			PreparedStatement ps = c.prepareStatement(update);
			ps.setInt(1, Integer.parseInt(item));
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("auction removed");
			}
		}
		catch (SQLException s) {
			s.printStackTrace();
		}
	}
	
	private void removeUser(String user) {
		String update = "DELETE FROM member " +
						"WHERE username=?";
		System.out.println("remove user: " + user);
		//runUpdate(update);
		try {
			PreparedStatement ps = c.prepareStatement(update);
			ps.setString(1, user);
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("user removed");
			}
		}
		catch (SQLException s) {
			s.printStackTrace();
		}
	}

}
