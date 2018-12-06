package fr.gouv.beta.fabnum.commun.transverse.pagination;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageReponse<T> implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /** Liste de résultats. */
    private List<T> listeResultats;
    
    /** nb total de résultats. */
    private long nbTotalResultats;
    
    /**
     * Instantiates a new page reponse.
     *
     * @param listeResultats   the liste resultats
     * @param nbTotalResultats the nb total resultats
     */
    public PageReponse(final List<T> listeResultats, final long nbTotalResultats) {
        
        super();
        this.listeResultats = listeResultats;
        this.nbTotalResultats = nbTotalResultats;
    }
    
    /**
     * Constructeur non-paramétré.
     */
    public PageReponse() {
        // Constructeur non-parammétré
    }
}
