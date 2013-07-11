import java.io.*;
import java.util.*;

import jxl.read.biff.BiffException;

import org.json.JSONException;

import com.salesforce.chatter.authentication.*;

public class chatterTester {
	
	public static void main(String[] args) throws IOException, UnauthenticatedSessionException, AuthenticationException, NumberFormatException, JSONException, BiffException {
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
		
		
		Set<ChatterDataEntry> entries = ChatterPosts.getSampleDataEntries("useCase.xls");
		publishToCSV(entries);
		summarize(entries);
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
		System.out.println("Analyzing "+entries.size()+" Chatter posts...");
		int numIT = 0;
		for(ChatterDataEntry entry : entries) {
			if(entry.getEntryClass().equals("IT")) {
				numIT++;
			}
		}
		System.out.println("Counted " + numIT + " IT-related posts.\n");
		
		System.out.println("Trending services:");
		class ServiceWithFreq {
			private String service;
			private int freq;
			public ServiceWithFreq(String service, int freq) {
				this.service = service;
				this.freq = freq;
			}
			public String getService() {
				return service;
			}
			public int getFreq() {
				return freq;
			}
		}
		class FrequencyComp implements Comparator<ServiceWithFreq> {
			public int compare(ServiceWithFreq o1, ServiceWithFreq o2) {
				return o1.getFreq()-o2.getFreq();
			}
		}
		Map<String,Integer> serviceFrequencies = new TreeMap<String,Integer>(String.CASE_INSENSITIVE_ORDER);
		for(ChatterDataEntry entry : entries) {
			if(entry.getEntryClass().equals("NON-IT")) continue;
			if(serviceFrequencies.containsKey(entry.getTechnicalService())) serviceFrequencies.put(entry.getTechnicalService(),serviceFrequencies.get(entry.getTechnicalService())+1);
			else serviceFrequencies.put(entry.getTechnicalService(), 1);
		}
		Comparator<ServiceWithFreq> comparator = new FrequencyComp();
		PriorityQueue<ServiceWithFreq> q = new PriorityQueue<ServiceWithFreq>(200,comparator);
		for(String service : serviceFrequencies.keySet()) {
			q.add(new ServiceWithFreq(service,-1*serviceFrequencies.get(service)));
		}
		for(int i = 0; i < 5; i++) {
			ServiceWithFreq cur = q.poll();
			System.out.print(cur.getService());
			System.out.println(" ("+-1*cur.getFreq()+" mentions"+")");
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
