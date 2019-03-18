package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.geolatte.geom.Geometry;

@Entity
@Data
@Table(name = "adresse")
public class Adresse extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @Column(name = "numero")
    String numero;
    
    @Column(name = "rep")
    String complement;
    
    //    @Column(name = "nom_ld")
    //    String nom_ld;
    //    @Column(name = "x")
    //    String x;
    //    @Column(name = "y")
    //    String y;
    //    @Column(name = "lon")
    //    String lon;
    //    @Column(name = "lat")
    //    String lat;
    
    @Column(name = "id_ban")
    String idBAN;
    
    @JoinColumn(name = "adresse_rue_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Rue rue;
    
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry point;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_adresse")
    @SequenceGenerator(name = "seq_adresse", sequenceName = "adresse_column_1_seq", allocationSize = 1)
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
  