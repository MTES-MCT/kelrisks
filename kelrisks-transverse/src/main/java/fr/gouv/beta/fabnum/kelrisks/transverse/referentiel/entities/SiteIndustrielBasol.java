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
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "basol")
public class SiteIndustrielBasol extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @NaturalId
    @Column(name = "numerobasol")
    private String numerobasol;
    @Column(name = "identifiantbasias")
    private String identifiantbasias;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "code_insee")
    private String codeINSEE;
    @Column(name = "proprietaire")
    private String proprietaire;
    
    @Column(name = "geog", columnDefinition = "org.geolatte.geom.Geometry")
    @Type(type = "org.geolatte.geom.Geometry")
    private Geometry<?> point;
    @Column(name = "l2e_precision")
    private String      precision;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_siteIndustrielBasol")
    @SequenceGenerator(name = "seq_siteIndustrielBasol", sequenceName = "basol_id_seq", allocationSize = 1)
    private Long id;
    
    public Long getId() {
        
        if (this.id == null) { return 0L; }
        return this.id;
    }
}
  