package dogpath.server.dogpath.domain.walk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPathRecordRequest {

    private Long userId;
    private Long walkId;
}
