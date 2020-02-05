package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InstallationClasseeRepositoryTest extends AbstractRepositoryTest {
    
    @Autowired
    InstallationClasseeRepository installationClasseeRepository;
    
    @Test
    public void unPolygonDoitRetournerUneInstallationClassees() {
        
        Parcelle parcelle = parcelleRepository.getOne(2662371L);
        
        assertThat(parcelle).isNotNull();
        
        List<InstallationClassee> InstallationClassees = installationClasseeRepository.rechercherSitesDansPolygon(parcelle.getMultiPolygon());
        
        assertThat(InstallationClassees).hasSize(1);
    }
    
    @Test
    public void desPolygonsDoiventRetournerUneInstallationClassees() {
        
        List<Geometry<?>> polygons = new ArrayList<>();
        
        polygons.add(parcelleRepository.getOne(2662371L).getMultiPolygon()); // match
        polygons.add(parcelleRepository.getOne(311762L).getMultiPolygon());
        //        TODO : Fix problème avec 3 polygons
        //        polygons.add(parcelleRepository.getOne(1045743L).getMultiPolygon());
        
        List<InstallationClassee> InstallationClassees = installationClasseeRepository.rechercherSitesDansPolygons(polygons);
        
        assertThat(InstallationClassees).hasSize(1);
    }
    
    @Test
    public void neDoitRetournerAucuneInstallationClassees() {
        
        Parcelle parcelle = parcelleRepository.getOne(311762L);
        
        assertThat(parcelle).isNotNull();
        
        List<InstallationClassee> InstallationClassees = installationClasseeRepository.rechercherSitesDansPolygon(parcelle.getMultiPolygon());
        
        assertThat(InstallationClassees).isEmpty();
    }
}