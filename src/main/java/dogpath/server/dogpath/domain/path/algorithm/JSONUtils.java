package dogpath.server.dogpath.domain.path.algorithm;

import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

@Slf4j
public class JSONUtils {
    public static long parseDistanceFromResponse(String response) throws IOException, ParseException {
        return getTotalFeature(response, "totalDistance");
    }

    public static long parseTimeFromResponse(String response) throws IOException, ParseException {
        return getTotalFeature(response, "totalTime");
    }

    private static long getTotalFeature(String bodyString, String feature) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(bodyString);
        JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonObject.get("features").toString());

        JSONObject featureObject = (JSONObject) jsonArray.get(0);
        JSONObject propertiesObject = (JSONObject) featureObject.get("properties");
        long value = (Long) propertiesObject.get(feature);
        return value;
    }
}

