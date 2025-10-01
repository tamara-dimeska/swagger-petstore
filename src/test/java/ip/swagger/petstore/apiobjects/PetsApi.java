package ip.swagger.petstore.apiobjects;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class PetsApi extends BaseApi {
    private static final String baseUrl = BaseApi.BASE_URL + "/pet";

    public PetsApi() {
        super(HttpClient.newHttpClient());
    }

    public HttpResponse<String> createPet(String pet) {
        return makePostRequest(baseUrl, pet);
    }

    public HttpResponse<String> deletePet(int petId) {
        return makeDeleteRequest(baseUrl + "/" + petId);
    }

    public HttpResponse<String> updatePet(String body) {
        return makePutRequest(baseUrl, body);
    }

    public HttpResponse<String> findPetByStatus(String status) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("status", status);

        return makeGetRequest(baseUrl + "/findByStatus", parameters);
    }

    public HttpResponse<String> findPetByTags(String[] tags) {
        HashMap<String, String> parameters = new HashMap<>();
        for (String tag : tags) {
            parameters.put("tags", tag);
        }

        return makeGetRequest(baseUrl + "/findByTags", parameters);
    }

    public HttpResponse<String> findPetByPetId(String petId) {
        return makeGetRequest(baseUrl + "/" + petId);
    }
}
