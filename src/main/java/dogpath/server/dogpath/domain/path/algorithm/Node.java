package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.GeoMovement;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.geo.Point;

import java.awt.geom.Point2D;

@Getter
//@ToString
public class Node {
    Point centerPoint;
    Point topLeftPoint;
    Point topRightPoint;
    Point bottomLeftPoint;
    Point bottomRightPoint;
    double score;
    double heuristicDistance;

    // 중심 좌표를 생성자에서 입력 받으면 100m * 100m 노드 생성
    Node(double lat, double lng) {
        calculateNodeArea(lat, lng);
        this.score = 0.0;
        this.heuristicDistance = 0.0;
    }

    private void calculateNodeArea(double centerLat, double centerLng) {
        this.centerPoint = new Point(centerLat, centerLng);

        // 중심으로부터 북서쪽: 위도 증가, 경도 감소
        this.topLeftPoint = new Point(centerLat + GeoMovement.MOVE_50M.getLatChangeVal(), centerLng - GeoMovement.MOVE_50M.getLngChangeVal());

        // 중심으로부터 북동쪽: 위도 증가, 경도 증가
        this.topRightPoint = new Point(centerLat + GeoMovement.MOVE_50M.getLatChangeVal(), centerLng + GeoMovement.MOVE_50M.getLngChangeVal());

        // 중심으로부터 남서쪽: 위도 감소, 경도 감소
        this.bottomLeftPoint = new Point(centerLat - GeoMovement.MOVE_50M.getLatChangeVal(), centerLng - GeoMovement.MOVE_50M.getLngChangeVal());

        // 중심으로부터 남동쪽: 위도 감소, 경도 증가
        this.bottomRightPoint = new Point(centerLat - GeoMovement.MOVE_50M.getLatChangeVal(), centerLng + GeoMovement.MOVE_50M.getLngChangeVal());

    }
}
