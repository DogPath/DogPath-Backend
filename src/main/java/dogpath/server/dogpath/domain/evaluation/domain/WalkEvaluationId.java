package dogpath.server.dogpath.domain.evaluation.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class WalkEvaluationId implements Serializable {
    private Long walk;
    private Long evaluation;
}
