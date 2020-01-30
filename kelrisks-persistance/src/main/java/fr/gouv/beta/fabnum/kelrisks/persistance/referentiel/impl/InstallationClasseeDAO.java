package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IInstallationClasseeDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.InstallationClasseeRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QInstallationClassee;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table installationClassee
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("installationClasseeDAO")
public class InstallationClasseeDAO extends AbstractDAO<InstallationClassee> implements IInstallationClasseeDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QInstallationClassee installationClassee;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private InstallationClasseeRepository installationClasseeRepository;
    
    @Autowired
    public InstallationClasseeDAO(@Qualifier("installationClasseeRepository") InstallationClasseeRepository repo) {
        
        this.setRepo(repo);
        this.installationClasseeRepository = repo;
        this.installationClassee = QInstallationClassee.installationClassee;
    }
    
    @Override
    public List<InstallationClassee> rechercherInstallationsSurParcelle(String codeParcelle) {
        
        return installationClasseeRepository.rechercherInstallationsSurParcelle(codeParcelle);
    }
    
    @Override
    public List<InstallationClassee> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance) {
        
        return installationClasseeRepository.rechercherInstallationsDansRayonCentroideParcelle(codeParcelle, distance);
    }
    
    @Override
    public List<InstallationClassee> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon.size() == 1) { return installationClasseeRepository.rechercherSitesDansPolygon(multiPolygon.get(0)); }
        else { return installationClasseeRepository.rechercherSitesDansPolygons(multiPolygon); }
    }
    
    @Override
    public List<InstallationClassee> rechercherInstallationsSurParcelles(List<String> codes) {
        
        return installationClasseeRepository.rechercherInstallationsSurParcelles(codes);
    }
    
    @Override
    public List<InstallationClassee> rechercherRaisonsSociales(String codeINSEE, String query) {
        
        return installationClasseeRepository.rechercherRaisonsSociales(codeINSEE, query);
    }
    
    @Override
    public List<InstallationClassee> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        return installationClasseeRepository.rechercherSitesDansPolygon(polygon);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<InstallationClassee> getPathBuilder() {
        
        return new PathBuilder<>(InstallationClassee.class, "installationClassee");
    }
    
    @Override
    protected EntityPathBase<InstallationClassee> getQueryObject() {
        
        return installationClassee;
    }
}
  