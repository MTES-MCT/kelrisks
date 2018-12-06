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
@Table(name = "adresse")
public class Adresse extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @Column(name = "nom_voie")
    String nomVoie;
    //    @Column(name = "id_fantoir")
    //    String id_fantoir;
    @Column(name = "numero")
    String numero;
    //    @Column(name = "rep")
    //    String rep;
    @Column(name = "code_insee")
    String code_insee;
    @Column(name = "code_post")
    String code_post;
    //    @Column(name = "alias")
    //    String alias;
    @Column(name = "nom_ld")
    String nom_ld;
    @Column(name = "nom_afnor")
    String nom_afnor;
    @Column(name = "libelle_acheminement")
    String libelle_acheminement;
    //    @Column(name = "x")
    //    String x;
    //    @Column(name = "y")
    //    String y;
    //    @Column(name = "lon")
    //    String lon;
    //    @Column(name = "lat")
    //    String lat;
    @Column(name = "nom_commune")
    String nomCommune;
    @Column(name = "id_ban")
    String idBAN;
    
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
        
        if (this.id == null) { this.id = 0L; }
        return this.id;
    }
}
  