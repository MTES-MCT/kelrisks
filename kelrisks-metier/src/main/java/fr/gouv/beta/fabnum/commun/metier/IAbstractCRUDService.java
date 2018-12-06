package fr.gouv.beta.fabnum.commun.metier;


import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import fr.gouv.beta.fabnum.commun.transverse.exception.metier.EntityNotFoundException;
import fr.gouv.beta.fabnum.commun.transverse.exception.metier.ServiceException;
import fr.gouv.beta.fabnum.commun.transverse.pagination.PageReponse;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.commun.transverse.qo.CritereTri;

import java.util.Collection;
import java.util.List;

/**
 * Description de la classe.
 * <p/>
 * Date de re-création : 12 sept. 07
 *
 * @param <T> : le template objet sur lequel les méthodes doivent travailler
 *
 * @author alain
 * @version 1.1 : intégration des méthodes de recherche avec critère
 */
public interface IAbstractCRUDService<T extends AbstractEntity> extends IAbstractService {
    
    /**
     * Méthode compterTous
     * <p/>
     * Méthode qui compte les occurrences des objets de classe T
     *
     * @return le nombre d'objets de classe T
     *
     * @throws ServiceException
     */
    int compterTous() throws ServiceException;
    
    T getReference(Long id);
    
    /**
     * Méthode rechercherTous
     * <p/>
     * Méthode qui retourne l'ensemble objets persistés de la classe <T>
     *
     * @return liste des objets de classe <T> persistés.
     *
     * @throws ServiceException
     */
    List<T> rechercherTous() throws ServiceException;
    
    /**
     * Méthode rechercherTous
     * <p/>
     * Méthode qui retourne l'ensemble objets persistés de la classe <T> triée par le critère passé en paramètre
     *
     * @return liste des objets de classe <T> persistés triés.
     *
     * @throws ServiceException
     */
    List<T> rechercherTous(CritereTri critereTri) throws ServiceException;
    
    /**
     * Méthode rechercherParId
     * <p/>
     * Méthode qui retourne l'objet de classe <T> persisté, d'identifiant ID
     *
     * @param id : l'identifiant de l'objet à rechercher
     *
     * @return l"objet <T> d'identifiant id, ou bien null s'il n'existe pas.
     *
     * @throws ServiceException
     */
    T rechercherParId(Long id) throws ServiceException;
    
    /**
     * Méthode rechercherTousPagination
     * <p/>
     * Méthode qui retourne maxResults objets de classe T dans une liste, à partir de first
     *
     * @param first      : le premier objet à ramener
     * @param maxResults : le nombre d'objets à ramener
     *
     * @return la liste des objets demandés
     *
     * @throws ServiceException
     */
    List<T> rechercherTousPagination(int first, int maxResults) throws ServiceException;
    
    /**
     * Méthode rechercherTousPagination
     * <p/>
     * Méthode qui retourne maxResults objets de classe T dans une liste, à partir de first
     *
     * @param first      : le premier objet à ramener
     * @param maxResults : le nombre d'objets à ramener
     * @param critereTri : le critère de tri des objets à ramener
     *
     * @return la liste des objets demandés
     *
     * @throws ServiceException
     */
    List<T> rechercherTousPagination(int first, int maxResults, CritereTri critereTri) throws ServiceException;
    
    /**
     * Méthode compterEtRechercherTousPagination
     * <p/>
     * Méthode qui retourne maxResults objets de classe T dans une liste, à partir de first
     * et qui compte le nombre total de ces objets.
     *
     * @param first      : le premier objet à ramener
     * @param maxResults : le nombre d'objets à ramener
     *
     * @return la liste des objets demandés avec le compte
     *
     * @throws ServiceException
     */
    PageReponse<T> compterEtRechercherTousPagination(int first, int maxResults) throws ServiceException;
    
    /**
     * Méthode compterEtRechercherTousPagination
     * <p/>
     * Méthode qui retourne maxResults objets de classe T dans une liste, à partir de first
     * et qui compte le nombre total de ces objets.
     *
     * @param first      : le premier objet à ramener
     * @param maxResults : le nombre d'objets à ramener
     * @param critereTri : le critère de tri des objets à ramener
     *
     * @return la liste des objets demandés avec le compte
     *
     * @throws ServiceException
     */
    PageReponse<T> compterEtRechercherTousPagination(int first, int maxResults, CritereTri critereTri) throws ServiceException;
    
    /**
     * Méthode compterEtRechercherAvecCriterePagination
     * <p/>
     * Méthode qui retourne maxResults objets de classe T dans une liste, à partir de first
     * et qui compte le nombre total de ces objets respectant le critere de recherche donné.
     *
     * @param critere    : le critère de recherche
     * @param first      : le premier objet à ramener
     * @param maxResults : le nombre d'objets à ramener
     * @param critereTri : le critère de tri des objets à ramener
     *
     * @return la liste des objets demandés avec le compte
     *
     * @throws ServiceException
     */
    PageReponse<T> compterEtRechercherAvecCriterePagination(int first, int maxResults, CritereTri critereTri, AbstractQO... critere) throws ServiceException;
    
