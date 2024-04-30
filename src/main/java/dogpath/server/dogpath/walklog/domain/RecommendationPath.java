package dogpath.server.dogpath.walklog.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
public class RecommendationPath extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "recommendation_path_id")
    private Long id;
    private BigDecimal distance;
    private LocalTime time;
    private Polygon route;
}
