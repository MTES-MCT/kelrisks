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

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "argile")
public class Argile extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    @Column(name = "departement")
    String numeroDepartement;
    @Column(name = "niveau_alea")
    int    niveauAlea;
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry<?> multiPolygon;
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_argile")
    @SequenceGenerator(name = "seq_argile", sequenceName = "argile_id_seq", allocationSize = 1)
    private Long        id;
    
    public Long getId() {
        
        if (this.id == null) { return 0L; }
        return this.id;
    }
}
  