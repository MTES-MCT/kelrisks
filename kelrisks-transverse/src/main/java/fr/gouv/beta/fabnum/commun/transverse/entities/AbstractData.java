package fr.gouv.beta.fabnum.commun.transverse.entities;

import lombok.Data;

/**
 * Description de la classe : Classe ancêtre de tous les ValueObjects
 * Date de (re)-création : 29 août 07
 *
 * @author CharlesA
 * @version : 1.1 : passage Id et version en Integer
 */

@Data
public abstract class AbstractData implements java.io.Serializable {
    
    
    /**
     *
     */
    private static final long serialVersionUID = 5816277318988689252L;
    
    /**
     * Champ : boolean aSupprimer :
     * L'objet devra être supprimé
     */
    private boolean aSupprimer;
    
    /**
     * Champ : boolean isNew :
     * L'objet est nouveau
     */
    private boolean isNew;
    
    /**
     * Champ : Long identifiant :
     * Identifiant Hibernate de l'objet
     */
    private Long identifiant;
    
    /**
     * Champ : Long version : Pour locks optimistes hibernate
     * Insérer la description du champ
     */
    private Integer version;
    
    /**
     * Constructeur de AbstractData
     */
    protected AbstractData() {
        
        super();
    }
    
    /**
     * Méthode getId
     *
     * @return : Long identifiant : l'identifiant de l'objet
     */
    public Long getId() {
        
        if (this.identifiant == null) {
            this.identifiant = new Long(0);
        }
        return this.identifiant;
    }
    
    /**
     * Méthode setId
     */
    public void setId(Long pId) {
        
        this.identifiant = pId;
    }
    
    /**
     * Getter pour le champ supprimer :
     *
     * @return la valeur de supprimer
     */
    public boolean isASupprimer() {
        
        return aSupprimer;
    }
    
    /**
     * Setter pour le champ supprimer
     *
     * @param supprimer: la valeur du champ supprimer à placer
     */
    public void setASupprimer(boolean supprimer) {
        
        this.aSupprimer = supprimer;
    }
    
    /**
     * Getter pour le champ isNew :
     *
     * @return la valeur de isNew
     */
    public boolean isNew() {
        
        return isNew;
    }
    
    /**
     * Setter pour le champ isNew
     *
     * @param isNew: la valeur du champ isNew à placer
     */
    public void setNew(boolean isNew) {
        
        this.isNew = isNew;
    }
}
