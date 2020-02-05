package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanPreventionRisquesService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IPlanPreventionRisquesDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.PlanPreventionRisquesDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité PlanPreventionRisques
 */

@Service("planPreventionRisquesService")
public class PlanPreventionRisquesService extends AbstractCRUDService<PlanPreventionRisques> implements IPlanPreventionRisquesService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IPlanPreventionRisquesDAO dao;
    
    @Autowired
    public PlanPreventionRisquesService(@Qualifier("planPreventionRisquesDAO") final PlanPreventionRisquesDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<PlanPreventionRisques> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon == null || multiPolygon.isEmpty()) { return new ArrayList<>(); }
    
        return dao.rechercherSitesDansPolygons(multiPolygon);
    }
    
    @Override
    public List<PlanPreventionRisques> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        if (polygon == null || polygon.isEmpty()) { return new ArrayList<>(); }
        
        return dao.rechercherSitesDansPolygon(polygon);
    }
}
  