package edu.unsw.comp9321.assign2;

import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registration {
	ConnectionManager cm;
	Connection c;
	PreparedStatement register;
	String updateString;
	String uName;
	String nName;
	String fName;
	String lName;
	String password;
	String email;
	Integer birthYear;
	String address;
	String creditCard;
	
	public Registration() {
		cm = new ConnectionManager();
		c = cm.getConnection();
		updateString = "INSERT INTO member " +
							"(username, nickname, firstname," +
							" lastname, password, email, birthyear, " + 
							"address, creditcard, role, locked)" +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'member', true)"; // locked until email
		// prepare statement
		try {
			register = c.prepareStatement(updateString);
		}
		catch (SQLException s) {
			System.out.println("failed to prepare statement");
			s.printStackTrace();
		}
	}
	
	public void register(HttpServletRequest request, HttpServletResponse response) {
		
		/*
		 * substitute values passed via the request object
		 */
		try {
			uName = request.getParameter("username");
			nName = request.getParameter("nickname");
			fName = request.getParameter("firstname");
			lName = request.getParameter("lastname");
			password = request.getParameter("password");
			email = request.getParameter("email");
			birthYear = Integer.parseInt(request.getParameter("birthyear"));
			address = request.getParameter("address");
			creditCard = request.getParameter("creditcard");
			
			boolean result =  validateInput(uName, nName, fName, lName, 
											password, email, birthYear, address, creditCard);
			
			// check if validation failed and return (exit) from method
			if (result == false) {
				redirectFailedAttempt(request, response, "validation");
				return;
			}
			
			register.setString(1, uName);
			register.setString(2, nName);
			register.setString(3, fName);
			register.setString(4, lName);
			register.setString(5, password);
			register.setString(6, email);
			register.setInt(7, birthYear);
			register.setString(8, address);
			register.setString(9, creditCard);
			
		}
		catch (Exception e) {
			System.out.println("An error has occured generating update");
			e.printStackTrace();
		}
		
		/*
		 * Execute query and redirect according to result
		 */
		try {
			int rowsUpdated = register.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("User successfully registered");
				// send email here
				RequestDispatcher rd = request.getRequestDispatcher("Registered.jsp");
				rd.forward(request, response);
			}
			else {
				System.out.println("Registration failed");
				redirectFailedAttempt(request, response, "error");
			}
		}
		catch (Exception e) {
			System.out.println("An exception occured while executing query");
			e.printStackTrace();
			redirectFailedAttempt(request, response, "error");
		}
		
	}
	
	private void redirectFailedAttempt(HttpServletRequest request, HttpServletResponse response,
										String reason) {
		try {
			if (reason.equals("error")) {
				RequestDispatcher rd = request.getRequestDispatcher("RegistrationError.jsp");
				rd.forward(request, response);
			}
			else {
				request.setAttribute("failed", "validation");
				RequestDispatcher rd = request.getRequestDispatcher("Registration.jsp");
				rd.forward(request, response);
			}
			
		}
		catch (Exception e) {
			System.out.println("If you see this error something is very wrong");
		}
	}
	
	// if time permits create method to check data before insert
	public boolean validateInput(String uName, String nName, String fName, String lName, 
									String password, String email, Integer birthYear, 
									String address, String creditCard) { System.out.println("*** validating input ***");
		if (validator.checkText(uName) == false) {
			System.out.println("username failed");
			return false;
		}
		else if (validator.checkText(nName) == false) {
			System.out.println("nickname failed");
			return false;
		}
		else if (validator.checkText(fName) == false) {
			System.out.println("firstname failed");
			return false;
		}
		else if (validator.checkText(lName) == false) {
			System.out.println("lastname failed");
			return false;
		}
		else if (validator.checkPassword(password) == false) {
			System.out.println("password failed");
			return false;
		}
		else if (validator.checkEmail(email) == false) {
			System.out.println("email failed");
			return false;
		}
		else if (validator.checkYear(birthYear) == false) {
			System.out.println("birthyear failed");
			return false;
		}
		else if (validator.checkLongText(address) == false) {
			System.out.println("address failed");
			return false;
		}
		else if (validator.checkCreditCard(creditCard) == false) {
			System.out.println("creditcard failed");
			return false;
		}
		return true;
	}
}
