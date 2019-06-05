package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.geolatte.geom.Geometry;

@Entity
@Data
@Table(name = "geog")
public class Geog extends AbstractEntity {
    
    static final long serialVersionUID = 16554L;
    
    @Id
    @Column(name = "geom", columnDefinition = "geometry")
    private Geometry geom;
    
    private Integer version;
    
    @Override
    public String getCleFonc() {
        
        return this.toString();
    }
    
    @Override
    public Long getId() {
        
        return 0L;
    }
}
  