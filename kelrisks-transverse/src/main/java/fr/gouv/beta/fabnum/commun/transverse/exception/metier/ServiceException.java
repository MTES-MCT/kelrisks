package fr.gouv.beta.fabnum.commun.transverse.exception.metier;

import fr.gouv.beta.fabnum.commun.transverse.exception.AbstractGenericException;

public class ServiceException extends AbstractGenericException {
    
    /**
     * Champ : long serialVersionUID <p>
     * Champ serialCersionUID
     */
    private static final long serialVersionUID = 3546078073104642870L;
    
    /**
     * Constructeur de ServiceException
     */
    public ServiceException() {
        
        super();
    }
    
    /**
     * Constructeur de ServiceException
     *
     * @param message le message à remonter dans l'exception
     */
    public ServiceException(String message) {
        
        super(message);
    }
    
    /**
     * Constructeur de ServiceException
     *
     * @param message   le message à remonter dans l'exception
     * @param exception la cause de l'exception
     */
    public ServiceException(String message, Throwable exception) {
        
        super(message, exception);
    }
}
