package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.geolatte.geom.Geometry;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "peb")
public class PlanExpositionBruit extends AbstractEntity {
    
    @Column(name = "zone")
    String zone;
    
    @Column(name = "date_arret", columnDefinition = "DATE")
    Date dateArret;
    
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry<?> multiPolygon;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_peb")
    @SequenceGenerator(name = "seq_peb", sequenceName = "peb_id_seq", allocationSize = 1)
    private Long id;
    
    public String getCleFonc() {
        
        StringBuffer cleFonc = new StringBuffer();
        
        //TODO : Définir une clé fonctionnelle
        
        return cleFonc.toString().toUpperCase();
    }
    
    public Long getId() {
        
        if (this.id == null) { return 0L; }
        return this.id;
    }
}
