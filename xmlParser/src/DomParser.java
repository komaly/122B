import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParser {

	//No generics
	Document dom;


	public DomParser(){
		//create a list to hold the employee objects
	}

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each employee element and create a Employee object
		parseDocument();
		
	}
	
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("/home/ubuntu/cs122b-winter18-team-59/xmlParser/mains243.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	@SuppressWarnings("null")
	private void parseDocument()
	{
		try
		{
			String loginUser = "root";
	        String loginPasswd = "MySQLPassword123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	        dbcon.setAutoCommit(false);
	        
	        HashMap<String, String> movies = new HashMap<String, String>();
			ArrayList<String> mids = new ArrayList<String>();
	        
	        PreparedStatement checkMovies = dbcon.prepareStatement("SELECT title,id FROM movies");
			ResultSet moviesRS = checkMovies.executeQuery();
			while (moviesRS.next())
			{
				movies.put(moviesRS.getString("title"), moviesRS.getString("id"));
				mids.add(moviesRS.getString("id"));
			}
			
			HashMap<String, String> genres = new HashMap<String, String>();
			
			PreparedStatement sg = dbcon.prepareStatement("SELECT name, id FROM genres");
			ResultSet sgRS = sg.executeQuery();
			while (sgRS.next())
			{
				genres.put(sgRS.getString("name"), sgRS.getString("id"));
			}
	        
			ArrayList<Integer> maxID = new ArrayList<Integer>();
			
			PreparedStatement statement3 = dbcon.prepareStatement("SELECT MAX(id) as max FROM genres");
			ResultSet r = statement3.executeQuery();
			r.next();
			maxID.add(Integer.parseInt(r.getString("max")));
			
	        String title, director, genre;
	        int year;
	        
	        Random rand = new Random();
            PreparedStatement statement2 = dbcon.prepareStatement("INSERT INTO movies(id, title, year, director) VALUES(?, ?, ?, ?)");
            PreparedStatement statement4 = dbcon.prepareStatement("INSERT INTO genres_in_movies(genreId, movieId) VALUES(?, ?)");
            PreparedStatement statement5 = dbcon.prepareStatement("INSERT INTO genres(id, name) VALUES(?, ?)");
       
			//get the root element
			Element docEle = dom.getDocumentElement();
			
			//get a node list of <director films> elements
			NodeList nl = docEle.getElementsByTagName("directorfilms");
			
			if (nl != null && nl.getLength() > 0)
			{
				for (int i = 0 ; i < nl.getLength();i++)
				{
					//get the director films element
					Element el = (Element)nl.item(i);
					NodeList nl2 = el.getElementsByTagName("film");
					
					if (nl2 != null && nl2.getLength() > 0)
					{
						
						for (int j = 0 ; j < nl2.getLength();j++)
						{
							
							Element el2 = (Element)nl2.item(j);
							
							//generate movie ID
							String id = "";
							while (id.equals(""))
				            {
								String test = "tt" + Integer.toString(rand.nextInt(9000000) + 1000000);
								if (!mids.contains(test))
								{
									id = test;
									break;
								}

				            }
							mids.add(id);

							try
							{
								title = getTextValue(el2, "t");

								if (title.equals(""))
									continue;
								
								if (movies.containsKey(title))
									continue;
								else
									movies.put(title, id);
								
							}
							catch(Exception e)
							{
								System.out.println("Error with element " + el2 + " for movie title. Title set to  'Not found'.");
								title = "Not found";
								movies.put(title, id);
							}
							
							try
							{
								year = getIntValue(el2, "year");
							}
							catch(Exception e)
							{
								System.out.println("Error with element " + el2 + " for movie year. Year set to 0.");
								year = 0;
							}
							
							try
							{
								director = getTextValue(el2, "dirn");
								
								if (director.equals(""))
									director = "Not found";
							}
							catch(Exception e)
							{
								System.out.println("Error with element " + el2 + " for movie director. Director set to 'Not found'.");
								director = "Not found";
							}
							
							statement2.setString(1, id); //add id
							statement2.setString(2, title); // add title
							statement2.setInt(3, year); // add year
							statement2.setString(4, director); //add director
							
							statement2.addBatch();
							
							try
							{
								genre = getTextValue(el2, "cat");
								
								if (genre.equals(null))
									continue;
								
								String gID = null;
								if (genres.containsKey(genre))
								{
									gID = genres.get(genre);
								}
								
								else
								{
									int m = Collections.max(maxID) + 1;
									gID = Integer.toString(m);
									maxID.add(m);
									genres.put(genre, gID);
									
									statement5.setInt(1, Integer.parseInt(gID));
									statement5.setString(2, genre);
									statement5.addBatch();
									
								}
								
								//add to genres_in_movies
								statement4.setInt(1, Integer.parseInt(gID));
								statement4.setString(2, id);
								statement4.addBatch();
								
								
							}
							
							catch(Exception e)
							{
								System.out.println("Genre has error or does not exist.");
							}
						}						
					}
	
				}
			}
			statement2.executeBatch();
			statement5.executeBatch();
			statement4.executeBatch();
			dbcon.commit();
			
			System.out.println("Done!");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
			
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	
	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}


	
	public static void main(String[] args){
		//create an instance
		DomParser dpe = new DomParser();
		
		//call run example
		dpe.runExample();
	}

}