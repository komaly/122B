

import java.io.IOException;
import com.test.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class Login
 */
//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecapthcaResponse=" + gRecaptchaResponse);
		JsonObject responseJsonObject = new JsonObject();

		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		
		if(!valid)
		{
			responseJsonObject.addProperty("status", "fail");
			responseJsonObject.addProperty("message", "Recaptcha is wrong.");
			response.getWriter().write(responseJsonObject.toString());
			return;
		}
        response.setContentType("text/html");   

		String loginUser = "root";
        String loginPasswd = "MySQLPassword123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        String email = request.getParameter("username");
		String password = request.getParameter("password");
        
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();

            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            PreparedStatement statement = dbcon.prepareStatement("Select email, password,id, firstName,lastName from customers where email = ? and password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            // Perform the query
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
    			// login success:
    			// set this user into the session
            	
            	String name = rs.getString("firstName") + " " + rs.getString("lastName");
    			request.getSession().setAttribute("user", new User(email, rs.getString("id"), name));
    			
    			responseJsonObject.addProperty("status", "success");
    			responseJsonObject.addProperty("message", "success");
    			
    			response.getWriter().write(responseJsonObject.toString());    			
            }
            
            else
            {
            	// login fail

    			responseJsonObject.addProperty("status", "fail");
    			
    			PreparedStatement statement1 = dbcon.prepareStatement("Select email from customers where email = ?");
                statement1.setString(1, email);
                ResultSet rs1 = statement1.executeQuery();
                if (rs1.next())
                {
        			responseJsonObject.addProperty("message", "The password " + password + " is incorrect, please try again.");
        			response.getWriter().write(responseJsonObject.toString());
        			return;
                }
                
                PreparedStatement statement2 = dbcon.prepareStatement("Select password from customers where password = ?");
                statement2.setString(1, password);
                ResultSet rs2 = statement2.executeQuery();
                if (rs2.next())
                {
        			responseJsonObject.addProperty("message", "The username " + email + " is incorrect, please try again.");
        			response.getWriter().write(responseJsonObject.toString());
        			return;
                }
                
                responseJsonObject.addProperty("message", "The username " + email + " and password " + password + " are both incorrect, please try again.");
    			response.getWriter().write(responseJsonObject.toString());
            }
         
        }
        catch(Exception e){
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
