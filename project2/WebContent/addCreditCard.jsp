<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Credit Card</title>
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
			#create .container #create-row #create-column #create-box {
			  margin-top: 120px;
			  max-width: 600px;
			  height: 350px;
			  border: 1px solid #9C9C9C;
			  background-color: #EAEAEA;
			}
			#create .container #create-row #create-column #create-box #create-form {
			  padding: 20px;
			}
		</style>
	
	</head>
	<body>
		<%
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String address = request.getParameter("address");
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		String reenteredPassword = request.getParameter("password2");
		
		if (!password.equals(reenteredPassword))
		{
			response.getWriter().println("<script type=\"text/javascript\">");
    	   	response.getWriter().println("alert('Passwords entered are not the same, please try again.');");
    	   	response.getWriter().println("location='createaccount.html';");
    	   	response.getWriter().println("</script>");
    	   	return;
		}
		
		if (firstName.equals("") || lastName.equals("") || address.equals("") || email.equals("")
				|| password.equals("") || reenteredPassword.equals(""))
		{
			response.getWriter().println("<script type=\"text/javascript\">");
    	   	response.getWriter().println("alert('All fields must be filled, please try again.');");
    	   	response.getWriter().println("location='createaccount.html';");
    	   	response.getWriter().println("</script>");
    	   	return;
		}

		String loginUser = "root";
		String loginPasswd = "MySQLPassword123";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
    	   			
        PreparedStatement statement2 = dbcon.prepareStatement("SELECT * FROM customers WHERE firstName = ? and lastName = ?");
        statement2.setString(1, firstName);
        statement2.setString(2, lastName);
        ResultSet rs2 = statement2.executeQuery();
        if (rs2.next())
        {
     	    response.getWriter().println("<script type=\"text/javascript\">");
       		response.getWriter().println("alert('A user with this name is already registered.');");
   	   		response.getWriter().println("location='createaccount.html';");
   	   		response.getWriter().println("</script>");
   	   		return;
        }
    	   			
		%>
		<div id = "create">
			<h1 class="text-center text-white pt-5">Add a Credit Card</h1>
			<div class = "container">
				<div id="create-row" class="row justify-content-center align-items-center">
					<div id="create-column" class="col-md-6">
						<div id="create-box" class="col-md-12">
							<h1 class="text-center text-info pt-5">A credit card must be added to create an account.</h1>
							<form method="get" action="createaccount.jsp" class = "form">
								<label for="username" class="text-info">Credit Card Number:</label><br>
		                        <input type="text" name="cnum" id="cnum" class="form-control">
		                                
		                        <label for="username" class="text-info">Expiration date(Use format YYYY-MM-DD):</label><br>
		                        <input type="text" name="expdate" id="expdate" class="form-control">
		                        
		                        <div class="form-group">
		                           	<input type="submit" name="submit" class="btn btn-info btn-md" value="Submit">
								</div>
								<input type="hidden" id="fName" name="fName" value="<%= firstName%>" >
								<input type="hidden" id="lName" name="lName" value="<%= lastName%>" >
								<input type="hidden" id="address" name="address" value="<%= address%>" >
								<input type="hidden" id="email" name="email" value="<%= email%>" >
								<input type="hidden" id="password" name="password" value="<%= password%>" >
							</form>
						</div>
					</div>
				</div>	
			</div>
		</div>
	</body>
</html>