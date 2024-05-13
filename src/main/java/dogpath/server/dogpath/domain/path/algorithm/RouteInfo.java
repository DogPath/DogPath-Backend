package dogpath.server.dogpath.domain.path.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class RouteInfo {
    private List<Node> routeCoordinates;
    private double distance;
    private LocalTime time;

    public RouteInfo(List<Node> routeCoordinates) {
        this.routeCoordinates = routeCoordinates;
        distance = 0.0;
        time = LocalTime.of(0,0,0);
    }

    public void addDistance(double distance){
        this.distance += distance;
    }

    public void addTime(long time) {
        this.time.isAfter(LocalTime.ofSecondOfDay(time));
    }
}
