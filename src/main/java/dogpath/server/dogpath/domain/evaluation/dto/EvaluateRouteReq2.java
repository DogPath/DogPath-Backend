package dogpath.server.dogpath.domain.evaluation.dto;

import lombok.Data;

@Data
public class EvaluateRouteReq2 {
    private Long userId;
    private double distance;
    private String time;
    private String evaluations;
    private String imageUrl;
}
