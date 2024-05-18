package dogpath.server.dogpath.domain.evaluation.dto;

import lombok.Data;

@Data
public class EvaluateRouteReq {
    private Long userId;
    private double distance;
    private String time;
    private String evaluations;
}
