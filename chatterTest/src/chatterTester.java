import java.io.*;
import java.util.*;

import org.json.JSONException;

import com.salesforce.chatter.authentication.*;

public class chatterTester {
	
	public static void main(String[] args) throws IOException, UnauthenticatedSessionException, AuthenticationException, NumberFormatException, JSONException {
		/*Set<ChatterDataEntry> hist = ChatterPosts.getDataEntries(500);
		Map<String,Integer> histFreq = getPhraseFrequencies(hist);
		
		Set<ChatterDataEntry> cur = ChatterPosts.getDataEntries(50);
		Map<String,Integer> curFreq = getPhraseFrequencies(cur);
		
		for(String phrase : curFreq.keySet()) {
			if(phrase.length() < 3) continue; //Probably really short words are unimportant
			if(curFreq.get(phrase) <= 5) continue;
			if(!histFreq.containsKey(phrase)) {
				System.out.println(phrase+" "+curFreq.get(phrase));
				continue;
			}
			double histPhraseFreq = (double)histFreq.get(phrase)/500;
			double curPhraseFreq = (double)curFreq.get(phrase)/50;
			if(curPhraseFreq > 5*histPhraseFreq) {
				System.out.println(phrase+" "+curFreq.get(phrase));
			}
		}*/
		//Set<ChatterDataEntry> hist = ChatterPosts.getDataEntries(50);
		Set<ChatterDataEntry> hist = ChatterPosts.getDataEntries(50);
	}
	
	public static Map<String,Integer> getPhraseFrequencies(Set<ChatterDataEntry> posts) {
		Map<String,Integer> phraseFrequencies = new TreeMap<String,Integer>(String.CASE_INSENSITIVE_ORDER);
		for(ChatterDataEntry entry : posts) {
			List<String> phrases = ChatterPosts.generateAllPhrases(entry.getPost());
			for(String phrase : phrases) {
				if(phraseFrequencies.containsKey(phrase)) {
					phraseFrequencies.put(phrase,phraseFrequencies.get(phrase)+1);
				} else {
					phraseFrequencies.put(phrase,1);
				}
			}
		}
		return phraseFrequencies;
	}
	
}
