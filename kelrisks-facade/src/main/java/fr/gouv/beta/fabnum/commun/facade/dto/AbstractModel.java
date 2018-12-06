package fr.gouv.beta.fabnum.commun.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by thomas.bouchardon on 24/04/2018!
 */
@Data
public abstract class AbstractModel implements Serializable {
    
    private String anchor;
}
