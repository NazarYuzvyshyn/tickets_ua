package backend.json;

import java.io.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static gdTicketsUaDesctop.utils.TestFailure.failTest;

import static gdTicketsUaDesctop.Constants.JSON_REQUEST_TEMPLATES_LOCATION;


public class JsonRequestTemplateConverter {


    public static JsonObject convertFileToJson(String fileName) {

        String fullFilePath = JSON_REQUEST_TEMPLATES_LOCATION + fileName;
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fullFilePath));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (FileNotFoundException e) {
            failTest(String.format("File doesn't exist: %s", fullFilePath));
        }
        return jsonObject;
    }

}
