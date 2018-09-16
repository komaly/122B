<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Single Movie Information</title>
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
								"Select m.id, m.title, m.year, m.director, GROUP_CONCAT(DISTINCT(g.name)) AS genres_list, GROUP_CONCAT(DISTINCT(s.name)) AS stars_list "
						     		+ "from stars s "
						     		+ "inner join stars_in_movies as sm on sm.starId = s.id "
						     		+ "inner join movies as m on m.id = sm.movieId "
						     		+ "inner join genres_in_movies as gm on gm.movieId = m.id "
						     		+ "inner join genres as g on g.id = gm.genreId "
						     		+ "where m.title like ? "
						     		+ "group by m.title;");
						
						String t = request.getParameter("title");
						%>
						<h1 class="text-center text-white pt-5"><%=t %></h1>
						<%
						ps.setString(1, t);
						ResultSet rs=ps.executeQuery();
						
						out.println("<TABLE BORDER>");
						out.println("<tr>" + "<td>" + "Title" + "</td>" + "<td>" 
								+ "Year" + "</td>" + "<td>" + "Director" + "</td>" + "<td>" 
								+ "List of Genres" + "</td>" + "<td>" + "List of Stars" + 
								"</td>" + "<td>" + "Movie ID" + "</td>" + "<td>" + "Add to Cart" 
								+ "</td>" + "</tr>");
						
						while(rs.next()){
							String id=rs.getString("id");
							String title=rs.getString("title");
							String mYear = rs.getString("year");
							String mDirector = rs.getString("director");
							String genres_list = rs.getString("genres_list");
							String stars_list = rs.getString("stars_list");
							
							String toPrint = "<tr>" + "<td>" + title + "</td>" + "<td>" + mYear
						    		+ "</td>" + "<td>" + mDirector + "</td>" + "<td>";
							
						    List<String> genresNames = Arrays.asList(genres_list.split("\\s*,\\s*"));
						    for (int j = 0; j < genresNames.size(); j++)
						    {
						    	if (j != genresNames.size()- 1)
						    		toPrint += ("<a href = 'browseByGenre.jsp?genre=" + genresNames.get(j) + "'> " + genresNames.get(j) + ", </a>");
						    	else
						    		toPrint += ("<a href = 'browseByGenre.jsp?genre=" + genresNames.get(j) + "'> " + genresNames.get(j) + " </a>");
						    }
						    
						    toPrint += "</td>" + "<td> ";
						    
							List<String> starsNames = Arrays.asList(stars_list.split("\\s*,\\s*"));
							for (int i = 0; i < starsNames.size(); i++)
							{
								if (i != starsNames.size() - 1)
									toPrint += ("<a href = 'singlestar.jsp?starName=" + starsNames.get(i) + "'> " + starsNames.get(i) + ", </a>");
								else
									toPrint += ("<a href = 'singlestar.jsp?starName=" + starsNames.get(i) + "'> " + starsNames.get(i) + " </a>");
							}
							
							String temp = "</td>" + "<td>" + id + "</td>" + "<td>" 
							+ "<form action='AddToShoppingCart.jsp'>" 
							+ "<input type='hidden' name='movie' value='" + title +  "'/>"
							+ "<input type='submit' value='Add to Cart' /></form>" + "</td>" + "</tr>";
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
</div>
</body>
</html>