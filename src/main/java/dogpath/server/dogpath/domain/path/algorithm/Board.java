package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@AllArgsConstructor
public class Board {
    private List<BoardRow> boardRows;

    public static Board of(List<Node> nodes, WalkLength walkLength) {
        List<BoardRow> boardRowList = new ArrayList<>();
        int nodeCountOfOneSide = walkLength.getValue();
        int rowSize = nodes.size() / nodeCountOfOneSide;
        for (int i = 0; i < rowSize; i++) {
            List<Node> temp = nodes.subList(i * rowSize, (i + 1) * rowSize);
            BoardRow boardRow = BoardRow.from(temp);
            boardRowList.add(boardRow);
        }
        return new Board(boardRowList);
    }

    public Node getStartNode(Node userNode) {
        Node minNode = null;
        double minDistance = Double.MAX_VALUE;
        for (BoardRow br : boardRows) {
            List<Node> nodes = br.getNodes();
            for (Node node : nodes) {
                double distance = node.calculateHaversineDistance(userNode);
                if (distance < minDistance) {
                    minNode = node;
                    minDistance = distance;
                }
            }
        }
//        log.info("MINVAL");
//        log.info(String.
//                valueOf(minNode));
        return minNode;
    }

    public List<Node> getAllNodes() {
        return boardRows.stream()
                .map(BoardRow::getNodes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public Node getNode(int boardX, int boardY) {
        return boardRows.get(boardX)
                .getNodes().get(boardY);
    }

    public List<Node> getNeighborNodes(Node startNode) {
        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        List<Node> neighborNodes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int x = dx[i] + startNode.boardX;
            int y = dy[i] + startNode.boardY;
            if (x < 0 || x >= boardRows.size() || y < 0 || y >= boardRows.size()) {
                continue;
            }

            Node node = getNode(x, y);
            neighborNodes.add(node);
        }
        return neighborNodes;
    }

    public void reset() {
        boardRows.stream()
                .map(BoardRow::getNodes)
                .flatMap(Collection::stream)
                .forEach(node -> {
                    node.isVisited = false;
                    node.isPassingNode = false;
                    node.heuristicDistance = 0.0;
                });
    }

    public void printBoard(){
        for (int i = 0; i < boardRows.size(); i++) {
            BoardRow br = boardRows.get(i);
            System.out.println(br.printBoardRow());
            System.out.println();
        }
    }
}
