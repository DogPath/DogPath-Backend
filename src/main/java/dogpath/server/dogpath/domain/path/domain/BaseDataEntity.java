package dogpath.server.dogpath.domain.path.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseDataEntity {

    private Double latitude;
    private Double longitude;
}
