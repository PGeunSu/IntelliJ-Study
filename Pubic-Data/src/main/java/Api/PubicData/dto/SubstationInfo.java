package Api.PubicData.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SubstationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String USE_DT;

    private SubstationInfo() {
    }

    private String LINE_NUM;
    private String SUB_STA_NM;
    private double RIDE_PASGR_NUM;
    private double ALIGHT_PASGR_NUM;
    private String WORK_DT;

    public SubstationInfo(Long id, String USE_DT, String LINE_NUM,
                          String SUB_STA_NM, double RIDE_PASGR_NUM, double ALIGHT_PASGR_NUM,
                          String WORK_DT) {
        this.id = id;
        this.USE_DT = USE_DT;
        this.LINE_NUM = LINE_NUM;
        this.SUB_STA_NM = SUB_STA_NM;
        this.RIDE_PASGR_NUM = RIDE_PASGR_NUM;
        this.ALIGHT_PASGR_NUM = ALIGHT_PASGR_NUM;
        this.WORK_DT = WORK_DT;
    }

}