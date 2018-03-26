import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieList() {
        super();
    }
    
    public String getServletInfo() {
        return "Servlet connects to MySQL database and displays result of a SELECT";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String loginUser = "root";
        String loginPasswd = "MySQLPassword123";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>MovieDB: Top 20 Rated Movies</TITLE></HEAD>");
        out.println("<BODY><H1>MovieDB: Top 20 Rated Movies</H1>");
        out.println("<link rel='stylesheet' href='/project1/style.css' type='text/css' media='all'/>");

        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();

            String query = 
            		"SELECT m.title, m.year, m.director, GROUP_CONCAT(DISTINCT(g.name)) AS genre_list, GROUP_CONCAT(DISTINCT(s.name)) AS stars_list, r.rating\r\n" + 
            		"FROM ratings r \r\n" + 
            		"INNER JOIN movies AS m ON r.movieId = m.id\r\n" + 
            		"INNER JOIN genres_in_movies AS gm ON m.id = gm.movieId\r\n" + 
            		"INNER JOIN genres AS g ON g.id = gm.genreId\r\n" + 
            		"INNER JOIN stars_in_movies AS sm ON m.id = sm.movieId\r\n" + 
            		"INNER JOIN stars AS s ON s.id = sm.starId\r\n" + 
            		"GROUP BY m.title\r\n" + 
            		"ORDER BY r.rating DESC\r\n" + 
            		"LIMIT 20\r\n";

            // Perform the query
            ResultSet rs = statement.executeQuery(query);

            out.println("<TABLE border>");

            // Iterate through each row of rs
            while (rs.next()) {
                String m_title = rs.getString("title");
                String m_year = rs.getString("year");
                String m_director = rs.getString("director");
                String m_genres = rs.getString("genre_list");
                String m_stars = rs.getString("stars_list");
                String m_rating = rs.getString("rating");
                out.println("<tr>" + "<td>" + m_title + "</td>" + "<td>" + m_year + "</td>" + "<td>" + m_director
                		+ "</td>" + "<td>" + m_genres + "</td>" + "<td>" + m_stars + "</td>" + "<td>" +
                		m_rating+ "</td>" + "</tr>");
            }

            out.println("</TABLE>");

            rs.close();
            statement.close();
            dbcon.close();
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
