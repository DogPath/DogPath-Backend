package dogpath.server.dogpath.social.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import dogpath.server.dogpath.user.domain.User;
import dogpath.server.dogpath.walklog.domain.Walk;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
@IdClass(FavoriteId.class)
public class Favorite extends BaseEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk;
}
