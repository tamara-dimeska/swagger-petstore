package ip.swagger.petstore.tests.petapis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ip.swagger.petstore.apiobjects.PetsApi;
import ip.swagger.petstore.testdata.TestData;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreatePetTests extends BaseTest {
    PetsApi petsApi;

    @Test
    public void shouldReturn200WhenAPetIsCreated() throws Exception {
        String pet = generatePetObject(10, TestData.DOG_NAME, TestData.DOG_CATEGORY, TestData.DOG_TAGS, TestData.DOG_STATUS);
        petsApi = new PetsApi();

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = petsApi.createPet(pet);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(pet), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenAWringPetObjectIsSent() {
        String pet = generateWrongPetObject();
        petsApi = new PetsApi();

        HttpResponse<String> response = petsApi.createPet(pet);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }

    private static String generateWrongPetObject() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newPetInfo = mapper.createObjectNode();

            newPetInfo.put("id", 1);

            ObjectNode petCategory = mapper.createObjectNode();
            petCategory.put("id", TestData.PET_CATEGORY_ID);
            petCategory.put("name", TestData.DOG_CATEGORY);

            newPetInfo.set("category", petCategory);
            newPetInfo.put("name", TestData.DOG_NAME);

            ArrayNode photosUrls = mapper.createArrayNode();
            photosUrls.add(TestData.PET_URL);

            newPetInfo.set("photoUrls", photosUrls);

            ArrayNode tagsArray = mapper.createArrayNode();
            tagsArray.add("dog");

            newPetInfo.set("tags", tagsArray);

            newPetInfo.put("status", TestData.DOG_STATUS);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newPetInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
