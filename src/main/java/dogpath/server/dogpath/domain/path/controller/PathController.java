package dogpath.server.dogpath.domain.path.controller;

import dogpath.server.dogpath.domain.path.dto.FindRoutingReq;
import dogpath.server.dogpath.domain.path.dto.FindRoutingRes;
import dogpath.server.dogpath.domain.path.service.PathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/path")
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;

    @PostMapping("/routing")
    public ResponseEntity<List<FindRoutingRes>> getRoute(@RequestBody FindRoutingReq findRoutingReq) throws IOException, ParseException {
        return ResponseEntity.ok(pathService.findRoute(findRoutingReq));
    }
}
