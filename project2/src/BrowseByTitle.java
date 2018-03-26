

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
        response.getWriter().println("<BODY><H1>Pick a letter or number to view titles that start with it.</H1>");
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
            response.getWriter().println("<TABLE border>");

            response.getWriter().println("<tr>");
	        while (rs.next()){
	        	String letter = rs.getString("letter");
        		response.getWriter().println("<td>  ");
	        	response.getWriter().println("<a href='browseByTitle.jsp?letter=" + letter + "'>" + letter + "</a>");
	        	response.getWriter().println("  </td>");

	        	
	        }
	        response.getWriter().println("</tr>");
	        
	        response.getWriter().println("</TABLE border>");
	        response.getWriter().println("<a href=\"ShoppingCart\" title=\"Checkout\">" + 
	        		" <button style=\"height:25px;width:100px\">Checkout</button>" + 
	        		"</a>");
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
