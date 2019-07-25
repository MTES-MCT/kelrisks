package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasol;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasolParcelle;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class SiteIndustrielBasolParcelleQO extends AbstractQO {
    
    private String parcelleCodeINSEE;
    private String parcelleSection;
    private String parcelleNumero;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QSiteIndustrielBasol.siteIndustrielBasol.id.eq(id));}
        
        if (parcelleCodeINSEE != null && parcelleSection != null && parcelleNumero != null) {
            builder.and(QSiteIndustrielBasolParcelle.siteIndustrielBasolParcelle.codeINSEE.eq(parcelleCodeINSEE));
            builder.and(QSiteIndustrielBasolParcelle.siteIndustrielBasolParcelle.section.equalsIgnoreCase(parcelleSection));
            builder.and(QSiteIndustrielBasolParcelle.siteIndustrielBasolParcelle.numero.eq(parcelleNumero));
        }
    }
}
  