package dogpath.server.dogpath.domain.path.service;

import dogpath.server.dogpath.domain.path.algorithm.Board;
import dogpath.server.dogpath.domain.path.algorithm.Node;
import dogpath.server.dogpath.domain.path.algorithm.Range;
import dogpath.server.dogpath.domain.path.algorithm.RouteInfo;
import dogpath.server.dogpath.domain.path.algorithm.ScoreCalculator;
import dogpath.server.dogpath.domain.path.algorithm.SearchAlgorithm;
import dogpath.server.dogpath.domain.path.algorithm.enums.AllowanceDistance;
import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import dogpath.server.dogpath.domain.path.algorithm.enums.Weight;
import dogpath.server.dogpath.domain.path.domain.BaseDataEntity;
import dogpath.server.dogpath.domain.path.dto.FindRoutingReq;
import dogpath.server.dogpath.domain.path.dto.FindRoutingRes;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PathService {

    private final ScoreCalculator scoreCalculator;
    private final SearchAlgorithm searchAlgorithm;

    /**
     * 경로 탐색 메서드, 현재 위치를 기반으로 세가지 경로 탐색
     * @param findRoutingReq : Controller 요청
     * @return : 경과물 dto 리스트
     * @throws IOException
     */
    public List<FindRoutingRes> findRoute(FindRoutingReq findRoutingReq) throws IOException, ParseException {
        List<FindRoutingRes> findRoutingResList = new ArrayList<>();
        Point userCoordinate = new Point(findRoutingReq.getLatitude(),findRoutingReq.getLongitude());
        String walkTime = findRoutingReq.getWalkTime();
        WalkLength walkLength = WalkLength.valueOf(walkTime);

        Board calculatedBoard = getCalculatedBoard(userCoordinate, walkLength);

        //3개의 리스트 탐색해야 함
//        while (!isCompletedGeneratedRoutes(findRoutingResList)) {
        for (int i = 0; i < 3; i++) {
            log.info(i + " LINE GENERATION START");
            FindRoutingRes findRoutingRes = generateRoute(userCoordinate, walkLength, calculatedBoard);
            findRoutingResList.add(findRoutingRes);
            log.info(i + " LINE GENERATION END");
        }
        return findRoutingResList;
    }

    //길 생성 메소드
    //노드 리스트, 거리, 산책 시간 출력
    private FindRoutingRes generateRoute(Point userCoordinate, WalkLength walkLength, Board board) throws IOException, ParseException {
        while (true) {
            board.reset();
            Node userNode = new Node(userCoordinate.getX(), userCoordinate.getY());
            Node startNode = board.getStartNode(userNode);
            RouteInfo routeInfo = searchAlgorithm.findRouteByHeuristic(userNode, startNode, board);
            board.printBoard();
            System.out.println();
            routeInfo.printRoute();
            if (isAvailableDistance(walkLength, routeInfo.getDistance())) {
                return FindRoutingRes.from(routeInfo.getDistance(), routeInfo.getRouteCoordinates(), routeInfo.getTime());
            }
        }
    }

    //알고리즘 계산 된 distance가 walkLength별로 재탐색 범위 안인지 확인
    private boolean isAvailableDistance(WalkLength walkLength, long distance) {
        AllowanceDistance allowanceDistance = AllowanceDistance.ofWalkLength(walkLength);
        Range range = Range.fromWalkLength(walkLength, allowanceDistance);
        log.info(allowanceDistance.toString());
        log.info(range.toString());
        log.info(String.valueOf(distance));
        try {
            sleep(3000); // TODO :삭제 필요
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return range.inRange(distance);
    }

    private boolean isCompletedGeneratedRoutes(List<FindRoutingRes> findRoutingResList) {
        if (findRoutingResList.size() != 3) {
            return false;
        }
        return true;
    }

    private Board getCalculatedBoard(Point userCoordinate, WalkLength walkLength) {
        scoreCalculator.initData(userCoordinate.getX(), userCoordinate.getY(), walkLength.name());
        HashMap<Weight, List<? extends BaseDataEntity>> weightDataFromRepository = scoreCalculator.getWeightDataFromRepository();
        scoreCalculator.calculateNodeScore(weightDataFromRepository);
        return scoreCalculator.getBoard();
    }
}
