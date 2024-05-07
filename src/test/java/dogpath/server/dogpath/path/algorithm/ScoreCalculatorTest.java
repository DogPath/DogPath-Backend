package dogpath.server.dogpath.path.algorithm;

import dogpath.server.dogpath.path.algorithm.enums.Weight;
import dogpath.server.dogpath.path.domain.BaseDataEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ScoreCalculatorTest {

    @Autowired
    private ScoreCalculator scoreCalculator;
    private List<Node> nodes;
    private HashMap<Weight, List<? extends BaseDataEntity>> weightDataHashMap;

    @BeforeEach
    void setUp() {
        scoreCalculator.initData(37.54089413573132, 127.0713419265116, "SHORT");
    }


    @Test
    void calculateNodeScore() {
        weightDataHashMap = scoreCalculator.getWeightDataFromRepository();
        scoreCalculator.calculateNodeScore(weightDataHashMap);
        nodes = scoreCalculator.getNodes();

        assertThat(weightDataHashMap.get(Weight.CCTV).isEmpty()).isEqualTo(false);


        System.out.println(nodes.size());
        for (Node node : nodes) {
            System.out.println(node.centerPoint + " " + node.score);
        }
    }
}