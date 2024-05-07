package dogpath.server.dogpath.path.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Facility extends BaseDataEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String category;

    // 휴업일
    @Column
    private String rest;

    // 운영 시간
    @Column(name= "oper_time", length = 255)
    private String operationTime;

}
