package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.AdresseDataGouvPaginatedFeatures;

import java.util.List;

public interface IAdresseDataGouvService {
    
    List<AdresseDataGouvPaginatedFeatures.Adresse> rechercherCommune(String codeINSEE);
}
