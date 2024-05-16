package dogpath.server.dogpath.domain.evaluation.repository;

import dogpath.server.dogpath.domain.evaluation.domain.WalkEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkEvaluationRepository extends JpaRepository<WalkEvaluation, Long> {
}
