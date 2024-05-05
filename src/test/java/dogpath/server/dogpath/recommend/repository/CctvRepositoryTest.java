package dogpath.server.dogpath.recommend.repository;


import dogpath.server.dogpath.recommend.domain.Cctv;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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