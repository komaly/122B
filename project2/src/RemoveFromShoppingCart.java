

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
		response.getWriter().println("<BODY><H1 style = 'text-align: center; color:white;'>Your shopping cart has been updated!"
				+ " Please click 'Back' to continue looking for movies,"
				+ " 'Checkout' if you have completed your purchase, or 'Logout' if you wish to exit.</H1>");
		
		response.getWriter().println("<div style = 'text-align: center'>");
		response.getWriter().println("<a href='index.html' title='Back'>"
   		+ "<button  class=\"btn btn-light\" '>Back</button>"
   		+ "</a>");
		
		response.getWriter().println("<a href='ShoppingCart' title='Checkout'>"
		   		+ "<button class = 'btn btn-light'>Checkout</button>"
		   		+ "</a>");	
		
		response.getWriter().println("<a href='logout.jsp' title='Logout'>"
		   		+ "<button class = 'btn btn-light'>Logout</button>"
		   		+ "</a>");	
		response.getWriter().println("</div>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
