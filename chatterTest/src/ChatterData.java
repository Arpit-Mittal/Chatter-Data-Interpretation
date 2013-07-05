import com.salesforce.chatter.authentication.ChatterAuthMethod;
import com.salesforce.chatter.authentication.IChatterData;

// Chatter Data class
public class ChatterData implements IChatterData {

	private final String apiVersion = "24.0";
    private final String instanceUrl = "https://na13.salesforce.com";

    private final ChatterAuthMethod authMethod = ChatterAuthMethod.PASSWORD;
    private final String username = "fkoung@bmc.com";
    private final String password = "1579cDzH";

    private final String clientKey = "3MVG9A2kN3Bn17hsUcmLTAtRJ.5leZf87Ra6FmfEM587jDaCeGBxb1Mjm5cPdvZekNZod4l0E_r0xz1whrIMH";
    private final String clientSecret = "6048817529047401290";
    
	@Override
	public String getApiVersion() {
		return apiVersion;
	}

	@Override
	public ChatterAuthMethod getAuthMethod() {
		return authMethod;
	}

	@Override
	public String getClientCallback() {
		return "https://login.salesforce.com/services/oauth2/success";
	}

	@Override
	public String getClientCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientKey() {
		return clientKey;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public String getInstanceUrl() {
		return instanceUrl;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getRefreshToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

}
