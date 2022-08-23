package com.linkedin.api.azure;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AzureNamedEntitiesTest {

	@Value("${AZURE_API_KEY}")
	private String azureApiKey;

	private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback.cognitiveservices.azure.com";
	
	private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/entities/recognition/general";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";
	
	private static final String EXAMPLE_JSON  = "{"
			+ "  \"documents\": ["
			+ "    {"
			+ "      \"language\": \"en\","
			+ "      \"id\": \"1\","
			+ "      \"text\": \"The Landon Hotel was found in 1952 London by Arthur Landon after World War II.\""
			+ "    }"
			+ "  ]"
			+ "}";

	@Test
	public void getEntities() throws IOException, InterruptedException {
		
		// 1.  Create a client 
		
		// 2.  Create the request
		
		// 3.  Send the request and receive response
		
		// 4.  Work with the response

	}

}
