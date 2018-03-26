

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BrowseByGenre
 */
@WebServlet("/BrowseByGenre")
public class BrowseByGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseByGenre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginUser = "root";
        String loginPasswd = "MySQLPassword123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("text/html"); // Response mime type

        response.getWriter().println("<HTML><HEAD><TITLE>List of Genres</TITLE></HEAD>");
        response.getWriter().println("<BODY><H1>Genre List</H1>");
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");
        
        try
        {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	        Statement statement = dbcon.createStatement();
	        
	        String query = "Select name from genres;";
	        ResultSet rs = statement.executeQuery(query);
	        
            response.getWriter().println("<TABLE border>");

	        while (rs.next()){
	        	String genre = rs.getString("name");
	        	response.getWriter().println("<tr>" + "<td>");
	        	response.getWriter().println("<a href='browseByGenre.jsp?genre=" + genre + "'> " + genre + "</a>");
	        	response.getWriter().println("</tr>" + "</td>");
	        }
	        
	        response.getWriter().println("</TABLE border>");
	        response.getWriter().println("<a href=\"ShoppingCart\" title=\"Checkout\">" + 
	        		" <button style=\"height:25px;width:100px\">Checkout</button>" + 
	        		"</a>");
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
