<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.test.User"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>
<meta charset="ISO-8859-1">
<title>Add To Shopping Cart</title>
</head>
<body>

<h1>Shopping Cart</h1>

<table>
<%
	User user = (User)request.getSession().getAttribute("user");
	Map<String, String> cart = user.displayFullCart();
	
	if (cart.size() == 0)
		out.println("No items currently in cart.");
	else
	{
		out.println("Items and quantities currently in the cart:");
		for (Map.Entry<String, String> entry : cart.entrySet())
		{
			%>
			<tr> 
			<td>
			<%
		    out.println(entry.getKey().toString() + ":  " + entry.getValue().toString());
			%>
			</td>
			</tr> 
			<%
		}	
	}
%>
<tr>
<td>
 

</td>
</tr>

</table>

<table>
	<tr>
		<th>Movie Title</th>
		<th>Quantity</th>
		<th></th>
		<th></th>
	</tr>
	
	<tr>
		<td>
		<%String title = request.getParameter("movie"); %>
		<%=title %>
		</td>
		
		<form action= "AddToShoppingCart">
			<td>
			<input type = "number" id = 'quantity' name = 'quantity'/>
			<input type= "hidden" name = "movie" value = "<%=title %>">
			</td>
			
			<td>
			<input type='submit' value='Update'/>
			</td>
		</form>
	
		<td>
		<form action = "RemoveFromShoppingCart">
			<input type = 'submit' value = 'Remove'/>
			<input type= "hidden" name = "movie" value = "<%=title %>">
		</form>
		
		</td>
	</tr>
</table>

<a href='ShoppingCart' title='Checkout'>
	<button style='height:25px;width:100px'>Checkout</button>
</a>

</body>
</html>