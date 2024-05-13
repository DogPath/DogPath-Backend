package dogpath.server.dogpath.domain.path.dto;

import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.List;

public class FindRoutingRes {
    private List<Point2D.Double> routeCoordinates;
    private LocalTime time;
    private Double distance;
}
