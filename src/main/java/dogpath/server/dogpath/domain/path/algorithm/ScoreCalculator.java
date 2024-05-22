package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.Weight;
import dogpath.server.dogpath.domain.path.domain.BaseDataEntity;
import dogpath.server.dogpath.domain.path.repository.CctvRepository;
import dogpath.server.dogpath.domain.path.repository.ConstructionRepository;
import dogpath.server.dogpath.domain.path.repository.EmergencyBellRepository;
import dogpath.server.dogpath.domain.path.repository.FacilityRepository;
import dogpath.server.dogpath.domain.path.repository.GasStationRepository;
import dogpath.server.dogpath.domain.path.repository.ParkRepository;
import dogpath.server.dogpath.domain.path.repository.ParkingLotRepository;
import dogpath.server.dogpath.domain.path.repository.SecurityRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class ScoreCalculator {

    private final GridDivider gridDivider;
    private Board board;


    public void initData(double lat, double lng, String walk) {
        board = gridDivider.divideAreaIntoNodes(lat, lng, walk);
//        log.info("BOARD 생성 정보");
//        log.info(board.toString());
    }


    public void calculateNodeScore(ConcurrentHashMap<Weight, List<? extends BaseDataEntity>> data) {
        double score;
        List<Node> nodes = board.getAllNodes();
        for (Node node : nodes) {
            score = 0.0;

            for (Map.Entry<Weight, List<? extends BaseDataEntity>> entry : data.entrySet()) {
                score += calculateEntityScore(
                        countEntityNumberInNode(node, entry.getValue()),
                        entry.getKey());
            }
            node.score = score;
        }

    }

    private double calculateEntityScore(int entityCnt, Weight weight) {
        return Math.log(entityCnt + 1) * weight.getValue();
    }

    private boolean inRange(Node node, BaseDataEntity entity) {
        boolean isLatInRange = node.bottomRightPoint.getX() <= entity.getLatitude() && entity.getLatitude() <= node.topLeftPoint.getX();
        boolean isLngInRange = node.topLeftPoint.getY() <= entity.getLongitude() && entity.getLongitude() <= node.bottomRightPoint.getY();

        return isLatInRange && isLngInRange;
    }


    private int countEntityNumberInNode(Node node, List<? extends BaseDataEntity> entities) {
        int entityCount = 0;
        for (BaseDataEntity entity : entities) {
            if (inRange(node, entity)) {
                entityCount++;
            }
        }
        return entityCount;
    }

}
