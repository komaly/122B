<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Single Star Information</title>
<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>
</head>
<body>

<h1>Single Star Information</h1>

<%
try
{
	Connection con = null;
	Class.forName("com.mysql.jdbc.Driver");
	con = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false", "root", "MySQLPassword123");
	
	PreparedStatement ps=(PreparedStatement)con.prepareStatement(
			"select s.name, s.birthYear, group_concat(distinct(m.title)) as movie_list "
			+ "from stars s "
			+ "inner join stars_in_movies as sm on sm.starId = s.id "
			+ "inner join movies AS m ON m.id = sm.movieId "
			+ "where s.name like ?"
			+ "group by s.name");
	
	ps.setString(1, request.getParameter("starName"));
	ResultSet rs=ps.executeQuery();
	
	out.println("<TABLE BORDER>");
	out.println("<tr>" + "<td>" + "Name" + "</td>" + "<td>" 
			+ "Birth Date" + "</td>" + "<td>" + "Movie List" + "</tr>" + "</td>");
	
	while(rs.next()){
		String name =rs.getString("s.name");
		String birthYear =rs.getString("s.birthYear");
		String movie_list = rs.getString("movie_list");
	
		String toPrint = "<tr>" + "<td>" + name + "</td>" + "<td>" + birthYear + "</td>" + "<td> ";
		
		List<String> movieNames = Arrays.asList(movie_list.split("\\s*,\\s*"));
		for (int i = 0; i < movieNames.size(); i++)
		{
			toPrint += ("<a href = 'singlemovie.jsp?title=" + movieNames.get(i) + "'> " + movieNames.get(i) + ", </a>");
		}
		
		String temp = "</td>" + "</tr>";
		
		toPrint += temp;
		
		out.println(toPrint);
	}
	
	out.println("</TABLE BORDER>");
}
catch(Exception e)
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>
<a href="ShoppingCart" title="Checkout">
   		<button style="height:35px;width:100px">Checkout</button>
</a>
<a href="index.html" title="back">
  		<button style="height:35px;width:100px">Back To Main Page</button>
</a>

</body>
</html>