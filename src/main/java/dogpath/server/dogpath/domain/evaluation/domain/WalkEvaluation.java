package dogpath.server.dogpath.domain.evaluation.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import dogpath.server.dogpath.domain.walklog.domain.Walk;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
@IdClass(WalkEvaluationId.class)
public class WalkEvaluation extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @Id
    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk;
}
