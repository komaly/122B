<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Progma", "no-cache");
	response.addDateHeader("Expires", 0);
%>
<%
	session = request.getSession();
	String user = "";
	
	if (session.getAttribute("user") == null || session.getAttribute("user") == "" || session.getAttribute("user").equals(""))
	{
		response.sendRedirect("login.html");
	}
	
	else
	{
		user = session.getAttribute("user").toString();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logged Out</title>
</head>
<body>
	<%
		if (session.getAttribute("user") != null)
		{
			session.removeAttribute("user");
			request.getSession(false);
			session.setAttribute("user", null);
			session.invalidate();
			response.sendRedirect("_dashboard.html");
		}
	%>
</body>
</html>