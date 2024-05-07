package dogpath.server.dogpath.recommend.domain;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@MappedSuperclass
public abstract class BaseDataEntity {

    private Double latitude;
    private Double longitude;
}
