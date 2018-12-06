package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteIndustrielBasiasService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISiteIndustrielBasiasDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.SiteIndustrielBasiasDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité SiteIndustrielBasias
 */

@Service("siteIndustrielBasiasService")
public class SiteIndustrielBasiasService extends AbstractCRUDService<SiteIndustrielBasias> implements ISiteIndustrielBasiasService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private ISiteIndustrielBasiasDAO dao;
    
    @Autowired
    public SiteIndustrielBasiasService(@Qualifier("siteIndustrielBasiasDAO") final SiteIndustrielBasiasDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSiteSurParcelle(String codeParcelle) {
        
        return dao.rechercherSiteSurParcelle(codeParcelle);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance) {
        
        return dao.rechercherSiteDansRayonCentroideParcelle(codeParcelle, distance);
    }
}
  