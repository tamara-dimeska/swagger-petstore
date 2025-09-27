package ip.swagger.petstore.apiobjects;

import ip.swagger.petstore.utils.Utils;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;

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

    public HttpResponse<String> deleteUser(String username) {
        return makeDeleteRequest(baseUrl + "/" + username);
    }

    public HttpResponse<String> loginUser(String username, String passport) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", passport);

        return makeGetRequest(baseUrl + "/login", parameters);
    }

    public HttpResponse<String> logoutUser() {
        return makeGetRequest(baseUrl + "/logout");
    }

    public HttpResponse<String> updateUser(String username, String body) {
        return makePutRequest(baseUrl, username, body);
    }

    public HttpResponse<String> getUser(String username) {
        return makeGetRequest(baseUrl + "/" + username);
    }
}
