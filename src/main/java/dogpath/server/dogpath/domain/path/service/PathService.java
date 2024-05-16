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
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PathService {

    private final ScoreCalculator scoreCalculator;
    private final SearchAlgorithm searchAlgorithm;

    public List<FindRoutingRes> findRoute(FindRoutingReq findRoutingReq) throws IOException {
        List<FindRoutingRes> findRoutingResList = new ArrayList<>();
        Point userCoordinate = findRoutingReq.getUserCoordinate();
        String walkTime = findRoutingReq.getWalkTime();
        WalkLength walkLength = WalkLength.valueOf(walkTime);

        Board calculatedBoard = getCalculatedBoard(userCoordinate, walkLength);

        //3개의 리스트 탐색해야 함.
        while (!isCompletedGeneratedRoutes(findRoutingResList)) {
            FindRoutingRes findRoutingRes = generateRoute(findRoutingReq.getUserCoordinate(), walkLength, calculatedBoard);
            findRoutingResList.add(findRoutingRes);
        }
        return findRoutingResList;
    }

    //길 생성 메소드
    //노드 리스트, 거리, 산책 시간 출력
    private FindRoutingRes generateRoute(Point userCoordinate, WalkLength walkLength, Board calculatedNodes) throws IOException {
        while (true) {
            //TODO : 시작 노드(목적지가 될 노드)를 설정해주어야 함.
            // 시작지(0,0)기준 근처 노드 중 가장 value가 큰 노드로 할 예정.(이걸 어케 정하누)
            // Board 객체에 생성하는 메서드가 필요하지 않을까 생각
            Node userCoordinateNode = new Node(userCoordinate.getX(), userCoordinate.getY());
            RouteInfo routeInfo = searchAlgorithm.findRouteByHeuristic(userCoordinateNode, calculatedNodes);
            if (isAvailableDistance(walkLength, routeInfo.getDistance())) {
                return FindRoutingRes.from(routeInfo.getDistance(), routeInfo.getRouteCoordinates(), routeInfo.getTime());
            }
        }
    }

    //알고리즘 계산 된 distance가 walkLength별로 재탐색 범위 안인지 확인
    private boolean isAvailableDistance(WalkLength walkLength, Double distance) {
        AllowanceDistance allowanceDistance = AllowanceDistance.ofWalkLength(walkLength);
        Range range = Range.fromWalkLength(walkLength, allowanceDistance);
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
