package dogpath.server.dogpath.domain.walk.repository;

import dogpath.server.dogpath.domain.walk.domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalkRepository extends JpaRepository<Walk, Long> {
    @Query("SELECT a FROM Walk a JOIN FETCH a.user u WHERE u.id = :user_id")
    List<Walk> findPathRecordsById(@Param("user_id") Long userId);
}
