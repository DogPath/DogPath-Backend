package dogpath.server.dogpath.tmap;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TMapService {

    @Value("${tmap.app.key}")
    public static String APP_KEY;

    public Response getRouteByTMap(double startX, double startY) throws IOException {
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
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{" +
                        "\"startX\":126.92365493654832," +
                        "\"startY\":37.556770374096615," +
                        "\"endX\":126.92432158129688," +
                        "\"endY\":37.55279861528311," +
                        "\"passList\":\"126.92774822,37.55395475_126.92577620,37.55337145\"," +
                        "\"reqCoordType\":\"WGS84GEO\"," +
                        "\"startName\":\"%EC%B6%9C%EB%B0%9C\"," +
                        "\"endName\":\"%EB%8F%84%EC%B0%A9\"," +
                        "\"searchOption\":\"0\"," +
                        "\"resCoordType\":\"WGS84GEO\"," +
                        "\"sort\":\"index\"" +
                        "}"
        );
        Request request = new Request.Builder()
                .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("appKey", "JTg68MjFA07LLpm89zHoP7zOSTYaQaIS2ZXFB1bC")
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
}
