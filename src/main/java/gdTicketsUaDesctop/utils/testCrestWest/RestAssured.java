package gdTicketsUaDesctop.utils.testCrestWest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import gdTicketsUaDesctop.utils.testCrestWest.Son;
import gdTicketsUaDesctop.utils.testCrestWest.Telecom;
import static gdTicketsUaDesctop.utils.Log.info;


import java.util.*;

import static io.restassured.RestAssured.*;


public class RestAssured {

    public static <T> T getValue(Response response, String directJsonKey) {
        T resp = response.body().jsonPath().get(directJsonKey);
        info("Was used direct json key: " + directJsonKey);
        info("And got value: " + resp.toString());
        info("The value is type of: " + resp.getClass().getSimpleName());
        return resp;
    }

    public static Map getValue(Response response) {
        Map resp = response.body().as(Map.class);
        info("And got value: " + resp.toString());
        info("The value is type of: " + resp.getClass().getSimpleName());
        return resp;
    }

    String json = "{\n" +
            "            \"price\": 31.99,\n" +
            "            \"pricingType\": {\n" +
            "                \"nameValuePairs\": {\n" +
            "                    \"ebookRentType\": \"Regular\",\n" +
            "                    \"termDays\": \"180\",\n" +
            "                    \"pricingContractId\": \"302\",\n" +
            "                    \"termName\": \"180-day Rental\",\n" +
            "                    \"sourceCost\": \"31.99\",\n" +
            "                    \"expires\": \"2018-03-25 17:11\",\n" +
            "                    \"term\": \"EBOOK\",\n" +
            "                    \"priceType\": \"Rent\",\n" +
            "                    \"dueDate\": \"2018-09-20\",\n" +
            "                    \"termId\": \"107\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"logId\": \"bef183a5-8a7d-42f0-b685-2c2ac0fcc07a\"\n" +
            "        }";

//    static Map result = new Gson().fromJson(json, Map.class);

    public static void main(String[] args) {
        Response response = get(
                "http://catalog.test3.cheggnet.com:6001/catalog-api/rest/catalog/priced/byId/LBP-29797365");
//        response.body().prettyPrint();
//        String jsonString = response.getBody().asString();
//        getValue(response, "prices[1].pricingType.nameValuePairs.termName");

        List<Map> prices = getValue(response, "prices");
        int [] arr = {-1};
        boolean yes = prices.stream().
                peek(i -> arr[0]++).
                map(d -> (Map) d.get("pricingType")).
                map(d -> (Map) d.get("nameValuePairs")).
                filter(d -> d.get("priceType").equals("Rent") && d.get("termDays").equals("120")).
                peek(System.out::println).
                findFirst().
                isPresent();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (yes){
            System.out.println(prices.get(arr[0]).get("logId"));
        }
        }

        }



//!!!!!!! String resp = response.body().jsonPath().get("prices[1].pricingType.nameValuePairs.termName");
// ------------------------------------
    // String jsonString = response.getBody().asString();
    // Son son = new Gson().fromJson(jsonString, Son.class);
// -----------------------------------
//JsonObject son = new Gson().fromJson(jsonString, JsonObject.class);
//    JsonArray prices = son.getAsJsonArray("prices");
//        for (JsonElement price: prices){
//        System.out.println(price.getAsJsonObject().
//                getAsJsonObject("pricingType").
//                getAsJsonObject("nameValuePairs").get("termName"));


