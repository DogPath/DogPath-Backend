package dogpath.server.dogpath.recommend.repository;

import dogpath.server.dogpath.recommend.domain.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {

}
