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

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "s3ic")
public class InstallationClassee extends AbstractEntity {
    
    static final long serialVersionUID = 1L;
    
    private String  code;
    private String  nom;
    @Column(name = "raison_sociale")
    private String  raisonSociale;
    @Column(name = "etat_activite")
    private String  etatActivite;
    private String  regime;
    private String  commune;
    @Column(name = "code_insee")
    private String  codeInsee;
    @Column(name = "code_postal")
    private String  codePostal;
    private String  adresse;
    @Column(name = "complement_adresse")
    private String  complementAdresse;
    private String  departement;
    @Column(name = "centroide_commune")
    private boolean centroideCommune;
    
    @Column(name = "geog", columnDefinition = "geometry")
    private Geometry point;
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_s3ic")
    @SequenceGenerator(name = "seq_s3ic", sequenceName = "s3ic_id_seq", allocationSize = 1)
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