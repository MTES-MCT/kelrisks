package fr.gouv.beta.fabnum.commun.transverse.exception.metier;

public class EntityNotFoundException extends ServiceException {
    
    /**
     * Champ : long serialVersionUID :
     */
    private static final long serialVersionUID = 8622243185509795714L;
    
    
    public EntityNotFoundException(String pMsg) {
        
        super(pMsg);
    }
    
    public EntityNotFoundException(String pMsg, Throwable pThrowable) {
        
        super(pMsg, pThrowable);
    }
}
