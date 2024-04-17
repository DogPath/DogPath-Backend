package dogpath.server.dogpath.social.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class FavoriteId implements Serializable {
    private Long user;
    private Long walk;
}
