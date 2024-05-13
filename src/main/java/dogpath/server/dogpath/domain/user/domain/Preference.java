package dogpath.server.dogpath.domain.user.domain;

import dogpath.server.dogpath.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Preference extends BaseEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long id;

    private int parkPref;
    private int securityPref;
    private int facilityPref;
    private int parkingLotPref;
    private int gasStationPref;
    private int constructionPref;

}
