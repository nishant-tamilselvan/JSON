

package wiki.users;


import java.io.*;
import java.util.*;
import java.net.URL;

import org.json.simple.JSONArray;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



 /*
 * @author NNISHANT TAMILSELVAN
 */
public class WikiUsers {

    /**
     * @param args the command line arguments
     */
    
    
    // Method to look up number of contributions done by a particular user.
    void Count() throws FileNotFoundException
    {
        try {
        FileReader reader;
            reader = new FileReader("output.json");
            
            JSONObject usercontribsObject = (JSONObject)new JSONParser().parse(reader);
            
            
            JSONArray contribsArray = (JSONArray) usercontribsObject.get("usercontribs");
            JSONObject firstcontrib = (JSONObject) contribsArray.get(0);
            System.out.println("The total contribution of USER: "+firstcontrib.get("user")+" is "+contribsArray.size());
           
    }catch (IOException | ParseException ex){}
        finally{
            
        }
    }
    public static void main(String[] args) throws IOException {
        WikiUsers obj = new WikiUsers();
        Scanner user_input = new Scanner(System.in);
        System.out.println("Enter the name of user to find his contribution in wikidata");
        String user = user_input.next();
        
        
        
        String url = "https://en.wikipedia.org/w/api.php?action=query&list=usercontribs&ucuser="+user+"&format=json";
        
       
        //ObjectOutputStream oos = null;
        
        try {
            String wikiuser = IOUtils.toString(new URL(url));
            JSONObject wikiuserObject = (JSONObject) JSONValue.parseWithException(wikiuser);
            
            // Reading JSON response and saving it to Object childObject
            Object childObject = wikiuserObject.get("query");
            
             /* JSON Response for User- AGbot
                {
    "batchcomplete": "",
    "query": {
        "usercontribs": [
            {
                "userid": 21360213,
                "user": "AGbot",
                "pageid": 22451705,
                "revid": 625142271,
                "parentid": 616263544,
                "ns": 0,
                "title": "Wilbraham Egerton",
                "timestamp": "2014-09-11T20:44:54Z",
                "comment": "fix",
                "size": 305
            }
        ]
    }
}
        */    
            
            // Writing Contribution Details of the user into file "output.json"
            FileWriter file = new FileWriter("output.json");
            try {
                file.write(childObject.toString());
            } catch (IOException e){}
            finally{
                file.flush();
                file.close();
            }
        
        }catch (IOException | ParseException e) {
        }
        finally {
          // Calling the Count method to look up number of contributions done by a user.  
          obj.Count();
            
        }
       
    }
    
}
