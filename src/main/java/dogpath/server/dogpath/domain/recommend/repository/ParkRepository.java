package dogpath.server.dogpath.domain.recommend.repository;

import dogpath.server.dogpath.domain.recommend.domain.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {

}
