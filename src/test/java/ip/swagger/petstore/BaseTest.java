package ip.swagger.petstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class BaseTest {

    public static String baseUrl = "http://localhost:8080/api/v3";

    public static String generateRandomUsername() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }

    public static String generateUserObject() {
        try {
            String username = generateRandomUsername();

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode user = mapper.createObjectNode();

            user.put("id", 1);
            user.put("username", username);
            user.put("firstName", username + "-firstName");
            user.put("lastName", username + "-lastName");
            user.put("email", username + "@email.com");
            user.put("password", "12345");
            user.put("phone", "12345");
            user.put("userStatus", 1);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<String> makePostRequest(String url, String body) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
