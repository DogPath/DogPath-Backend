package dogpath.server.dogpath.domain.evaluation.repository;

import dogpath.server.dogpath.domain.evaluation.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Evaluation findByTitle(String title);
}
