import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SentimentAnalysisRequest {
    private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback-098.cognitiveservices.azure.com";
    private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/sentiment";
    private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
    private static final String API_KEY = "0da82e3f3f3f48159a309b96d6232cc4";
    private static final String EXAMPLE_JSON = "{\n" +
            "  \"documents\": [\n" +
            "    {\n" +
            "      \"language\": \"en\",\n" +
            "      \"id\": \"1\",\n" +
            "      \"text\": \"Hello world. This is some input text that I love.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"language\": \"en\",\n" +
            "      \"id\": \"2\",\n" +
            "      \"text\": \"It's incredibly sunny outside! I'm so happy.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"language\": \"en\",\n" +
            "      \"id\": \"3\",\n" +
            "      \"text\": \"Pike place market is my favorite Seattle attraction.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static String sendRequest(String textForAnalysis) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(textForAnalysis))
                .header(API_KEY_HEADER_NAME, API_KEY)
                .header("Content-Type", "application/json")
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
