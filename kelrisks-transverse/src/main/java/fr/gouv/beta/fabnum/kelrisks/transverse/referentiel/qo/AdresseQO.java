package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;


import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QAdresse;
import lombok.Data;

import com.querydsl.core.BooleanBuilder;

@Data
public class AdresseQO extends AbstractQO {
    
    private String nomCommune;
    private String nomVoie;
    private String numero;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QAdresse.adresse.id.eq(id));}
        if (nomCommune != null) {builder.and(QAdresse.adresse.nomCommune.equalsIgnoreCase(nomCommune));}
        if (nomVoie != null) {builder.and(QAdresse.adresse.nomVoie.equalsIgnoreCase(nomVoie));}
        if (numero != null) {builder.and(QAdresse.adresse.numero.eq(numero));}
    }
}
  