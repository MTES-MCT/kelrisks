package fr.gouv.beta.fabnum.commun.transverse.entities;

import fr.gouv.beta.fabnum.commun.transverse.exception.metier.LockOptimisteException;
import fr.gouv.beta.fabnum.commun.utils.string.StringUtils;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements java.io.Serializable {
    
    private static final long serialVersionUID = 5816277318988689252L;
    
    /**
     * Champ : Long version : Pour locks optimistes JPA
     * Insérer la description du champ
     */
    @Version
    private Integer version;
    
    /**
     * Constructeur de AbstractData
     */
    protected AbstractEntity() {
        
        super();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        
        if (this == other) { return true; }
        if (!(other.getClass().equals(this.getClass()))) { return false; }
        AbstractEntity data = (AbstractEntity) other;
        
        return false;
    }
    
    @Override
    public String toString() {
        
        return StringUtils.toStringBuilder(this);
    }
    
    /**
     * Fonction utilitaire permettant d'obtenir une clé identifiant une entite
     * Il s'agit de la concaténation du nom de la classe et de son id
     */
    public String calculCle() {
        
        return this.getClass().toString() + this.getId();
    }
    
    /**
     * Permet de vérifier si la version à changé depuis la précédente requête.
     * Il faut donc pour cela avoir rechargé l'objet depuis la base.
     * <p>
     * On fait ceci car la gestion de version d'hibernate ne gère pas ce cas. Il gère seulement le problème de lock
     * dans la même transaction
     */
    public void checkVersion(int version) throws LockOptimisteException {
        
        if (this.version != version) {
            throw new LockOptimisteException("Problème de version dans l'entité : " + this.getClass().getCanonicalName());
        }
    }
    
    public abstract Long getId();
    
    /**
     * Getter pour le champ version :
     *
     * @return la valeur de version
     */
    public Integer getVersion() {
        
        return (version != null ? version : 0);
    }
    
    /**
     * Setter pour le champ version
     *
     * @param version: la valeur du champ version à placer
     */
    public void setVersion(Integer version) {
        
        this.version = version;
    }
}
