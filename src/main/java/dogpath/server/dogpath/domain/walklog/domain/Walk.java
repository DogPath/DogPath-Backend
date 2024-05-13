package dogpath.server.dogpath.domain.walklog.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import dogpath.server.dogpath.domain.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String imageURL;
}
