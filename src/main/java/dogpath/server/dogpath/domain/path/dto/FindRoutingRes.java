package dogpath.server.dogpath.domain.path.dto;

import dogpath.server.dogpath.domain.path.algorithm.Node;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
public class FindRoutingRes {
    private List<Point> routeCoordinates;
    private LocalTime time;
    private Double distance;


    @Builder
    public FindRoutingRes(Double distance, List<Point> routeCoordinates, LocalTime time) {
        this.distance = distance;
        this.routeCoordinates = routeCoordinates;
        this.time = time;
    }

    public static FindRoutingRes from(Double distance, List<Node> routeCoordinates, LocalTime time) {
        List<Point> points = routeCoordinates.stream()
                .map(Node::getCenterPoint)
                .toList();
        return FindRoutingRes.builder()
                .distance(distance)
                .routeCoordinates(points)
                .time(time)
                .build();
    }
}
