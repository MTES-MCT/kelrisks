package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SiteIndustrielBasolRepositoryTest extends AbstractRepositoryTest {
    
    @Autowired
    SiteIndustrielBasolRepository siteIndustrielBasolRepository;
    
    @Test
    public void unPolygonDoitRetournerUnSiteIndustrielBasol() {
        
        Parcelle parcelle = parcelleRepository.getOne(2475428L);
        
        assertThat(parcelle).isNotNull();
        
        List<SiteIndustrielBasol> SiteIndustrielBasols = siteIndustrielBasolRepository.rechercherSitesDansPolygon(parcelle.getMultiPolygon());
        
        assertThat(SiteIndustrielBasols).hasSize(1);
    }
    
    @Test
    public void desPolygonsDoiventRetournerUnSiteIndustrielBasol() {
        
        List<Geometry<?>> polygons = new ArrayList<>();
        
        polygons.add(parcelleRepository.getOne(2475428L).getMultiPolygon()); // match
        polygons.add(parcelleRepository.getOne(311762L).getMultiPolygon());
        //        TODO : Fix problème avec 3 polygons
        //        polygons.add(parcelleRepository.getOne(2662371L).getMultiPolygon());
        
        List<SiteIndustrielBasol> SiteIndustrielBasols = siteIndustrielBasolRepository.rechercherSitesDansPolygons(polygons);
        
        assertThat(SiteIndustrielBasols).hasSize(1);
    }
    
    @Test
    public void neDoitRetournerAucunSitesIndustrielsBasol() {
        
        Parcelle parcelle = parcelleRepository.getOne(311762L);
        
        assertThat(parcelle).isNotNull();
        
        List<SiteIndustrielBasol> SiteIndustrielBasols = siteIndustrielBasolRepository.rechercherSitesDansPolygon(parcelle.getMultiPolygon());
        
        assertThat(SiteIndustrielBasols).isEmpty();
    }
}