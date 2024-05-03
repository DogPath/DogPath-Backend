package dogpath.server.dogpath.recommend.algorithm.enums;

public enum WalkLength {
    SHORT(10),
    MEDIUM(20),
    LONG(40);

    private final int value;

    WalkLength(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