    /**
     * Méthode compterEtRechercherAvecCriterePagination
     * <p/>
     * Méthode qui retourne maxResults objets de classe T dans une liste, à partir de first
     * et qui compte le nombre total de ces objets respectant le critere de recherche donné.
     *
     * @param critere    : le critère de recherche
     * @param first      : le premier objet à ramener
     * @param maxResults : le nombre d'objets à ramener
     *
     * @return la liste des objets demandés avec le compte
     *
     * @throws ServiceException
     */
    PageReponse<T> compterEtRechercherAvecCriterePagination(int first, int maxResults, AbstractQO... critere) throws ServiceException;
    
    /**
     * Enregistre (création/modification) l'entité donnée.
     *
     * @param data : l'objet à persister
     *
     * @return l'entité persistée
     *
     * @throws ServiceException
     */
    T save(T data) throws ServiceException;
    
    /**
     * Enregistre (création/modification) l'entité donnée.
     *
     * @param pData l'objet de classe T à persister (créer ou mettre à jour)
     *
     * @return l'id de l'entity qui vient dêtre créée ou modifiée
     *
     * @throws ServiceException
     */
    Long saveAndReturnId(T pData) throws ServiceException;
    
    /**
     * Méthode saveListe
     * <p>
     * Méthode qui persiste une collection d'objets de classe T
     *
     * @param data : la liste d'objets de classe T
     *
     * @throws ServiceException
     */
    void saveListe(Collection<T> data) throws ServiceException;
    
    /**
     * Méthode supprimer
     * <p/>
     * Methode qui supprime l'objet T de la persistance
     *
     * @param data : l'objet à supprimer
     *
     * @throws ServiceException
     */
    void supprimer(T data) throws ServiceException;
    
    /**
     * Méthode compterAvecCritere
     *
     * @param critere
     *
     * @return le nombre d'occurences répondant aux critères passés dans critère
     *
     * @throws ServiceException
     */
    int compterAvecCritere(AbstractQO... critere) throws ServiceException;
    
    /**
     * Méthode rechercherAvecCriterePagination
     *
     * @param critere
     * @param first
     * @param maxResults
     *
     * @return une liste d'entités répondant aux critères passés dans le formulaire critere
     *
     * @throws ServiceException
     */
    List<T> rechercherAvecCriterePagination(int first, int maxResults, AbstractQO... critere) throws ServiceException;
    
    /**
     * Méthode rechercherAvecCriterePagination
     *
     * @param critere
     * @param first
     * @param maxResults
     * @param critereTri
     *
     * @return une liste d'entités répondant aux critères passés dans le formulaire critere, triés selon le critère de
     * tri critereTri
     *
     * @throws ServiceException
     */
    List<T> rechercherAvecCriterePagination(int first, int maxResults, CritereTri critereTri, AbstractQO... critere) throws ServiceException;
    
    /**
     * Méthode rechercherAvecCritere
     *
     * @param critere
     *
     * @return une liste d'entités répondant aux critères passés dans le formulaire critere
     *
     * @throws ServiceException
     */
    List<T> rechercherAvecCritere(AbstractQO... critere) throws ServiceException;
    
    /**
     * Méthode rechercherAvecCritere
     *
     * @param critere
     * @param critereTri
     *
     * @return une liste d'entités répondant aux critères passés dans le formulaire critere, triés selon le critère de
     * tri critereTri
     *
     * @throws ServiceException
     */
    List<T> rechercherAvecCritere(CritereTri critereTri, AbstractQO... critere) throws ServiceException;
    
    
    /**
     * Suppression de l'objet portant l'identifiant spécifié
     *
     * @param id L'identifiant de l'objet à supprimer
     *
     * @throws ServiceException
     */
    void supprimer(Long id) throws ServiceException;
    
    /**
     * Recherche l'entité correspondant au critère fourni.
     *
     * @param critere Le critère de recherche.
     *
     * @return L'entité correspondante.
     *
     * @throws ServiceException En cas d'erreur lors de la recherche, si plusieurs entités correspondent, ou si aucune entité ne
     *                          correspond.
     */
    T rechercherResultatUniqueAvecCritere(AbstractQO... critere) throws ServiceException;
    
    /**
     * Recherche l'entité correspondant à l'id donné. Si aucune entité n'est trouvée, une erreur est levée.
     *
     * @param id L'id de l'entité recherchée.
     *
     * @return L'entité correspondant.
     *
     * @throws ServiceException        En cas d'erreur lors de la recherche.
     * @throws EntityNotFoundException Si aucune entité existante ne correspond à l'id donné.
     */
    T rechercherParIdExistant(Long id) throws ServiceException;
    
    /**
     * Méthode rechercherTousTrieOrdre
     * <p>
     * Méthode qui retourne l'ensemble objets persistés de la classe <T> trié selon l'ordre
     *
     * @return liste des objets de classe <T> persistés.
     *
     * @throws ServiceException
     */
    List<T> rechercherTousTrieOrdre() throws ServiceException;
    
    /**
     * Méthode rechercherTousTrieCode
     * <p>
     * Méthode qui retourne l'ensemble objets persistés de la classe <T> trié selon le code
     *
     * @return liste des objets de classe <T> persistés.
     *
     * @throws ServiceException
     */
    List<T> rechercherTousTrieCode() throws ServiceException;
    
    /**
     * Méthode getMaxId
     * <p>
     * Méthode qui retourne le max des id de la table concernée
     *
     * @return le max des id
     *
     * @throws ServiceException
     */
    Long getMaxId() throws ServiceException;
}
