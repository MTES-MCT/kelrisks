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
    
    private String       code;
    private List<String> codes   = new ArrayList<>();
    private String       section;
    private String       numero;
    private String       codePostal;
    private String       codeINSEE;
    private List<SecNum> secNums = new ArrayList<>();
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QParcelle.parcelle.id.eq(id));}
        if (code != null) {builder.and(QParcelle.parcelle.code.eq(code));}
        if (codes != null && !codes.isEmpty()) {builder.and(QParcelle.parcelle.code.in(codes));}
        if (section != null) {builder.and(QParcelle.parcelle.section.equalsIgnoreCase(section));}
        if (numero != null) {builder.and(QParcelle.parcelle.numero.eq(numero));}
        if (codeINSEE != null) {builder.and(QParcelle.parcelle.commune.eq(codeINSEE));}
        
        if (secNums != null && !secNums.isEmpty()) {
            
            BooleanBuilder anySecNumCoupleBuilder = new BooleanBuilder();
            secNums.forEach(secNum -> {
                BooleanBuilder secAndNumBuilder = new BooleanBuilder();
                secAndNumBuilder.and(QParcelle.parcelle.section.eq(secNum.section));
                secAndNumBuilder.and(QParcelle.parcelle.numero.eq(secNum.numero));
                anySecNumCoupleBuilder.or(secAndNumBuilder);
            });
            builder.and(anySecNumCoupleBuilder);
        }
    }
    
    @Data
    public static class SecNum {
        
        private String section;
        private String numero;
        
        public SecNum(String section, String numero) {
            
            this.section = section;
            this.numero = numero;
        }
    }
}
  