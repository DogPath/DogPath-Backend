package dogpath.server.dogpath.domain.path.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindRoutingReq {
    private double latitude;
    private double longitude;
    private String walkTime;
}
