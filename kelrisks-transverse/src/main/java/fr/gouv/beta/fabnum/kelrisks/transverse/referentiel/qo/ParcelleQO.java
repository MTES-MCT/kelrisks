package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QParcelle;
import lombok.Data;

import com.querydsl.core.BooleanBuilder;

@Data
public class ParcelleQO extends AbstractQO {
    
    private String code;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QParcelle.parcelle.id.eq(id));}
        if (code != null) {builder.and(QParcelle.parcelle.code.eq(code));}
    }
}
  