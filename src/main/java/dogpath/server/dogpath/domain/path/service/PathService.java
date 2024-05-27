package dogpath.server.dogpath.domain.path.service;

import dogpath.server.dogpath.domain.path.algorithm.Board;
import dogpath.server.dogpath.domain.path.algorithm.BoardMap;
import dogpath.server.dogpath.domain.path.algorithm.Node;
import dogpath.server.dogpath.domain.path.algorithm.Range;
import dogpath.server.dogpath.domain.path.algorithm.RouteInfo;
import dogpath.server.dogpath.domain.path.algorithm.ScoreCalculator;
import dogpath.server.dogpath.domain.path.algorithm.SearchAlgorithm;
import dogpath.server.dogpath.domain.path.algorithm.WeightDataManager;
import dogpath.server.dogpath.domain.path.algorithm.enums.AllowanceDistance;
import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import dogpath.server.dogpath.domain.path.dto.FindRoutingReq;
import dogpath.server.dogpath.domain.path.dto.FindRoutingRes;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PathService {

    private final ScoreCalculator scoreCalculator;
    private final SearchAlgorithm searchAlgorithm;

    /**
     * 경로 탐색 메서드, 현재 위치를 기반으로 세가지 경로 탐색
     *
     * @param findRoutingReq : Controller 요청
     * @return : 경과물 dto 리스트
     * @throws IOException
     */
    public List<FindRoutingRes> findRoute(FindRoutingReq findRoutingReq) throws IOException, ParseException {
        List<FindRoutingRes> findRoutingResList = new ArrayList<>();
        Point userCoordinate = new Point(findRoutingReq.getLatitude(), findRoutingReq.getLongitude());
        String walkTime = findRoutingReq.getWalkTime();
        WalkLength walkLength = WalkLength.valueOf(walkTime);

//        Board calculatedBoard = getCalculatedBoard(userCoordinate, walkLength);
        Board calculatedBoard = getCalculatedBoardFromMap(userCoordinate, walkLength);

        //3개의 리스트 탐색해야 함
//        while (!isCompletedGeneratedRoutes(findRoutingResList)) {
//        for (int i = 0; i < 3; i++) {
//            log.info(i + " LINE GENERATION START");
        FindRoutingRes findRoutingRes = generateRoute(userCoordinate, walkLength, calculatedBoard);
        findRoutingResList.add(findRoutingRes);
//            log.info(i + " LINE GENERATION END");
//        }
        return findRoutingResList;
    }

    public List<FindRoutingRes> findRouteTest(FindRoutingReq findRoutingReq) throws IOException, ParseException {
        List<FindRoutingRes> findRoutingResList = new ArrayList<>();
        Point userCoordinate = new Point(findRoutingReq.getLatitude(), findRoutingReq.getLongitude());
        String walkTime = findRoutingReq.getWalkTime();
        WalkLength walkLength = WalkLength.valueOf(walkTime);

        Board calculatedBoard = getCalculatedBoardFromMap(userCoordinate, walkLength);

        Random random = new Random();
        //3개의 리스트 탐색해야 함
        FindRoutingRes findRoutingRes = generateRouteTest(userCoordinate, walkLength, calculatedBoard);
        AllowanceDistance allowanceDistance = AllowanceDistance.ofWalkLength(walkLength);
        Range range = Range.fromWalkLength(walkLength, allowanceDistance);

        double minDistance = range.getMin();
        double maxDistance = range.getMax();
        Long distance = (long) (minDistance + (maxDistance - minDistance) * random.nextDouble());
        LocalTime time = calculateTime(distance, 4); // Assume average walking speed of 4 km/h

        findRoutingRes.setDistance(distance);
        findRoutingRes.setTime(time);

        findRoutingResList.add(findRoutingRes);
        return findRoutingResList;
    }

    private static LocalTime calculateTime(long distance, int speedKmPerHour) {
        double timeInHours = (double) distance / 1000 / speedKmPerHour;
        int hours = (int) timeInHours;
        int minutes = (int) ((timeInHours - hours) * 60);
        int seconds = (int) ((((timeInHours - hours) * 60) - minutes) * 60);
        return LocalTime.of(hours, minutes, seconds);
    }

    private FindRoutingRes generateRouteTest(Point userCoordinate, WalkLength walkLength, Board board) throws IOException, ParseException {
        board.printBoard();
        while (true) {
            board.reset();
            Node userNode = new Node(userCoordinate.getX(), userCoordinate.getY());
            Node startNode = board.getStartNode(userNode);
            RouteInfo routeInfo = searchAlgorithm.findRouteByHeuristicTest(userNode, startNode, board, walkLength);
            System.out.println();
            routeInfo.printRoute();
            return FindRoutingRes.from(routeInfo.getDistance(), routeInfo.getRouteCoordinates(), routeInfo.getTime());
        }
    }

    private Board getCalculatedBoardFromMap(Point userCoordinate, WalkLength walkLength) {
        if (BoardMap.isContain(userCoordinate, walkLength)) {
            return BoardMap.getBoard(userCoordinate, walkLength);
        }
        return getCalculatedBoard(userCoordinate, walkLength);
    }

    //길 생성 메소드
    //노드 리스트, 거리, 산책 시간 출력
    private FindRoutingRes generateRoute(Point userCoordinate, WalkLength walkLength, Board board) throws IOException, ParseException {
        board.printBoard();
        while (true) {
            board.reset();
            Node userNode = new Node(userCoordinate.getX(), userCoordinate.getY());
            Node startNode = board.getStartNode(userNode);
            RouteInfo routeInfo = searchAlgorithm.findRouteByHeuristic(userNode, startNode, board, walkLength);
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
        scoreCalculator.calculateNodeScore(WeightDataManager.weightDataMap);
        BoardMap.addBoard(userCoordinate, walkLength, scoreCalculator.getBoard());
        return scoreCalculator.getBoard();
    }
}
