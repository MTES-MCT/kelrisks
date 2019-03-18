package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasol;
import lombok.Data;

import com.querydsl.core.BooleanBuilder;

@Data
public class SiteIndustrielBasolQO extends AbstractQO {
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QSiteIndustrielBasol.siteIndustrielBasol.id.eq(id));}
    }
}
  