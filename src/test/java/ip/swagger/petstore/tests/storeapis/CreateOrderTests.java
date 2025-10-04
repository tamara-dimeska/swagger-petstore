package ip.swagger.petstore.tests.storeapis;

import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.StoreApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

public class CreateOrderTests extends BaseTest {
    StoreApi storeApi;

    @Test
    public void shouldReturn200WhenAnOrderIsCreated() throws Exception{
        String order = generateOrderObject(10, 123, 2, "available");
        storeApi = new StoreApi();

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = storeApi.createAnOrder(order);

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        // mapper.readTree ignores any whitespaces and formatting
        Assert.assertEquals(mapper.readTree(response.body()), mapper.readTree(order), "Expected and actual body do not match.");
    }

    @Test
    public void shouldReturn400WhenAnOrderIsNotCreated() {
        String order = generateOrderObject("abc", 123, 2, "available");
        storeApi = new StoreApi();

        HttpResponse<String> response = storeApi.createAnOrder(order);

        Assert.assertEquals(response.statusCode(), 400, "Expected HTTP 400 response.");
        Assert.assertTrue(response.body().contains("Input error: unable to convert input to io.swagger.petstore.model.Order"), "Expected body was not returned.");
    }
}
