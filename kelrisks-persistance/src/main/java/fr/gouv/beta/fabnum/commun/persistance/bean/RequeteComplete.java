package fr.gouv.beta.fabnum.commun.persistance.bean;


import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;

import java.util.List;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Cette classe regroupe une requête JPAQuery et une liste d'alias QueryDSL.
 */
public class RequeteComplete {
    
    JPAQueryBase query;
    
    List<EntityPathBase<? extends AbstractEntity>> aliases;
    
    public RequeteComplete(JPAQueryBase query) {
        
        this.query = query;
    }
    
    public RequeteComplete(JPAQuery query, List<EntityPathBase<? extends AbstractEntity>> aliases) {
        
        this.query = query;
        this.aliases = aliases;
    }
    
    public List<EntityPathBase<? extends AbstractEntity>> getAliases() {
        
        return aliases;
    }
    
    public void setAliases(List<EntityPathBase<? extends AbstractEntity>> aliases) {
        
        this.aliases = aliases;
    }
    
    public JPAQueryBase getQuery() {
        
        return query;
    }
    
    public void setQuery(JPAQueryBase query) {
        
        this.query = query;
    }
}
