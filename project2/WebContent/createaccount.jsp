<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.util.*"%>
<%@page import = "java.io.*" %>
<%@page import = "java.text.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Account Created</title>
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	
		<style>
			body {
			  margin: 0;
			  padding: 0;
			  background-color: #17A2B8 !important;
			  height: 100vh;
			}
		</style>
	</head>
	<body>
		<%
		try
		{
			String loginUser = "root";
			String loginPasswd = "MySQLPassword123";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
			
			String firstName = request.getParameter("fName");
			String lastName = request.getParameter("lName");
			String address = request.getParameter("address");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String ccNum = request.getParameter("cnum");
			String expDate = request.getParameter("expdate");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			
			try
			{
				dateFormat.parse(expDate);
			}
			catch(ParseException e)
			{
				response.getWriter().println("<script type=\"text/javascript\">");
	    	   	response.getWriter().println("alert('The expiration date must be a valid date in the format YYYY-MM-DD.');");
	    	   	response.getWriter().println("location='createaccount.html';");
	    	   	response.getWriter().println("</script>");
	    	   	return;
			}
			
			
			if (ccNum.equals("") || expDate.equals(""))
			{
				response.getWriter().println("<script type=\"text/javascript\">");
	    	   	response.getWriter().println("alert('All fields must be filled, please try again.');");
	    	   	response.getWriter().println("location='createaccount.html';");
	    	   	response.getWriter().println("</script>");
	    	   	return;
			}
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	    	   			
	        PreparedStatement statement4 = dbcon.prepareStatement("Select * from customers where ccId = ?");
	    	statement4.setString(1, ccNum);
	    	   			
	    	ResultSet rs2 = statement4.executeQuery();
	    	if (rs2.next())
	    	{
	    		response.getWriter().println("<script type=\"text/javascript\">");
	    	   	response.getWriter().println("alert('Credit card number is already in use, please type in a unique credit card.');");
	    	   	response.getWriter().println("location='createaccount.html';");
	    	   	response.getWriter().println("</script>");
	    	   	return;
	    	}
	    	   			
	        String query = "SELECT MAX(id) as id FROM customers";
			ResultSet rs3 = dbcon.createStatement().executeQuery(query);
			
			String id = null;
			if (rs3.next())
			{
				String max = rs3.getString("id");
				
				int currMax = Integer.parseInt(max) + 1;
				id = Integer.toString(currMax);
			}
            
            PreparedStatement statement3 = dbcon.prepareStatement(
            		"INSERT INTO creditcards(id, firstName, lastName, expiration) VALUES(?, ?, ?, ?)");
            
        	statement3.setString(1, ccNum);
           	statement3.setString(2, firstName);
           	statement3.setString(3, lastName);
           	statement3.setString(4, expDate);
            statement3.executeUpdate();
            
            PreparedStatement statement2 = dbcon.prepareStatement(
           			"INSERT INTO customers(id, firstName, lastName, ccId, address, email, password) VALUES(?, ?, ?, ?, ?, ?, ?)");
           	
           	statement2.setString(1, id);
           	statement2.setString(2, firstName);
           	statement2.setString(3, lastName);
           	statement2.setString(4, ccNum);
           	statement2.setString(5, address);
           	statement2.setString(6, email);
           	statement2.setString(7, password);
            statement2.executeUpdate();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		%>
		<h1 class="text-center text-white pt-5">Your account has been created! You may now login with your email and password.</h1>
		<div style = "text-align: center;">
			<a href = "login.html" >
				<button>Customer Login Page</button>
			</a>
		</div>
	</body>
</html>