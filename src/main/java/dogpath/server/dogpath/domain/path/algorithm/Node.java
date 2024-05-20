package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.GeoMovement;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;

import java.util.Objects;


@Slf4j
@Getter
public class Node {
    Point centerPoint;
    Point topLeftPoint;
    Point topRightPoint;
    Point bottomLeftPoint;
    Point bottomRightPoint;
    double score;
    double heuristicDistance;
    boolean isVisited;
    boolean isPassingNode;
    int boardX;
    int boardY;

    // 중심 좌표를 생성자에서 입력 받으면 100m * 100m 노드 생성
    public Node(double lat, double lng) {
        calculateNodeArea(lat, lng);
        this.score = 0.0;
        this.heuristicDistance = 0.0;
        isVisited = false;
        isPassingNode = false;
    }

    public Node(double lat, double lng, int boardX, int boardY) {
        calculateNodeArea(lat, lng);
        this.score = 0.0;
        this.heuristicDistance = 0.0;
        isVisited = false;
        isPassingNode = false;
        this.boardX = boardX;
        this.boardY = boardY;
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

    public double calculateEuclidean(Node node) {
        Point centerPoint1 = this.centerPoint;
        Point centerPoint2 = node.centerPoint;

        double x = Math.pow(centerPoint1.getX() - centerPoint2.getX(), 2);
        double y = Math.pow(centerPoint1.getY() - centerPoint2.getY(), 2);
        return Math.sqrt(x + y);
    }

    public double calculateHaversineDistance(Node node) {
        double distance;
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        Point centerPoint1 = this.centerPoint;
        Point centerPoint2 = node.centerPoint;

        double deltaLatitude = Math.abs(centerPoint1.getX() - centerPoint2.getX()) * toRadian;
        double deltaLongitude = Math.abs(centerPoint1.getY() - centerPoint2.getY()) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(centerPoint1.getX() * toRadian) * Math.cos(centerPoint2.getX() * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance;
    }

    //휴리스틱 값
    public double getFValue(Node destination) {
        this.heuristicDistance = getValueG() + getValueH(destination);
        return this.heuristicDistance;
    }

    //g(n) 값
    private double getValueG(){
        return -1 * score;
    }

    //h(n) 값
    private double getValueH(Node destination){
        return this.calculateHaversineDistance(destination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return boardX == node.boardX && boardY == node.boardY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardX, boardY);
    }

    @Override
    public String toString() {
        return "Node{" +
                "boardX=" + boardX +
                ", boardY=" + boardY +
                ", centerPoint=" + centerPoint +
                ", score=" + score +
                ", heuristicDistance=" + heuristicDistance +
                ", isVisited=" + isVisited +
                ", isPassingNode=" + isPassingNode +
                "}\n";
    }
}
