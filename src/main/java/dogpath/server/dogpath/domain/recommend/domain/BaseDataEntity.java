package dogpath.server.dogpath.domain.recommend.domain;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@MappedSuperclass
public abstract class BaseDataEntity {

    private Double latitude;
    private Double longitude;
}
