package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.transverse.qo.CritereTri;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ICommuneMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ICommuneService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QCommune;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.CommuneQO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionCommuneFacade extends AbstractFacade implements IGestionCommuneFacade {
    
    @Autowired
    ICommuneMapper   communeMapper;
    @Autowired
    IParcelleService parcelleService;
    @Autowired
    ICommuneService  communeService;
    
    @Override
    public List<CommuneDTO> rechercherCommunePartielle(String query) {
        
        CommuneQO communeQO = new CommuneQO();
        
        communeQO.setCommunePartielle(query);
    
        CritereTri critereTri = new CritereTri();
        critereTri.ajouteOrdre(QCommune.commune.nomCommune.asc());
    
        return communeMapper.toDTOs(communeService.rechercherAvecCritere(critereTri, communeQO));
    }
    
    @Override
    public CommuneDTO rechercherCommuneAvecCodeINSEE(String codeINSEE) {
        
        CommuneQO communeQO = new CommuneQO();
        
        communeQO.setCodeINSEE(codeINSEE);
        
        return communeMapper.toDTO(communeService.rechercherResultatUniqueAvecCritere(communeQO));
    }
}
