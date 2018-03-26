//TESTING12345

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");  
        
        String title = request.getParameter("title");
        String year = request.getParameter("year");
        String director = request.getParameter("director");
        String star = request.getParameter("star");
        String genre = request.getParameter("genre");
        
        if (title.equals("") || year.equals("") || director.equals("") ||
        		star.equals("") || genre.equals(""))
        {
        	response.getWriter().println("<script type=\"text/javascript\">");
     	   	response.getWriter().println("alert('All fields must be filled, please try again.');");
     	   	response.getWriter().println("location='addmovie.html';");
     	   	response.getWriter().println("</script>");
     	   	return;
        }
        
        if (!year.matches("[0-9]+") || Integer.valueOf(year) < 1500 || Integer.valueOf(year) > 2018)
        {
     	   	response.getWriter().println("<script type=\"text/javascript\">");
        		response.getWriter().println("alert('Year must be a year that is greater than or equal to 1500 or less than 2018, please try again.');");
     	   	response.getWriter().println("location='addmovie.html';");
     	   	response.getWriter().println("</script>");
     	   	return;
        }
        
        if (!director.matches("[a-zA-Z ]+") || !star.matches("[a-zA-Z ]+") || !genre.matches("[a-zA-Z ]+"))
    	{
    	   response.getWriter().println("<script type=\"text/javascript\">");
    	   response.getWriter().println("alert('Must type in a valid name of a director, star, and genre, please try again.');");
    	   response.getWriter().println("location='addmovie.html';");
    	   response.getWriter().println("</script>");
    	   return;
    	}
        
        try
        {
        	Context initCtx = new InitialContext();
            if (initCtx == null)
                response.getWriter().println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
            	response.getWriter().println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/MasterDB");

            if (ds == null)
            	response.getWriter().println("ds is null.");

            Connection dbcon = ds.getConnection();
            CallableStatement cs = dbcon.prepareCall("{call add_movie (?,?,?,?,?)}");

             cs.setString(1, title);
             cs.setString(2, year);
             cs.setString(3, director);
             cs.setString(4, star);
             cs.setString(5, genre);
             
             cs.executeUpdate();
             
 	         response.getWriter().println("<div style=\"text-align:center\">");
             response.getWriter().println("<H1>Congratulations! The following entry has been added to the database:</H1>");
             PreparedStatement statement = dbcon.prepareStatement("SELECT * FROM movies WHERE title = ? and year = ? and director = ?");
             statement.setString(1, title);
             statement.setString(2, year);
             statement.setString(3, director);
             ResultSet rs = statement.executeQuery();
             response.getWriter().println("<TABLE border>");
             response.getWriter().println("<tr>" + "<td>" + "Title" + "</td>" + "<td>" + "Year"
 	        		+ "</td>" + "<td>" + "Director" + "</td>" + "<td>" + "ID" + "</td>" + "<td>" 
            		 + "Star" + "</td>" + "<td>" + "Genre" + "</td>" + "</tr>");
             
             while (rs.next())
             {
            	 response.getWriter().println("<tr>" + "<td>" + rs.getString("title") + "</td>" + "<td>" + rs.getString("year")
            			 					+ "</td>" + "<td>" + rs.getString("director") + "</td>" + "<td>" + rs.getString("id")
            			 					+ "</td>");
             }
             
             response.getWriter().println("<td>" + star + "</td>" + "<td>" + genre + "</td>" + "</tr>");
             
             response.getWriter().println("</TABLE border>");
             
 	         response.getWriter().println("</div>");
 	        response.getWriter().println("<a href='employeeMain.html' title='EmployeeMain'>"
               		+ "<button style='height:35px;width:100px'>Back to Main Page</button>"
               		+ "</a>");

 	       dbcon.close();
 	       rs.close();
 	       cs.close();
        }
        catch(Exception e)
        {
        	response.getWriter().println(e.getMessage());
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
