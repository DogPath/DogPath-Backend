package dogpath.server.dogpath.recommend.repository;

import dogpath.server.dogpath.recommend.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
