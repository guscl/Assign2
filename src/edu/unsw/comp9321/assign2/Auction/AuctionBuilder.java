package edu.unsw.comp9321.assign2;

import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Date;
import java.text.*;

public class AuctionBuilder {
	ConnectionManager cm;
	Connection c;
	PreparedStatement auction; // insert item
	String auctionString;
	//String startTime;
	String length;
	String title;
	String category;
	String picture; // url
	String description;
	String postage;
	int startPrice;
	int reserve;
	int increment;
	String bidHours; String temp;
	
	public AuctionBuilder() {
		cm = new ConnectionManager();
		c = cm.getConnection();
		
		auctionString = "INSERT INTO auction (username, auctiondate, starttime, " +
							"auctionlength, status, title, category, " +
							"picture, description, postagedetails, " +
							"startingprice, reserveprice, bidincrement)" +
						"VALUES (?, ?, ?, ?, 'new', ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// prepare statement
		try {
			auction = c.prepareStatement(auctionString);
		}
		catch (SQLException s) {
			System.out.println("An error occured initialising query");
			s.printStackTrace();
		}
		
	}
	
	/*
	 * Adds auction to database and sends confirmation email
	 * calls addToItem
	 * calls addToAuction
	 */
	public void createAuction(HttpServletRequest request, HttpServletResponse response) {
		/*This version does not yet allow for pictures to be added*/
		System.out.println("createAuction called");
		
		// get values from request
		getValues(request);

		if (validateForm() == false) {
			request.setAttribute("failed", "validation"); System.out.println("Validation failed!!!!");
			return; //invalid input
		} // works up to here
		//SessionBean user = (SessionBean) request.getAttribute("SessionTracker");
		//System.out.println("Adding auction for: " + user.getUser());
		try {
			auction.setString(1, "user1"); // get from session
			DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
			Date date = new Date();
			auction.setString(2, dateFormat.format(date));
			dateFormat = new SimpleDateFormat("HH:mm:ss");
			auction.setString(3, dateFormat.format(date));
			/*DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date(); 
			String time = dateFormat.format(date);*/
			//auction.setTime(2, date.getTime());
			//auction.setDate(2, new Date()); // set current time
			if (request.getParameter("biddingHours").equals("")) {
				auction.setInt(4, 10); System.out.println("empty length default to 10");
			}
			else if (Integer.parseInt(request.getParameter("biddingHours")) < 3 ||
						Integer.parseInt(request.getParameter("biddingHours")) > 60) {
				System.out.println("auction length is invalid");
				redirectFailedAttempt(request, response);
			}
			else {
				auction.setString(4, request.getParameter("length"));System.out.println("specified length");
			}
			auction.setString(5, title);
			auction.setString(6, category);
			auction.setString(7, picture);
			auction.setString(8, description);
			auction.setString(9, postage);
			auction.setInt(10, startPrice);
			auction.setInt(11, reserve);
			auction.setInt(12, increment);
			
			int result = auction.executeUpdate();
			if (result > 0) {
				System.out.println("successfully added auction");
				RequestDispatcher rd = request.getRequestDispatcher("BidList.jsp");
				try {
					rd.forward(request, response);
				}
				catch (Exception e) {
					
				}
				
			}
			else {
				redirectFailedAttempt(request, response);
			}
		}
		catch (SQLException s) {
			System.out.println("An error occured querying the database");
			s.printStackTrace();
			redirectFailedAttempt(request, response);
		}
		
		return;
	}
	
	/*
	 * Initiates auction
	 * sets start time
	 * how to track auction not yet implemented - will need to initialize tracking
	 */
	public void confirm() { // rename to startAuction if needed
		
	}
	
	private void redirectFailedAttempt(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher rdFailed = request.getRequestDispatcher("BidRegistration.jsp");
			rdFailed.forward(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean validateForm() {
		if (validator.checkLongText(title) == false) { System.out.println("title failed validation");
			return false;
		}
		else if (validator.checkLongText(category) == false) { System.out.println("category failed validation");
			return false;
		}
		else if (validator.checkURL(picture) == false) { System.out.println("picture failed validation");
			return false;
		}
		else if (validator.checkLongText(description) == false) { System.out.println("description failed validation");
			return false;
		}
		else if (validator.checkLongText(postage) == false) { System.out.println("postage failed validation");
			return false;
		}
		else if (validator.checkDollarValue(startPrice) == false) { System.out.println("startPrice failed validation");
			return false;
		}
		else if (validator.checkDollarValue(reserve) == false) { System.out.println("reserve failed validation");
			return false;
		}
		else if (validator.checkDollarValue(increment) == false) { System.out.println("increment failed validation");
			return false;
		}
		return true;
	}
	
	/*
	 * Extracts form data from request
	 */
	private void getValues(HttpServletRequest request) {
		//startTime; // set at time of confirmation
		length = (String) request.getParameter("biddinghours");
		title = (String) request.getParameter("title");
		category = (String) request.getParameter("category");
		picture = (String) request.getParameter("picture"); 
		description = (String) request.getParameter("description");
		postage = (String) request.getParameter("postage");
		temp = (String) request.getParameter("startingPrice"); //startingPrice
		System.out.println("startingPrice: " + temp);
		startPrice = Integer.parseInt(temp);
		temp = (String) request.getParameter("reservePrice");
		System.out.println("reservePrice: " + temp);
		reserve = Integer.parseInt(temp);
		temp = (String) request.getParameter("biddingIncrements");
		System.out.println("biddingIncrements: " + temp);
		increment = Integer.parseInt(temp);
		bidHours = (String) request.getParameter("biddingHours");
	}
}
