package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QParcelle;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class ParcelleQO extends AbstractQO {
    
    private String            code;
    private List<String>      codes     = new ArrayList<>();
    private String            section;
    private String            numero;
    private String            codePostal;
    private String            codeINSEE;
    private List<SecNumInsee> parcelles = new ArrayList<>();
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QParcelle.parcelle.id.eq(id));}
        if (code != null) {builder.and(QParcelle.parcelle.code.eq(code));}
        if (codes != null && !codes.isEmpty()) {builder.and(QParcelle.parcelle.code.in(codes));}
        if (section != null) {builder.and(QParcelle.parcelle.section.equalsIgnoreCase(section));}
        if (numero != null) {builder.and(QParcelle.parcelle.numero.eq(numero));}
        if (codeINSEE != null) {builder.and(QParcelle.parcelle.commune.eq(codeINSEE));}
        
        if (parcelles != null && !parcelles.isEmpty()) {
            
            BooleanBuilder anySecNumCoupleBuilder = new BooleanBuilder();
            parcelles.forEach(secNumInsee -> {
                BooleanBuilder secAndNumBuilder = new BooleanBuilder();
                secAndNumBuilder.and(QParcelle.parcelle.section.equalsIgnoreCase(secNumInsee.section));
                secAndNumBuilder.and(QParcelle.parcelle.numero.eq(secNumInsee.numero));
                secAndNumBuilder.and(QParcelle.parcelle.commune.eq(secNumInsee.insee));
                anySecNumCoupleBuilder.or(secAndNumBuilder);
            });
            builder.and(anySecNumCoupleBuilder);
        }
    }
    
    @Data
    public static class SecNumInsee {
        
        private String section;
        private String numero;
        private String insee;
        
        public SecNumInsee(String section, String numero, String insee) {
            
            this.section = section;
            this.numero = numero;
            this.insee = insee;
        }
    }
}
  