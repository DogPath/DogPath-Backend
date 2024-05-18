package dogpath.server.dogpath.domain.walk.repository;

import dogpath.server.dogpath.domain.walk.domain.Walk;
import dogpath.server.dogpath.domain.walk.dto.PathRecordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WalkRepository extends JpaRepository<Walk, Long> {
    @Query("SELECT a FROM Walk a JOIN FETCH a.user u WHERE u.id = :user_id")
    List<Walk> findPathRecordsById(@Param("user_id") Long userId);

    @Query("SELECT a FROM Walk a JOIN FETCH a.user u WHERE u.id = :user_id AND a.id = :walk_id")
    Optional<PathRecordDTO> findPathRecordById(@Param("walk_id") Long walkId, @Param("user_id") Long userId);
}
