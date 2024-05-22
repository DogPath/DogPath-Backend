package dogpath.server.dogpath.domain.path.algorithm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AllowanceDistance {
    /*
        산책 거리에 따른 허용 범위를 관리하는 Enum 클래스
        짧은 산책 -> 10% / 중간 산책 -> 10% / 긴 산책 -> 10% (조절가능)
     */
    SHORT(0.1),
    MEDIUM(0.15),
    LONG(0.25);

    private double range;

    public static AllowanceDistance ofWalkLength(WalkLength walkLength) {
        return AllowanceDistance.valueOf(walkLength.name());
    }
}
