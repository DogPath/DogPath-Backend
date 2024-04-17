package dogpath.server.dogpath.walklog.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
public class RealPath extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "real_path_id")
    private Long id;
    private BigDecimal distance;
    private LocalTime time;
    private MultiLineString route;
    private Point centerCoordiate;
}
