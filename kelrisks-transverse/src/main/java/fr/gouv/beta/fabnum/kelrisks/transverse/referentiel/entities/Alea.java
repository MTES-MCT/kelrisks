package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Table(name = "ref_alea")
public class Alea extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    @Column(name = "code_alea_gaspar")
    String codeGaspar;
    @Column(name = "code_alea")
    String code;
    @Column(name = "libelle_alea")
    String libelle;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_famille_alea")
    FamilleAlea familleAlea;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ref_alea")
    @SequenceGenerator(name = "seq_ref_alea", sequenceName = "ref_alea_id_seq", allocationSize = 1)
    private Long id;
    
    public Long getId() {
        
        if (this.id == null) { return 0L; }
        return this.id;
    }
}
  