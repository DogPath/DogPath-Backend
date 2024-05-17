package dogpath.server.dogpath.domain.evaluation.controller;

import dogpath.server.dogpath.domain.evaluation.dto.EvaluateRouteReq;
import dogpath.server.dogpath.domain.evaluation.service.EvaluationService;
import dogpath.server.dogpath.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/path")
@RestController
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PostMapping("/records")
    public ResponseEntity evaluate(@RequestBody EvaluateRouteReq evaluateRouteReq){
        HttpStatus httpStatus = evaluationService.evaluateRoute(evaluateRouteReq);
        return ResponseEntity.status(httpStatus.value())
                .build();
    }
}
