package fr.gouv.beta.fabnum.commun.persistance;


import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;

public interface IAbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {
    
    @NonNull
    List<T> findAll();
    
    @NonNull
    List<T> findAll(@NonNull Sort var1);
    
    @Query("SELECT max(t.id) FROM #{#entityName} t")
    Long getMaxId();
}
