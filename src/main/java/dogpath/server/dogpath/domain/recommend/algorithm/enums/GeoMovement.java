package dogpath.server.dogpath.domain.recommend.algorithm.enums;

public enum GeoMovement {

    MOVE_50M(0.0004504995306159225, 0.0005659693923752604),
    MOVE_100M(0.0009009983061231845, 0.0011319387847505207);

    private final double latChangeVal;
    private final double lngChangeVal;

    GeoMovement(double latChangeVal, double lngChangeVal) {
        this.latChangeVal = latChangeVal;
        this.lngChangeVal = lngChangeVal;
    }

    public double getLatChangeVal(){
        return latChangeVal;
    }

    public double getLngChangeVal(){
        return lngChangeVal;
    }
}
