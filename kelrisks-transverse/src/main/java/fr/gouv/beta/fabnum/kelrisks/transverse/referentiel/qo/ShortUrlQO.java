package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QShortUrl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.querydsl.core.BooleanBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShortUrlQO extends AbstractQO {
    
    private String code;
    private String url;
    
    @Override
    public void feedBuilder(BooleanBuilder builder) {
        
        if (id != null) {builder.and(QShortUrl.shortUrl.id.eq(id));}
        if (code != null) {builder.and(QShortUrl.shortUrl.code.eq(code));}
        if (url != null) {builder.and(QShortUrl.shortUrl.url.eq(url));}
    }
}
  