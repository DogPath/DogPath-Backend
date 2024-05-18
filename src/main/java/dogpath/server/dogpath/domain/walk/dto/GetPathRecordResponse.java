package dogpath.server.dogpath.domain.walk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPathRecordResponse {
    @JsonProperty("pathRecord")
    PathRecordDTO pathRecordDTO;
}
