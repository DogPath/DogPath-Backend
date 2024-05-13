package dogpath.server.dogpath.domain.path.algorithm;


import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.List;

@Component
public class SearchAlgorithm {
    public List<Point> findRouteByHeuristic(List<Node> calculatedNodes) {
        return null;
    }

    public Double getDistanceByRouteCoordinates(List<Point> routeCoordinates) {
        return null;
    }

    public LocalTime getTimeByRouteCoordinates(List<Point> routeCoordinates) {
        return null;
    }
}
