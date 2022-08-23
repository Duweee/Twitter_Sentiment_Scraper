import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.security.auth.login.Configuration;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App{
    public static Properties properties = new Properties();
    public static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) throws IOException, InterruptedException{

        FileInputStream configFile = new FileInputStream("src/config.properties");
        properties.load(configFile);
        Timer millisDelay = new Timer(Integer.parseInt(properties.getProperty("millisDelay")));
        int numberOfApiCalls = Integer.parseInt(properties.getProperty("numberOfApiCalls"));

        double[] confidenceScores = new double[3];
        Map<String, String> tweetDatabase = new HashMap<>();

        for (int i = 0; i < numberOfApiCalls; i++){
            runTweetAnalysis(confidenceScores, properties, tweetDatabase, millisDelay);
            Thread.sleep(millisDelay.getDelay());
        }
        System.out.println("Total Pos: " + confidenceScores[0]);
        System.out.println("Total Neutral: " + confidenceScores[1]);
        System.out.println("Total Neg: " + confidenceScores[2]);
    }


    public static void runTweetAnalysis(double[] confidenceScores, Properties properties, Map<String, String> tweetDatabase, Timer millisDelay) throws IOException, InterruptedException {
        String twitterResponse = TwitterApiRequest.sendRequest(properties.getProperty("keyword"));

        // parsing twitter response into array of [id, text] objects
        JsonNode twitterResponseArray = objectMapper.readTree(twitterResponse).get("data");
        int twitterResponseArrayLength = twitterResponseArray.size();

        // building azure request
        TextAnalyticsRequest request = new TextAnalyticsRequest();
        for (int i = 0; i < twitterResponseArrayLength; i++) {
            JsonNode tweet = twitterResponseArray.get(i);
            // only passing english tweets to azure
            if (tweet.get("lang").toString().equals("\"en\"")) {
                String formattedTweetString = formatTweet(tweet.get("text").toString());
                // only sending new tweets to azure, no duplicates
                if (!tweetDatabase.containsKey(formattedTweetString)) {
                    request.add(new TextDocument(String.valueOf(i), formattedTweetString, "en"));
                    tweetDatabase.put(formattedTweetString, formattedTweetString);
                }
            }
        }
        // if all tweets are duplicates... extend delay between Tweet gets
        if (request.isEmpty()) {
            System.out.println("No New Tweets");
            millisDelay.extendTimer();
        } else {
            String requestInJson = objectMapper.writeValueAsString(request);
            String azureResponse = SentimentAnalysisRequest.sendRequest(requestInJson);
            // converts azure response from JSON to Object
            JsonNode azureResponseArray = objectMapper.readTree(azureResponse).get("documents");
            int azureResponseArrayLength = azureResponseArray.size();
            double scorePos = 0;
            double scoreNeg = 0;
            double scoreNeutral = 0;
            for (int i = 0; i < azureResponseArrayLength; i++) {
                JsonNode confidenceScore = azureResponseArray.get(i).get("confidenceScores");
                System.out.println(request.getDocuments().get(i).getText());
                System.out.println(confidenceScore);
                scorePos += confidenceScore.get("positive").asDouble();
                scoreNeg += confidenceScore.get("negative").asDouble();
                scoreNeutral += confidenceScore.get("neutral").asDouble();
            }
            confidenceScores[0] += scorePos;
            confidenceScores[1] += scoreNeutral;
            confidenceScores[2] += scoreNeg;

            System.out.printf("Positive: %f\n" +
                    "Neutral: %f\n" +
                    "Negative: %f\n", scorePos, scoreNeutral, scoreNeg);
        }
    }

    public static String formatTweet(String tweet){
        tweet = SentenceFormatter.removeMentions(tweet);
        tweet = SentenceFormatter.removeUrls(tweet);
        return tweet.trim();
    }
}
