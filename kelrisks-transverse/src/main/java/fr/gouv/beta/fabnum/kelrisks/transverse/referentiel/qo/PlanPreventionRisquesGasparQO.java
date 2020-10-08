package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QPlanPreventionRisquesGaspar;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanPreventionRisquesGasparQO extends AbstractQO {
    
    private String  idGaspar;
    private String  codeINSEE;
    private Boolean annuleOuAbroge;
    private Boolean existsInGeorisque;
    private Boolean existsInGpu;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
    
        if (idGaspar != null) { builder.and(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.idGaspar.eq(idGaspar)); }
        if (codeINSEE != null) { builder.and(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.codeINSEE.eq(codeINSEE)); }
        if (annuleOuAbroge != null && !annuleOuAbroge) {
            builder.and(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.dateAbrogation.isNull());
            builder.and(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.dateAnnulation.isNull());
        }
        if (existsInGeorisque != null) {builder.and((QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.existsInGeorisque.eq(existsInGeorisque)));}
        if (existsInGpu != null) {builder.and((QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.existsInGpu.eq(existsInGpu)));}
    }
}
  