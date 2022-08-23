import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TwitterApiRequest {
    private static final String URL = "https://api.twitter.com/2/tweets/search/recent?query=";
    // endpoint = URL + keyWordsYou'reLookingFor
    private static final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAMt7fAEAAAAA0rIQr4fCKvjVu9sDJnLeVl80K8s%3DVsIZfKpphi7FR2sj8gJfD4uadNBZ1RsVF7KAWOMNCgSyedcF7Z";
    private static final String languageParameter = "&tweet.fields=lang";

    public static String sendRequest(String keywords) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .uri(URI.create(URL + keywords + languageParameter))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());
        return response.body();
    }
}

