

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class InsertStar
 */
@WebServlet("/InsertStar")
public class InsertStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertStar() {
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
        response.getWriter().println("<HEAD><TITLE>Star Inserted </TITLE></HEAD>");
	
        String name = request.getParameter("name");
        String birthyear = request.getParameter("birthyear");
        
        if (name.equals("") || !name.matches("[a-zA-Z ]+"))
    	{
    	   response.getWriter().println("<script type=\"text/javascript\">");
    	   response.getWriter().println("alert('Must type in a valid name of a star, please try again.');");
    	   response.getWriter().println("location='insertStar.html';");
    	   response.getWriter().println("</script>");
    	   return;
    	}
        
       if (!birthyear.equals("")) 
       {
    	   if (!birthyear.matches("[0-9]+") || Integer.valueOf(birthyear) < 1500  || Integer.valueOf(birthyear) > 2018)
           {
        	   	response.getWriter().println("<script type=\"text/javascript\">");
           		response.getWriter().println("alert('Birth year must be a year that is greater than or equal to 1500 or less than 2018, please try again.');");
        	   	response.getWriter().println("location='insertStar.html';");
        	   	response.getWriter().println("</script>");
        	   	return;
           }
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
           
           Random rand = new Random();
           String id = "";
           PreparedStatement statement1;
          
           while (id.equals(""))
           {
        	   int num = rand.nextInt(9000000) + 1000000;
               String test = "nm" + num;
               statement1 = dbcon.prepareStatement("SELECT id FROM stars where id = ?");
               statement1.setString(1,test);
               ResultSet rs = statement1.executeQuery();
               if (!rs.next())
               {
            	   id = test;
            	   break;
               }
           }
           
           
           PreparedStatement statement2 = dbcon.prepareStatement("SELECT name FROM stars WHERE name = ?");
           statement2.setString(1, name);
           ResultSet rs2 = statement2.executeQuery();
           if (rs2.next())
           {
        	    response.getWriter().println("<script type=\"text/javascript\">");
          		response.getWriter().println("alert('Star name already in database, please try again.');");
       	   		response.getWriter().println("location='insertStar.html';");
       	   		response.getWriter().println("</script>");
       	   		return;
           }
           
           PreparedStatement statement;
           
           if (birthyear.equals(""))
           {
        	   statement = dbcon.prepareStatement(
            		   "INSERT INTO stars(id, name, birthYear) "
            		   + "VALUES('" + id + "', '" + name + "', NULL)");
           }
           
           else
           {
        	   statement = dbcon.prepareStatement(
            		   "INSERT INTO stars(id, name, birthYear) "
            		   + "VALUES('" + id + "', '" + name + "', '" + birthyear + "')");
           }
           
           statement.executeUpdate();
           
           response.getWriter().println("<BODY><div style=\'text-align:center\'><H1>Congratulations! The stars table has been updated with the following entry:</H1>");
       	   
       	   PreparedStatement statement3 = dbcon.prepareStatement("SELECT * FROM stars WHERE id = ?");
       	   statement3.setString(1, id);
	       ResultSet rs3 = statement3.executeQuery();
	       response.getWriter().println("<TABLE border>");
	       
	       response.getWriter().println("<tr>" + "<td>" + "ID" + "</td>" + "<td>" + "Name"
	        		+ "</td>" + "<td>" + "BirthYear" + "</td>" + "</tr>");
	       
	       while (rs3.next())
	       {
	    	   String mId = rs3.getString("id");
	    	   String mName = rs3.getString("name");
	    	   String mbirthYear = rs3.getString("birthYear");
	    	   
	    	   response.getWriter().println("<tr>" + "<td>" + mId + "</td>" + "<td>"
	    			   						+ mName + "</td>" + "<td>" + mbirthYear
	    			   						+ "</td>" + "</tr>");
	       }
	       
	       response.getWriter().println("</TABLE border>");
	       response.getWriter().println("</div></BODY>");
	       response.getWriter().println("<a href='employeeMain.html' title='EmployeeMain'>"
              		+ "<button style='height:35px;width:100px'>Back to Main Page</button>"
              		+ "</a>");
	       
	       dbcon.close();
	       rs2.close();
	       rs3.close();

       }
       catch(Exception e)
       {
       		response.getWriter().print(e.getMessage());
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