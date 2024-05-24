package dogpath.server.dogpath.domain.evaluation.controller;

import dogpath.server.dogpath.domain.evaluation.dto.EvaluateRouteReq;
import dogpath.server.dogpath.domain.evaluation.service.EvaluationService;
import dogpath.server.dogpath.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/path")
@RestController
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @CrossOrigin(origins = "*")
    @PostMapping("/records")
    public ResponseEntity evaluate(@RequestPart(name = "request") EvaluateRouteReq evaluateRouteReq, @RequestPart(name = "routeImg") MultipartFile routeImg) throws IOException {
        HttpStatus httpStatus = evaluationService.evaluateRoute(evaluateRouteReq, routeImg);
        return ResponseEntity.status(httpStatus.value())
                .build();
    }
}
