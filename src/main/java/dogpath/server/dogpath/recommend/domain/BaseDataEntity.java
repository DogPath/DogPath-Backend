package dogpath.server.dogpath.recommend.domain;


import jakarta.persistence.MappedSuperclass;
import org.locationtech.jts.geom.Point;

@MappedSuperclass
public abstract class BaseDataEntity {

    private Point coordinate;
}