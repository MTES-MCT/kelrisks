package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QInstallationClassee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class InstallationClasseeQO extends AbstractQO {
    
    private String       codePostal;
    //    private Boolean      centroideCommune;
    private String       codeINSEE;
    private List<String> precisions;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QInstallationClassee.installationClassee.id.eq(id));}
        if (codePostal != null) {builder.and(QInstallationClassee.installationClassee.codePostal.eq(codePostal));}
        if (codeINSEE != null) {builder.and(QInstallationClassee.installationClassee.codeInsee.eq(codeINSEE));}
        //        if (centroideCommune != null) {builder.and(QInstallationClassee.installationClassee.precision.equalsIgnoreCase("municipality"));}
        if (!CollectionUtils.isEmpty(precisions)) {builder.and(QInstallationClassee.installationClassee.precision.in(precisions));}
    }
}
  