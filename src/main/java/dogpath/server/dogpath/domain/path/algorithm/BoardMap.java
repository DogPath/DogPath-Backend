package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import org.springframework.data.geo.Point;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardMap {
    //Key : (x,y)WALKLENGTH ex. (37.12324, 127.82132)SHORT
    private static final Map<String, Board> boards = new ConcurrentHashMap<>();
    private static final String keyFormat = "(%f,%f)%s";

    public static void addBoard(Point point, WalkLength walkLength, Board board) {
        String key = makeKey(point, walkLength);
        addBoard(key,board);
    }

    public static void addBoard(String key, Board board) {
        boards.put(key, board);
    }

    public static Board getBoard(Point point, WalkLength walkLength) {
        String key = makeKey(point, walkLength);
        return getBoard(key);
    }
    public static Board getBoard(String key) {
        return boards.getOrDefault(key, null);
    }

    public static boolean isContain(Point point, WalkLength walkLength) {
        String key = makeKey(point, walkLength);
        return boards.containsKey(key);
    }

    private static String makeKey(Point point, WalkLength walkLength) {
        return String.format(keyFormat, point.getX(), point.getY(), walkLength.name());
    }
}
