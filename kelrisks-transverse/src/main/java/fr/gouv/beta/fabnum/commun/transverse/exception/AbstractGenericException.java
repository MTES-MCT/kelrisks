package fr.gouv.beta.fabnum.commun.transverse.exception;

import fr.gouv.beta.fabnum.commun.transverse.messages.MessageProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenericException extends RuntimeException {
    
    private static final long serialVersionUID = 2497445589568337092L;
    
    /**
     * Liste des messages d'erreur (cas d'erreurs multiples).
     */
    private final List<MessageProperty> erreursMultiples = new ArrayList<>();
    /**
     * Indique si l'exception a déjà été loguée.
     */
    private       boolean               logged;
    /**
     * Tableau des paramètres de la propriété "message" (qui est une clé d'un fichier properties).
     */
    private       String[]              params;
    
    /**
     * Constructeur de AbstractGenericException
     */
    public AbstractGenericException() {
        
        super();
    }
    
    /**
     * Constructeur de AbstractGenericException
     *
     * @param message : Le message à faire remonter dans l'exception
     */
    public AbstractGenericException(String message) {
        
        super(message);
    }
    
    /**
     * Constructeur de AbstractGenericException
     *
     * @param cause : cause de l'exception
     */
    public AbstractGenericException(Throwable cause) {
        
        super(cause);
    }
    
    /**
     * Constructeur de AbstractGenericException
     *
     * @param message:   Le message à faire remonter dans l'exception
     * @param exception: cause de l'exception
     */
    public AbstractGenericException(String message, Throwable exception) {
        
        super(message, exception);
    }
    
    public List<MessageProperty> getErreursMultiples() {
        
        return erreursMultiples;
    }
    
    public String[] getParams() {
        
        return params;
    }
    
    public boolean isLogged() {
        
        return logged;
    }
    
    public void setLogged(boolean logged) {
        
        this.logged = logged;
    }
}
