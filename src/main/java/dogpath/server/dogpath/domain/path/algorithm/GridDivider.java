package dogpath.server.dogpath.domain.path.algorithm;


import dogpath.server.dogpath.domain.path.algorithm.enums.GeoMovement;
import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GridDivider {
    /**
     * 1. 사용자 위치, 산책 길이를 통해 좌상단 노드의 중심 좌표를 구한다.
     * 2. 좌상단 노드를 생성하고 배열에 넣는다.
     * 3. 좌상단 노드의 중심으로부터 다른 노드들의 중심 좌표를 계산한다.
     * 4. 노드를 모두 생성하고 배열을 리턴.
     */

    private Node getTopLeftNode(double centerLat, double centerLng, WalkLength walkLength) {
        int count = walkLength.getValue() - 1;
        return new Node(centerLat + GeoMovement.MOVE_50M.getLatChangeVal() * count, centerLng - GeoMovement.MOVE_50M.getLngChangeVal() * count);
    }


    public Board divideAreaIntoNodes(double centerLat, double centerLng, String walk) {
        List<Node> nodes = new ArrayList<>();
        WalkLength walkLength = WalkLength.valueOf(walk);
        Node topLeftNode = getTopLeftNode(centerLat, centerLng, walkLength);

        double lat = topLeftNode.centerPoint.getX();
        double lng = topLeftNode.centerPoint.getY();

        for (int i = 0; i < walkLength.getValue(); i++) {

            for (int j = 0; j < walkLength.getValue(); j++) {
                nodes.add(new Node(lat, lng, i, j));
                lng += GeoMovement.MOVE_100M.getLngChangeVal();
            }

            lat -= GeoMovement.MOVE_100M.getLatChangeVal();
            lng = topLeftNode.centerPoint.getY();
        }
        return Board.of(nodes, walkLength);
    }


}
