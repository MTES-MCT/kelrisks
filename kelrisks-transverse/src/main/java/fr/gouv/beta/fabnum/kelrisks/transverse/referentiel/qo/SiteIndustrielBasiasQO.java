package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class SiteIndustrielBasiasQO extends AbstractQO {
    
    private String raisonSociale;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QSiteIndustrielBasias.siteIndustrielBasias.id.eq(id));}
        if (raisonSociale != null) {
            for (String word : raisonSociale.split(" ")) {
                builder.and(QSiteIndustrielBasias.siteIndustrielBasias.raisonSociale.containsIgnoreCase(word));
            }
        }
    }
}
  