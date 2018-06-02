package backend.json;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static gdTicketsUaDesctop.utils.loggers.Logger.info;

public class ResponseHendler {

    public static <T> T getValue(Response response, String directJsonKey) {
        T resp = response.body().jsonPath().get(directJsonKey);
        info("Was used direct json key: " + directJsonKey);
        info("And got value: " + resp.toString());
        info("The value is type of: " + resp.getClass().getSimpleName());
        return resp;
    }

    public static Map getResponseAsMap(Response response) {
        Map responseAsMap = response.body().as(Map.class);
        info("Response is returned as Map.class");
        return responseAsMap;
    }

    public static List getResponseAsList(Response response) {
        List responseAsList = response.body().as(List.class);
        info("Response is returned as List.class");
        return responseAsList;
    }
}
