package dogpath.server.dogpath.path.repository;

import dogpath.server.dogpath.path.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

}
