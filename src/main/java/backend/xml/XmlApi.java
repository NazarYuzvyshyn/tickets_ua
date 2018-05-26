package backend.xml;
import io.restassured.response.Response;
import java.util.*;
import static io.restassured.RestAssured.*;

public class XmlApi {

    private Map<String, String> requestHeaders;

    public XmlApi(){
        requestHeaders = new HashMap<>();
        setDefaultHeaders();
    }

    public Response xmlPost(String requestUrl, String requestBody){
        return post(requestUrl, requestHeaders, requestBody);
    }

    public Response xmlPost(String requestUrl, Map<String, String> headers, String requestBody){
        extendDefaultHeaders(headers);
        return post(requestUrl, requestHeaders, requestBody);
    }

    private Response post(String requestUrl, Map<String, String> requestHeaders, String requestBody) {
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
        requestHeaders.put("Content-Type", "text/xml");
    }

}
