package fr.gouv.beta.fabnum.commun.transverse.exception.metier;

public class AccessForbiddenException extends RegleGestionException {
    
    
    private static final long serialVersionUID = -7434634674097104536L;
    
    /**
     * Champ : long serialVersionUID :
     * Insérer la description du champ
     */
    
    
    public AccessForbiddenException(String pMsg) {
        
        super(pMsg);
    }
    
    public AccessForbiddenException(String pMsg, Throwable pThrowable) {
        
        super(pMsg, pThrowable);
    }
}
