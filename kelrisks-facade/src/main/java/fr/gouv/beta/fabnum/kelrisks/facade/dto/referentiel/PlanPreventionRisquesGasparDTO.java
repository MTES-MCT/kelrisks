package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import java.util.Date;

@Data
public class PlanPreventionRisquesGasparDTO {
    
    private String  idGaspar;
    private AleaDTO alea;
    private String  codeINSEE;
    private Date    datePrescription;
    private Date    dateApplicationAnticipee;
    private Date    dateDeprescription;
    private Date    dateApprobation;
    private Date    dateAbrogation;
    private Long    id;
}
  