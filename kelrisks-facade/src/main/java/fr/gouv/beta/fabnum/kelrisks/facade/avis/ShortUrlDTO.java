package fr.gouv.beta.fabnum.kelrisks.facade.avis;

import lombok.Data;

import java.util.Date;

@Data
public class ShortUrlDTO {
    
    private String url;
    private String code;
    private Date   dateMaj;
    private Long   id;
    private int    version;
}
  