package gdTicketsUaDesctop.utils.testCrestWest;

import backend.json.JsonApi;
import backend.json.DomsRequestBuilder;
import backend.json.ResponseHendler;
import com.google.gson.JsonObject;

import static backend.json.JsonRequestTemplateConverter.convertFileToJson;

import io.restassured.response.Response;
import static gdTicketsUaDesctop.Constants.DOMS_EVENT_ENDPOINT;

import java.util.*;


public class JsonRequestMain {

    public static void main(String[] args) {
//        Response response = get(
//                "http://catalog.test3.cheggnet.com:6001/catalog-api/rest/catalog/priced/byId/LBP-29797365");
////        response.body().prettyPrint();
////        String jsonString = response.getBody().asString();
////        getValue(response, "prices[1].pricingType.nameValuePairs.termName");
//
//        List<Map> prices = getValue(response, "prices");
//        int [] arr = {-1};
//        boolean yes = prices.stream().
//                peek(i -> arr[0]++).
//                map(d -> (Map) d.get("pricingType")).
//                map(d -> (Map) d.get("nameValuePairs")).
//                filter(d -> d.get("priceType").equals("Rent") && d.get("termDays").equals("120")).
//                peek(System.out::println).
//                findFirst().
//                isPresent();
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        if (yes){
//            System.out.println(prices.get(arr[0]).get("logId"));
//        }
//        }


        JsonObject json = convertFileToJson("domsRequestTemplate.json");

        DomsRequestBuilder drb = new DomsRequestBuilder(json);
        json = drb.withEvent("pause").
                withEventMetadataResumeDate("2018-11-17").
                withOrderLineId("947359150").
                buildJsonRequestObject();

        List<JsonObject> bodyAsList = new ArrayList<>();
        bodyAsList.add(json);


        JsonApi jsonApi = new JsonApi();
        Response response = jsonApi.postJson(DOMS_EVENT_ENDPOINT, bodyAsList);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        String message = ResponseHendler.getValue(response, "errors[0].errorMessage");


    }
}



