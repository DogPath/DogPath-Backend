package dogpath.server.dogpath.recommend.algorithm;

public enum WalkLength {
    SHORT(1),
    MEDIUM(2),
    LONG(4);

    private final int value;

    private WalkLength(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
