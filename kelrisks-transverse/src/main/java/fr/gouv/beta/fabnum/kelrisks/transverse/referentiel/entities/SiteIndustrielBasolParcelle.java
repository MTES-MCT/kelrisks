package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false, exclude = {"siteIndustrielBasol"})
@Table(name = "basol_parcelle")
public class SiteIndustrielBasolParcelle extends AbstractEntity {
    
    @Column(name = "numerobasol")
    String numeroBasol;
    @Column(name = "commune")
    String codeINSEE;
    @Column(name = "section")
    String section;
    @Column(name = "numero")
    String numero;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numerobasol", referencedColumnName = "numerobasol", insertable = false, updatable = false)
    private SiteIndustrielBasol siteIndustrielBasol;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_SiteIndustrielBasolParcelle")
    @SequenceGenerator(name = "seq_SiteIndustrielBasolParcelle", sequenceName = "basol_parcelle_id_seq", allocationSize = 1)
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
