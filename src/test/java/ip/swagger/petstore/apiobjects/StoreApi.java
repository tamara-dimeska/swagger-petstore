package ip.swagger.petstore.apiobjects;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class StoreApi extends BaseApi {
    private static final String baseUrl = BaseApi.BASE_URL + "/store";

    public StoreApi() {
        super(HttpClient.newHttpClient());
    }

    public HttpResponse<String> getInventory() {
        return makeGetRequest(baseUrl + "/inventory");
    }

    public HttpResponse<String> createAnOrder(String body) {
        return makePostRequest(baseUrl + "/order", body);
    }

    public HttpResponse<String> findOrder(int orderId) {
        return makeGetRequest(baseUrl + "/order/" + orderId);
    }

    public HttpResponse<String> findOrder(String orderId) {
        return makeGetRequest(baseUrl + "/order/" + orderId);
    }

    public HttpResponse<String> deleteOrder(int orderId) {
        return makeDeleteRequest(baseUrl + "/order/" + orderId);
    }

    public HttpResponse<String> deleteOrder(String orderId) {
        return makeDeleteRequest(baseUrl + "/order/" + orderId);
    }
}
