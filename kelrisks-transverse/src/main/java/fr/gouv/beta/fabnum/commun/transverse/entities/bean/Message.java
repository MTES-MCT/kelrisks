package fr.gouv.beta.fabnum.commun.transverse.entities.bean;

import lombok.Data;

import java.util.List;

/**
 * Objet représentant un message à afficher
 * Created by thomas.feroul on 25/05/16.
 */
@Data
public class Message {
    
    private String cle;
    
    private List<String> argumentList;
    
    public Message(String cle) {
        
        this.cle = cle;
    }
    
    public Message(String cle, List<String> argumentList) {
        
        this.cle = cle;
        this.argumentList = argumentList;
    }
}
