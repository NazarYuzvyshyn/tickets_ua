package backend.json;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import java.util.*;
import static io.restassured.RestAssured.*;


public class JsonApi {

    private Map<String, String> requestHeaders;

    public JsonApi(){
        requestHeaders = new HashMap<>();
        setDefaultHeaders();
    }


    public Response getJson(String requestUrl) {
        return get(requestUrl, requestHeaders);
    }

    public Response get(String requestUrl, Map<String, String> headers) {
        extendDefaultHeaders(headers);
        return given().
                headers(requestHeaders).
                when().
                get(requestUrl);
    }

    public Response postJson(String requestUrl, Map<String, String> headers, JsonObject requestBody) {
        extendDefaultHeaders(headers);
        return post(requestUrl, requestHeaders, requestBody);
    }

    public Response postJson(String requestUrl, Map<String, String> headers, List<JsonObject> requestBody) {
        extendDefaultHeaders(headers);
        return post(requestUrl, requestHeaders, requestBody);
    }

    public Response postJson(String requestUrl, JsonObject requestBody) {
        return post(requestUrl, requestHeaders, requestBody);
    }

    public Response postJson(String requestUrl, List<JsonObject> requestBody) {
        return post(requestUrl, requestHeaders, requestBody);
    }

    private Response post(String requestUrl, Map<String, ?> requestHeaders, Object requestBody) {
        return given().
                headers(requestHeaders).
                body(requestBody).
                log().uri().log().headers().log().body().
                when().
                post(requestUrl).prettyPeek();
    }

    private void extendDefaultHeaders(Map<String, String> headers) {
        requestHeaders.putAll(headers);
    }

    private void setDefaultHeaders() {
        requestHeaders.put("Content-Type", "application/json");
    }

}
