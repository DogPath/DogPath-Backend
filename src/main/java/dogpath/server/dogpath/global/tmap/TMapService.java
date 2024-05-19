package dogpath.server.dogpath.global.tmap;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@Slf4j
@Component
public class TMapService {

    @Value("${tmap.app.key}")
    public static String APP_KEY;
    public static String bodyFormat = "{" +
            "\"startX\":%f," +
            "\"startY\":%f," +
            "\"endX\":%f," +
            "\"endY\":%f," +
            "\"passList\":\"%s\"," +
            "\"reqCoordType\":\"WGS84GEO\"," +
            "\"startName\":\"%s\"," +
            "\"endName\":\"%s\"," +
            "\"searchOption\":\"0\"," +
            "\"resCoordType\":\"WGS84GEO\"," +
            "\"sort\":\"index\"" +
            "}";
    public Response getRouteByTMap(Point startPoint, String startName, Point endPoint, String endName, Point[] passList) throws IOException {
        OkHttpClient client = new OkHttpClient();
        /*
            startX : 출발지X좌표(경도)
            startY : 출발지Y좌표(위도)
            endX : 목적지X좌표(경도)
            endY : 목적지Y좌표(위도)
            passList : 경유지 X, Y좌표를 , 와 _ 로 구분(최대 5곳)
            reqCoordType : 요청 좌표계유형(WGS84GEO)
            startName : 출발지 명칭 - UTF8인코딩
            endName : 목적지 명칭 - UTF8인코딩
            searchOption : 경로탐색 옵션 - 0: 추천, 4 : 추천 + 대로우선, 10: 최단, 30: 최단거리 + 계단제외
            resCoordType : 응답 좌표계 유형(WGS84GEO)
            sort : 정렬 - index(기본값), custom(라인노드, 포인트노드의 순서로 정렬)

         */
        Point point = new Point(1.0,2.0);

        String encodedStartName = StandardCharsets.UTF_8.encode(startName).toString();
        String encodedEndName = StandardCharsets.UTF_8.encode(endName).toString();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                makeBody(startPoint, encodedStartName, endPoint, encodedEndName, passList)
        );
        Request request = new Request.Builder()
                .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("appKey", "JTg68MjFA07LLpm89zHoP7zOSTYaQaIS2ZXFB1bC")
                .build();

        Response response = client.newCall(request).execute();
        log.info("RESPONSE CODE : " + response.code());
        log.info("RESPONSE MESSAGE: " + response.body().string());
        return response;
    }

    private String makeBody(Point startPoint, String startName, Point endPoint, String endName, Point[] passList) {
        String passListString = getPassListBodyString(passList);
        return String.format(bodyFormat,
                startPoint.getX(),
                startPoint.getY(),
                endPoint.getX(),
                endPoint.getY(),
                passListString,
                startName,
                endName);
    }

    private String getPassListBodyString(Point[] passList) {
        StringBuffer sb = new StringBuffer();
        Arrays.stream(passList)
                .forEach(point -> {
                    sb.append(point.getX())
                            .append(",")
                            .append(point.getY())
                            .append("_");
                });
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
