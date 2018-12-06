package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasias;
import lombok.Data;

import com.querydsl.core.BooleanBuilder;

@Data
public class SiteIndustrielBasiasQO extends AbstractQO {
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QSiteIndustrielBasias.siteIndustrielBasias.id.eq(id));}
    }
}
  