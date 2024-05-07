package dogpath.server.dogpath.path.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.geom.Point2D;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindRoutingReq {
    private Point2D.Double userCoordinate;
    private String walkTime;
}
