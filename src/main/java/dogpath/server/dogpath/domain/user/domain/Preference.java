package dogpath.server.dogpath.domain.user.domain;

import dogpath.server.dogpath.domain.evaluation.domain.Evaluation;
import dogpath.server.dogpath.domain.evaluation.domain.EvaluationCategory;
import dogpath.server.dogpath.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Preference extends BaseEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long id;

    @ColumnDefault("0")
    private int parkPref;
    @ColumnDefault("0")
    private int securityPref;
    @ColumnDefault("0")
    private int facilityPref;
    @ColumnDefault("0")
    private int parkingLotPref;
    @ColumnDefault("0")
    private int gasStationPref;
    @ColumnDefault("0")
    private int constructionPref;

    public void updateEvaluation(Evaluation evaluation) {
        EvaluationCategory category = evaluation.getCategory();
        switch (category) {
            case 공원 -> parkPref++;
            case 주유소 -> gasStationPref++;
            case 주차장 -> parkingLotPref++;
            case 공사현장 -> constructionPref++;
            case 문화시설 -> facilityPref++;
            case 치안시설 -> securityPref++;
        }
    }
}
