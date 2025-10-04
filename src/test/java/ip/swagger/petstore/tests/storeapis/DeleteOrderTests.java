package ip.swagger.petstore.tests.storeapis;

import ip.swagger.petstore.apiobjects.StoreApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.Random;

public class DeleteOrderTests extends BaseTest {
    StoreApi storeApi;
    int id;

    @BeforeMethod
    public void setup() {
        id = new Random().nextInt(1000);
        String order = generateOrderObject(id, 123, 10, "available");
        storeApi = new StoreApi();

        storeApi.createAnOrder(order);
    }

    @Test
    public void shouldReturn200WhenAnOrderIsDeleted() {
        HttpResponse<String> response = storeApi.deleteOrder(id);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
    }

    @Test
    public void shouldReturn400WhenTheOrderIdIsInvalid() {
        HttpResponse<String> response = storeApi.deleteOrder("abc");

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(response.body().contains("Input error: couldn't convert `abc` to type `class java.lang.Long"), "Expected body was not returned.");
    }

    /*
     * Skipping the TC, because there is a bug.
     * The documentation says that when orderId that does not exist is provided,
     * the response code is 404, but in reality, it returns 200.
     */
    @Test(enabled = false)
    public void shouldReturn404WhenTheOrderIdIsNotFound() {
        HttpResponse<String> response = storeApi.deleteOrder(id + 1);

        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 response.");
    }
}
