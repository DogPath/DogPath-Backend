package dogpath.server.dogpath.recommend.algorithm;

public enum GeoMovement {

    MOVE_50M(0.000450499, 0.000565969),
    MOVE_100M(0.000900998, 0.001131938);

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
