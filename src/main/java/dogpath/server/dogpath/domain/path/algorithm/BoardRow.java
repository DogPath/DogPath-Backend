package dogpath.server.dogpath.domain.path.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardRow {
    private List<Node> nodes;

    public static BoardRow from(List<Node> temp) {
        return new BoardRow(temp);
    }

    public String printBoardRow(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            sb.append("(")
                    .append(node.getBoardX())
                    .append(", ")
                    .append(node.getBoardY())
                    .append(")")
                    .append(" : ")
                    .append(node.getCenterPoint())
                    .append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            sb.append("isVisited : ")
                    .append(node.isVisited)
                    .append(", ")
                    .append("isPassingNode : ")
                    .append(node.isPassingNode)
                    .append(" ");
        }
        return sb.toString();
    }
}
