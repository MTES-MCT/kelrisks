package fr.gouv.beta.fabnum.commun.transverse.exception.metier;

import fr.gouv.beta.fabnum.commun.transverse.exception.AbstractGenericException;

public class NoAuthenticatedUserException extends AbstractGenericException {
    
    private static final long serialVersionUID = -2474797351822009232L;
    
    public NoAuthenticatedUserException(String pMsg) {
        
        super(pMsg);
    }
    
    public NoAuthenticatedUserException(String pMsg, Throwable pThrowable) {
        
        super(pMsg, pThrowable);
    }
}
