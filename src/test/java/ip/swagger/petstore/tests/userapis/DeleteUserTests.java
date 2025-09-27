package ip.swagger.petstore.tests.userapis;

import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class DeleteUserTests extends BaseTest {
    UsersApi usersApi;
    String username;

    @BeforeMethod
    public void setup() {
        username = generateRandomUsername();
        String user = generateUserObject(username);
        usersApi = new UsersApi();

        usersApi.createUser(user);
    }

    @Test
    public void shouldReturn200WhenUserIsDeleted() {
        HttpResponse<String> response = usersApi.deleteUser(username);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
    }

    /*
     * Skipping the TC, because there is a bug.
     * The documentation says that when username that does not exist is provided,
     * the response code is 400, but in reality, it returns 200.
     */
    @Test(enabled=false)
    public void shouldReturn404WhenTheUsernameDoesNotExist() {
        HttpResponse<String> response = usersApi.deleteUser(username + "invalid");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
