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
    
    public String getParcelleCode(String codeINSEE, String parcelleCode) {
    
        List<ParcelleDTO> parcelles = getParcelles(codeINSEE, parcelleCode);
    
        if (parcelles == null) { return null; }
        if (parcelles.size() > 1) {return null;}
        return parcelles.get(0).getCode();
    }
    
    public List<ParcelleDTO> getParcelles(String codeINSEE, String parcelleSecNums) {
        
        Pattern      pattern    = Pattern.compile("((?:[a-zA-Z]+)|(?:\\d{1,2})).*?(\\d+)");
        List<String> secNumList = Arrays.asList(parcelleSecNums.split(","));
        
        ParcelleQO parcelleQO = new ParcelleQO();
        parcelleQO.setCodeINSEE(codeINSEE);
        
        secNumList.forEach(secNum -> {
            
            Matcher matcher = pattern.matcher(secNum);
            
            while (matcher.find()) {
                
                parcelleQO.getSecNums().add(new ParcelleQO.SecNum(matcher.group(1), matcher.group(2)));
            }
        });
        
        List<ParcelleDTO> parcelleDTOs = gestionParcelleFacade.rechercherAvecCritere(parcelleQO);
        
        if (parcelleDTOs.isEmpty()) { return null; }
        else { return parcelleDTOs; }
    }
    
    ParcelleDTO getParcelleDTO(String codeINSEE,
                               String codeParcelle) {
        
        String parcelleCode = getParcelleCode(codeINSEE, codeParcelle);
        
        ParcelleQO parcelleQO = new ParcelleQO();
        parcelleQO.setCodeINSEE(codeINSEE);
        parcelleQO.setCode(parcelleCode);
        
        return gestionParcelleFacade.rechercherResultatUniqueAvecCritere(parcelleQO);
    }
}
