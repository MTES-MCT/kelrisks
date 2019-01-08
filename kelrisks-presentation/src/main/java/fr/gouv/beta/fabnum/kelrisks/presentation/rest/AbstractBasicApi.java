package fr.gouv.beta.fabnum.kelrisks.presentation.rest;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

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
    
        parcelleCode = parcelleCode.replaceAll(" ", "-");
        
        Pattern pattern = Pattern.compile("\\w+-\\d+");
        Matcher matcher = pattern.matcher(parcelleCode);
    
        List<ParcelleDTO> parcelleDTOs;
        
        if (matcher.matches()) {
            ParcelleQO parcelleQO = new ParcelleQO();
            parcelleQO.setCodeINSEE(codeINSEE);
            parcelleQO.setSection(parcelleCode.split("-")[0]);
            parcelleQO.setNumero(parcelleCode.split("-")[1]);
    
            parcelleDTOs = gestionParcelleFacade.rechercherAvecCritere(parcelleQO);
        }
        else {
            ParcelleQO parcelleQO = new ParcelleQO();
            parcelleQO.setCode(parcelleCode);
    
            parcelleDTOs = gestionParcelleFacade.rechercherAvecCritere(parcelleQO);
        }
    
        if (parcelleDTOs.isEmpty()) { return null;}
        else if (parcelleDTOs.size() > 1) { return null;}
        else { return parcelleDTOs.get(0).getCode(); }
    }
}
