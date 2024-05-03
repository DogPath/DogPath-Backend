package dogpath.server.dogpath.recommend.algorithm;

import dogpath.server.dogpath.recommend.algorithm.enums.WalkLength;
import lombok.ToString;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static dogpath.server.dogpath.recommend.algorithm.enums.GeoMovement.*;

@ToString
public class GridDivider {
    private Point2D.Double centerPoint;
    private WalkLength walkLength;

    GridDivider(double centerLat, double centerLng, String walkLength) {
        this.centerPoint = new Point2D.Double(centerLat, centerLng);
        this.walkLength = WalkLength.valueOf(walkLength);
    }

    /**
     * 1. 사용자 위치, 산책 길이를 통해 좌상단 노드의 중심 좌표를 구한다.
     * 2. 좌상단 노드를 생성하고 배열에 넣는다.
     * 3. 좌상단 노드의 중심으로부터 다른 노드들의 중심 좌표를 계산한다.
     * 4. 노드를 모두 생성하고 배열을 리턴.
     */

    private Node getTopLeftNode() {
        int count = walkLength.getValue() - 1;
        Node node = new Node(centerPoint.x + MOVE_50M.getLatChangeVal() * count, centerPoint.y - MOVE_50M.getLngChangeVal() * count);
        System.out.println(node.centerPoint);
        return node;

    }

    private List<Node> divideAreaIntoNodes() {
        ArrayList<Node> nodes = new ArrayList<>();

        Node topLeftNode = getTopLeftNode();
        double lat = topLeftNode.centerPoint.x;
        double lng = topLeftNode.centerPoint.y;

        for (int i = 0; i < walkLength.getValue(); i++) {

            for (int j = 0; j < walkLength.getValue(); j++) {
                nodes.add(new Node(lat, lng));
                lng += MOVE_100M.getLngChangeVal();
            }

            lat -= MOVE_100M.getLatChangeVal();
            lng = topLeftNode.centerPoint.y;
        }
        return nodes;
    }


//    public static void main(String[] args) {
//        GridDivider gridDivider = new GridDivider(37.540861, 127.071292, "MEDIUM");
//        List<Node> nodes = gridDivider.divideAreaIntoNodes();
//        CSVUtils.createCSV(nodes);
//        System.out.println(nodes.size());
//    }


}
