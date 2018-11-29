Fabflix Website

This site emulates a movie website where users can log in, search up movies, and buy them.
Users can add or remove movies from their shopping cart, and then proceed to checkout, where they 
type in their credit card information to buy the selected movies. All data is retrieved from a MySQL
database dubbed "moviedb". Moviedb contains tables with information on credit cards, customers,
employees, movies, genres, genres in movies, stars, stars in movies, ratings, and sales. All initial
information inserted into the database can be found in the movie-data.sql file.

To access the site, go to: http://ec2-13-56-78-21.us-west-1.compute.amazonaws.com:8080/project2/login.html
. You may create your own account by clicking "Create Account", or you can type in the login information for a specific user in the database, 
such as the user with "a@email.com" for the username and "a2" for the password. 

Note: Since you cannot actually buy movies through this site, when prompted for credit card information when creating
an account, simply type in a random set of numbers for the credit card number and a random date for the expiration date.
The expiration date must be a valid date in the form YYYY-MM-DD.

You then reach the main page, where you can click "Advanced Search" to search for a movie by title, year, director, 
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

You then type in the credit card information for a user to buy the movie(s). Either use the credit card information that you entered when creating
an account, or use credit card information already in the database. For example, type in "Bill" for the first name,
"Wang" for the last name, "0011 2233 4455 6677" for the credit card number, and "2009-09-07" for the expiration 
date. The transaction is now completed, and the sales tables in the database has been updated to reflect this
new sale.

The site also has an employee page where employees login and make changes to the database.
Access the employee login page by clicking the "Employee Login" button on the bottom right of the customer login page.
Then type in the login information for an employee. For example, type in "classta@email.edu" for the username
and "classta" for the password. Once logged in, the employee has the option of inserting a star into the 
database, inserting a movie into the database, or simply viewing the metadata of all tables in the moviedb
database.

Both customer and employee login pages implement Recaptcha to verify the user is human.
Both customer and employee dashboards have logout buttons to logout of the accounts.

Created in CS 122B at UCI: Projects in Databases and Web Applications
