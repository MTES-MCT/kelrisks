package fr.gouv.beta.fabnum.commun.transverse.exception.technique;

import fr.gouv.beta.fabnum.commun.transverse.exception.AbstractGenericException;

public class TechniqueException extends AbstractGenericException {
    
    /**
     * Champ : long serialVersionUID :
     */
    private static final long serialVersionUID = 6884249240338199197L;
    
    /**
     * Constructeur de TechniqueException
     */
    public TechniqueException() {
        
        super();
    }
    
    /**
     * Constructeur de TechniqueException
     *
     * @param pCode : le message à faire remonter dans l'exception
     */
    public TechniqueException(String pCode) {
        
        super(pCode);
    }
    
    /**
     * Constructeur de TechniqueException
     *
     * @param pCode : : le message à faire remonter dans l'exception
     * @param exp   : la cause de l'exception
     */
    public TechniqueException(String pCode, Throwable exp) {
        
        super(pCode, exp);
    }
    
    /**
     * Constructeur de TechniqueException
     *
     * @param exp : la cause de l'exception
     */
    public TechniqueException(Throwable exp) {
        
        super(exp);
    }
}
