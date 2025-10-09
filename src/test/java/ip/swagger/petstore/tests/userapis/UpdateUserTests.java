package ip.swagger.petstore.tests.userapis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.testdata.TestData;
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

    private static String updateUserObject(String username) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newUserInfo = mapper.createObjectNode();

            newUserInfo.put("id", TestData.USER_ID);
            newUserInfo.put("username", username + "UPDATED");
            newUserInfo.put("firstName", username + "-firstName UPDATED");
            newUserInfo.put("lastName", username + "-lastName UPDATED");
            newUserInfo.put("email", username + "UPDATED" + TestData.EMAIL_DOMAIN);
            newUserInfo.put("password", TestData.USER_PASSWORD_UPDATED);
            newUserInfo.put("phone", TestData.USER_PHONE_UPDATED);
            newUserInfo.put("userStatus", TestData.USER_STATUS);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUserInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
