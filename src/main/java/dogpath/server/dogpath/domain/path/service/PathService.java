package dogpath.server.dogpath.domain.path.service;

import dogpath.server.dogpath.domain.path.algorithm.Node;
import dogpath.server.dogpath.domain.path.algorithm.Range;
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

import java.awt.geom.Point2D;
import java.time.LocalTime;
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

    public List<FindRoutingRes> findRoute(FindRoutingReq findRoutingReq) {
        List<FindRoutingRes> findRoutingResList = new ArrayList<>();
        Point userCoordinate = findRoutingReq.getUserCoordinate();
        String walkTime = findRoutingReq.getWalkTime();
        WalkLength walkLength = WalkLength.valueOf(walkTime);

        List<Node> calculatedNodes = getCalculatedNodes(userCoordinate, walkLength);

        //3개의 리스트 탐색해야 함.
        while (!isCompletedGeneratedRoutes(findRoutingResList)) {
            FindRoutingRes findRoutingRes = generateRoute(walkLength, calculatedNodes);
            findRoutingResList.add(findRoutingRes);
        }
        return findRoutingResList;
    }

    //길 생성 메소드
    //노드 리스트, 거리, 산책 시간 출력
    private FindRoutingRes generateRoute(WalkLength walkLength, List<Node> calculatedNodes) {
        while (true) {
            List<Point> routeCoordinates = searchAlgorithm.findRouteByHeuristic(calculatedNodes);
            Double distance = searchAlgorithm.getDistanceByRouteCoordinates(routeCoordinates);
            if (isAvailableDistance(walkLength, distance)) {
                LocalTime time = searchAlgorithm.getTimeByRouteCoordinates(routeCoordinates);
                return FindRoutingRes.from(distance, routeCoordinates, time);
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

    private List<Node> getCalculatedNodes(Point userCoordinate, WalkLength walkLength) {
        scoreCalculator.initData(userCoordinate.getX(), userCoordinate.getY(), walkLength.name());
        HashMap<Weight, List<? extends BaseDataEntity>> weightDataFromRepository = scoreCalculator.getWeightDataFromRepository();
        scoreCalculator.calculateNodeScore(weightDataFromRepository);
        return scoreCalculator.getNodes();
    }
}
