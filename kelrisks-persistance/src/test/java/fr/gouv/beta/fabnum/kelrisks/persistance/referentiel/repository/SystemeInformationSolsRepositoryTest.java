package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SystemeInformationSols;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SystemeInformationSolsRepositoryTest {
    
    //    @Autowired
    //    TestEntityManager                entityManager;
    @Autowired
    SystemeInformationSolsRepository systemeInformationSolsRepository;
    
    @Test
    public void doitRetournerUnSystemeInformationSols() {
        
        List<SystemeInformationSols> systemeInformationSols = systemeInformationSolsRepository.rechercherZoneContenantPolygon(null);
        
        assertThat(systemeInformationSols).hasSize(1);
    }
    
    @Test
    public void neDoitRetournerAucunSystemeInformationSols() {
        
        List<SystemeInformationSols> systemeInformationSols = systemeInformationSolsRepository.rechercherZoneContenantPolygon(null);
        
        assertThat(systemeInformationSols).isEmpty();
    }
}