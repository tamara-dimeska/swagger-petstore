package ip.swagger.petstore.tests.userapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.tests.BaseTest;
import ip.swagger.petstore.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreateUsersFromAListTests extends BaseTest {
    UsersApi usersApi;

    @Test
    public void shouldReturn200WhenMultipleUsersAreCreated() throws Exception {
        String[] users = {generateUserObject(), generateUserObject()};
        String usersString = new Utils().combineUserObjectsInString(users);
        usersApi = new UsersApi();

        HttpResponse<String> response = usersApi.createUsersFromList(users);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(usersString), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenEmptyBodyForUserListIsSent() throws Exception {
        usersApi = new UsersApi();
        String[] users = {};

        HttpResponse<String> response = usersApi.createUsersFromList(users);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }

    @Test
    public void shouldReturn400WhenAUserInsteadOfUserListIsSent() throws Exception {
        String users = generateUserObject();
        usersApi = new UsersApi();

        HttpResponse<String> response = usersApi.createUsersFromList(users);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
