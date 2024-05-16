package dogpath.server.dogpath.domain.path.algorithm.enums;

public enum Weight {
    PARK(10),
    FACILITY(2),
    CCTV(1),
    EMERGENCY_BELL(1),
    SECURITY(1),
    PARKING_LOT(-1),
    GAS_STATION(-1),
    CONSTRUCTION(-5);

    private final int value;

    Weight(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
