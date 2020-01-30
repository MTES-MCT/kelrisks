package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteIndustrielBasiasService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISiteIndustrielBasiasDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.SiteIndustrielBasiasDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
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
    public List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideGeometry(Geometry<?> geometry, double distance) {
    
        return dao.rechercherSiteDansRayonCentroideParcelle(geometry, distance / 100000D); // Conversion en mètres
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
        
        if (multiPolygon == null || multiPolygon.isEmpty()) { return new ArrayList<>(); }
        
        return dao.rechercherSitesDansPolygons(multiPolygon);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        if (polygon == null || polygon.isEmpty()) { return new ArrayList<>(); }
        
        return dao.rechercherSitesDansPolygon(polygon);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherParNomProprietaireDansRayonGeometry(Geometry<?> geometry, String nomProprietaire, double distance) {
        
        return dao.rechercherParNomProprietaireDansRayonGeometry(geometry, nomProprietaire, distance / 100000D);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherRaisonsSociales(String codeINSEE, String query) {
    
        return dao.rechercherRaisonsSociales(codeINSEE, query);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSitesSurParcelles(List<String> codes) {
        
        return dao.rechercherSitesSurParcelles(codes);
    }
}
  