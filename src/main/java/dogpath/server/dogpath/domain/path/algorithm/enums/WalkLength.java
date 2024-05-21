package dogpath.server.dogpath.domain.path.algorithm.enums;

import lombok.Getter;

@Getter
public enum WalkLength {
    // Value의 의미: 한 변에 포함되는 Node의 개수
    SHORT(5),
    MEDIUM(7),
    LONG(8);

    private final int value;

    WalkLength(int value){
        this.value = value;
    }

    public int getMeter(){
        if (this == SHORT) {
            return 1000;
        }
        if (this == MEDIUM) {
            return 2000;
        }
        if (this == LONG) {
            return 4000;
        }
        return (this.value/5 * 1000);
    }
}
