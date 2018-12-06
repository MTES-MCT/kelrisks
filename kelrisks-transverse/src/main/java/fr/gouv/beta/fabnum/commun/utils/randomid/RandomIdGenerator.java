package fr.gouv.beta.fabnum.commun.utils.randomid;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class RandomIdGenerator implements IdentifierGenerator {
    
    private static Random generator = new Random();
    
    
    @Override
    public Serializable generate(SharedSessionContractImplementor sessionImplementor, Object o) throws HibernateException {
        
        long id = generator.nextLong();
        
        id = Math.abs(id);
        
        return id;
    }
}
