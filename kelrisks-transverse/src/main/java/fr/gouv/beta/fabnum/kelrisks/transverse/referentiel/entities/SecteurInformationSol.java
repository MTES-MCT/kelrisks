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
@Table(name = "sis")
public class SecteurInformationSol extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @Column(name = "numero_affichage")
    String numero;
    @Column(name = "numero_basol")
    String numeroBasol;
    @Column(name = "adresse")
    String adresse;
    @Column(name = "lieu_dit")
    String lieuDit;
    @Column(name = "code_insee")
    String codeINSEE;
    @Column(name = "nom_commune")
    String nomCommune;
    @Column(name = "nom_departement")
    String nomDepartement;
    @Column(name = "surface_m2")
    Double surface;
    
    @Column(name = "geocoded_result_score")
    private Double   scoreGeocodage;
    @Column(name = "geog", columnDefinition = "org.geolatte.geom.Geometry")
    @Type(type = "org.geolatte.geom.Geometry")
    private Geometry multiPolygon;
    @Column(name = "geog_precision")
    private String   precision;
    @Column(name = "adresse_id")
    private String   adresseId;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sis")
    @SequenceGenerator(name = "seq_sis", sequenceName = "sis_id_seq", allocationSize = 1)
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