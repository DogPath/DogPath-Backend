package dogpath.server.dogpath.path.repository;

import dogpath.server.dogpath.path.domain.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {

}
