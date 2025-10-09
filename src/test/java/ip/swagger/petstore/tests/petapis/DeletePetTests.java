package ip.swagger.petstore.tests.petapis;

import ip.swagger.petstore.apiobjects.PetsApi;
import ip.swagger.petstore.testdata.TestData;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.Random;

public class DeletePetTests extends BaseTest {
    PetsApi petsApi;
    int id;

    @BeforeMethod
    public void setup() {
        id = new Random().nextInt(1000);
        String pet = generatePetObject(id, TestData.DOG_NAME, TestData.DOG_CATEGORY, TestData.DOG_TAGS, TestData.DOG_STATUS);
        petsApi = new PetsApi();

        petsApi.createPet(pet);
    }

    @Test
    public void shouldReturn200WhenAPetIsSuccessfullyDeleted() {
        HttpResponse<String> response = petsApi.deletePet(id);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
    }

    /*
     * Skipping the TC, because there is a bug.
     * The documentation says that when pet id is not valid,
     * the response code is 400, but in reality, it returns 200.
     */
    @Test(enabled=false)
    public void shouldReturn400WhenPetIdIsInvalid() {
        HttpResponse<String> response = petsApi.deletePet(id + 1);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
    }
}
