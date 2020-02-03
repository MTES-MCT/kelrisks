package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QPlanPreventionRisquesGaspar;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanPreventionRisquesGasparQO extends AbstractQO {
    
    private String idGaspar;
    private String codeINSEE;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
    
        if (idGaspar != null) { builder.and(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.idGaspar.eq(idGaspar)); }
        if (codeINSEE != null) { builder.and(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.codeINSEE.eq(codeINSEE)); }
    }
}
  