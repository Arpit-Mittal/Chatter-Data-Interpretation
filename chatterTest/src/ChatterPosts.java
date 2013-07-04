import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import com.salesforce.chatter.ChatterService;
import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;
import com.salesforce.chatter.commands.ChatterCommand;

public class ChatterPosts {
	
	private static final int MAX_PHRASE_LENGTH = 5;
	
	public static Set<ChatterDataEntry> getSampleDataEntries(String filename) throws IOException, NumberFormatException, JSONException {
		Set<ChatterDataEntry> entries = new HashSet<ChatterDataEntry>();
		
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String curLine;
		while((curLine = in.readLine()) != null) {
			ChatterDataEntry entry = new ChatterDataEntry();
			System.out.println(curLine);
			entry.setPost(curLine);
			entry.setSentimentScore(Chatterbox.getAngerLevel(curLine));
			entries.add(entry);
		}
		in.close();
		return entries;
	}
	
	public static Set<ChatterDataEntry> getDataEntries(int numPosts) throws IOException, UnauthenticatedSessionException, AuthenticationException, NumberFormatException, JSONException {
		ChatterService service = new ChatterService(new ChatterData());
		ObjectMapper mapper = new ObjectMapper();
		ChatterCommand cmd = new GetCompanyFeedItems();
        HttpMethod result = service.executeCommand(cmd);
        
        Set<ChatterDataEntry> posts = new HashSet<ChatterDataEntry>();
        int counter = 1;
		while(true) {
			JsonNode feedItemPage = mapper.readValue(result.getResponseBodyAsStream(), JsonNode.class);
	        for(JsonNode feedItem : feedItemPage.path("items")) {
	        	JsonNode userDesc = feedItem.path("actor");
	        	if(!userDesc.path("type").toString().equals("\"User\"")) continue; //Don't care if not posted by user
	        	
	        	ChatterDataEntry entry = new ChatterDataEntry();
	        	
	        	entry.setUser(stripQuotes(userDesc.path("name").toString())); //Get user
	        	
	        	//Get post
	        	JsonNode feedBodyText = feedItem.path("body").path("text");
	        	String post = StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeHtml4(feedBodyText.toString()));
	        	post = stripQuotes(post);
	        	entry.setPost(post);
	        	
	        	entry.setNumComments(Integer.parseInt(feedItem.path("comments").path("total").toString()));
	        	
	        	parse(entry); //Get technical service, business service, class
	        	
	        	System.out.println(entry);
	        	posts.add(entry);
	        	
	        	counter++;
	        	if(counter > numPosts) {
	        		return posts;
	        	}
	        }
	        String nextPageUrl = feedItemPage.path("nextPageUrl").toString();
	        if(nextPageUrl.equals("null")) break;
	        ChatterCommand nextPageCmd = new GetNextFeedItems(nextPageUrl);
	        result = service.executeCommand(nextPageCmd);
		}
		return posts;
	}
	
	public static String stripQuotes(String text) {
		return text.substring(1,text.length()-1);
	}
	
	private static List<String> generatePhrasesOfLength(String text, int length) {
		List<String> phrases = new LinkedList<String>();
		String[] words = text.split("\\s");
		for(int i = 0; i <= words.length-length; i++) {
			String cur = "";
			for(int j = i; j < i+length; j++) {
				cur += words[j]+" ";
			}
			phrases.add(cur.trim());
		}
		return phrases;
	}
	
	public static List<String> generateAllPhrases(String text) {
		List<String> phrases = new LinkedList<String>();
		for(int len = Math.min(MAX_PHRASE_LENGTH, text.length()); len > 0; len--) {
			phrases.addAll(generatePhrasesOfLength(text,len));
		}
		return phrases;
	}
	
	private static Map<String,String> loadAliases(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		Map<String,String> aliases = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
		String curLine = in.readLine();
		while((curLine = in.readLine()) != null) {
			String officialName = curLine;
			while(!(curLine = in.readLine()).isEmpty()) {
				aliases.put(curLine, officialName);
			}
		}
		in.close();
		return aliases;
	}
	
	private static void parse(ChatterDataEntry entry) throws IOException {
		//Load technical services
		Map<String,String> TStoBS = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
		Set<String> technicalServices = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		BufferedReader in = new BufferedReader(new FileReader("services.txt"));
		String curLine;
		while((curLine = in.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(curLine,",");
			String bs = st.nextToken();
			st.nextToken(); //Get rid of the manager
			String ts = st.nextToken();
			TStoBS.put(ts, bs);
			technicalServices.add(ts);
		}
		in.close();
		
		Map<String,String> aliases = loadAliases("aliases.txt");
		
		//Fill in technical service, business service, and class
		List<String> phrases = generateAllPhrases(entry.getPost().replaceAll("#",""));
		for(String phrase : phrases) {
			if(aliases.containsKey(phrase)) phrase = aliases.get(phrase);
			if(technicalServices.contains(phrase)) {
				entry.setTechnicalService(phrase);
				entry.setBusinessService(TStoBS.get(phrase));
				entry.setEntryClass("IT");
				return;
			}
		}
		//Didn't find any service
		entry.setTechnicalService("N/A");
		entry.setBusinessService("N/A");
		entry.setEntryClass("NON-IT");
	}
}
