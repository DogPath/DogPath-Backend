package dogpath.server.dogpath.recommend.algorithm;

import lombok.Getter;
import lombok.ToString;

import java.awt.geom.Point2D;

@Getter
@ToString
public class Node {

    private static final double LAT_MOVE_50M = 0.00045050;
    private static final double LNG_MOVE_50M = 0.00056597;
    Point2D.Double centerPoint;
    Point2D.Double leftTopPoint;
    Point2D.Double rightTopPoint;
    Point2D.Double leftBottomPoint;
    Point2D.Double rightBottomPoint;
    double score;
    double heuristicDistance;

    // 중심 좌표를 생성자에서 입력 받으면 100m * 100m 노드 생성
    Node(double lat, double lng) {
        calculateNodeArea(lat, lng);
        this.score = 0.0;
        this.heuristicDistance = 0.0;
    }

    private void calculateNodeArea(double centerLat, double centerLng) {
        this.centerPoint = new Point2D.Double(centerLat, centerLng);

        // 중심으로부터 북서쪽: 위도 증가, 경도 감소
        this.leftTopPoint = new Point2D.Double(centerLat + LAT_MOVE_50M, centerLng - LNG_MOVE_50M);

        // 중심으로부터 북동쪽: 위도 증가, 경도 증가
        this.rightTopPoint = new Point2D.Double(centerLat + LAT_MOVE_50M, centerLng + LNG_MOVE_50M);

        // 중심으로부터 남서쪽: 위도 감소, 경도 감소
        this.leftBottomPoint = new Point2D.Double(centerLat - LAT_MOVE_50M, centerLng - LNG_MOVE_50M);

        // 중심으로부터 남동쪽: 위도 감소, 경도 증가
        this.rightBottomPoint = new Point2D.Double(centerLat - LAT_MOVE_50M, centerLng + LNG_MOVE_50M);

    }
}
