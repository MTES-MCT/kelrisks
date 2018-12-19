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
    private String adressePartielle;
    private String codePostal;
    private String nomVoiePartiel;
    private String codeINSEE;
    private String idBan;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QAdresse.adresse.id.eq(id));}
        if (nomCommune != null) {builder.and(QAdresse.adresse.nomCommune.equalsIgnoreCase(nomCommune));}
        if (codePostal != null) {builder.and(QAdresse.adresse.codePostal.eq(codePostal));}
        if (codeINSEE != null) {builder.and(QAdresse.adresse.codeINSEE.eq(codeINSEE));}
        if (idBan != null) {builder.and(QAdresse.adresse.idBAN.eq(idBan));}
        if (nomVoie != null) {builder.and(QAdresse.adresse.nomVoie.equalsIgnoreCase(nomVoie));}
        if (numero != null) {builder.and(QAdresse.adresse.numero.contains(numero));}
        if (adressePartielle != null) {
            BooleanBuilder orBuilder = new BooleanBuilder();
            orBuilder.or(QAdresse.adresse.codePostal.containsIgnoreCase(adressePartielle));
            orBuilder.or(QAdresse.adresse.nomCommune.containsIgnoreCase(adressePartielle));
            builder.and(orBuilder);
        }
        if (nomVoiePartiel != null) {
            BooleanBuilder orBuilder = new BooleanBuilder();
            for (String s : nomVoiePartiel.split(" ")) {
                orBuilder.or(QAdresse.adresse.nomVoie.containsIgnoreCase(s));
            }
            builder.and(orBuilder);
        }
    }
}
  