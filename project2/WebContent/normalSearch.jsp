<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie List</title>
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">
 
<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>
 
<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>	

<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>

</head>
<body>
<h1>Movie List</h1>

<table border="2" id = movielist>
<thead>
<tr>
   <th>Title</th>
   <th>Year</th>
   <th>Director</th>
   <th>Genres List</th>
   <th>Stars List</th>
   <th>Movie ID</th>
   <th>Add to Cart</th>
</tr>
</thead>
<tbody>
<%
try
{
	String loginUser = "root";
    String loginPasswd = "MySQLPassword123";
    String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
    
    String query = request.getParameter("title");
    String[] qArray = query.trim().split("\\s+");
    
    String s = "select * from movietitles "
    		+ "where MATCH(titles) AGAINST('"; 
    
    for (int i = 0; i < qArray.length; i++)
    {
    	String add = qArray[i] + "* ";
    	s += add;
    }
    
    s += "' in boolean mode)";

    PreparedStatement statement = dbcon.prepareStatement(s);
    ResultSet rs = statement.executeQuery();
    
    if (!rs.next()) //no results
    {
    	response.getWriter().println("<script type=\"text/javascript\">");
   		response.getWriter().println("alert('There are no movies with this information, please try again.');");
   		response.getWriter().println("location='index.html';");
   		response.getWriter().println("</script>");
    }
    
    rs.beforeFirst();
    
    PreparedStatement statement2 = dbcon.prepareStatement("select m.title, m.year, m.director, m.id, GROUP_CONCAT(DISTINCT(g.name)) AS genres_list, GROUP_CONCAT(DISTINCT(s.name)) AS stars_list "
			+ "from genres as g "
			+ "inner join genres_in_movies as gm on gm.genreId = g.id "
			+ "inner join movies as m on m.id = gm.movieId "
			+ "inner join stars_in_movies as sm on sm.movieId = m.id "
			+ "inner join stars as s on s.id = sm.starId "
			+ "where m.title = ? "
			+ "group by m.title");
    ResultSet rs2;
    
    
    while (rs.next()){
    	statement2.setString(1, rs.getString("titles"));
    	rs2 = statement2.executeQuery();
    	
    	while (rs2.next())
    	{
			String mId = rs2.getString("id");
			String mTitle = rs2.getString("title");
			String mYear = rs2.getString("year");
			String mDirector = rs2.getString("director");
			String genres_list = rs2.getString("genres_list");
			String stars_list = rs2.getString("stars_list");
			%>
			
			<tr>
			<td>
			<%
				String titleLink = "<a href='singlemovie.jsp?title=" + mTitle + "'>" + mTitle + "</a>";
			%>
			 <%=titleLink%>
			</td>
			
			<td>
			<%=mYear %>
			</td>
			
			<td>
			<%=mDirector %>
			</td>
			
			<td>
			<%=genres_list %>
			</td>
			
			<td>
			<%
				String toPrint = "";
				List<String> starsNames = Arrays.asList(stars_list.split("\\s*,\\s*"));
				for (int i = 0; i < starsNames.size(); i++)
				{
					toPrint += ("<a href = 'singlestar.jsp?starName=" + starsNames.get(i) + "'> " + starsNames.get(i) + ", </a>");
				}
			%>
			<%=toPrint %>
			</td>
			
			<td>
			<%=mId %>
			</td>
			
			<td>
				<form action= "AddToShoppingCart.jsp">
					<input type="hidden" name="movie" value="<%=mTitle %>" />
				 	<input type="submit" value="Add to Cart">
				</form>
			</td>
			
			</tr>
		
<% 		}
    }
}
catch(InstantiationException | IllegalAccessException | ClassNotFoundException e)
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}
catch (SQLException e)
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>
</tbody>

</table>

<a href="ShoppingCart" title="Checkout">
   		<button style="height:35px;width:100px">Checkout</button>
</a>

<a href="index.html" title="back">
  		<button style="height:35px;width:100px">Back To Main Page</button>
</a>

<script>
       $(function () {
           $('#movielist').dataTable({
        	   "bFilter": false
           });
       });
</script>


</body>

</html>