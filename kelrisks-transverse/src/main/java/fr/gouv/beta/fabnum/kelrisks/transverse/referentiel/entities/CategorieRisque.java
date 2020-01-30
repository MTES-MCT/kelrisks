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

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "ref_categorie_risque")
public class CategorieRisque extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    String code;
    String libelle;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ref_categorie_risque")
    @SequenceGenerator(name = "seq_ref_categorie_risque", sequenceName = "ref_categorie_risque_id_seq", allocationSize = 1)
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
  