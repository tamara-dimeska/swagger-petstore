package ip.swagger.petstore.userapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreateUserTests extends BaseTest {

    @Test
    public void shouldReturn200WhenASingleUserIsCreated() throws Exception {
        String user = generateUserObject();
        HttpResponse<String> response = makePostRequest(baseUrl + "/user", user);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(user), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenEmptyBodyForUserIsSent() throws Exception {
        HttpResponse<String> response = makePostRequest(baseUrl + "/user", "");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
