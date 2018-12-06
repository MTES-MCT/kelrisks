package fr.gouv.beta.fabnum.commun.transverse.entities;

import lombok.Data;

/**
 *
 */
@Data
public class HabilitationCerbere implements java.io.Serializable {
    
    private static final long serialVersionUID = 3257568390934574898L;
    
    private String nomProfil;
    private String restriction;
    private String portee;
    
    public HabilitationCerbere() {
        
        super();
    }
}
