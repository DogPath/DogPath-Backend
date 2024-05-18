package dogpath.server.dogpath.domain.walk.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import dogpath.server.dogpath.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Walk extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walk_id")
    private Long id;

    private LocalTime time;
    private BigDecimal distance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String imageURL;

    @Builder
    public Walk(BigDecimal distance, String imageURL, LocalTime time, User user) {
        this.distance = distance;
        this.imageURL = imageURL;
        this.time = time;
        this.user = user;
    }
}
