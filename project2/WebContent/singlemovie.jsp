<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.sql.DataSource"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Single Movie Information</title>
<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>
</head>
<body>

<h1>Single Movie Information</h1>
<%
try
{
	Context initCtx = new InitialContext();
    if (initCtx == null)
        response.getWriter().println("initCtx is NULL");

    Context envCtx = (Context) initCtx.lookup("java:comp/env");
    if (envCtx == null)
    	response.getWriter().println("envCtx is NULL");

    // Look up our data source
    DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

    if (ds == null)
    	response.getWriter().println("ds is null.");

    Connection dbcon = ds.getConnection();
	PreparedStatement ps= dbcon.prepareStatement(
			"Select m.id, m.title, m.year, m.director, GROUP_CONCAT(DISTINCT(g.name)) AS genres_list, GROUP_CONCAT(DISTINCT(s.name)) AS stars_list "
	     		+ "from stars s "
	     		+ "inner join stars_in_movies as sm on sm.starId = s.id "
	     		+ "inner join movies as m on m.id = sm.movieId "
	     		+ "inner join genres_in_movies as gm on gm.movieId = m.id "
	     		+ "inner join genres as g on g.id = gm.genreId "
	     		+ "where m.title like ? "
	     		+ "group by m.title;");
	
	ps.setString(1, request.getParameter("title"));
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
	    	toPrint += ("<a href = 'browseByGenre.jsp?genre=" + genresNames.get(j) + "'> " + genresNames.get(j) + ", </a>");
	    }
	    
	    toPrint += "</td>" + "<td> ";
	    
		List<String> starsNames = Arrays.asList(stars_list.split("\\s*,\\s*"));
		for (int i = 0; i < starsNames.size(); i++)
		{
			toPrint += ("<a href = 'singlestar.jsp?starName=" + starsNames.get(i) + "'> " + starsNames.get(i) + ", </a>");
		}
		
		String temp = "</td>" + "<td>" + id + "</td>" + "<td>" 
		+ "<form action='AddToShoppingCart.jsp'>" 
		+ "<input type='hidden' name='movie' value='" + title +  "'/>"
		+ "<input type='submit' value='Add to Cart' /></form>" + "</td>" + "</tr>";
		toPrint += temp;
		out.println(toPrint); 
	}
	
	out.println("</TABLE BORDER>");
	 dbcon.close();
     rs.close();
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