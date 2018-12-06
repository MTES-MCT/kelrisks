package fr.gouv.beta.fabnum.commun.transverse.entities;


import lombok.Data;

/**
 * Description de la classe : Génération de couplet identifiant, version
 * Date de (re)-création : 10 septembre 2008
 *
 * @author RibesaA
 */
@Data
public class IdentifiantVersion {
    
    
    long identifiant;
    int  version;
    
    public IdentifiantVersion(long identifiant, int version) {
        
        this.identifiant = identifiant;
        this.version = version;
    }
}