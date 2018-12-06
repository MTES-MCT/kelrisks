package fr.gouv.beta.fabnum.commun.persistance;


import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.DAOException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.commun.transverse.qo.CritereTri;

import java.util.Collection;
import java.util.List;

/**
 * Interface présentant toutes les méthodes communes qui gèrent la persistance des entities
 * <p/>
 * Date de (re)-création : 29 août 07
 *
 * @param <T> : template de la classe dont il faut gérer la persistence
 *
 * @author CharlesA
 * @version : 1.1 : Intégration de la recherche utilisant un AbstractActionForm comme critère
 */
public interface IAbstractDAO<T extends AbstractEntity> {
    
    /**
     * Méthode clearCache
     *
     * @throws DAOException
     */
    void clearJPACache() throws DAOException;
    
    T getReference(Long id);
    
    /**
     * Méthode compterTous
     *
     * @return : le nombre d'objets de classe T persistés
     *
     * @throws DAOException
     */
    int compterTous() throws DAOException;
    
    /**
     * Méthode rechercherTous
     *
     * @return la liste complètes des objets persistés de classe T
     */
    List<T> rechercherTous() throws DAOException;
    
    /**
     * Méthode rechercherTous(CritereTri critereTri)
     *
     * @return la liste complètes des objets persistés de classe T triée sur le critère
     */
    List<T> rechercherTous(CritereTri critereTri) throws DAOException;
    
    /**
     * Méthode rechercherTousPagination
     *
     * @param first      : l'index du premier élément à ramener dans la recherche (classement : id)
     * @param maxResults : le nombre d'éléments à ramener dans le recherche
     *
     * @return une liste d'objet de classe T correspondant à la requête
     */
    List<T> rechercherTousPagination(int first, int maxResults) throws DAOException;
    
    /**
     * Méthode rechercherTousPagination
     *
     * @param first      : l'index du premier élément à ramener dans la recherche (classement : critereTri)
     * @param maxResults : le nombre d'éléments à ramener dans la recherche
     * @param critereTri : le bean contenant la propriété et l'ordre du tri à effectuer
     *
     * @return une liste d'objet de classe T correspondant à la requête
     */
    List<T> rechercherTousPagination(int first, int maxResults, CritereTri critereTri) throws DAOException;
    
    
    /**
     * Méthode rechercherParId
     *
     * @param id : l'identifiant de l'objet persisté à retourner
     *
     * @return L'objet persisté de classe T d'identifiant "id" ou null
     */
    T rechercherParId(Long id) throws DAOException;
    
    /**
     * Enregistre (création/modification) l'entité donnée.
     *
     * @param pData : l'objet de classe T à persister (créer ou mettre à jour)
     */
    T save(T pData) throws DAOException;
    
    /**
     * Enregistre (création/modification) l'entité donnée.
     *
     * @param pData l'objet de classe T à persister (créer ou mettre à jour)
     *
     * @return l'id de l'entity qui vient d'être créée ou modifiée
     *
     * @throws DAOException
     */
    Long saveAndReturnId(T pData) throws DAOException;
    
    /**
     * Méthode saveAll
     * <p/>
     *
     * @param list : la collection d'objets de classe T à persister
     */
    void saveAll(Collection<T> list) throws DAOException;
    
    /**
     * Méthode delete
     *
     * @param pData : l'objet de classe T à supprimer de la persistance
     */
    void delete(T pData) throws DAOException;
    
    /**
     * Méthode rechercherAvecCriterePagination
     *
     * @param critere
     * @param first
     * @param maxResults
     *
     * @return une liste d'entités <T> répondant aux critères passés dans le formulaire critere
     *
     * @throws DAOException
     */
    List<T> rechercherAvecCriterePagination(int first, int maxResults, AbstractQO... critere) throws DAOException;
    
    /**
     * Méthode rechercherAvecCriterePagination
     *
     * @param critere
     * @param first
     * @param maxResults
     *
     * @return une liste d'entités <T> répondant aux critères passés dans le formulaire critere, triés selon le critère
     * de tri critereTri
     *
     * @throws DAOException
     */
    List<T> rechercherAvecCriterePagination(Integer first, Integer maxResults, CritereTri critereTri, AbstractQO... critere) throws DAOException;
    
    /**
     * Méthode rechercherAvecCritere
     *
     * @param critere
     *
     * @return une liste d'entités <T> répondant aux critères passés dans le formulaire critere
     *
     * @throws DAOException
     */
    List<T> rechercherAvecCritere(AbstractQO... critere) throws DAOException;
    
    /**
     * Méthode rechercherAvecCritere
     *
     * @param critere
     *
     * @return une liste d'entités <T> répondant aux critères passés dans le formulaire critere, triés selon le critère
     * de tri critereTri
     *
     * @throws DAOException
     */
    List<T> rechercherAvecCritere(CritereTri critereTri, AbstractQO... critere) throws DAOException;
    
    /**
     * Méthode compterAvecCritere
     *
     * @param critere
     *
     * @return le nombre d'occurrences de T vérifiant les critères passés en paramètre dans critère
     *
     * @throws DAOException
     */
    int compterAvecCritere(AbstractQO... critere) throws DAOException;
    
    /**
     * Méthode flush
     */
    void flush();
    
    void forceIncrementVersion(T entity);
    
    /**
     * Méthode getMaxId
     *
     * @return le max des id de l'ensemble des entités concernées
     */
    Long getMaxId() throws DAOException;
}
