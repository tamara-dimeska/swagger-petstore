package ip.swagger.petstore.tests.userapis;

import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class UserLogoutTests extends BaseTest {
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
    public void shouldReturn200WhenUserIsLoggedOut() {
        HttpResponse<String> response = usersApi.logoutUser();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        Assert.assertTrue(response.body().contains("User logged out"), "Expected body was not returned.");
    }
}
