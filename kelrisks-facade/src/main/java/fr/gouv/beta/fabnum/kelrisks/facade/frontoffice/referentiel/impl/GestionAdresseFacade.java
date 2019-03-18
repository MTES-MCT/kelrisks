package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IAdresseMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAdresseService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.AdresseQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.CommuneQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionAdresseFacade extends AbstractFacade implements IGestionAdresseFacade {
    
    @Autowired
    IAdresseMapper   adresseMapper;
    @Autowired
    IParcelleService parcelleService;
    @Autowired
    IAdresseService  adresseService;
    
    @Override
    public AdresseDTO rechercherAdresseAvecParcelle(String codeParcelle) {
        
        ParcelleQO parcelleQO = new ParcelleQO();
        
        parcelleQO.setCode(codeParcelle);
        
        List<Parcelle> parcelles = parcelleService.rechercherAvecCritere(parcelleQO);
        
        if (parcelles.isEmpty()) {return null;} //TODO
        if (parcelles.size() > 1) {return null;} // TODO
        
        List<Adresse> adresses = adresseService.rechercherAdresseDansGeometry(parcelles.get(0).getMultiPolygon());
        
        if (adresses.isEmpty()) {return null;} //TODO
        if (adresses.size() > 1) {return null;} // TODO
        
        return adresseMapper.toDTO(adresses.get(0));
    }
    
    @Override
    public List<AutocompleteDTO> rechercherRuePartielle(String codeINSEE, String query) {
        
        return adresseMapper.toRueAutoCompleteDTOs(adresseService.rechercherVoiePartielle(codeINSEE, query));
    }
    
    @Override
    public List<AutocompleteDTO> rechercherNumeroPartiel(String codeINSEE, String nomVoie, String numero) {
        
        AdresseQO adresseQO = new AdresseQO();
        adresseQO.setNomVoie(nomVoie);
        adresseQO.setNumero(numero);
    
        CommuneQO communeQO = new CommuneQO();
        communeQO.setCodeINSEE(codeINSEE);
    
        return adresseMapper.toNumeroVoieAutoCompleteDTOs(adresseService.rechercherAvecCritere(adresseQO, communeQO));
    }
    
    @Override
    public String rechercherCodeINSEE(String codePostal) {
        
        AdresseQO adresseQO = new AdresseQO();
    
        CommuneQO communeQO = new CommuneQO();
        communeQO.setCodePostal(codePostal);
    
        List<Adresse> adresses = adresseService.rechercherAvecCriterePagination(0, 1, adresseQO, adresseQO);
    
        return adresseMapper.toDTO(adresses.get(0)).getCommune().getCodeINSEE();
    }
    
    @Override
    public AdresseDTO rechercherAdresseIdBan(String idban) {
        
        AdresseQO adresseQO = new AdresseQO();
        adresseQO.setIdBan(idban);
        
        Adresse adresse = adresseService.rechercherResultatUniqueAvecCritere(adresseQO);
        
        return adresseMapper.toDTO(adresse);
    }
}
