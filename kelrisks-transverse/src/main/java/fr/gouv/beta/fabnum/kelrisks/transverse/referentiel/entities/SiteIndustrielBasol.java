package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.geolatte.geom.Geometry;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "basol")
public class SiteIndustrielBasol extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @NaturalId
    @Column(name = "numerobasol")
    private String   numerobasol;
    @Column(name = "identifiantbasias")
    private String   identifiantbasias;
    @Column(name = "adresse")
    private String   adresse;
    @Column(name = "commune")
    private String   commune;
    @Column(name = "code_insee")
    private String   codeinsee;
    @Column(name = "proprietaire")
    private String   proprietaire;
    @Column(name = "geocoded_score")
    private Float    scoreGeocodage;
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry point;
    @Column(name = "geocoded_geog", columnDefinition = "geometry")
    private Geometry pointGeocode;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "siteIndustrielBasol", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<SiteIndustrielBasolParcelle> parcelles = new HashSet<>();
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_siteIndustrielBasol")
    @SequenceGenerator(name = "seq_siteIndustrielBasol", sequenceName = "basol_id_seq", allocationSize = 1)
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
  