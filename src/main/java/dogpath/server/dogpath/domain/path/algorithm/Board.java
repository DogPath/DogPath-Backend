package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
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
    public List<Node> getAllNodes(){
        return boardRows.stream()
                .map(BoardRow::getNodes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
