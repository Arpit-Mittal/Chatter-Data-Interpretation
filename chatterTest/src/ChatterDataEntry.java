
public class ChatterDataEntry {
	
	public ChatterDataEntry() {
		this.user = null;
		this.post = null;
		this.numComments = 0;
		this.entryClass = null;
		this.entryType = null;
		this.sentimentScore = 0;
		this.entryPriority = null;
		this.technicalService = null;
		this.businessService = null;
		this.organization = null;
		this.email = null;
	}
	
	public ChatterDataEntry(String user, String post, int numComments, String entryClass, String entryType, double sentimentScore, double frequencyScore, String entryPriority, String technicalService, String businessService, String organization, String email) {
		this.user = user;
		this.post = post;
		this.numComments = numComments;
		this.entryClass = entryClass;
		this.entryType = entryType;
		this.sentimentScore = sentimentScore;
		this.entryPriority = entryPriority;
		this.technicalService = technicalService;
		this.businessService = businessService;
		this.organization = organization;
		this.email = email;
	}
	
	public String toString() {
		String out = "";
		out += "\""+user+"\",";
		out += "\""+post.replaceAll("\"","")+"\",";
		out += "\""+numComments+"\",";
		out += "\""+entryClass+"\",";
		out += "\""+entryType+"\",";
		out += "\""+sentimentScore+"\",";
		out += "\""+frequencyScore+"\",";
		out += "\""+entryPriority+"\",";
		out += "\""+technicalService+"\",";
		out += "\""+businessService+"\",";
		out += "\""+organization+"\",";
		out += "\""+email+"\",";
		return out;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public int getNumComments() {
		return numComments;
	}
	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}
	public String getEntryClass() {
		return entryClass;
	}
	public void setEntryClass(String entryClass) {
		this.entryClass = entryClass;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public double getSentimentScore() {
		return sentimentScore;
	}
	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}
	public double getFrequencyScore() {
		return frequencyScore;
	}
	public void setFrequencyScore(double frequencyScore) {
		this.frequencyScore = frequencyScore;
	}
	public String getEntryPriority() {
		return entryPriority;
	}
	public void setEntryPriority(String entryPriority) {
		this.entryPriority = entryPriority;
	}
	public String getTechnicalService() {
		return technicalService;
	}
	public void setTechnicalService(String technicalService) {
		this.technicalService = technicalService;
	}
	public String getBusinessService() {
		return businessService;
	}
	public void setBusinessService(String businessService) {
		this.businessService = businessService;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	private String user;
	private String post;
	private int numComments;
	private String entryClass;
	private String entryType;
	private double sentimentScore;
	private double frequencyScore;
	private String entryPriority;
	private String technicalService;
	private String businessService;
	private String organization;
	private String email;
}
