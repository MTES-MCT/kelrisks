package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import java.util.List;

import org.geolatte.geom.Geometry;

@Data
public class CommuneDTO {
    
    private String           codeINSEE;
    private String           codePostal;
    private String           nomCommune;
    private Geometry<?>      multiPolygon;
    private List<CommuneDTO> communesLimitrophes;
    private String           codeZoneSismicite;
    private String           classePotentielRadon;
}
