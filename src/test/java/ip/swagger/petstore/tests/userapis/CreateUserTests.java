package ip.swagger.petstore.tests.userapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreateUserTests extends BaseTest {
    UsersApi usersApi;

    @Test
    public void shouldReturn200WhenASingleUserIsCreated() throws Exception {
        String user = generateUserObject();
        usersApi = new UsersApi();

        HttpResponse<String> response = usersApi.createUser(user);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(user), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenEmptyBodyForUserIsSent() throws Exception {
        usersApi = new UsersApi();

        HttpResponse<String> response = usersApi.createUser("");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
