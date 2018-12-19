package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IParcelleMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAdresseService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.AdresseQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionParcelleFacade extends AbstractFacade implements IGestionParcelleFacade {
    
    @Autowired
    IParcelleMapper  parcelleMapper;
    @Autowired
    IParcelleService parcelleService;
    @Autowired
    IAdresseService  adresseService;
    
    @Override
    public ParcelleDTO rechercherParcelleAvecAdresse(String codeINSEE, String rue, String numero) {
        
        AdresseQO adresseQO = new AdresseQO();
        adresseQO.setCodeINSEE(codeINSEE);
        adresseQO.setNomVoie(rue);
        adresseQO.setNumero(numero);
        
        List<Adresse> adresses = adresseService.rechercherAvecCritere(adresseQO);
        
        if (adresses.isEmpty()) {return null;} //TODO
        if (adresses.size() > 1) {return null;} // TODO
        
        Adresse adresse = adresses.get(0);
        
        Parcelle parcelle = parcelleService.rechercherParcelleContenantPoint(adresse.getPoint());
        
        return parcelleMapper.toDTO(parcelle);
    }
    
    @Override
    public ParcelleDTO rechercherResultatUniqueAvecCritere(ParcelleQO parcelleQO) {
        
        ParcelleDTO parcelleDTO = parcelleMapper.toDTO(parcelleService.rechercherResultatUniqueAvecCritere(parcelleQO));
        
        return parcelleDTO;
    }
    
    @Override
    public List<ParcelleDTO> rechercherAvecCritere(ParcelleQO parcelleQO) {
        
        List<ParcelleDTO> parcelleDTOs = parcelleMapper.toDTOs(parcelleService.rechercherAvecCritere(parcelleQO));
        
        return parcelleDTOs;
    }
    
    @Override
    public ParcelleDTO rechercherParcelleAvecIdBan(String idBAN) {
        
        AdresseQO adresseQO = new AdresseQO();
        adresseQO.setIdBan(idBAN);
        
        Adresse  adresse  = adresseService.rechercherResultatUniqueAvecCritere(adresseQO);
        Parcelle parcelle = parcelleService.rechercherParcelleContenantPoint(adresse.getPoint());
        
        return parcelleMapper.toDTO(parcelle);
    }
}
