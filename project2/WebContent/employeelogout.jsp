<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	session = request.getSession();
	String user = "";
	
	if (session.getAttribute("user") == null || session.getAttribute("user") == "" || session.getAttribute("user").equals(""))
	{
		response.sendRedirect("_dashboard.html");
	}
	
	else
	{
		user = session.getAttribute("user").toString();
	}
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Logout Confirmation</title>

	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>
	<style>
		#main .container #main-row #main-column #main-box {
		  margin-top: 120px;
		  max-width: 600px;
		  height: 320px;
		}
	</style>
</head>
<body>
	<div id = "main">
		<div class = "container">
			<div id="main-row" class="row justify-content-center align-items-center">
				<div id="main-column" class="col-md-6">
					<div id="main-box" class="col-md-12">
						<h1 style = "text-align: center; color: white;">Are you sure you want to log out?</h1>
						<%
							response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
							response.setHeader("Progma", "no-cache");
							response.addDateHeader("Expires", 0);
						%>
						<div class = "text-center form-group">
							<a href = "employeesignout.jsp">
								<button class = "btn btn-light">Yes, logout</button>
							</a>
							
							<a href = "employeeMain.html">
								<button class = "btn btn-light">No, back to main page.</button>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>