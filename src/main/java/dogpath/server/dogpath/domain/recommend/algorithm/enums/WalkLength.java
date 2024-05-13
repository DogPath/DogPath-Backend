package dogpath.server.dogpath.domain.recommend.algorithm.enums;

import lombok.Getter;

@Getter
public enum WalkLength {
    // Value의 의미: 한 변에 포함되는 Node의 개수
    SHORT(5),
    MEDIUM(10),
    LONG(20);

    private final int value;

    WalkLength(int value){
        this.value = value;
    }

}
