

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.User;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
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
		
        response.getWriter().println("<HTML><HEAD><TITLE>Shopping Cart</TITLE></HEAD>");
		
		User user = (User)request.getSession().getAttribute("user");
		Map<String, String> cart = user.displayFullCart();
		
		if (cart.size() == 0)
		{
			response.getWriter().println("<BODY><H1>There are currently no items in your shopping cart."
					+ " Please click 'Back' to return to the home page and add items to your cart.</H1>");
			
			response.getWriter().println("<a href='index.html' title='Back'>"
			   		+ "<button style='height:25px;width:100px'>Back</button>"
			   		+ "</a>");
			
			return;
		}
			
	    response.getWriter().println("<BODY><H1>All Movies Currently in Your Cart</H1>");
		response.getWriter().println("<div style=\"text-align:center\">");
        response.getWriter().println("<TABLE border>");
		
        response.getWriter().println("<tr>" + "<td>" + "Movie" + "</td>" + "<td>" + "Quantity"
        		+ "</td>" + "<td>" + "" + "</td>" + "</tr>");
        
        
		for (Map.Entry<String, String> entry : cart.entrySet())
		{
			response.getWriter().println("<tr>" + "<td>");
		    response.getWriter().println(entry.getKey().toString());
		    response.getWriter().println("</td>" + "<td>");
		    response.getWriter().println( entry.getValue().toString());
		    response.getWriter().println("</td>" + "<td>");
		    response.getWriter().println("<form action = 'RemoveFromShoppingCart'>"
		    		+ "<input type = 'submit' value = 'Remove'/>"
		    		+ "<input type= 'hidden' name = 'movie' value = '"
		    		+ entry.getKey().toString() + "'></form>");
		    response.getWriter().println("</td>" + "</tr>");
		}
		
		response.getWriter().println("</TABLE border>");
		
		response.getWriter().println("<H2>If you would like to proceed to check out, please click 'Check Out'."
				+ " If you would like to continue shopping, please click 'Back'.</H2>");
		
		response.getWriter().println("<a href='checkout.html' title='Checkout'>"
		   		+ "<button style='height:25px;width:100px'>Checkout</button>"
		   		+ "</a>");
		
		response.getWriter().println("<a href='index.html' title='Back'>"
		   		+ "<button style='height:25px;width:100px'>Back</button>"
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
