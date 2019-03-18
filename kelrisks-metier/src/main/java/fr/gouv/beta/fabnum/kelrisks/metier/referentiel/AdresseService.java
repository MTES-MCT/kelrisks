package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAdresseService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IAdresseDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.AdresseDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité Adresse
 */

@Service("adresseService")
public class AdresseService extends AbstractCRUDService<Adresse> implements IAdresseService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IAdresseDAO dao;
    
    @Autowired
    public AdresseService(@Qualifier("adresseDAO") final AdresseDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<Adresse> rechercherAdresseDansGeometry(Geometry geometry) {
        
        return dao.rechercherAdresseDansGeometry(geometry);
    }
    
    @Override
    public List<Adresse> rechercherVoiePartielle(String codePostal, String query) {
        
        String codePostalQuery = "";
        
        for (String s : codePostal.split(" ")) {
            codePostalQuery += s;
        }
        
        return dao.rechercherVoiePartielle(codePostal, query);
    }
}
  