import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class Chatterbox {
	public static double getAngerLevel(String text) throws NumberFormatException, JSONException {
		HttpResponse<JsonNode> request = Unirest.post("https://chatterboxco-anger-detection-for-social-media.p.mashape.com/anger/current/classify_text/")
				  .header("X-Mashape-Authorization", "A2kwOEnbFOuYgbK4RhES7oyAT5ysZzjN")
				  .field("lang", "en")
				  .field("text", text)
				  .asJson();
		System.out.println(request.getBody().toString());
		JSONObject response = request.getBody().getObject();
		return response.getDouble("anger_value");
	}
}
