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
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<style>
	#search .container #search-row #search-column #search-box {
	  	margin-top: 120px;
	  	max-width: 600px;
	  	height: 450px;
	}
	#search .container #search-row #search-column #search-box h1{
	 	
		text-align: center;
	}
</style>
<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>
</head>
<body>

<div id = "search">
	<div class = "container">
		<div id="search-row" class="row justify-content-center align-items-center">
			<div id="search-column" class="col-md-6">
				<div id="search-box" class="col-md-12">
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
						
						String n = request.getParameter("starName");
					%>
					<h1 class="text-center text-white pt-5"><%=n %></h1>
					<%
						ps.setString(1, n);
						ResultSet rs=ps.executeQuery();
						
						out.println("<TABLE BORDER align='center'>");
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
								if (i != movieNames.size() - 1)
									toPrint += ("<a href = 'singlemovie.jsp?title=" + movieNames.get(i) + "'> " + movieNames.get(i) + ", </a>");
								else
									toPrint += ("<a href = 'singlemovie.jsp?title=" + movieNames.get(i) + "'> " + movieNames.get(i) + " </a>");

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
				</div>
			</div>
		</div>
	</div>
</div>
<div style="position:relative;float: right;bottom:0px;right:0px;z-index:999" >
	<a href="ShoppingCart" title="Checkout">
	   		<button class="btn btn-light">Checkout</button>
	</a>
	<a href="index.html" title="back">
	  		<button class="btn btn-light">Back To Main Page</button>
	</a>
	<a href = "logout.jsp">
		<button class = "btn btn-light">Logout</button>
	</a>
</div>
</body>
</html>