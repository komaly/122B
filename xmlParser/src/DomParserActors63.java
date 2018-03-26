import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParserActors63 {

	Document dom;


	public DomParserActors63(){

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
			dom = db.parse("/home/ubuntu/cs122b-winter18-team-59/xmlParser/actors63.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	@SuppressWarnings("null")
	private void parseDocument(){
		
		try
		{
			String loginUser = "root";
	        String loginPasswd = "MySQLPassword123";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	        dbcon.setAutoCommit(false);
	        
	        HashMap<String, String> actors = new HashMap<String, String>();
	        
	        PreparedStatement getActors = dbcon.prepareStatement("SELECT name, id FROM stars");
	        ResultSet actoRS = getActors.executeQuery();
	        
	        while (actoRS.next())
	        {
	        	actors.put(actoRS.getString("name"), actoRS.getString("id"));
	        }
			
	        PreparedStatement insertStars = dbcon.prepareStatement("INSERT INTO stars(id, name, birthYear) VALUES(?, ?, ?)");
	        
	        String name;
	        int dob;
	        Random rand = new Random();
	        
			//get the root element
			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getElementsByTagName("actor");
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0 ; i < nl.getLength();i++)
				{
					Element el = (Element)nl.item(i);

					//generate star ID
					String id = "";
					while (id.equals(""))
		            {
						String test = "nm" + Integer.toString(rand.nextInt(9000000) + 1000000);
						if (!actors.containsValue(test))
						{
							id = test;
							break;
						}

		            }
					
					try
					{
						name = getTextValue(el, "stagename");
						
						if (name.equals(""))
							name = "null";
						
						if (actors.containsKey(name))
						{
							continue;
						}
							
						else
							actors.put(name, id);
					}
					catch (Exception e)
					{
						System.out.println("Error with element " + el + " for star name. Star name set to null.");
						name = "null";
						actors.put(name, id);
					}
					
					
					try
					{
						dob = getIntValue(el, "dob");
					}
					catch (Exception e)
					{
						System.out.println("Error with element " + el + " for date of birth. Birth year set to null.");
						dob = 0000;
					}
					
					insertStars.setString(1, id);
					insertStars.setString(2, name);
					insertStars.setInt(3, dob);
					
					insertStars.addBatch();
					
				}
			}
			insertStars.executeBatch();
			dbcon.commit();
			
			System.out.println("Done!");
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
		DomParserActors63 dpe = new DomParserActors63();
		
		//call run example
		dpe.runExample();
	}

}