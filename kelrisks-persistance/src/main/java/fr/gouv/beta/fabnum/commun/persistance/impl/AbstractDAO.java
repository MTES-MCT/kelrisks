package fr.gouv.beta.fabnum.commun.persistance.impl;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.commun.persistance.bean.RequeteComplete;
import fr.gouv.beta.fabnum.commun.persistance.threadlocal.ThreadLocalSetEntityPathBase;
import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.DAOException;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.commun.transverse.qo.CritereTri;
import fr.gouv.beta.fabnum.commun.utils.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.JoinType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Objet DAO ancêtre de tous les objets DAO.
 * <p/>
 * Cet objet abstrait contient des méthodes non absraites qui permettent d'effectuer les tâches d'intégration pouvant
 * être communes à tous les objets persistants.
 * <p/>
 * Date de (re)-création : 26 mars 15
 *
 * @param <T> : le template objet sur lequel les méthodes doivent travailler
 *
 * @version : 1.0
 */

@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractDAO<T extends AbstractEntity> implements IAbstractDAO<T> {
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    /**
     * Champ : IAbstractRepository<T> repo
     * <p/>
     * Objet interface IAbstractRepository à qui seront déléguées les tâches de persistence
     */
    private IAbstractRepository<T> repo;
    
    @Override
    public void clearJPACache() throws DAOException {
        
        Session session = (Session) entityManager.getDelegate();
        session.getSessionFactory().getCache().evictAllRegions();
    }
    
    public T getReference(Long id) {
        
        //noinspection unchecked
        Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        
        return entityManager.getReference(persistentClass, id);
    }
    
    public int compterTous() throws DAOException {
        
        try {
            return (int) repo.count();
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.compterTous()", e);
        }
    }
    
    public List<T> rechercherTous() throws DAOException {
        
        try {
            return repo.findAll();
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherTous()", e);
        }
    }
    
    public List<T> rechercherTous(final CritereTri critereTri) throws DAOException {
        
        try {
            return repo.findAll(toSort(critereTri));
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherTous(critereTri)", e);
        }
    }
    
    public List<T> rechercherTousPagination(final int first, final int maxResults) throws DAOException {
        
        try {
            CritereTri critereTri = new CritereTri();
            return rechercherTousPagination(first, maxResults, critereTri);
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherTousPagination(first,maxResults)", e);
        }
    }
    
    public List<T> rechercherTousPagination(final int first, final int maxResults, final CritereTri critereTri) throws DAOException {
        
        try {
            return repo.findAll(PageRequest.of(first / maxResults, maxResults, toSort(critereTri))).getContent();
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherTousPagination(first,maxResults,critereTri)", e);
        }
    }
    
    public T rechercherParId(final Long id) throws DAOException {
        
        try {
            return repo.findById(id).orElseThrow(TechniqueException::new);
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherParId(id)", e);
        }
    }
    
    public T save(final T pData) throws DAOException {
        
        try {
            return repo.save(pData);
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.save(pData)", e);
        }
    }
    
    public Long saveAndReturnId(final T pData) throws DAOException {
        
        try {
            return save(pData).getId();
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.saveAndReturnId(pData)", e);
        }
    }
    
    public void saveAll(final Collection<T> list) throws DAOException {
        
        try {
            for (T objToSave : list) {
                save(objToSave);
            }
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.saveAll(list)", e);
        }
    }
    
    public void delete(final T pData) throws DAOException {
        
        try {
            repo.delete(pData);
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.delete(pData)", e);
        }
    }
    
    public List<T> rechercherAvecCriterePagination(final int first, final int maxResults, AbstractQO... critere) throws DAOException {
        
        try {
            CritereTri critereTri = new CritereTri();
            return rechercherAvecCriterePagination(first, maxResults, critereTri, critere);
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherAvecCriterePagination(critere,first,maxResults)", e);
        }
    }
    
    public List<T> rechercherAvecCriterePagination(Integer first, Integer maxResults, CritereTri critereTri, AbstractQO... critere) throws DAOException {
        
        try {
            // On crée la requête en tenant compte des critères de sélection
            JPAQueryBase<T, ?> query = composerRequete(critere).getQuery();
    
            if (critereTri.getDistinct() != null) {
                query.distinct().select(critereTri.getDistinct());
            }
            
            // On ajoute la pagination
            if (first != null && maxResults != null) {
                query.limit(maxResults).offset(first);
            }
            
            // Ajout d'un tri par défaut sur les id en sus d'un éventuel tri déjà fait
            // (Fix le problème de tri sur un champ champ dont la valeur est la même cumulé avec la pagination => Ordre partiellement aléatoire)
            //            critereTri.ajouteOrdre(new OrderSpecifier(Order.DESC, getPathBuilder().get("id")));
            // On ajoute les critères de tri à la requète
            applyOrderBy(query, critereTri);
            
            return query.fetch();
        }
        catch (TechniqueException e) {
            throw new DAOException("Erreur lors de la composition des critères", e);
        }
    }
    
    public List<T> rechercherAvecCritere(AbstractQO... critere) throws DAOException {
        
        try {
            CritereTri critereTri = new CritereTri();
            return rechercherAvecCritere(critereTri, critere);
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.rechercherAvecCritere(critere)", e);
        }
    }
    
    public List<T> rechercherAvecCritere(CritereTri critereTri, AbstractQO... critere) throws DAOException {
        
        return rechercherAvecCriterePagination(null, null, critereTri, critere);
    }
    
    public int compterAvecCritere(AbstractQO... critere) throws DAOException {
        
        try {
            return (int) composerRequete(critere).getQuery().fetchCount();
        }
        catch (TechniqueException e) {
            throw new DAOException("Erreur lors de la composition des critères", e);
        }
    }
    
    /**
     * Envoie l'ordre flush à JPA : force l'exécution des requêtes
     * C'est utile en cas de problème avec des contraintes en base.
     * Cela permet de garantir l'ordre d'insertion des éléments.
     * <p>
     * A n'utiliser qu'ne cas de nécéssité prouvée...
     */
    public void flush() {
        
        repo.flush();
    }
    
    @Override
    public void forceIncrementVersion(T entity) {
        
        entityManager.lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }
    
    /**
     * voir {@link IAbstractDAO#getMaxId()}
     */
    public Long getMaxId() throws DAOException {
        
        try {
            Long max = repo.getMaxId();
            if (max == null) {
                return 0L;
            }
            else {
                return max;
            }
        }
        catch (Exception e) {
            throw new DAOException("Erreur dans AbstractDAO.getMaxId(pData)", e);
        }
    }
    
    public void initCompteurSetsCharges() {
        
        Set<EntityPathBase> entitiesChargees = ThreadLocalSetEntityPathBase.get();
        
        if (entitiesChargees == null) {
            entitiesChargees = new HashSet<>();
            ThreadLocalSetEntityPathBase.set(entitiesChargees);
        }
        else { entitiesChargees.clear(); }
    }
    
    public boolean verifierEtIncrementerCompteurSetsCharges(EntityPathBase<?> entity) {
        
        Set<EntityPathBase> entitiesChargees = ThreadLocalSetEntityPathBase.get();
        
        //Afin de prévenir le chargement d'un même set deux fois, ce qui pourrait déclancher une erreur 'bag' et/ou être catastrophique en terme de performances.
        boolean added = entitiesChargees.add(entity);
        
        if (added) {
            
            if (entitiesChargees.size() > 1) {
                //                System.out.println("-------------------------------------------------------------------------------------");
                //                System.out.println(" /!\\           " + entitiesChargees.size() + " sets chargés !!!");
                //                                for (EntityPathBase e : entitiesChargees) System.out.println(" /!\\            - " + e.toString());
                //                System.out.println("-------------------------------------------------------------------------------------");
            }
        }
        else {
            //            System.out.println(" /!\\           Vous avez tenté de charger deux fois le même set (" + entity.toString() + ") !!!");
        }
        
        return added;
    }
    
    /*
     *  Les méthodes génériques de création de requêtes par QueryObject
     *
     */
    protected abstract void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException;
    
    /**
     * Prépare une requête QueryDSL basé sur l'entité gérée par ce DAO.
     * Correspond au "Select from entitée"
     *
     * @return La requête QueryDSL utilisable pour cette Entité.
     */
    protected JPAQueryBase fromThis() {
        
        JPAQuery<?> query = new JPAQuery(entityManager);
        return query.from(getQueryObject());
    }
    
    /**
     * Ajoute les différents tris présents dans critereTri à la requête QueryDSL
     * N'ajoute rien si le critereTri est null
     * Fait la distinction entre tri "classique" sous forme de String et la liste de tri typé QueryDSL
     *
     * @param query      : la requête en entrée à modifier, ne doit pas être null
     * @param critereTri : le critère à intégrer
     *
     * @return la requête avec les tris ajoutés
     */
    protected JPAQueryBase applyOrderBy(JPAQueryBase<T, ?> query, CritereTri critereTri) throws TechniqueException {
        
        try {
            assert (query != null);
            if (critereTri != null) {
                
                if (!CollectionUtils.isEmpty(critereTri.getOrdres())) {
                    for (OrderSpecifier<?> ordre : critereTri.getOrdres()) {
                        query.orderBy(ordre);
                    }
                }
                
                if (critereTri.getPropriete() != null) {
                    PathBuilder<Object> path = getPathBuilder().get(critereTri.getPropriete());
                    
                    @SuppressWarnings("unchecked") ComparablePath<?> comparablePath = getPathBuilder().getComparable(critereTri.getPropriete(), (Class<Comparable<?>>) path.getType());
                    
                    query.orderBy(critereTri.isAscendant() ? comparablePath.asc() : comparablePath.desc());
                }
            }
            return query;
        }
        catch (Exception e) {
            throw new TechniqueException("Erreur lors de l'ajout des tris", e);
        }
    }
    
    /**
     * Transorme le critereTri en un critère de tri utilisable par springdata.
     * Fait la distinction entre tri "classique" sous forme de String et la liste de tri typé QueryDSL
     *
     * @param critereTri : le critère à intégrer
     *
     * @return L'objet Sort de Springdata utilisable dans un repository
     */
    protected Sort toSort(CritereTri critereTri) throws TechniqueException {
        
        try {
            List<Sort.Order> tris = new ArrayList<>();
            if (!CollectionUtils.isEmpty(critereTri.getOrdres())) {
                for (OrderSpecifier<?> ordre : critereTri.getOrdres()) {
                    tris.add(new Sort.Order(ordre.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, ordre.getTarget().toString().split("\\.", 2)[1]));
                }
            }
            else {
                tris.add(new Sort.Order(critereTri.isAscendant() ? Sort.Direction.ASC : Sort.Direction.DESC, critereTri.getPropriete()));
            }
    
            return Sort.by(tris);
        }
        catch (Exception e) {
            throw new TechniqueException("Erreur lors de la conversion des tris", e);
        }
    }
    
    protected Predicate composerCriteres(AbstractQO[] leCritere) throws TechniqueException {
        
        BooleanBuilder builder = new BooleanBuilder();
        
        for (AbstractQO abstractQO : leCritere) {
            
            if (abstractQO != null) {
                abstractQO.feedBuilder(builder);
            }
        }
        
        return builder;
    }
    
    protected RequeteComplete composerRequete(AbstractQO... leCritere) throws TechniqueException {
        
        JPAQueryBase<?, ?> query = fromThis();
        
        initCompteurSetsCharges();
        
        // On ajoute les chargements optionnels
        
        ajouterChargementsOptionnels(query, leCritere);
        
        // On ajoute les critères
        
        Predicate qCritere = composerCriteres(leCritere);
        
        query.where(qCritere);
        
        // Distinct uniquement si un left join a été fait
        if (query.getMetadata().getJoins()
                    .stream()
                    .anyMatch(joinExpression -> joinExpression.getType().equals(JoinType.LEFTJOIN))) {
            query.distinct();
        }
        
        return new RequeteComplete(query);
    }
    
    protected abstract PathBuilder<T> getPathBuilder();
    
    protected abstract EntityPathBase<T> getQueryObject();
    
    public IAbstractRepository<T> getRepo() {
        
        return repo;
    }
    
    public void setRepo(IAbstractRepository<T> repo) {
        
        this.repo = repo;
    }
}
