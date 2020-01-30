package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IInstallationClasseeService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IInstallationClasseeDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.InstallationClasseeDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité InstallationClassee
 */

@Service("installationClasseeService")
public class InstallationClasseeService extends AbstractCRUDService<InstallationClassee> implements IInstallationClasseeService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IInstallationClasseeDAO dao;
    
    @Autowired
    public InstallationClasseeService(@Qualifier("installationClasseeDAO") final InstallationClasseeDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<InstallationClassee> rechercherInstallationsSurParcelle(String codeParcelle) {
        
        return dao.rechercherInstallationsSurParcelle(codeParcelle);
    }
    
    @Override
    public List<InstallationClassee> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance) {
        
        return dao.rechercherInstallationsDansRayonCentroideParcelle(codeParcelle, distance / 100000D); // Conversion en mètres
    }
    
    @Override
    public List<InstallationClassee> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon == null || multiPolygon.isEmpty()) { return new ArrayList<>(); }
    
        return dao.rechercherSitesDansPolygons(multiPolygon);
    }
    
    @Override
    public List<InstallationClassee> rechercherInstallationsSurParcelles(List<String> codes) {
        
        return dao.rechercherInstallationsSurParcelles(codes);
    }
    
    @Override
    public List<InstallationClassee> rechercherRaisonsSociales(String codeINSEE, String query) {
        
        return dao.rechercherRaisonsSociales(codeINSEE, query);
    }
    
    @Override
    public List<InstallationClassee> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        return dao.rechercherSitesDansPolygon(polygon);
    }
}
  