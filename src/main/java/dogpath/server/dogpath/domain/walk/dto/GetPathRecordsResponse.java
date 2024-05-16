package dogpath.server.dogpath.domain.walk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPathRecordsResponse {

    @JsonProperty("pathRecords")
    List<PathRecordDTO> pathRecordDTOS;
}
