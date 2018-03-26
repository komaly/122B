import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParserCast124 {

	Document dom;
	HashMap<String, String> movieFidandTitle = new HashMap<String, String>();
	HashSet<String> stars = new HashSet<String>();


	public DomParserCast124(){
	}

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile("/home/ubuntu/cs122b-winter18-team-59/xmlParser/mains243.xml");
		parseMains();
		
		parseXmlFile("/home/ubuntu/cs122b-winter18-team-59/xmlParser/actors63.xml");
		parseActors();
		
		parseXmlFile("/home/ubuntu/cs122b-winter18-team-59/xmlParser/casts124.xml");
		parseDocument();
		
	}
	
	
	private void parseXmlFile(String filename){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(filename);
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseMains()
	{
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
						
						try
						{
							movieFidandTitle.put(getTextValue(el2, "fid"), getTextValue(el2, "t"));
						}
						catch(Exception e)
						{
							System.out.println("Error with getting fid and title from a movie from mains data. Movie skipped.");
						}
						
					}
				}
			}
		}
		System.out.println("done parsing mains243");
	}
	
	private void parseActors()
	{
		//get the root element
		Element docEle = dom.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("actor");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0 ; i < nl.getLength();i++)
			{
				Element el = (Element)nl.item(i);
				
				try
				{
					stars.add(getTextValue(el, "stagename"));
				}
				catch(Exception e)
				{
					System.out.println("Error with getting data for a specific actor. Actor skipped.");
				}
			}
		}
		System.out.println("done parsing actors243");
	}
	
	
	private void parseDocument(){

		try
		{
			String loginUser = "root";
	        String loginPasswd = "MySQLPassword123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	        dbcon.setAutoCommit(false);
	        
	        HashMap<String, String> moviesIDS = new HashMap<String, String>();
	        PreparedStatement statement = dbcon.prepareStatement("SELECT * FROM movies");
	        ResultSet rs = statement.executeQuery();
	        while (rs.next())
	        	moviesIDS.put(rs.getString("title"), rs.getString("id"));
	        
	        HashMap<String, String> starsIDS = new HashMap<String, String>();
	        PreparedStatement statement1 = dbcon.prepareStatement("SELECT * FROM stars");
	        ResultSet rs1 = statement1.executeQuery();
	        while (rs1.next())
	        	starsIDS.put(rs1.getString("name"), rs1.getString("id"));
	        

	        HashMap<String, List<String>> starsAndMovies = new HashMap<String, List<String>>();
	        PreparedStatement statement3 = dbcon.prepareStatement("SELECT * FROM stars_in_movies");
	        ResultSet rs2 = statement3.executeQuery();
	        while (rs2.next())
	        {
	        	if (!starsAndMovies.containsKey(rs2.getString("starId")))
	        	{
	        		starsAndMovies.put(rs2.getString("starId"), new ArrayList<String>());
	        	}
	        	
	        	starsAndMovies.get(rs2.getString("starId")).add(rs2.getString("movieId"));
	        }
	        	
	        
	        PreparedStatement statement2 = dbcon.prepareStatement("INSERT INTO stars_in_movies(starId, movieId) VALUES(?, ?)");
			
			String filmid, movieID = null, starID = null;
			Element docEle = dom.getDocumentElement();

			NodeList nl = docEle.getElementsByTagName("dirfilms");
			if(nl != null && nl.getLength() > 0) 
			{
				for (int i = 0 ; i < nl.getLength();i++)
				{
					Element el = (Element) nl.item(i);
					NodeList nl2 = el.getElementsByTagName("filmc");
					
					if(nl2 != null && nl2.getLength() > 0) 
					{
						for (int j = 0 ; j < nl2.getLength();j++)
						{
							Element el1 = (Element) nl2.item(j);
							NodeList nl3 = el1.getElementsByTagName("m");
							
							if (nl3 != null && nl3.getLength() > 0)
							{
								for (int k = 0; k < nl3.getLength(); k++)
								{
									Element e = (Element)nl3.item(k);
									
									//film id corresponds to fid in main file
									try
									{
										filmid = getTextValue(e, "f");
										String mTitle;
										
										if (movieFidandTitle.containsKey(filmid)) //if film in main file
										{
											mTitle = movieFidandTitle.get(filmid); //get title from main file
											
											if (moviesIDS.containsKey(mTitle)) //if movie in movie database, set movieId
												movieID = moviesIDS.get(mTitle);
											else
												continue;
											
										}
										else
											continue;
										
									}
									catch(Exception e1)
									{
										System.out.println("Error with film ID information for element " + e + ". Entry skipped.");
										continue;
									}
									
									//a corresponds to stage name in actor file
									String name;
									try
									{
										name = getTextValue(e, "a");
										
										if (stars.contains(name))
										{
											if (starsIDS.containsKey(name)) //if star in database, set starID
												starID = starsIDS.get(name);
											else
												continue;
										}
										else
											continue;
										
									}
									catch(Exception e1)
									{
										System.out.println("Error with star name information for element " + e + ". Entry skipped.");
										continue;
									}		
									
									//if starsAndMovies already has entry, continue
									if (starsAndMovies.containsKey(starID) && starsAndMovies.get(starID).contains(movieID))
									{
										continue;
									}
									else
									{
										//add to stars and movies
										
										if (!starsAndMovies.containsKey(starID))
										{
											starsAndMovies.put(starID, new ArrayList<String>());
										}
							        	starsAndMovies.get(starID).add(movieID);

										statement2.setString(1, starID);
										statement2.setString(2, movieID);
				
										statement2.addBatch();
									}
									
									
								}
								
							}
						}
					}

				}
				
				statement2.executeBatch();
				dbcon.commit();
				System.out.println("Completed!");
			}
		}
		catch(Exception e)
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

	
	public static void main(String[] args){
		//create an instance
		DomParserCast124 dpe = new DomParserCast124();
		
		//call run example
		dpe.runExample();
	}

}