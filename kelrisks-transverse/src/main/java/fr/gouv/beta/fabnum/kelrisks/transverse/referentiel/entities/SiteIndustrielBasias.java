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

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "basias")
public class SiteIndustrielBasias extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @Column(name = "indice_departemental")
    private String      identifiant;
    private String      adresse;
    @Column(name = "numero_insee")
    private String      codeINSEE;
    @Column(name = "raison_sociale")
    private String      raisonSociale;
    @Column(name = "geog", columnDefinition = "org.geolatte.geom.Geometry")
    @Type(type = "org.geolatte.geom.Geometry")
    private Geometry<?> point;
    @Column(name = "precision_adresse")
    private String      precision;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_siteIndustriel")
    @SequenceGenerator(name = "seq_siteIndustriel", sequenceName = "basias_id_seq", allocationSize = 1)
    private Long id;
    
    public Long getId() {
        
        if (this.id == null) { return 0L; }
        return this.id;
    }
    
    public String getCleFonc() {
        
        StringBuffer cleFonc = new StringBuffer();
        
        //TODO : Définir une clé fonctionnelle
        
        return cleFonc.toString().toUpperCase();
    }
}
  