package dogpath.server.dogpath.domain.path.algorithm.enums;

import dogpath.server.dogpath.domain.path.algorithm.Range;

public enum HaversineRange {
    SHORT(0),
    MEDIUM(1000),
    LONG(1200);

    private final int value;

    HaversineRange(int value) {
        this.value = value;
    }


    public static HaversineRange fromWalkLength(WalkLength walkLength) {
        if (walkLength == WalkLength.SHORT) {
            return SHORT;
        }
        if (walkLength == WalkLength.MEDIUM) {
            return MEDIUM;
        }
        if (walkLength == WalkLength.LONG) {
            return LONG;
        }
        return null;
    }
    public boolean inRange(double distance) {
        return distance > this.value;
    }
}
