package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.enums;

public enum PrecisionEnum {
    
    PARCELLE("parcel"),
    NUMERO("housenumber"),
    RUE("street"),
    LIEU_DIT("locality"),
    COMMUNE("municipality");
    
    private String code;
    
    PrecisionEnum(String code) {
        
        this.code = code;
    }
    
    public String getCode() {
        
        return code;
    }
}
