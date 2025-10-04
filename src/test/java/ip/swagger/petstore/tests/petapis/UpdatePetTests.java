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

public class UpdatePetTests extends BaseTest {
    PetsApi petsApi;
    int id;

    @BeforeMethod
    public void setup() {
        id = new Random().nextInt(1000);
        String pet = generatePetObject(id, "Doggo", "dog", new String[]{"dog"}, "available");
        petsApi = new PetsApi();

        petsApi.createPet(pet);
    }

    @AfterMethod
    public void cleanUp() {
        petsApi.deletePet(id);
    }

    @Test
    public void shouldReturn200WhenThePetIsSuccessfullyUpdated() throws Exception {
        String updatedPet = generatePetObject(id, "Catto", "cat", new String[]{"cat"}, "pending");

        HttpResponse<String> response = petsApi.updatePet(updatedPet);

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(updatedPet), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn404WhenThePetIdDoesNotExist() {
        String updatedPet = generatePetObject(id + 123, "Catto", "cat", new String[]{"cat"}, "pending");

        HttpResponse<String> response = petsApi.updatePet(updatedPet);

        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 response.");
        Assert.assertTrue(response.body().contains("Pet not found"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenThePetIdIsInvalid() {
        String updatedPet = generatePetObject("abc", "Catto", "cat", new String[]{"cat"}, "pending");

        HttpResponse<String> response = petsApi.updatePet(updatedPet);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(response.body().contains("Input error: unable to convert input to io.swagger.petstore.model.Pet"), "Expected body was not returned.");
    }
}
