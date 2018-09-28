<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.test.User"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>

<style>
	#search .container #search-row #search-column #search-box {
	  margin-top: 120px;
	  max-width: 600px;
	  height: 400px;
	  border: 1px solid #9C9C9C;
	  background-color: #EAEAEA;
	}
</style>
<link rel='stylesheet' href='/project2/styles.css' type='text/css' media='all'/>
<meta charset="ISO-8859-1">
<title>Add To Shopping Cart</title>
</head>
<body>

<div id = "search">
	<div class = "container">
		<div id="search-row" class="row justify-content-center align-items-center">
			<div id="search-column" class="col-md-6">
				<div id="search-box" class="col-md-12">
					<h1 class="text-center text-info pt-5">Shopping Cart</h1>
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
								<th  style = 'text-align: center;'>Movie Title</th>
								<th  style = 'text-align: center;'>Quantity</th>
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
						<div style="position: absolute; right:0; bottom:0;" >
							<a href="ShoppingCart" title="Checkout">
							   		<button class="btn btn-info" >Checkout</button>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<a href = "logout.jsp" style = "position:relative;float: right;bottom:0px;right:0px;z-index:999">
		<button class = "btn btn-light">Logout</button>
	</a>
</body>
</html>