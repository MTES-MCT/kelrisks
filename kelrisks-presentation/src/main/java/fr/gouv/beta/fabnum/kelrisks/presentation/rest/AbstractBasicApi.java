package fr.gouv.beta.fabnum.kelrisks.presentation.rest;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

public class AbstractBasicApi {
    
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    @Autowired
    IGestionAdresseFacade  gestionAdresseFacade;
    
    String getParcelleCode(String codeINSEE, String parcelleCode) {
    
        Pattern pattern = Pattern.compile("([a-zA-Z])+.*?(\\d+)");
        Matcher matcher = pattern.matcher(parcelleCode);
    
        List<ParcelleDTO> parcelleDTOs = new ArrayList<>();
    
        while (matcher.find()) {
            ParcelleQO parcelleQO = new ParcelleQO();
            parcelleQO.setCodeINSEE(codeINSEE);
            parcelleQO.setSection(matcher.group(1));
            parcelleQO.setNumero(matcher.group(2));
            
            parcelleDTOs = gestionParcelleFacade.rechercherAvecCritere(parcelleQO);
        }
    
        if (parcelleDTOs.isEmpty()) { return null;}
        else if (parcelleDTOs.size() > 1) { return null;}
        else { return parcelleDTOs.get(0).getCode(); }
    }
}
