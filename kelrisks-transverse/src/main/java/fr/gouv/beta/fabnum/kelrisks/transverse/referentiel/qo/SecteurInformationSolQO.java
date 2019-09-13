package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSecteurInformationSol;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecteurInformationSolQO extends AbstractQO {
    
    private List<String> precisions;
    private String       codeINSEE;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QSecteurInformationSol.secteurInformationSol.id.eq(id));}
        if (codeINSEE != null) {builder.and(QSecteurInformationSol.secteurInformationSol.codeInsee.eq(codeINSEE));}
        if (!CollectionUtils.isEmpty(precisions)) {builder.and(QSecteurInformationSol.secteurInformationSol.precision.in(precisions));}
    }
}