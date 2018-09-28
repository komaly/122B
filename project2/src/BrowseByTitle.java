

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
 * Servlet implementation class BrowseByTitle
 */
@WebServlet("/BrowseByTitle")
public class BrowseByTitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseByTitle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String loginUser = "root";
        String loginPasswd = "MySQLPassword123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";

        response.setContentType("text/html"); // Response mime type

        response.getWriter().println("<HTML><HEAD><TITLE>Titles</TITLE></HEAD>");
        response.getWriter().println("<BODY><H1 style = 'text-align: center; color:white;'>Browse By Title</H1>");
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");
        
        try
        {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	        Statement statement = dbcon.createStatement();
	        
	        String query = "Select distinct(substr(title, 1, 1)) as letter " + 
	        		"from movies " + 
	        		"order by letter ASC;";
	        
	        ResultSet rs = statement.executeQuery(query);
	        
	        response.getWriter().println("<div style=\"text-align:center\">");
            response.getWriter().println("<TABLE border align= 'center' width = '1000'>");

            response.getWriter().println("<tr>");
	        while (rs.next()){
	        	String letter = rs.getString("letter");
	        	if (letter.matches("[a-zA-Z]+"))
	        	{
	        		response.getWriter().println("<td style= 'text-align: center;'>");
		        	response.getWriter().println("<a href='browseByTitle.jsp?letter=" + letter + "'>" + letter + "</a>");
		        	response.getWriter().println("  </td>");
	        	}
	        }
	        response.getWriter().println("</tr>");
	        
	        response.getWriter().println("</TABLE border>");
	        response.getWriter().println("<div style='position:relative;float: right;bottom:0px;right:0px;'>"
	        		+ "<a href='index.html' title='back'>"
	        		+ "<button class='btn btn-light'>Back To Main Page</button>"
	        		+ "</a>"
	        		+ "<a href='ShoppingCart' title='Checkout'>"
	        		+ "<button class=\"btn btn-light\" >Checkout</button>"
	        		+ "</a>"
	        		+ "<a href = 'logout.jsp'>"
	        		+ "<button class = 'btn btn-light'>Logout</button>"
	        		+ "</a>"
	        		+ "</div>");
	    	
	        response.getWriter().println("</div>");

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
