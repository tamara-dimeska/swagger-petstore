package ip.swagger.petstore.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ip.swagger.petstore.apiobjects.UsersApi;

import java.util.UUID;

public class BaseTest {

    public static String generateRandomUsername() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }

    public static String generateUserObject(String username) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode user = mapper.createObjectNode();

            user.put("id", 1);
            user.put("username", username);
            user.put("firstName", username + "-firstName");
            user.put("lastName", username + "-lastName");
            user.put("email", username + "@email.com");
            user.put("password", "12345");
            user.put("phone", "12345");
            user.put("userStatus", 1);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String updateUserObject(String username) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newUserInfo = mapper.createObjectNode();

            newUserInfo.put("id", 1);
            newUserInfo.put("username", username + "UPDATED");
            newUserInfo.put("firstName", username + "-firstName UPDATED");
            newUserInfo.put("lastName", username + "-lastName UPDATED");
            newUserInfo.put("email", username + "UPDATED@email.com");
            newUserInfo.put("password", "54321");
            newUserInfo.put("phone", "54321");
            newUserInfo.put("userStatus", 1);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUserInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUserAndLogin(UsersApi usersApi, String username) {
        String user = generateUserObject(username);

        usersApi.createUser(user);
        usersApi.loginUser(username, "12345");
    }
}
