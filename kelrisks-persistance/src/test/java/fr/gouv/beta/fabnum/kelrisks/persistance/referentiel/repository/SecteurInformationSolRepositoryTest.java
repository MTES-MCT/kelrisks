package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SecteurInformationSolRepositoryTest extends AbstractRepositoryTest {
    
    @Autowired
    SecteurInformationSolRepository secteurInformationSolRepository;
    
    @Test
    public void unPolygonDoitRetournerUnSecteurInformationSols() {
    
        Parcelle parcelle = parcelleRepository.getOne(1045474L);
    
        assertThat(parcelle).isNotNull();
    
        List<SecteurInformationSol> SecteurInformationSols = secteurInformationSolRepository.rechercherSecteursDansPolygon(parcelle.getMultiPolygon());
    
        assertThat(SecteurInformationSols).hasSize(1);
    }
    
    @Test
    public void desPolygonsDoiventRetournerUnSecteurInformationSols() {
    
        List<Geometry<?>> polygons = new ArrayList<>();
    
        polygons.add(parcelleRepository.getOne(1045474L).getMultiPolygon()); // match
        polygons.add(parcelleRepository.getOne(311762L).getMultiPolygon());
        //        TODO : Fix problème avec 3 polygons
        //        polygons.add(parcelleRepository.getOne(2662371L).getMultiPolygon());
    
        List<SecteurInformationSol> SecteurInformationSols = secteurInformationSolRepository.rechercherSecteursDansPolygons(polygons);
        
        assertThat(SecteurInformationSols).hasSize(1);
    }
    
    @Test
    public void neDoitRetournerAucunSecteurInformationSols() {
    
        Parcelle parcelle = parcelleRepository.getOne(311762L);
    
        assertThat(parcelle).isNotNull();
    
        List<SecteurInformationSol> SecteurInformationSols = secteurInformationSolRepository.rechercherSecteursDansPolygon(parcelle.getMultiPolygon());
    
        assertThat(SecteurInformationSols).isEmpty();
    }
}