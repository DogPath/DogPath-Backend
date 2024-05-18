package dogpath.server.dogpath.domain.walk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dogpath.server.dogpath.domain.walk.domain.Walk;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PathRecordDTO {
    private Long walkId;
    private String time;
    private float distance;
    private String imageUrl;
    private String evaluations;

    public PathRecordDTO(Walk walk) {
        this.walkId = walk.getId();
        this.time = walk.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.distance = walk.getDistance().floatValue();
        this.imageUrl = walk.getImageURL();
        this.evaluations = null;
    }

}
