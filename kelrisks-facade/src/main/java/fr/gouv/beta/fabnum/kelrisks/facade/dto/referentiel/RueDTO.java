package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class RueDTO {
    
    String     nomVoie;
    String     id_fantoir;
    String     alias;
    String     nom_afnor;
    String     libelle_acheminement;
    CommuneDTO commune;
    
    private Long id;
}
  