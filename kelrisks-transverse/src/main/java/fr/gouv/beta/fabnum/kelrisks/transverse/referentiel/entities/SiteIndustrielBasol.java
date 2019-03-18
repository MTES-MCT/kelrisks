package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;

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
@Table(name = "basol")
public class SiteIndustrielBasol extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @Column(name = "numerosite")
    String numerosite;
    @Column(name = "numerobasol")
    String numerobasol;
    @Column(name = "numeros3ic")
    String numeros3ic;
    @Column(name = "identifiantbasias")
    String identifiantbasias;
    //    @Column(name = "georeferencement")
    //    String georeferencement;
    //    @Column(name = "coordxlambertii")
    //    String coordxlambertii;
    //    @Column(name = "coordylambertii")
    //    String coordylambertii;
    //    @Column(name = "precision")
    //    String precision;
    @Column(name = "adresse")
    String adresse;
    @Column(name = "commune")
    String commune;
    @Column(name = "codeinsee")
    String codeinsee;
    @Column(name = "codepostal")
    String codepostal;
    @Column(name = "proprietaire")
    String proprietaire;
    @Column(name = "responsable")
    String responsable;
    
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry point;
    
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
  