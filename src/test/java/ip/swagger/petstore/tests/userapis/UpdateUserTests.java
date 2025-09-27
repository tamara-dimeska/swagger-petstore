package ip.swagger.petstore.tests.userapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class UpdateUserTests extends BaseTest {
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
    public void shouldReturn200WhenUserIsUpdated() throws Exception {
        String newUser = updateUserObject(username);
        HttpResponse<String> response = usersApi.updateUser(username, newUser);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(newUser), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn404WhenTheUsernameDoesNotExist() {
        String newUser = updateUserObject(username);
        usersApi.deleteUser(username);
        HttpResponse<String> response = usersApi.updateUser(username, newUser);

        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 response.");
        Assert.assertTrue(response.body().contains("User not found"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenEmptyBodyIsSent() throws Exception {
        HttpResponse<String> response = usersApi.updateUser(username, "");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(response.body().contains("No User provided. Try again?"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenStringAsBodyIsSent() throws Exception {
        HttpResponse<String> response = usersApi.updateUser(username, "test");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(response.body().contains("Input error: unable to convert input to io.swagger.petstore.model.User"), "Expected body was not returned.");
    }
}
