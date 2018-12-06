package fr.gouv.beta.fabnum.commun.transverse.exception.metier;

public class LockOptimisteException extends ServiceException {
    
    /**
     * Champ : long serialVersionUID :
     */
    private static final long serialVersionUID = 8622243185509795714L;
    
    
    public LockOptimisteException(String pMsg) {
        
        super(pMsg);
    }
    
    public LockOptimisteException(String pMsg, Throwable pThrowable) {
        
        super(pMsg, pThrowable);
    }
}
