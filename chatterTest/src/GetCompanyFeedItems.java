import com.salesforce.chatter.commands.ChatterCommand;


public class GetCompanyFeedItems implements ChatterCommand {
	
	public static final String URI = "/chatter/feeds/company/feed-items";
	
	@Override
	public String getURI() {
		return URI;
	}

}
