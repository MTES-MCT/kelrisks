package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class SiteIndustrielBasiasQO extends AbstractQO {
    
    private String       raisonSociale;
    private List<String> precisions;
    private String       codeINSEE;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QSiteIndustrielBasias.siteIndustrielBasias.id.eq(id));}
        if (raisonSociale != null) {
            for (String word : raisonSociale.split(" ")) {
                builder.and(QSiteIndustrielBasias.siteIndustrielBasias.raisonSociale.containsIgnoreCase(word));
            }
        }
        if (codeINSEE != null) {builder.and(QSiteIndustrielBasias.siteIndustrielBasias.codeINSEE.eq(codeINSEE));}
        if (!CollectionUtils.isEmpty(precisions)) {builder.and(QSiteIndustrielBasias.siteIndustrielBasias.precision.in(precisions));}
    }
}
  