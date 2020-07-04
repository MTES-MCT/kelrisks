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
import org.hibernate.annotations.Type;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "s3ic")
public class InstallationClassee extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    private String code;
    private String nom;
    private String regime;
    private String commune;
    @Column(name = "code_insee")
    private String codeInsee;
    @Column(name = "code_postal")
    private String codePostal;
    private String adresse;
    @Column(name = "complement_adresse")
    private String complementAdresse;
    
    @Column(name = "geog", columnDefinition = "org.geolatte.geom.Geometry")
    @Type(type = "org.geolatte.geom.Geometry")
    private Geometry<?> multiPolygon;
    @Column(name = "geog_precision")
    private String      precision;
    @Column(name = "adresse_id")
    private String   adresseId;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_s3ic")
    @SequenceGenerator(name = "seq_s3ic", sequenceName = "s3ic_id_seq", allocationSize = 1)
    private Long id;
    
    public Long getId() {
    
        if (this.id == null) { return 0L; }
        return this.id;
    }
}