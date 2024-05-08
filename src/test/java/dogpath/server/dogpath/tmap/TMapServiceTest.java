package dogpath.server.dogpath.tmap;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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

        //when
        Response routeByTMap = tMapService.getRouteByTMap(1.0, 1.0);

        //then

        ResponseBody body = routeByTMap.body();

        String string = body.string();

        assertAll(
                () -> assertThat(routeByTMap.isSuccessful()).isTrue(),
                () -> assertThat(body).isNotNull(),
                () -> assertThat(string).isNotNull());}

}