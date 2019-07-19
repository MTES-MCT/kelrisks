package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
@EqualsAndHashCode(callSuper = false)
@Table(name = "ppr")
public class PlanPreventionRisques extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    Geometry geog;
    
    @Column(name = "id_gaspar")
    String idGaspar;
    
    @Column(name = "date_validite", columnDefinition = "DATE")
    Date dateValidite;
    
    String libelle;
    
    String titre;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "categorie_ppr")
    CategoriePPR categorie;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ppr")
    @SequenceGenerator(name = "seq_ppr", sequenceName = "ppr_id_seq", allocationSize = 1)
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
  