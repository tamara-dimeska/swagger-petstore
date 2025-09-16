package ip.swagger.petstore.userapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreateUsersFromAListTests extends BaseTest {
    @Test
    public void shouldReturn200WhenMultipleUsersAreCreated() throws Exception {
        String[] usersArray = {generateUserObject(), generateUserObject()};
        String users = "[" + String.join(",", usersArray) + "]";
        HttpResponse<String> response = makePostRequest(baseUrl + "/user/createWithList", users);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(users), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenEmptyBodyForUserListIsSent() throws Exception {
        HttpResponse<String> response = makePostRequest(baseUrl + "/user/createWithList", "");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }

    @Test
    public void shouldReturn400WhenAUserInsteadOfUserListIsSent() throws Exception {
        String user = generateUserObject();
        HttpResponse<String> response = makePostRequest(baseUrl + "/user/createWithList", user);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
