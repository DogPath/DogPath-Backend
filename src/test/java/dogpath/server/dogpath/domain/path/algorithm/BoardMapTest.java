package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapTest {

    @Test
    public void BoardMap_추가_테스트() throws Exception {
        //given
        Point userCoordinate = new Point(37.5423265, 127.0759204);
        String walkTime = "LONG";

        //when
        BoardMap.addBoard(userCoordinate, WalkLength.valueOf(walkTime), new Board(new ArrayList<>()));

        //then
        Assertions
                .assertThat(BoardMap.getBoard(userCoordinate, WalkLength.valueOf(walkTime)))
                .isNotNull();
    }
}