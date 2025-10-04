package ip.swagger.petstore.tests.storeapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.StoreApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.Random;

public class FindOrderTests extends BaseTest {
    StoreApi storeApi;
    int id;
    String order;

    @BeforeMethod
    public void setup() {
        id = new Random().nextInt(1000);
        order = generateOrderObject(id, 123, 10, "available");
        storeApi = new StoreApi();

        storeApi.createAnOrder(order);
    }

    @AfterMethod
    public void cleanUp() {
        storeApi.deleteOrder(id);
    }

    @Test
    public void shouldReturn200WhenTheOrderInformationIsSuccessfullyReturned() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = storeApi.findOrder(id);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(order), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn404WhenOrderIdIsNotFound() {
        HttpResponse<String> response = storeApi.findOrder(id + 123);

        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 response.");
        Assert.assertTrue(response.body().contains("Order not found"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturn400WhenTheOrderIdIsNotValid() {
        HttpResponse<String> response = storeApi.findOrder("abc");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(response.body().contains("Input error: couldn't convert `abc` to type `class java.lang.Long"), "Expected body was not returned.");
    }
}
