import java.io.*;
import java.util.*;

import org.json.JSONException;

import com.salesforce.chatter.authentication.*;

public class chatterTester {
	
	public static void main(String[] args) throws IOException, UnauthenticatedSessionException, AuthenticationException, NumberFormatException, JSONException {
		/*
		Set<ChatterDataEntry> hist = ChatterPosts.getDataEntries(800);
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
		
		Set<ChatterDataEntry> entries = ChatterPosts.getSampleDataEntries("sampleData.txt");
		//publishToCSV(entries);
		summarize(entries);
		/*
		for(ChatterDataEntry entry : entries) {
			System.out.println(entry);
		}*/
	}
	
	public static void publishToCSV(Set<ChatterDataEntry> entries) throws IOException {
		BufferedWriter out = new BufferedWriter(new PrintWriter("chatterTable.csv"));
		for(ChatterDataEntry entry : entries) {
			out.write(entry.toString());
			out.write("\n");
			out.flush();
		}
		out.close();
	}
	
	public static void summarize(Set<ChatterDataEntry> entries) {
		System.out.println("--------SUMMARY--------");
		System.out.println("Analyzing last "+entries.size()+" Chatter posts...");
		int numIT = 0;
		for(ChatterDataEntry entry : entries) {
			if(entry.getEntryClass().equals("IT")) {
				numIT++;
			}
		}
		System.out.println("Counted " + numIT + " IT-related posts.");
		System.out.println("Trending services:");
		Set<String> topServices = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		for(ChatterDataEntry entry : entries) {
			if(entry.getFrequencyScore() > .02) {
				topServices.add(entry.getTechnicalService());
			}
		}
		for(String service : topServices) {
			System.out.println(service);
		}
		System.out.println();
		System.out.println("Trending topics:");
		Map<String,Integer> phraseFrequencies = getPhraseFrequencies(entries);
		for(String phrase : phraseFrequencies.keySet()) {
			if(phrase.length() < 3) continue;
			if(!phrase.contains(" ")) continue;
			if((double)phraseFrequencies.get(phrase)/entries.size() > .15) {
				System.out.println(phrase);
			}
		}
	}
	
	public static Map<String,Integer> getPhraseFrequencies(Set<ChatterDataEntry> posts) {
		Map<String,Integer> phraseFrequencies = new TreeMap<String,Integer>(String.CASE_INSENSITIVE_ORDER);
		for(ChatterDataEntry entry : posts) {
			List<String> phrases = ChatterPosts.generateAllPhrases(ChatterPosts.cleanPost(entry.getPost()));
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
