package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.geolatte.geom.Geometry;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "ssp")
public class SiteSolPolue extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    private String      nom;
    private String      description;
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry<?> multiPolygon;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ssp")
    @SequenceGenerator(name = "seq_ssp", sequenceName = "ssp_id_seq", allocationSize = 1)
    private Long id;
    
    public Long getId() {
    
        if (this.id == null) { return 0L; }
        return this.id;
    }
}