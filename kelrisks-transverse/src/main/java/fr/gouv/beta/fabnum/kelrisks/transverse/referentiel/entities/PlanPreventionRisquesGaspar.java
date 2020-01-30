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

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "gaspar")
public class PlanPreventionRisquesGaspar extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    String idGaspar;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_ref_categorie_risque")
    CategorieRisque categorieRisque;
    
    @Column(name = "num_risque")
    String numRisque;
    
    @Column(name = "libelle_risque")
    String libelleRisque;
    
    @Column(name = "code_insee")
    String codeINSEE;
    
    @Column(name = "date_prescription")
    Date datePrescription;
    
    @Column(name = "date_application_anticipee")
    Date dateApplicationAnticipee;
    
    @Column(name = "date_deprescription")
    Date dateDeprescription;
    
    @Column(name = "date_approbation")
    Date dateApprobation;
    
    @Column(name = "date_abrogation")
    Date dateAbrogation;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gaspar")
    @SequenceGenerator(name = "seq_gaspar", sequenceName = "gaspar_id_seq", allocationSize = 1)
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
  