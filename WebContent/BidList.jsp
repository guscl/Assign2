<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="edu.unsw.comp9321.assign2.*,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Auctions</title>
</head>
<body>

	<table border=1>
		<tr>
			<td>Title</td>
			<td>Description</td>
			<td>Reserve Price</td>
			<td>Lasting Minutes</td>

		</tr>
		<!-- temp section -->
		<%
			if (request.getAttribute("SessionTracker") == null) {
				System.out.println("unknown user is not permitted");
				// redirect user away
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}
			else { 				
			SessionBean sb = (SessionBean) request.getAttribute("SessionTracker");
			String user = sb.getUser();
			System.out.println("user is: " + user);
			}
		%>
		<!-- end of temp  -->
		<%
			
			AuctionReader auctionReader = new AuctionReader();
			ResultSet rs = auctionReader.getAuctions();
			AuctionBean bean = new AuctionBean();
			while(rs.next()){
				bean.setId(rs.getInt("id"));
				bean.setUsername(rs.getString("username"));
				bean.setStartTime(rs.getTime("starttime").toString());
				bean.setAuctionLength(rs.getInt("auctionlength"));
				bean.setStatus(rs.getString("status"));
				bean.setTitle(rs.getString("title"));
				bean.setCategory(rs.getString("category"));
				bean.setPictureUrl(rs.getString("picture"));
				bean.setDescription(rs.getString("description"));
				bean.setPostageDetails(rs.getString("postagedetails"));
				bean.setStartingPrice(rs.getInt("startingprice"));
				bean.setReservePrice(rs.getInt("reserveprice"));
				bean.setBiddingIncrement(rs.getInt("bidincrement"));
		%>

		<tr>
			<td><%=bean.getTitle()%></td>
			<td><%=bean.getDescription()%></td>
			<td><%=bean.getReservePrice()%></td>
			<td>????</td>
		</tr>

		<%
			}
		%>

	</table>
	<br>
	<a href="AuctionBuilder.jsp">Place an item for auction</a>
	<br>

	<br>
	<form action="ControlServlet?action=logout" method="post">
		<input type='submit' value='Log Out'>
	</form>
</body>
</html>
