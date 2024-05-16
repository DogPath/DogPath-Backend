package dogpath.server.dogpath.domain.walk.service;

import dogpath.server.dogpath.domain.walk.dto.GetPathRecordsResponse;
import dogpath.server.dogpath.domain.walk.dto.PathRecordDTO;
import dogpath.server.dogpath.domain.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalkService {
    private final WalkRepository walkRepository;

    public GetPathRecordsResponse getPathRecordsByUserId(Long userId) {
        log.debug("[WalkService.getPathRecordsByUserId]");
        return new GetPathRecordsResponse(
                walkRepository.findPathRecordsById(userId)
                        .stream()
                        .map(PathRecordDTO::new)
                        .collect(Collectors.toList()));
    }

}
