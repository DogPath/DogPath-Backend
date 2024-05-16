package dogpath.server.dogpath.domain.path.repository;

import dogpath.server.dogpath.domain.path.domain.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {

}
