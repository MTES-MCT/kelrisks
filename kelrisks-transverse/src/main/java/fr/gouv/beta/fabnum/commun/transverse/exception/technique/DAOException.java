package fr.gouv.beta.fabnum.commun.transverse.exception.technique;

import fr.gouv.beta.fabnum.commun.transverse.exception.AbstractGenericException;

public class DAOException extends AbstractGenericException {
    
    /**
     * Champ : long serialVersionUID :
     */
    private static final long serialVersionUID = 4051046380262994486L;
    
    
    /**
     * Constructeur de DAOException
     *
     * @param message : le message à faire remonter dans l'exception
     */
    public DAOException(String message) {
        
        super(message);
    }
    
    /**
     * Constructeur de DAOException
     *
     * @param message   : le message à faire remonter dans l'exception
     * @param exception : la cause de l'exception
     */
    public DAOException(String message, Throwable exception) {
        
        super(message, exception);
    }
}
