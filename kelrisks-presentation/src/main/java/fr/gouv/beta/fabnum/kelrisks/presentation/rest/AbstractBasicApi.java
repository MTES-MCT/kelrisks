package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

public class AbstractBasicApi {
    
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    
    public String getParcelleCode(String codeINSEE, String codeParcelle) {
        
        return getParcelleCode(codeParcelle + "@", codeINSEE);
    }
    
    public String getParcelleCode(String parcelleSecNumInseeList) {
        
        List<ParcelleDTO> parcelles = getParcelles(parcelleSecNumInseeList);
        
        if (parcelles == null) { return null; }
        if (parcelles.size() > 1) {return null;}
        return parcelles.get(0).getCode();
    }
    
    public List<ParcelleDTO> getParcelles(String codeINSEE, String codeParcelle) {
        
        return getParcelles(codeParcelle + "@" + codeINSEE);
    }
    
    public List<ParcelleDTO> getParcelles(String parcelleSecNumInseeList) {
        
        Pattern      pattern    = Pattern.compile("((?:[a-zA-Z]+)|(?:\\d{1,2})).*?(\\d+)");
        List<String> secNumList = Arrays.asList(parcelleSecNumInseeList.split(","));
        
        ParcelleQO parcelleQO = new ParcelleQO();
        
        secNumList.forEach(secNum -> {
            
            String[] secNumANDInsee = secNum.split("@");
            
            Matcher matcher = pattern.matcher(secNumANDInsee[0]);
            
            while (matcher.find()) {
                
                parcelleQO.getParcelles().add(new ParcelleQO.SecNumInsee(matcher.group(1), matcher.group(2), secNumANDInsee[1]));
            }
        });
        
        List<ParcelleDTO> parcelleDTOs = gestionParcelleFacade.rechercherAvecCritere(parcelleQO);
        
        if (parcelleDTOs.isEmpty()) { return null; }
        else { return parcelleDTOs; }
    }
    
    ParcelleDTO getParcelleDTO(String codeINSEE,
                               String codeParcelle) {
    
        String parcelleCode = getParcelleCode(codeParcelle + '@' + codeINSEE);
        
        ParcelleQO parcelleQO = new ParcelleQO();
        parcelleQO.setCodeINSEE(codeINSEE);
        parcelleQO.setCode(parcelleCode);
        
        return gestionParcelleFacade.rechercherResultatUniqueAvecCritere(parcelleQO);
    }
}
