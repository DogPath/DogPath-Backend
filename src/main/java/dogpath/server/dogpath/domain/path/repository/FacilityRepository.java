package dogpath.server.dogpath.domain.path.repository;


import dogpath.server.dogpath.domain.path.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
