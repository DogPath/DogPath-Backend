package dogpath.server.dogpath.domain.path.algorithm;

import dogpath.server.dogpath.domain.path.algorithm.enums.AllowanceDistance;
import dogpath.server.dogpath.domain.path.algorithm.enums.WalkLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Range {
    private double min;
    private double max;

    public static Range fromWalkLength(WalkLength walkLength, AllowanceDistance allowanceDistance) {
        int standardLength = walkLength.getMeter();
        double minRange = standardLength * (1 - allowanceDistance.getRange()/2);
        double maxRange = standardLength * (1 + allowanceDistance.getRange()*2);
        return new Range(minRange, maxRange);
    }

    public boolean inRange(double distance) {
        return min <= distance && max >= distance;
    }
}
