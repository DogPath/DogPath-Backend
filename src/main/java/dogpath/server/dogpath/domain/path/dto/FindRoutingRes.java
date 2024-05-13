package dogpath.server.dogpath.domain.path.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.awt.geom.Point2D;
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

    public static FindRoutingRes from(Double distance, List<Point> routeCoordinates, LocalTime time) {
        return FindRoutingRes.builder()
                .distance(distance)
                .routeCoordinates(routeCoordinates)
                .time(time)
                .build();
    }
}
