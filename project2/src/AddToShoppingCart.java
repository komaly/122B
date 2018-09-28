

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.User;

/**
 * Servlet implementation class AddToShoppingCart
 */
@WebServlet("/AddToShoppingCart")
public class AddToShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToShoppingCart() {
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
		String quantity = request.getParameter("quantity");
		
		if (quantity.equals(""))
			response.sendRedirect("AddToShoppingCart.jsp?movie=" + movie);
		
		if (Integer.parseInt(quantity) < 0 || quantity.indexOf("e") != -1)
		{
			response.getWriter().println("<script type=\"text/javascript\">");
        	response.getWriter().println("alert('Quantity specified must be zero or greater, please try again.');");
        	response.getWriter().println("location='AddToShoppingCart.jsp?movie=" + movie + "';");
        	response.getWriter().println("</script>");
        	return;
		}
		
		User user = (User)request.getSession().getAttribute("user");
		if (!quantity.equals("0"))
			user.addToCart(movie, quantity);
		else
			user.removeFromCart(movie);
		
        response.getWriter().println("<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>");
		
        response.getWriter().println("<HTML><HEAD><TITLE>Updated Cart</TITLE></HEAD>");
		response.getWriter().println("<BODY><H1 style = 'text-align: center; color:white;'>Your shopping cart has been updated!"
				+ " Please click 'Back' to go back to the main page and continue looking for movies,"
				+ "'Checkout' if you have completed your purchase, or 'Logout' if you wish to exit.</H1>");
		
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
