package dogpath.server.dogpath.domain.path.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.awt.geom.Point2D;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindRoutingReq {
    private Point userCoordinate;
    private String walkTime;
}
