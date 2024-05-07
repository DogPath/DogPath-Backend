package dogpath.server.dogpath.path.algorithm;


import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.List;

@Component
public class SearchAlgorithm {
    public List<Point2D.Double> findRouteByHeuristic(List<Node> calculatedNodes) {
    }

    public Double getDistanceByRouteCooridnates(List<Point2D.Double> routeCoordinates) {
        return null;
    }

    public LocalTime getTimeByRouteCoordinates(List<Point2D.Double> routeCoordinates) {
    }
}
