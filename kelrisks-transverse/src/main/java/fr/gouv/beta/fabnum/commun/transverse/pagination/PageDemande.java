package fr.gouv.beta.fabnum.commun.transverse.pagination;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageDemande implements Serializable {
    
    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -1093838308757274792L;
    
    /** Index de la page voulue (attention, l'index commence à zero). */
    private int indexPage;
    
    /** Taille de la page voulue (nombre de résultats). */
    private int taillePage;
    
    /** Champ de tri de la clause orderBy. */
    private String champTri;
    
    /** Le tri doit être dans le sens descendant ou pas. */
    private boolean desc;
    
    /** The valeur filtre. */
    private String valeurFiltre;
    
    /**
     * Constructeur paramétré.
     *
     * @param indexPage    Index de la page voulue (attention, l'index commence à zero).
     * @param taillePage   Taille de la page voulue (nombre de résultats par page).
     * @param champTri     champTri
     * @param desc         Tri par sens descandant ou pas.
     * @param valeurFiltre the valeur filtre
     */
    public PageDemande(final int indexPage, final int taillePage, final String champTri, final boolean desc, String valeurFiltre) {
        
        super();
        this.indexPage = indexPage;
        this.taillePage = taillePage;
        this.champTri = champTri;
        this.desc = desc;
        this.valeurFiltre = valeurFiltre;
    }
    
    /**
     * Constructeur paramétré.
     *
     * @param indexPage  Index de la page voulue (attention, l'index commence à zero).
     * @param taillePage Taille de la page voulue (nombre de résultats par page).
     * @param champTri   champTri
     * @param desc       Tri par sens descandant ou pas.
     */
    public PageDemande(final int indexPage, final int taillePage, final String champTri, final boolean desc) {
        
        super();
        this.indexPage = indexPage;
        this.taillePage = taillePage;
        this.champTri = champTri;
        this.desc = desc;
    }
    
    /**
     * Constructeur paramétré (sans tri).
     *
     * @param indexPage  Index de la page voulue (attention, l'index commence à zero).
     * @param taillePage Taille de la page voulue (nombre de résultats par page).
     */
    public PageDemande(final int indexPage, final int taillePage) {
        
        super();
        this.indexPage = indexPage;
        this.taillePage = taillePage;
    }
    
    /**
     * Constructeur non-paramétré.
     */
    public PageDemande() {
        // Constructeur non-parammétré
    }
    
    /**
     * Renvoi l'index du premier résultat voulu (utilisé par l'API typedQuery JPA).
     *
     * @return l'index du premier résultat (commence à zero)
     */
    public int getIndexPremierResultat() {
        
        return this.indexPage * this.taillePage;
    }
}
