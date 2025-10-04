package ip.swagger.petstore.tests.petapis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ip.swagger.petstore.apiobjects.PetsApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreatePetTests extends BaseTest {
    PetsApi petsApi;

    @Test
    public void shouldReturn200WhenAPetIsCreated() throws Exception {
        String pet = generatePetObject(10, "Doggo", "dogs", new String[]{"dog"}, "available");
        petsApi = new PetsApi();

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = petsApi.createPet(pet);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");

        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(pet), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenAWringPetObjectIsSent() {
        String pet = generateWrongPetObject("Doggo", "dogs", new String[]{"dog"}, "available");
        petsApi = new PetsApi();

        HttpResponse<String> response = petsApi.createPet(pet);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }

    private static String generateWrongPetObject(String name, String category, String[] tags, String status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newPetInfo = mapper.createObjectNode();

            newPetInfo.put("id", 1);

            ObjectNode petCategory = mapper.createObjectNode();
            petCategory.put("id", 1);
            petCategory.put("name", category);

            newPetInfo.set("category", petCategory);
            newPetInfo.put("name", name);

            ArrayNode photosUrls = mapper.createArrayNode();
            photosUrls.add("url");

            newPetInfo.set("photoUrls", photosUrls);

            ArrayNode tagsArray = mapper.createArrayNode();

            for (String tag: tags) {
                tagsArray.add(tag);
            }

            newPetInfo.set("tags", tagsArray);

            newPetInfo.put("status", status);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newPetInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
