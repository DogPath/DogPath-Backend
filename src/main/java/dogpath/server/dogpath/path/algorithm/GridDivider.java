package dogpath.server.dogpath.path.algorithm;

import dogpath.server.dogpath.path.algorithm.enums.WalkLength;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static dogpath.server.dogpath.path.algorithm.enums.GeoMovement.*;

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
        return new Node(centerLat + MOVE_50M.getLatChangeVal() * count, centerLng - MOVE_50M.getLngChangeVal() * count);
    }


    public List<Node> divideAreaIntoNodes(double centerLat, double centerLng, String walk) {
        ArrayList<Node> nodes = new ArrayList<>();
        WalkLength walkLength = WalkLength.valueOf(walk);
        Node topLeftNode = getTopLeftNode(centerLat, centerLng, walkLength);

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


}
