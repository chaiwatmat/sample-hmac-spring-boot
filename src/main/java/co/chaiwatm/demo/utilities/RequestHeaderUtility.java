package co.chaiwatm.demo.utilities;

import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import co.chaiwatm.demo.models.RequestHeaderModel;

public class RequestHeaderUtility {

    public static RequestHeaderModel GetHeaders(Map<String, String> requestHeaders) {
        requestHeaders.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(requestHeaders);
        RequestHeaderModel headerModel = gson.fromJson(jsonElement, RequestHeaderModel.class);

        System.out.println(headerModel);

        return headerModel;
    }
}