package ip.swagger.petstore.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ip.swagger.petstore.apiobjects.UsersApi;
import ip.swagger.petstore.testdata.TestData;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class BaseTest {

    public static String generateRandomUsername() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }

    public static String generateUserObject(String username) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode user = mapper.createObjectNode();

            user.put("id", TestData.USER_ID);
            user.put("username", username);
            user.put("firstName", username + "-firstName");
            user.put("lastName", username + "-lastName");
            user.put("email", username + TestData.EMAIL_DOMAIN);
            user.put("password", TestData.USER_PASSWORD);
            user.put("phone", TestData.USER_PHONE);
            user.put("userStatus", TestData.USER_STATUS);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUserAndLogin(UsersApi usersApi, String username) {
        String user = generateUserObject(username);

        usersApi.createUser(user);
        usersApi.loginUser(username, TestData.USER_PASSWORD);
    }

    public static String generatePetObject(int id, String name, String category, String[] tags, String status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newPetInfo = mapper.createObjectNode();

            newPetInfo.put("id", id);

            ObjectNode petCategory = mapper.createObjectNode();
            petCategory.put("id", TestData.PET_CATEGORY_ID);
            petCategory.put("name", category);

            newPetInfo.set("category", petCategory);
            newPetInfo.put("name", name);

            ArrayNode photosUrls = mapper.createArrayNode();
            photosUrls.add("url");

            newPetInfo.set("photoUrls", photosUrls);

            ArrayNode tagsArray = mapper.createArrayNode();
            ObjectNode tagsInfo = mapper.createObjectNode();
            int index = 0;

            for (String tag: tags) {
                tagsInfo.put("id", index);
                tagsInfo.put("name", tag);
            }
            tagsArray.add(tagsInfo);
            newPetInfo.set("tags", tagsArray);

            newPetInfo.put("status", status);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newPetInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generatePetObject(String id, String name, String category, String[] tags, String status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newPetInfo = mapper.createObjectNode();

            newPetInfo.put("id", id);

            ObjectNode petCategory = mapper.createObjectNode();
            petCategory.put("id", TestData.PET_CATEGORY_ID);
            petCategory.put("name", category);

            newPetInfo.set("category", petCategory);
            newPetInfo.put("name", name);

            ArrayNode photosUrls = mapper.createArrayNode();
            photosUrls.add("url");

            newPetInfo.set("photoUrls", photosUrls);

            ArrayNode tagsArray = mapper.createArrayNode();
            ObjectNode tagsInfo = mapper.createObjectNode();
            int index = 0;

            for (String tag: tags) {
                tagsInfo.put("id", index);
                tagsInfo.put("name", tag);
            }
            tagsArray.add(tagsInfo);
            newPetInfo.set("tags", tagsArray);

            newPetInfo.put("status", status);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newPetInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateOrderObject(int id, int petId, int quantity, String status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode order = mapper.createObjectNode();
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
            String time = OffsetDateTime.now(ZoneOffset.UTC).format(formatter);

            order.put("id", id);
            order.put("petId", petId);
            order.put("quantity", quantity);
            order.put("shipDate", time);
            order.put("status", status);
            order.put("complete", true);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateOrderObject(String id, int petId, int quantity, String status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode order = mapper.createObjectNode();
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
            String time = OffsetDateTime.now(ZoneOffset.UTC).format(formatter);

            order.put("id", id);
            order.put("petId", petId);
            order.put("quantity", quantity);
            order.put("shipDate", time);
            order.put("status", status);
            order.put("complete", true);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
