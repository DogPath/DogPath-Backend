package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.Weight;
import dogpath.server.dogpath.domain.path.domain.BaseDataEntity;
import dogpath.server.dogpath.domain.path.domain.Cctv;
import dogpath.server.dogpath.domain.path.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class WeightDataManager {
    private final CctvRepository cctvRepository;
    private final ConstructionRepository constructionRepository;
    private final EmergencyBellRepository emergencyBellRepository;
    private final FacilityRepository facilityRepository;
    private final GasStationRepository gasStationRepository;
    private final ParkRepository parkRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final SecurityRepository securityRepository;

    public static ConcurrentHashMap<Weight, List<? extends BaseDataEntity>> weightDataMap = null;

    @PostConstruct
    public void initWeightData() {
        log.info("[WeightDataManager.initWeightData]");
        if (weightDataMap == null) {
            weightDataMap = getWeightDataFromRepository();
        }

    }

    public ConcurrentHashMap<Weight, List<? extends BaseDataEntity>> getWeightDataFromRepository() {
        ConcurrentHashMap<Weight, List<? extends BaseDataEntity>> hashMap = new ConcurrentHashMap<>();

        List<Cctv> cctvs = cctvRepository.findAll();
        hashMap.put(Weight.CCTV, cctvs);
        hashMap.put(Weight.CONSTRUCTION, constructionRepository.findAll());
        hashMap.put(Weight.EMERGENCY_BELL, emergencyBellRepository.findAll());
        hashMap.put(Weight.FACILITY, facilityRepository.findAll());
        hashMap.put(Weight.GAS_STATION, gasStationRepository.findAll());
        hashMap.put(Weight.PARK, parkRepository.findAll());
        hashMap.put(Weight.PARKING_LOT, parkingLotRepository.findAll());
        hashMap.put(Weight.SECURITY, securityRepository.findAll());

        return hashMap;
    }

}
