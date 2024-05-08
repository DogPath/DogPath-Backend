package dogpath.server.dogpath.path.algorithm;

import dogpath.server.dogpath.path.algorithm.enums.AllowanceDistance;
import dogpath.server.dogpath.path.algorithm.enums.WalkLength;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Range {
    private double min;
    private double max;

    public static Range fromWalkLength(WalkLength walkLength, AllowanceDistance allowanceDistance) {
        int standardLength = walkLength.getKm();
        double minRange = standardLength * (1 - allowanceDistance.getRange());
        double maxRange = standardLength * (1 + allowanceDistance.getRange());
        return new Range(minRange, maxRange);
    }

    public boolean inRange(double distance) {
        return min <= distance && max >= distance;
    }
}
