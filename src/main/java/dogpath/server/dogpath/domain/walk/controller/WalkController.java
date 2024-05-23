package dogpath.server.dogpath.domain.walk.controller;

import dogpath.server.dogpath.domain.walk.dto.GetPathRecordRequest;
import dogpath.server.dogpath.domain.walk.dto.GetPathRecordResponse;
import dogpath.server.dogpath.domain.walk.dto.GetPathRecordsResponse;
import dogpath.server.dogpath.domain.walk.service.WalkService;
import dogpath.server.dogpath.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/{record_id}")
    public ResponseEntity deletePathRecord(@PathVariable("record_id") Long recordId) {
        log.debug("[WalkController.deletePathRecord]");
        HttpStatus httpStatus = walkService.deletePathRecordById(recordId);
        return ResponseEntity.status(httpStatus.value())
                .build();
    }


    @GetMapping("/{record_id}")
    public BaseResponse<GetPathRecordResponse> getPathRecordById(@RequestParam Long userId, @PathVariable("record_id") Long walkId) {
        log.debug("[WalkController.getPathRecordById]");
        return new BaseResponse<>(walkService.getPathRecord(userId, walkId));
    }


}
