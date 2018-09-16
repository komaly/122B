

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.User;

/**
 * Servlet implementation class CheckOut
 */
@WebServlet("/CheckOut")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");       
		String loginUser = "root";
        String loginPasswd = "MySQLPassword123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String exp = request.getParameter("expiration");
        String cardNum = request.getParameter("cardNum");
        
        if (firstName.equals("") || lastName.equals("") || exp.equals("") || cardNum.equals(""))
        {
        	response.sendRedirect("checkout.html");
        	return;
        }
        
        try
        {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            PreparedStatement statement = dbcon.prepareStatement(
            		"SELECT id, firstName, lastName, expiration "
            		+"FROM creditcards "
            		+"WHERE id = ? and firstName = ? and lastName = ? and expiration = ?");
            
            statement.setString(1, cardNum);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, exp);
            
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
            	User user = (User)request.getSession().getAttribute("user");
        		Map<String, String> cart = user.displayFullCart();
        		
        		for (Map.Entry<String, String> entry : cart.entrySet())
        		{
        			PreparedStatement statement2 = dbcon.prepareStatement(
        					"SELECT id "
        					+ "FROM movies "
        					+ "WHERE title = ?");
        			statement2.setString(1, entry.getKey());
        			ResultSet rs2 = statement2.executeQuery();
        			
        			String movieId = null;
        			if (rs2.next())
        				movieId = rs2.getString("id");
        			        			
        			Calendar cal=Calendar.getInstance();
        			int month =  cal.get(Calendar.MONTH) + 1;
        			String date = cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DAY_OF_MONTH);
        			
        			String query = "SELECT MAX(id) as id FROM sales";
        			ResultSet rs3 = dbcon.createStatement().executeQuery(query);
        			
        			String stringCurrMax = null;
        			if (rs3.next())
        			{
        				String max = rs3.getString("id");
        				
        				int currMax = Integer.parseInt(max) + 1;
        				stringCurrMax = Integer.toString(currMax);
        			}
        			
        			PreparedStatement statement4 = dbcon.prepareStatement(
                			"INSERT INTO sales(id, customerId, movieId, saleDate) "
                			+ "VALUES('" + stringCurrMax + "', '" + user.getCustomerId() + "', '" + movieId
                			+ "', '" + date + "')");
        			
        			statement4.executeUpdate();
        		}
        		
            	
            	response.getWriter().println("<BODY><div style=\'text-align:center; color: white;\'><H1>Confirmation Page</H1>");
            	response.getWriter().println("<H2 style=\'text-align:center; color: white;\'>Your order has been placed. Thank you for shopping with us!</H2></div>");
            
            	response.getWriter().println("<div style='text-align: center'>"
    	        		+ "<a href='index.html' title='back'>"
    	        		+ "<button class='btn btn-light'>Main Page</button>"
    	        		+ "</a>"
    	        		+ "</div>");
            }
            
            else
            {
            	response.getWriter().println("<script type=\"text/javascript\">");
            	response.getWriter().println("alert('Your card information was incorrect, please try again.');");
            	response.getWriter().println("location='checkout.html';");
            	response.getWriter().println("</script>");
            	
        		return;
            }
        	
        }
        
        catch(Exception e)
        {
        	response.getWriter().print("error");
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
