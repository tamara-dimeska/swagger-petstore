package ip.swagger.petstore.tests.petapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.PetsApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.Random;

public class FindPetTests extends BaseTest {
    PetsApi petsApi;
    int id;
    HttpResponse<String> response;

    @BeforeMethod
    public void setup() {
        id = new Random().nextInt(1000);
        String pet = generatePetObject(id, "Doggo", "dog", new String[]{"dog"}, "available");
        petsApi = new PetsApi();

        response = petsApi.createPet(pet);
    }

    @AfterMethod
    public void cleanUp() {
        petsApi.deletePet(id);
    }

    @Test
    public void shouldReturn200WhenAValidPetIdIsEntered() throws Exception {
        HttpResponse<String> getResponse = petsApi.findPetByPetId(id);
        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(getResponse.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(getResponse.body()), mapper.readTree(response.body()), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn200WhenAValidStatusIsEntered() {
        HttpResponse<String> getResponse = petsApi.findPetByStatus("available");

        Assert.assertEquals(getResponse.statusCode(), 200, "Expected HTTP 200 response.");
        Assert.assertTrue(getResponse.body().contains(response.body()), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn200WhenAValidSTagIsEntered() {
        HttpResponse<String> getResponse = petsApi.findPetByTags(new String[]{"dog"});

        Assert.assertEquals(getResponse.statusCode(), 200, "Expected HTTP 200 response.");
        Assert.assertTrue(getResponse.body().contains(response.body()), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn200WhenMultipleValidSTagsAreEntered() {
        String secondPet = generatePetObject(id + 1, "Catto", "cat", new String[]{"cat"}, "available");
        HttpResponse<String> secondPetResponse = petsApi.createPet(secondPet);

        HttpResponse<String> getResponse = petsApi.findPetByTags(new String[]{"dog", "cat"});

        Assert.assertEquals(getResponse.statusCode(), 200, "Expected HTTP 200 response.");
        Assert.assertTrue(getResponse.body().contains(response.body()), "Expected and actual body do not match.");
        Assert.assertTrue(getResponse.body().contains(secondPetResponse.body()), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn404WhenThePetIdDoesNotExist() {
        HttpResponse<String> getResponse = petsApi.findPetByPetId(id + 12342);

        Assert.assertEquals(getResponse.statusCode(), 404, "Expected HTTP 404 response.");
        Assert.assertTrue(getResponse.body().contains("Pet not found"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenAnInvalidPetIdIsEntered() {
        HttpResponse<String> getResponse = petsApi.findPetByPetId("abc");

        Assert.assertEquals(getResponse.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(getResponse.body().contains("Input error: couldn't convert `abc` to type `class java.lang.Long`"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenAnInvalidStatusIsEntered() {
        HttpResponse<String> getResponse = petsApi.findPetByStatus("online");

        Assert.assertEquals(getResponse.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(getResponse.body().contains("Input error: query parameter `status value `online` is not in the allowable values `[available, pending, sold]`"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenAnInvalidTagIsEntered() {
        HttpResponse<String> getResponse = petsApi.findPetByTags(new String[]{""});

        Assert.assertEquals(getResponse.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(getResponse.body().contains("No tags provided. Try again?"), "Expected body was not returned.");
    }
}
