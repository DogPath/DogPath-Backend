package dogpath.server.dogpath.path.repository;

import dogpath.server.dogpath.path.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
