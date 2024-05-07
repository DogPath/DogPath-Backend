package dogpath.server.dogpath.path.algorithm;

import dogpath.server.dogpath.path.algorithm.enums.Weight;
import dogpath.server.dogpath.path.domain.*;
import dogpath.server.dogpath.path.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class ScoreCalculator {

    private final CctvRepository cctvRepository;
    private final ConstructionRepository constructionRepository;
    private final EmergencyBellRepository emergencyBellRepository;
    private final FacilityRepository facilityRepository;
    private final GasStationRepository gasStationRepository;
    private final ParkRepository parkRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final SecurityRepository securityRepository;

    private final GridDivider gridDivider;

    private List<Node> nodes;


    public void initData(double lat, double lng, String walk) {
        nodes = gridDivider.divideAreaIntoNodes(lat, lng, walk);
    }

    public HashMap<Weight, List<? extends BaseDataEntity>> getWeightDataFromRepository() {
        HashMap<Weight, List<? extends BaseDataEntity>> hashMap = new HashMap<>();

        hashMap.put(Weight.CCTV, cctvRepository.findAll());
        hashMap.put(Weight.CONSTRUCTION, constructionRepository.findAll());
        hashMap.put(Weight.EMERGENCY_BELL, emergencyBellRepository.findAll());
        hashMap.put(Weight.FACILITY, facilityRepository.findAll());
        hashMap.put(Weight.GAS_STATION, gasStationRepository.findAll());
        hashMap.put(Weight.PARK, parkRepository.findAll());
        hashMap.put(Weight.PARKING_LOT, parkingLotRepository.findAll());
        hashMap.put(Weight.SECURITY, securityRepository.findAll());

        return hashMap;
    }


    //TODO: 로직 수정 or 리팩토링 필요 (score가 private하지 않아야 작동 가능한 로직)
    public void calculateNodeScore(HashMap<Weight, List<? extends BaseDataEntity>> data) {
        double score;
        for (Node node : nodes) {
            score = 0.0;

            for (Map.Entry<Weight, List<? extends BaseDataEntity>> entry : data.entrySet()) {
                score += calculateEntityScore(
                        countEntityNumberInNode(node, entry.getValue()),
                        entry.getKey());
            }

            // 개선 방안: score를 private하게 만들고, Node 클래스에 score를 수정하는 로직을 구현한 메서드를 추가할 예정
            node.score = score;
        }

    }

    private double calculateEntityScore(int entityCnt, Weight weight) {
        return Math.log(entityCnt + 1) * weight.getValue();
    }

    private boolean inRange(Node node, BaseDataEntity entity) {
        boolean isLatInRange = node.bottomRightPoint.x <= entity.getLatitude() && entity.getLatitude() <= node.topLeftPoint.x;
        boolean isLngInRange = node.topLeftPoint.y <= entity.getLongitude() && entity.getLongitude() <= node.bottomRightPoint.y;

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
