package fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.enums;

public enum PrecisionEnum {
    
    BASIAS_NUMERO("numéro"),
    BASIAS_RUE("rue"),
    
    BASOL_NUMERO("housenumber"),
    BASOL_RUE("street"),
    BASOL_COMMUNE("municipality"),
    
    S3IC_NUMERO("housenumber"),
    S3IC_PARCELLE("parcel"),
    S3IC_COMMUNE("municipality");
    
    private String code;
    
    PrecisionEnum(String code) {
        
        this.code = code;
    }
    
    public String getCode() {
        
        return code;
    }
}
