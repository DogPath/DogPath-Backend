package dogpath.server.dogpath.domain.evaluation.repository;

import dogpath.server.dogpath.domain.evaluation.domain.WalkEvaluation;
import dogpath.server.dogpath.domain.evaluation.domain.WalkEvaluationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkEvaluationRepository extends JpaRepository<WalkEvaluation, WalkEvaluationId> {
    List<WalkEvaluation> findByWalkId(Long walkId);
}
