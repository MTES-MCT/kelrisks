package fr.gouv.beta.fabnum.commun.metier.impl;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import fr.gouv.beta.fabnum.commun.transverse.exception.metier.EntityNotFoundException;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.DAOException;
import fr.gouv.beta.fabnum.commun.transverse.pagination.PageReponse;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.commun.transverse.qo.CritereTri;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service métier abstrait qui contient des méthodes communes à tous les services métiers.
 * <p/>
 * Date de re-création : 12 sept. 07
 *
 * @param <T> : le template objet sur lequel les méthodes doivent travailler
 *
 * @author alain
 * @version 1.0
 */
public abstract class AbstractCRUDService<T extends AbstractEntity> extends AbstractService implements IAbstractCRUDService<T> {
    
    /**
     * Logger
     */
    protected static     Log             logCommun        = LogFactory.getLog(AbstractCRUDService.class);
    /**
     *
     */
    private static final long            serialVersionUID = -5891873951458356051L;
    /**
     * Champ : IAbstractDAO<T> fdao
     * <p/>
     * Objet interface IAbstractDAO à qui seront déléguées les tâches de persistence
     */
    private              IAbstractDAO<T> fdao;
    
    /**
     * voir {@link IAbstractCRUDService#compterTous()}
     */
    @Override
    public int compterTous() throws ServiceException {
        
        try {
            return this.fdao.compterTous();
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    @Override
    public T getReference(Long id) {
        
        return fdao.getReference(id);
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherTous()}
     */
    @Override
    public List<T> rechercherTous() throws ServiceException {
        
        try {
            return this.fdao.rechercherTous();
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherTous()}
     */
    @Override
    public List<T> rechercherTous(CritereTri critereTri) throws ServiceException {
        
        try {
            return this.fdao.rechercherTous(critereTri);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherParId(Long id)}
     */
    @Override
    public T rechercherParId(Long id) throws ServiceException {
        
        try {
            return this.fdao.rechercherParId(id);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherTousPagination(int first, int maxResults)}
     */
    @Override
    public List<T> rechercherTousPagination(int first, int maxResults) throws ServiceException {
        
        try {
            return this.fdao.rechercherTousPagination(first, maxResults);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir
     * {@link IAbstractCRUDService#rechercherTousPagination(int first, int maxResults, CritereTri critereTri)}
     */
    @Override
    public List<T> rechercherTousPagination(int first, int maxResults, CritereTri critereTri) throws ServiceException {
        
        try {
            return this.fdao.rechercherTousPagination(first, maxResults, critereTri);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir
     * {@link IAbstractCRUDService#compterEtRechercherTousPagination(int first, int maxResults)}
     */
    @Override
    public PageReponse<T> compterEtRechercherTousPagination(int first, int maxResults) throws ServiceException {
        
        PageReponse<T> pageRetour = new PageReponse<>();
        pageRetour.setNbTotalResultats(this.compterTous());
        pageRetour.setListeResultats(this.rechercherTousPagination(first, maxResults));
        return pageRetour;
    }
    
    /**
     * voir
     * {@link IAbstractCRUDService#compterEtRechercherTousPagination(int first, int maxResults, CritereTri critereTri)}
     */
    @Override
    public PageReponse<T> compterEtRechercherTousPagination(int first, int maxResults, CritereTri critereTri) throws ServiceException {
        
        PageReponse<T> pageRetour = new PageReponse<>();
        pageRetour.setNbTotalResultats(this.compterTous());
        pageRetour.setListeResultats(this.rechercherTousPagination(first, maxResults, critereTri));
        return pageRetour;
    }
    
    /**
     * voir {@link IAbstractCRUDService#compterEtRechercherAvecCriterePagination(int, int, AbstractQO...)}
     */
    public PageReponse<T> compterEtRechercherAvecCriterePagination(int first, int maxResults, CritereTri critereTri, AbstractQO... critere) throws ServiceException {
        
        PageReponse<T> pageRetour = new PageReponse<>();
        pageRetour.setNbTotalResultats(this.compterAvecCritere(critere));
        pageRetour.setListeResultats(this.rechercherAvecCriterePagination(first, maxResults, critereTri, critere));
        return pageRetour;
    }
    
    /**
     * voir {@link IAbstractCRUDService#compterEtRechercherAvecCriterePagination(int, int, AbstractQO...)}
     */
    public PageReponse<T> compterEtRechercherAvecCriterePagination(int first, int maxResults, AbstractQO... critere) throws ServiceException {
        
        PageReponse<T> pageRetour = new PageReponse<>();
        pageRetour.setNbTotalResultats(this.compterAvecCritere(critere));
        pageRetour.setListeResultats(this.rechercherAvecCriterePagination(first, maxResults, critere));
        return pageRetour;
    }
    
    /**
     * voir
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(T data) throws ServiceException {
        
        try {
            return this.fdao.save(data);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir
     */
    @Transactional(rollbackFor = Exception.class)
    public Long saveAndReturnId(T data) throws ServiceException {
        
        try {
            return this.fdao.saveAndReturnId(data);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#saveListe(Collection)}
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveListe(Collection<T> list) throws ServiceException {
        
        try {
            this.fdao.saveAll(list);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir
     */
    @Transactional(rollbackFor = Exception.class)
    public void supprimer(T data) throws ServiceException {
        
        try {
            this.fdao.delete(data);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#compterAvecCritere(AbstractQO...)}
     */
    public int compterAvecCritere(AbstractQO... critere) throws ServiceException {
        
        try {
            return this.getFdao().compterAvecCritere(critere);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherAvecCriterePagination(int, int, AbstractQO...)}
     */
    public List<T> rechercherAvecCriterePagination(int first, int maxResults, AbstractQO... critere) throws ServiceException {
        
        try {
            return this.fdao.rechercherAvecCriterePagination(first, maxResults, critere);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherAvecCriterePagination(int, int, AbstractQO...)}
     */
    public List<T> rechercherAvecCriterePagination(int first, int maxResults, CritereTri critereTri, AbstractQO... critere) throws ServiceException {
        
        try {
            return this.getFdao().rechercherAvecCriterePagination(first, maxResults, critereTri, critere);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherAvecCritere(AbstractQO...)}
     */
    public List<T> rechercherAvecCritere(AbstractQO... critere) throws ServiceException {
        
        try {
            return this.fdao.rechercherAvecCritere(critere);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherAvecCritere(AbstractQO...)}
     */
    public List<T> rechercherAvecCritere(CritereTri critereTri, AbstractQO... critere) throws ServiceException {
        
        try {
            return this.getFdao().rechercherAvecCritere(critereTri, critere);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#supprimer(Long)}
     */
    @Transactional(rollbackFor = Exception.class)
    public void supprimer(Long objectId) throws ServiceException {
        
        T objectToDelete = this.rechercherParId(objectId);
        if (objectToDelete != null) {
            this.supprimer(objectToDelete);
        }
        else {
            throw new EntityNotFoundException("L'objet a déjà été supprimé");
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherResultatUniqueAvecCritere(AbstractQO...)}
     */
    public T rechercherResultatUniqueAvecCritere(AbstractQO... critere) throws ServiceException {
        
        List<T> resultList = rechercherAvecCritere(critere);
        if (resultList.size() == 0) {
            throw new EntityNotFoundException(MessageFormat.format("Un seul résultat attendu. Nombre de résultats obtenus : {0}.", resultList.size()));
        }
        else if (resultList.size() > 1) {
            throw new ServiceException(MessageFormat.format("Un seul résultat attendu. Nombre de résultats obtenus : {0}.", resultList.size()));
        }
        return resultList.get(0);
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherParIdExistant(Long)}
     */
    public T rechercherParIdExistant(Long id) throws ServiceException {
        
        T entity = rechercherParId(id);
        if (null == entity) {
            throw new EntityNotFoundException("L'objet a déjà été supprimé");
        }
        return entity;
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherTousTrieOrdre()}
     */
    public List<T> rechercherTousTrieOrdre() throws ServiceException {
        
        try {
            CritereTri critereTri = new CritereTri();
            critereTri.setPropriete("ordre");
            return this.fdao.rechercherTous(critereTri);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#rechercherTousTrieCode()}
     */
    public List<T> rechercherTousTrieCode() throws ServiceException {
        
        try {
            CritereTri critereTri = new CritereTri();
            critereTri.setPropriete("code");
            return this.fdao.rechercherTous(critereTri);
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * voir {@link IAbstractCRUDService#getMaxId()}
     */
    public Long getMaxId() throws ServiceException {
        
        try {
            return this.fdao.getMaxId();
        }
        catch (DAOException e) {
            throw new ServiceException("Erreur lors de l'accès à la couche d'intégration.", e);
        }
    }
    
    /**
     * Getter pour le champ fdao :
     *
     * @return la valeur de fdao
     */
    public IAbstractDAO<T> getFdao() {
        
        return fdao;
    }
    
    /**
     * Setter pour le champ fdao
     *
     * @param fdao : la valeur du champ fdao à placer
     */
    public void setFdao(IAbstractDAO<T> fdao) {
        
        this.fdao = fdao;
    }
}
