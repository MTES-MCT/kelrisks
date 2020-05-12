package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.Collections;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ParcelleRepositoryTest extends AbstractRepositoryTest {
    
    @Autowired
    ParcelleRepository parcelleRepository;
    
    @Test
    public void doitRetournerUneGeometrie() {
        
        Parcelle parcelle = parcelleRepository.getOne(311762L);
        
        assertThat(parcelle).isNotNull();
        
        String union = parcelleRepository.rechercherUnionParcelles(Collections.singletonList(39863887L));
        
        assertThat(union).isNotNull();
    }
}