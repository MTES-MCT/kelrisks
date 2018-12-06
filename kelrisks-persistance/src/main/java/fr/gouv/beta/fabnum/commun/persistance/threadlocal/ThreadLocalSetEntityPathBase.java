package fr.gouv.beta.fabnum.commun.persistance.threadlocal;


import java.util.Set;

import com.querydsl.core.types.dsl.EntityPathBase;

/**
 * Created by thomas.feroul on 06/03/2018!
 */
public class ThreadLocalSetEntityPathBase {
    
    private static final ThreadLocal<Set<EntityPathBase>> threadLocal = new ThreadLocal<>();
    
    public static void set(Set<EntityPathBase> entityPathBaseSet) {
        
        threadLocal.set(entityPathBaseSet);
    }
    
    public static void unset() {
        
        threadLocal.remove();
    }
    
    public static Set<EntityPathBase> get() {
        
        return threadLocal.get();
    }
}
