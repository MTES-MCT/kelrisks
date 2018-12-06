/**
 *
 */

package fr.gouv.beta.fabnum.commun.transverse.qo;

import lombok.Data;

import java.io.Serializable;

import com.querydsl.core.BooleanBuilder;

@Data
public abstract class AbstractQO implements Serializable {
    
    private static final long serialVersionUID = -1502227415382410729L;
    protected            Long id;
    
    public abstract void feedBuilder(BooleanBuilder builder);
}
