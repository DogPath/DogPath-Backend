package dogpath.server.dogpath.recommend.repository;


import dogpath.server.dogpath.recommend.domain.Cctv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CctvRepositoryTest {

    @Autowired
    private CctvRepository cctvRepository;

    @Test
    void 전체_조회() {

        List<Cctv> all = cctvRepository.findAll();
        assertThat(all.size()).isEqualTo(40070);

        for (Cctv cctv : all) {
            System.out.println(cctv.getLatitude() + " " + cctv.getLongitude());
        }

    }
}