package dogpath.server.dogpath.tmap;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import dogpath.server.dogpath.global.tmap.TMapService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("test")
class TMapServiceTest {

    @Test
    public void TMapServiceAPI_테스트() throws Exception {
        //given
        TMapService tMapService = new TMapService();
        Point startPoint = new Point(37.539542645445685,127.07077595711924);
        Point endPoint= new Point(37.54675063189466 ,127.0741717734735);
        String startName = "건대입구역";
        String endName = "어린이대공원역";
        Point[] passList = new Point[1];
        passList[0] = new Point(37.54134464205793,127.07530371225825);

        //when
        Response routeByTMap = tMapService.getRouteByTMap(startPoint,startName,endPoint,endName, passList);

        //then

        ResponseBody body = routeByTMap.body();

//        String string = body.string();

        assertAll(
                () -> assertThat(routeByTMap.isSuccessful()).isTrue(),
//                () -> assertThat(body).isNotNull(),
//                () -> assertThat(string).isNotNull(),
                () -> assertThat(routeByTMap.code()).isEqualTo(200)
        );
    }

}