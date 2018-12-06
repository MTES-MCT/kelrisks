package fr.gouv.beta.fabnum.commun.transverse.messages;

import lombok.Data;

/**
 * Message dont la valeur est définie dans un fichier properties.
 *
 * @author jean-marc.pelle
 */
@Data
public class MessageProperty {
    
    /**
     * Clé du message.
     */
    private String   cleMessage;
    /**
     * Tableau des paramètres du message.
     */
    private String[] params;
    
    /**
     * Instantiates a new message property.
     */
    public MessageProperty() {
        
        super();
    }
    
    /**
     * Instantiates a new message property.
     *
     * @param cleMessage the cle message
     */
    public MessageProperty(String cleMessage) {
        
        super();
        this.cleMessage = cleMessage;
    }
    
    /**
     * Instantiates a new message property.
     *
     * @param cleMessage the cle message
     * @param params     the params
     */
    public MessageProperty(String cleMessage, String[] params) {
        
        super();
        this.cleMessage = cleMessage;
        this.params = params == null ? null : params.clone();
    }
}
