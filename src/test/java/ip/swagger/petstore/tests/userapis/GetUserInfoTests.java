package ip.swagger.petstore.tests.userapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class GetUserInfoTests extends BaseTest {
    UsersApi usersApi;
    String username;

    @BeforeMethod
    public void setup() {
        username = generateRandomUsername();
        usersApi = new UsersApi();

        createUserAndLogin(usersApi, username);
    }

    @AfterMethod
    public void cleanUp() {
        usersApi.deleteUser(username);
    }

    @Test
    public void shouldReturn200AndTheUserObjectWhenUserInfoIsReturned() throws Exception{
        String user = generateUserObject(username);
        HttpResponse<String> response = usersApi.getUser(username);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(user), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn404WhenTheUsernameDoesNotExist() {
        usersApi.deleteUser(username);
        HttpResponse<String> response = usersApi.getUser(username);

        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 response.");
        Assert.assertTrue(response.body().contains("User not found"), "Expected body was not returned.");
    }
}
