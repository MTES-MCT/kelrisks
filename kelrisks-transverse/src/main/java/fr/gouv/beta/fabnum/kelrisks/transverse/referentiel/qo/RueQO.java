package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QRue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class RueQO extends AbstractQO {
    
    String nomVoiePartiel;
    String nomVoie;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) { builder.and(QRue.rue.id.eq(id)); }
        if (nomVoie != null) { builder.and(QRue.rue.nomVoie.equalsIgnoreCase(nomVoie)); }
        if (nomVoiePartiel != null) {
            for (String s : nomVoiePartiel.split(" ")) { builder.and(QRue.rue.nomVoie.containsIgnoreCase(s)); }
        }
    }
}
  