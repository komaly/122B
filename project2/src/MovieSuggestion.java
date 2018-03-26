

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MovieSuggestion
 */
@WebServlet("/movie-suggestion")
public class MovieSuggestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieSuggestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setContentType("text/html");
			// setup the response json arrray
			JsonArray jsonArray = new JsonArray();
			
			// get the query string from parameter
			String query = request.getParameter("query");
        
			String loginUser = "root";
	        String loginPasswd = "MySQLPassword123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	        
			
			// return the empty json array if query is null or empty
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
		    String[] qArray = query.trim().split("\\s+");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	  
	        PreparedStatement statement, statement2;
	        ResultSet rs, rs2;
	        
	        String s = "select * from movie_titles_ids "
	        		+ "where MATCH(titles) AGAINST('"; 
	        
	        String sStars = "select * from stars_names_ids "
	        		+ "where MATCH(names) AGAINST('"; 
	        
	        for (int i = 0; i < qArray.length; i++)
	        {
	        	String add = qArray[i] + "* ";
	        	s += add;
	        	sStars += add;
	        }
	        
	        s += "' in boolean mode)";
	        sStars += "' in boolean mode)";
	        
	        statement = dbcon.prepareStatement(s);
        	rs = statement.executeQuery();
        	
        	while (rs.next()) //for each title and id
        	{
        		jsonArray.add(generateJsonObject(rs.getString("ids"), rs.getString("titles"), "MOVIES"));
        	}
        	
        	statement2 = dbcon.prepareStatement(sStars);
        	rs2 = statement2.executeQuery();
        	while (rs2.next()) //for each star and id
        	{
        		jsonArray.add(generateJsonObject(rs2.getString("ids"), rs2.getString("names"), "STARS"));
        	}
       
			
			response.getWriter().write(jsonArray.toString());
			return;
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Iron Man",
	 *   "data": { "category": "marvel", "ID": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(String ID, String Name, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", Name);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("ID", ID);
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
