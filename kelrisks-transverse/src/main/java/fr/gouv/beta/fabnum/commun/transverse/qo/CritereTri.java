package fr.gouv.beta.fabnum.commun.transverse.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.StringPath;

@Data
public class CritereTri implements Serializable {
    
    /**
     * Champ serialVersionUID
     */
    private static final long serialVersionUID = 7914369229164646504L;
    //    @Autowired
    //    ApplicationProperties applicationProperties;
    
    /**
     * La version typée grâce à QueryDSL
     */
    private List<OrderSpecifier<?>> ordres = new ArrayList<>();
    
    /**
     * Champ propriete : la propriete sur laquelle il faut trier
     */
    private String propriete;
    
    /**
     * Champ ascendant : true = croissant, false = decroissant
     */
    private boolean ascendant;
    
    private StringPath distinct;
    
    /**
     * Constructeur
     *
     * @param unePropriete : propriété sur laquelle effectuer le tri
     * @param isAscendant  : true pour un tri ascendant
     */
    public CritereTri(String unePropriete, boolean isAscendant) {
        
        setPropriete(unePropriete);
        setAscendant(isAscendant);
    }
    
    /**
     * Constructeur par défaut : tri ascendant sur "id"
     */
    public CritereTri() {
    
        //        setPropriete(applicationProperties.getCritere().getTri().getDefaut()); TODO : Comprendre pourquoi les prop ne sont pas injectées (Problème d'ordre ?)
        //        setPropriete("id");
        //        setAscendant(true);
    }
    
    public void ajouteOrdre(OrderSpecifier<?> ordre) {
        
        ordres.add(ordre);
    }
}
