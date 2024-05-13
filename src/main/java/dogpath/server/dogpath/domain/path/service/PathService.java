package dogpath.server.dogpath.domain.path.service;

import dogpath.server.dogpath.domain.path.algorithm.Node;
import dogpath.server.dogpath.domain.path.algorithm.ScoreCalculator;
import dogpath.server.dogpath.domain.path.algorithm.SearchAlgorithm;
import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import dogpath.server.dogpath.domain.path.algorithm.enums.Weight;
import dogpath.server.dogpath.domain.path.domain.BaseDataEntity;
import dogpath.server.dogpath.domain.path.dto.FindRoutingReq;
import dogpath.server.dogpath.domain.path.dto.FindRoutingRes;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@XSlf4j
@Service
@Transactional
@AllArgsConstructor
public class PathService {

    private final ScoreCalculator scoreCalculator;
    private final SearchAlgorithm searchAlgorithm;

    public FindRoutingRes findRoute(FindRoutingReq findRoutingReq) {
        List<FindRoutingRes> findRoutingResList = new ArrayList<>();
        Point2D.Double userCoordinate = findRoutingReq.getUserCoordinate();
        String walkTime = findRoutingReq.getWalkTime();
        WalkLength walkLength = WalkLength.valueOf(walkTime);

        List<Node> calculatedNodes = getCalculatedNodes(userCoordinate, walkLength);
        while (!isCompletedGeneratedRoutes(findRoutingResList)) {
            FindRoutingRes findRoutingRes = generateRoute(walkLength, calculatedNodes);
            findRoutingResList.add(findRoutingRes);
        }
    }

    private FindRoutingRes generateRoute(WalkLength walkLength, List<Node> calculatedNodes) {
        List<Point2D.Double> routeCoordinates = searchAlgorithm.findRouteByHeuristic(calculatedNodes);
        Double distance = searchAlgorithm.getDistanceByRouteCooridnates(routeCoordinates);
        AllowanceDistance allowanceDistance = AllowanceDistance.valueOf(walkLength.name());
        LocalTime time = searchAlgorithm.getTimeByRouteCoordinates(routeCoordinates);

    }

    private boolean isCompletedGeneratedRoutes(List<FindRoutingRes> findRoutingResList){
        if (findRoutingResList.size() != 3) {
            return false;
        }
        return true;
    }
    
    private List<Node> getCalculatedNodes(Point2D.Double userCoordinate, WalkLength walkLength) {
        scoreCalculator.initData(userCoordinate.getX(), userCoordinate.getY(), walkLength.name());
        HashMap<Weight, List<? extends BaseDataEntity>> weightDataFromRepository = scoreCalculator.getWeightDataFromRepository();
        scoreCalculator.calculateNodeScore(weightDataFromRepository);
        return scoreCalculator.getNodes();
    }
}
