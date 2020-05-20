package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;

import java.util.List;

import org.geolatte.geom.Point;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanExpositionBruitRepositoryTest extends AbstractRepositoryTest {
    
    static final String AEROPORT_CDG = "{\"geometry\": {" +
                                       " \"type\": \"Point\"," +
                                       " \"coordinates\": [2.53986270, 49.02230650]" +
                                       "}}";
    @Autowired
    PlanExpositionBruitRepository planExpositionBruitRepository;
    
    @Test
    public void doitRetournerUneZoneA() {
        
        String zone = planExpositionBruitRepository.rechercherZoneCentroid((Point<?>) GeoJsonUtils.fromGeoJson(AEROPORT_CDG));
        
        assertThat(zone).isEqualTo("A");
    }
    
    @Test
    public void doitRetournerDeuxPlans() {
        
        List<PlanExpositionBruit> planExpositionBruits = planExpositionBruitRepository.rechercherPlanExpositionBruitDansRayon((Point<?>) GeoJsonUtils.fromGeoJson(AEROPORT_CDG),
                                                                                                                              475d);
        
        assertThat(planExpositionBruits).hasSize(2);
    }
}