package ip.swagger.petstore.tests.userapis;

import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class UserLoginTests extends BaseTest {
    UsersApi usersApi;
    String username;

    @BeforeMethod
    public void setup() {
        username = generateRandomUsername();
        String user = generateUserObject(username);
        usersApi = new UsersApi();

        usersApi.createUser(user);
    }

    @AfterMethod
    public void cleanUp() {
        usersApi.deleteUser(username);
    }

    @Test
    public void shouldReturn200WhenUserIsLoggedIn() {
        HttpResponse<String> response = usersApi.loginUser(username, "12345");

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // TODO check if we can also check that the session number is returned
        Assert.assertTrue(response.body().contains("Logged in user session: "), "Expected body was not returned.");
    }

    /*
     * Skipping the next 2 TCs, because there is a bug.
     * The documentation says that when an invalid password/username are provided,
     * the response code is 400, but in reality, it returns 200.
     */
    @Test(enabled=false)
    public void shouldReturn400WhenThePasswordIsWrong() {
        HttpResponse<String> response = usersApi.loginUser(username, "1");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }


    @Test(enabled=false)
    public void shouldReturn400WhenTheUsernameIsWrong() {
        HttpResponse<String> response = usersApi.loginUser(username + "incorrect", "12345");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
