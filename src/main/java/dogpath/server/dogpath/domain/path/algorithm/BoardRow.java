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
}
