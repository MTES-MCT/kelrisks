package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanPreventionRisquesRepositoryTest extends AbstractRepositoryTest {
    
    @Autowired
    PlanPreventionRisquesRepository planPreventionRisquesRepository;
    
    @Test
    public void doitRetournerUnPlanPreventionRisques() {
        
        Parcelle parcelle = parcelleRepository.getOne(2687610L);
        
        assertThat(parcelle).isNotNull();
        
        List<PlanPreventionRisques> planPreventionRisques = planPreventionRisquesRepository.rechercherSitesDansPolygon(parcelle.getMultiPolygon());
        
        assertThat(planPreventionRisques).hasSize(1);
    }
    
    @Test
    public void neDoitRetournerAucunPlanPreventionRisques() {
        
        Parcelle parcelle = parcelleRepository.getOne(311762L);
        
        assertThat(parcelle).isNotNull();
        
        List<PlanPreventionRisques> planPreventionRisques = planPreventionRisquesRepository.rechercherSitesDansPolygon(parcelle.getMultiPolygon());
        
        assertThat(planPreventionRisques).isEmpty();
    }
}