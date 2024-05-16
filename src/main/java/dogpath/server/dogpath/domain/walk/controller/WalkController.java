package dogpath.server.dogpath.domain.walk.controller;

import dogpath.server.dogpath.domain.walk.dto.GetPathRecordsResponse;
import dogpath.server.dogpath.domain.walk.service.WalkService;
import dogpath.server.dogpath.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/path/records")
public class WalkController {

    private final WalkService walkService;

    @GetMapping
    public BaseResponse<GetPathRecordsResponse> getPathRecords(@RequestParam Long userId) {
        log.debug("[WalkController.getPathRecords]");
        return new BaseResponse<>(walkService.getPathRecordsByUserId(userId));
    }


}
