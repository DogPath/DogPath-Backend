package dogpath.server.dogpath.domain.recommend.repository;

import dogpath.server.dogpath.domain.recommend.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
