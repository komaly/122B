

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewMetaData
 */
@WebServlet("/ViewMetaData")
public class ViewMetaData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewMetaData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
        response.getWriter().println("<HEAD><TITLE>View Database MetaData </TITLE></HEAD>");
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");       
		String loginUser = "root";
        String loginPasswd = "MySQLPassword123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.getWriter().println("<BODY><H1 style = 'text-align: center; color: white; font-size: 35px'>MetaData of Database</H1>");
        
        try
        {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            
            PreparedStatement statement1 = dbcon.prepareStatement("SELECT * FROM creditcards");
            ResultSet rs1 = statement1.executeQuery();
            ResultSetMetaData rsmd1 = rs1.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Credit Cards</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd1.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd1.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd1.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement2 = dbcon.prepareStatement("SELECT * FROM customers");
            ResultSet rs2 = statement2.executeQuery();
            ResultSetMetaData rsmd2 = rs2.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Customers</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd2.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd2.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd2.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement3 = dbcon.prepareStatement("SELECT * FROM employees");
            ResultSet rs3 = statement3.executeQuery();
            ResultSetMetaData rsmd3 = rs3.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Employees</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd3.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd3.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd3.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement4= dbcon.prepareStatement("SELECT * FROM genres");
            ResultSet rs4 = statement4.executeQuery();
            ResultSetMetaData rsmd4 = rs4.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Genres</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd4.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd4.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd4.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement5= dbcon.prepareStatement("SELECT * FROM genres_in_movies");
            ResultSet rs5 = statement5.executeQuery();
            ResultSetMetaData rsmd5 = rs5.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Genres In Movies</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd5.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd5.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd5.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement6= dbcon.prepareStatement("SELECT * FROM movies");
            ResultSet rs6 = statement6.executeQuery();
            ResultSetMetaData rsmd6 = rs6.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Movies</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd6.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd6.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd6.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement7= dbcon.prepareStatement("SELECT * FROM ratings");
            ResultSet rs7 = statement7.executeQuery();
            ResultSetMetaData rsmd7 = rs7.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Ratings</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd7.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd7.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd7.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement8= dbcon.prepareStatement("SELECT * FROM sales");
            ResultSet rs8 = statement8.executeQuery();
            ResultSetMetaData rsmd8 = rs8.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Sales</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd8.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd8.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd8.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement9= dbcon.prepareStatement("SELECT * FROM stars");
            ResultSet rs9 = statement9.executeQuery();
            ResultSetMetaData rsmd9 = rs9.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Stars</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd9.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd9.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd9.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            PreparedStatement statement0= dbcon.prepareStatement("SELECT * FROM stars_in_movies");
            ResultSet rs0 = statement0.executeQuery();
            ResultSetMetaData rsmd0 = rs0.getMetaData();
            response.getWriter().println("<H2 style = 'text-align: center; color: white;'>Stars In Movies</H2>");
            response.getWriter().println("<TABLE border align=\"center\">");
            response.getWriter().println("<tr>" + "<td>" + "Column Attribute" + "</td>" + "<td>"
            		+ "Column Type" + "</td>" + "</tr>");
            for (int i = 0; i < rsmd0.getColumnCount(); i++)
            {
            	response.getWriter().println("<tr>" + "<td>" + rsmd0.getColumnLabel(i + 1)
            			+ "</td>" + "<td>" + rsmd0.getColumnTypeName(i + 1) + "</td>" + "</tr>");
            }
            response.getWriter().println("</TABLE border>");
            
            response.getWriter().println("<a href='employeeMain.html' title='EmployeeMain'>"
               		+ "<button style='height:35px;width:100px;position:relative;float: right;bottom:0px;right:0px;z-index:999'>Back to Main Page</button>"
               		+ "</a>");
        }
        catch(Exception e)
        {
        	response.getWriter().println("error");
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
