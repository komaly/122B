package com.test;

import java.util.HashMap;
import java.util.Map;

/*
 * This User class only has the username field in this example.
 * 
 * However, in the real project, this User class can contain many more things,
 * for example, the user's shopping cart items.
 * 
 */
public class User {
	
	private final String username;
	private final String id;
	private final String fullname;
	private Map<String, String> cart = new HashMap<String, String>();
	
	public User(String username, String id, String fullname) {
		this.username = username;
		this.id = id;
		this.fullname = fullname;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void addToCart(String movie, String quantity)
	{
		cart.put(movie, quantity);
	}
	
	public void removeFromCart(String movie)
	{
		cart.remove(movie);
	}
	
	public Map<String, String> displayFullCart()
	{
		return cart;
	}
	
	public String getCustomerId()
	{
		return id;
	}
}
