package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IParcelleDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.ParcelleRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QParcelle;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.geolatte.geom.codec.Wkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table parcelle
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("parcelleDAO")
public class ParcelleDAO extends AbstractDAO<Parcelle> implements IParcelleDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QParcelle parcelle;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private ParcelleRepository parcelleRepository;
    
    @Autowired
    public ParcelleDAO(@Qualifier("parcelleRepository") ParcelleRepository repo) {
        
        this.setRepo(repo);
        this.parcelleRepository = repo;
        this.parcelle = QParcelle.parcelle;
    }
    
    @Override
    public Parcelle rechercherClosestParcelleAvecPoint(Geometry<?> point) {
    
        return parcelleRepository.rechercherClosestParcelleAvecPoint(point);
    }
    
    @Override
    public Geometry<?> rechercherExpendedParcelle(Geometry<?> parcelleGeog, double distance) {
    
        String wkt = parcelleRepository.rechercherExpendedParcelle(parcelleGeog, distance);
        return Wkt.fromWkt(wkt);
    }
    
    @Override
    public List<Parcelle> rechercherParcellesContigues(Geometry<?> polygon) {
        
        return parcelleRepository.rechercherParcellesContigues(polygon);
    }
    
    @Override
    public Parcelle rechercherClosestParcelleAvecCoordonnees(double x, double y) {
    
        return parcelleRepository.rechercherClosestParcelleAvecCoordonnees(x, y);
    }
    
    @Override
    public Geometry<?> rechercherUnionParcellesContigues(Geometry<?> polygon) {
        
        //        List<Parcelle> parcelles  = parcelleRepository.rechercherParcellesContigues(polygon);
        //        Geometry[]     geometries = parcelles.stream().map(Parcelle::getMultiPolygon).toArray(Geometry[]::new);
        //        List<Geometry<?>> geometries = parcelles.stream().limit(2).map(Parcelle::getMultiPolygon).collect(Collectors.toList());
        
        String wkt = parcelleRepository.rechercherUnionParcellesContigues(polygon);
        if (wkt == null) { return polygon; }
        return Wkt.fromWkt(wkt);
    }
    
    @Override
    public Geometry<?> rechercherCentroidParcelle(Geometry<?> polygon) {
        
        String wkt = parcelleRepository.rechercherCentroidParcelle(polygon);
        return Wkt.fromWkt(wkt);
    }
    
    @Override
    public Geometry<?> rechercherParcellesIntersectionnantSurface(Geometry<?> polygon) {
    
        return Wkt.fromWkt(parcelleRepository.rechercherParcellesIntersectionnantSurface(polygon));
    }
    
    @Override
    public List<Parcelle> rechercherParcellesDansRayon(double x, double y, double radius) {
        
        return parcelleRepository.rechercherParcellesDansRayon(x, y, radius);
    }
    
    @Override
    public Geometry<?> rechercherUnionParcelles(List<Long> ids) {
        
        return Wkt.fromWkt(parcelleRepository.rechercherUnionParcelles(ids));
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<Parcelle> getPathBuilder() {
        
        return new PathBuilder<>(Parcelle.class, "parcelle");
    }
    
    @Override
    protected EntityPathBase<Parcelle> getQueryObject() {
        
        return parcelle;
    }
}
  