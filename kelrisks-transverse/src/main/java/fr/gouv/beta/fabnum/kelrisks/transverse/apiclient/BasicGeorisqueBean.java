package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;

@Data
public class BasicGeorisqueBean {
    
    Integer results;
    Integer page;
    Integer total_pages;
    Integer response_code;
    String  message;
    String  next;
    String  previous;
}
