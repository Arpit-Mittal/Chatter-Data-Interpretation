import com.salesforce.chatter.commands.ChatterCommand;


public class GetNextFeedItems implements ChatterCommand {
	
	private final String URI;
	private final String prefix = "/services/data/v28.0";
	
	public GetNextFeedItems(String url) {
		URI = truncateURL(url);
	}
	private String truncateURL(String url) {
		return url.substring(prefix.length()+1,url.length()-1);
	}
	@Override
	public String getURI() {
		return URI;
	}

}
