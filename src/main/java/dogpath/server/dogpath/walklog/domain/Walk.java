package dogpath.server.dogpath.walklog.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import dogpath.server.dogpath.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
public class Walk extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "walk_id")
    private Long id;

    private LocalTime time;
    private BigDecimal distance;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Reaction reaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_path_id")
    private RecommendationPath recommendationPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_path_id")
    private RealPath realPath;
}
