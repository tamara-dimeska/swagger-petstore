package ip.swagger.petstore.apiobjects;

import ip.swagger.petstore.utils.Utils;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class UsersApi extends BaseApi {
    private static final String baseUrl = BaseApi.BASE_URL + "/user";

    public UsersApi() {
        super(HttpClient.newHttpClient());
    }

    public HttpResponse<String> createUser(String user) {
        return makePostRequest(baseUrl, user);
    }

    public HttpResponse<String> createUsersFromList(String[] users) {
        String usersString = new Utils().combineUserObjectsInString(users);

        return makePostRequest(baseUrl + "/createWithList", usersString);
    }

    public HttpResponse<String> createUsersFromList(String users) {
        return makePostRequest(baseUrl + "/createWithList", users);
    }
}
