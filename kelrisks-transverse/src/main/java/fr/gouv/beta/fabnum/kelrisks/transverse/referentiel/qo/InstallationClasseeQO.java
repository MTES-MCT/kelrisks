package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QInstallationClassee;
import lombok.Data;

import com.querydsl.core.BooleanBuilder;

@Data
public class InstallationClasseeQO extends AbstractQO {
    
    private String  codePostal;
    private Boolean centroideCommune;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QInstallationClassee.installationClassee.id.eq(id));}
        if (codePostal != null) {builder.and(QInstallationClassee.installationClassee.codePostal.eq(codePostal));}
        if (centroideCommune != null) {builder.and(QInstallationClassee.installationClassee.centroideCommune.eq(centroideCommune));}
    }
}
  