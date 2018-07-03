Fabflix Website

This site emulates a movie website and is one in which users can log in, search up movies, and buy them.
Users can add or remove movies from their shopping cart, and then proceed to checkout, where they 
type in their credit card information to buy the selected movies. All data is retrieved from a MySQL
database dubbed "moviedb". Moviedb contains tables with information on credit cards, customers,
employees, movies, genres, genres in movies, stars, stars in movies, ratings, and sales. All initial
information inserted into the database can be found in the movie-data.sql file.

To access the site, go to: ec2-13-57-13-30.us-west-1.compute.amazonaws.com:8080/project2/login.html
Type in the login information for a specific user. For example, type in "a@email.com" for the username
and "a2" for the password. 

You then reach the main page, where you can click "Search" to search for a movie by title, year, director, 
and/or star. You can also search by certain genres, or by the first letter of the title of a movie by clicking 
"Browse". All result pages for these options are paginated lists of movies with their title, year, director, 
genres list, stars list, and movie ID. You can click on a movie and be redirected to a page with that single 
movies information, or you can click on one of the stars names to be redirected to a page with that single 
stars information. There is also a search bar on the main page that has an autocomplete function. Clicking 
on one of the movies or stars offered by this autocomplete function redirects the site to that single movie 
or stars information page. If you simply type in the search bar and press enter or click "Execute", the page 
redirects to a paginated list of movies where the title matches with what was typed. All movie lists also have 
the option of adding a movie to your cart. Once you have added movies to your cart, you can view items in your 
cart, add or remove items, and then click "Checkout". 

You then type in the credit card information for a user. For example, type in "Bill" for the first name,
"Wang" for the last name, "0011 2233 4455 6677" for the credit card number, and "2009-09-07" for the expiration 
date. The transaction is now completed, and the sales tables in the database has been updated to reflect this
new sale.

The site also has an employee page where employees login and make changes to the database.
To access the employee site, go to: ec2-13-57-13-30.us-west-1.compute.amazonaws.com:8080/project2/_dashboard.html
Then type in the login information for an employee. For example, type in "classta@email.edu" for the username
and "classta" for the password. Once logged in, the employee has the option of inserting a star into the 
database, inserting a movie into the database, or simply viewing the metadata of all tables in the moviedb
database.

Both customer and employee login pages implement Recaptcha to verify the user is human.

Created in CS 122B at UCI: Projects in Databases and Web Applications
