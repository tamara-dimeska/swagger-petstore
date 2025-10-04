package ip.swagger.petstore.tests.storeapis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ip.swagger.petstore.apiobjects.StoreApi;
import ip.swagger.petstore.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.Random;

public class GetInventoryTests extends BaseTest {
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
    public void shouldReturn200WhenAnInventoryIsSuccessfullyReturned() {
        HttpResponse<String> response = storeApi.getInventory();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 response.");
        Assert.assertTrue(response.body().contains("approved"), "Expected body was not returned.");
        Assert.assertTrue(response.body().contains("placed"), "Expected body was not returned.");
        Assert.assertTrue(response.body().contains("available"), "Expected body was not returned.");
        Assert.assertTrue(response.body().contains("delivered"), "Expected body was not returned.");
    }

    @Test
    public void shouldReturnIncreasedNumberOfInventoryAfterCreatingANewOrder() throws Exception {
        HttpResponse<String> response = storeApi.getInventory();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.body());
        int numberOfAvailableOrders = node.get("available").asInt();

        String newOrder = generateOrderObject(id + 1, 123, 10, "available");
        storeApi.createAnOrder(newOrder);

        response = storeApi.getInventory();
        node = mapper.readTree(response.body());
        int newNumberOfAvailableOrders = node.get("available").asInt();

        Assert.assertEquals(numberOfAvailableOrders + 10, newNumberOfAvailableOrders, "Number is not increased as expected.");
    }
}
