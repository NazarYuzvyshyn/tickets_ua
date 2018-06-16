package backend.xml;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static gdTicketsUaDesctop.utils.loggers.Logger.info;
import static io.restassured.RestAssured.*;

public class XmlApi {

    private Map<String, String> requestHeaders;
    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();

    private PrintStream requestVar = new PrintStream(request, true);
    private PrintStream responseVar = new PrintStream(response, true);


    public XmlApi() {
        requestHeaders = new HashMap<>();
        setDefaultHeaders();
    }

    public Response xmlPost(String requestUrl, String requestBody) {
        return post(requestUrl, requestHeaders, requestBody);
    }

    public Response xmlPost(String requestUrl, Map<String, String> headers, String requestBody) {
        extendDefaultHeaders(headers);
        return post(requestUrl, requestHeaders, requestBody);
    }

    private List<Filter> logFilters() {
        List<Filter> list = new ArrayList<>();
        list.add(new RequestLoggingFilter(LogDetail.URI, requestVar));
        list.add(new RequestLoggingFilter(LogDetail.HEADERS, requestVar));
        list.add(new RequestLoggingFilter(LogDetail.BODY, requestVar));

        list.add(new ResponseLoggingFilter(LogDetail.STATUS, responseVar));
        list.add(new ResponseLoggingFilter(LogDetail.HEADERS, responseVar));
        list.add(new ResponseLoggingFilter(LogDetail.BODY, responseVar));
        return list;
    }

    private Response post(String requestUrl, Map<String, String> requestHeaders, String requestBody) {
        RestAssured.filters(logFilters());
        Response responseAll = given().
                headers(requestHeaders).
                body(requestBody).
                when().
                post(requestUrl);
        info("------------   Request   --------------");
        info(request.toString());
        info("------------   Response   --------------");
        info(response.toString());
        return responseAll;
    }

    private void extendDefaultHeaders(Map<String, String> headers) {
        requestHeaders.putAll(headers);
    }

    private void setDefaultHeaders() {
        requestHeaders.put("Content-Type", "text/xml");
    }

}
