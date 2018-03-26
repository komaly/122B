

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.User;

/**
 * Servlet implementation class RemoveFromShoppingCart
 */
@WebServlet("/RemoveFromShoppingCart")
public class RemoveFromShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFromShoppingCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String movie = request.getParameter("movie");
		User user = (User)request.getSession().getAttribute("user");
		user.removeFromCart(movie);
				
		response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");
		
        response.getWriter().println("<HTML><HEAD><TITLE>Updated Cart</TITLE></HEAD>");
		response.getWriter().println("<BODY><H1>Your shopping cart has been updated!"
				+ " Please click 'Search' or 'Browse' to continue looking for movies,"
				+ " or click 'Checkout' if you have completed your purchase.</H1>");
		
		response.getWriter().println("<a href='search.html' title='Search'>"
   		+ "<button style='height:25px;width:100px'>Search</button>"
   		+ "</a>");

		response.getWriter().println("<a href='browse.html' title='Browse'>"
   		+ "<button style='height:25px;width:100px'>Browse</button>"
   		+ "</a>");
		
		response.getWriter().println("<a href='ShoppingCart' title='Checkout'>"
		   		+ "<button style='height:25px;width:100px'>Checkout</button>"
		   		+ "</a>");		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
