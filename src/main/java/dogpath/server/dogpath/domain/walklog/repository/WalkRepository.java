package dogpath.server.dogpath.domain.walklog.repository;

import dogpath.server.dogpath.domain.walklog.domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkRepository extends JpaRepository<Walk, Long> {
}
