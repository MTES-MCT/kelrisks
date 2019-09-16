package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QCommune;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommuneQO extends AbstractQO {
    
    private String nomCommune;
    private String communePartielle;
    private String codePostal;
    private String codeINSEE;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QCommune.commune.id.eq(id));}
        if (nomCommune != null) {builder.and(QCommune.commune.nomCommune.equalsIgnoreCase(nomCommune));}
        if (codePostal != null) {builder.and(QCommune.commune.codePostal.eq(codePostal));}
        if (codeINSEE != null) {builder.and(QCommune.commune.codeINSEE.eq(codeINSEE));}
        if (communePartielle != null) {
            BooleanBuilder orBuilder = new BooleanBuilder();
            orBuilder.or(QCommune.commune.codePostal.containsIgnoreCase(communePartielle));
            orBuilder.or(QCommune.commune.codeINSEE.containsIgnoreCase(communePartielle));
            orBuilder.or(QCommune.commune.nomCommune.containsIgnoreCase(communePartielle));
            builder.and(orBuilder);
        }
    }
}
  