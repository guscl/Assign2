package edu.unsw.comp9321.assign2;

import java.sql.*;

/*
 * Reads Users from the database and returns ResultSets
 * 
 */
public class UserReader {

	ConnectionManager cm;
	Connection c;
	String memberQuery = "SELECT * FROM member";
	PreparedStatement query;

	public UserReader() {
		cm = new ConnectionManager();
		c = cm.getConnection();
		try {
			query = c.prepareStatement(memberQuery);
		} catch (SQLException s) {
			System.out.println("Error preparing statment");
			s.printStackTrace();
		}
	}
	
	/*
	 * Return all the member from the DB
	 */
	public ResultSet getAllMembers() {
		ResultSet set = null;
		try {
			set = query.executeQuery();
			if (set == null) {
				System.out.println("No rows returned");
				return null;
			}
			else {
				System.out.println("returning results");
				return set;
			}
		}
		catch (SQLException s) {
			System.out.println("Error executing query");
			s.printStackTrace();
		}
		return set;
	}

}
