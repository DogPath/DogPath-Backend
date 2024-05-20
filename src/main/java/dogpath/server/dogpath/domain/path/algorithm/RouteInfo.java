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
    private long distance;
    private LocalTime time;

    public RouteInfo(List<Node> routeCoordinates) {
        this.routeCoordinates = routeCoordinates;
        distance = 0L;
        time = LocalTime.of(0,0,0);
    }

    public void addDistance(long distance){
        this.distance += distance;
    }

    public void addTime(long time) {
        this.time = this.time.plusSeconds(time);
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "distance=" + distance +
                ",\n routeCoordinates=" + routeCoordinates +
                ",\n time=" + time +
                '}';
    }

    public void printRoute() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < routeCoordinates.size(); i++) {
            Node node = routeCoordinates.get(i);
            sb.append("[").append(i).append("] ")
                    .append(node.getBoardX())
                    .append(", ")
                    .append(node.getBoardY())
                    .append("\n");
        }
        System.out.println(sb);
    }
}
