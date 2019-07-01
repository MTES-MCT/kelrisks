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
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.CommuneQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.RueQO;

import java.util.List;

import org.geolatte.geom.Geometry;
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
        adresseQO.setNumero(numero);
    
        RueQO rueQO = new RueQO();
        rueQO.setNomVoie(rue);
        
        CommuneQO communeQO = new CommuneQO();
        communeQO.setCodeINSEE(codeINSEE);
    
        List<Adresse> adresses = adresseService.rechercherAvecCritere(adresseQO, communeQO);
        
        if (adresses.isEmpty()) {return null;} //TODO
        if (adresses.size() > 1) {return null;} // TODO
        
        Adresse adresse = adresses.get(0);
    
        Parcelle parcelle = parcelleService.rechercherClosestParcelleAvecPoint(adresse.getPoint());
        
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
        Parcelle parcelle = parcelleService.rechercherClosestParcelleAvecPoint(adresse.getPoint());
        
        return parcelleMapper.toDTO(parcelle);
    }
    
    @Override
    public List<ParcelleDTO> rechercherParcellesContigues(Geometry geom) {
        
        return parcelleMapper.toDTOs(parcelleService.rechercherParcellesContigues(geom));
    }
    
    @Override
    public ParcelleDTO rechercherParcelleAvecCoordonnees(double x, double y) {
    
        ParcelleDTO parcelleDTO = parcelleMapper.toDTO(parcelleService.rechercherClosestParcelleAvecCoordonnees(x, y));
        
        return parcelleDTO;
    }
    
    @Override
    public Geometry rechercherExpendedParcelle(String code, double distance) {
        
        return parcelleService.rechercherExpendedParcelle(code, distance / 100000D);
    }
    
    @Override
    public Geometry rechercherUnionParcellesContigues(Geometry polygon) {
        
        return parcelleService.rechercherUnionParcellesContigues(polygon);
    }
}
