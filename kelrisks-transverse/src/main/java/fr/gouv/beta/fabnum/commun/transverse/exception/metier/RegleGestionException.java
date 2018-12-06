package fr.gouv.beta.fabnum.commun.transverse.exception.metier;

public class RegleGestionException extends ServiceException {
    
    private static final long serialVersionUID = 8622243185509795714L;
    
    public RegleGestionException(String pMsg) {
        
        super(pMsg);
    }
    
    public RegleGestionException(String pMsg, Throwable pThrowable) {
        
        super(pMsg, pThrowable);
    }
}
