

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
        response.getWriter().println("<HTML><HEAD><TITLE>List of Genres</TITLE></HEAD>");
        response.getWriter().println("<BODY><H1>Genre List</H1>");
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");
        
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
            
	        PreparedStatement statement = dbcon.prepareStatement("Select name from genres;");
	        
	        ResultSet rs = statement.executeQuery();
	        
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
	        
	        dbcon.close();
	        rs.close();
        }
        catch(Exception e)
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
