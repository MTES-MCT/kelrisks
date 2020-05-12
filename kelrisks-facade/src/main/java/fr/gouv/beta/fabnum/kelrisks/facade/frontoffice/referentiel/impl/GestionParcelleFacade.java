package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IParcelleMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionParcelleFacade extends AbstractFacade implements IGestionParcelleFacade {
    
    @Autowired
    IParcelleMapper  parcelleMapper;
    @Autowired
    IParcelleService parcelleService;
    
    @Override
    public ParcelleDTO rechercherResultatUniqueAvecCritere(ParcelleQO parcelleQO) {
        
        ParcelleDTO parcelleDTO = parcelleMapper.toDTO(parcelleService.rechercherResultatUniqueAvecCritere(parcelleQO));
        
        return parcelleDTO;
    }
    
    @Override
    public List<ParcelleDTO> rechercherAvecCritere(ParcelleQO parcelleQO) {
        
        List<ParcelleDTO> parcelleDTOs = parcelleMapper.toDTOs(parcelleService.rechercherAvecCritere(parcelleQO));
        
        return parcelleDTOs;
    }
    
    @Override
    public List<ParcelleDTO> rechercherParcellesContigues(Geometry<?> geom) {
    
        return parcelleMapper.toDTOs(parcelleService.rechercherParcellesContigues(geom));
    }
    
    @Override
    public ParcelleDTO rechercherParcelleAvecCoordonnees(double x, double y) {

        ParcelleDTO parcelleDTO = parcelleMapper.toDTO(parcelleService.rechercherClosestParcelleAvecCoordonnees(x, y));
        
        return parcelleDTO;
    }
    
    @Override
    public Geometry<?> rechercherExpendedParcelle(Geometry<?> parcelleGeog, double distance) {
    
        return parcelleService.rechercherExpendedParcelle(parcelleGeog, distance / 100000D);
    }
    
    @Override
    public Geometry<?> rechercherUnionParcellesContigues(Geometry<?> polygon) {
        
        return parcelleService.rechercherUnionParcellesContigues(polygon);
    }
    
    @Override
    public Geometry<?> rechercherCentroidParcelle(Geometry<?> polygon) {
        
        return parcelleService.rechercherCentroidParcelle(polygon);
    }
    
    @Override
    public ParcelleDTO rechercherParcellesIntersectionnantSurface(Geometry<?> polygon) {
    
        return parcelleMapper.toDTO(parcelleService.rechercherParcellesIntersectionnantSurface(polygon));
    }
    
    @Override
    public List<ParcelleDTO> rechercherParcellesDansRayon(double x, double y, double radius) {
        
        return parcelleMapper.toDTOs(parcelleService.rechercherParcellesDansRayon(x, y, radius));
    }
    
    @Override
    public Geometry<?> rechercherUnionParcelles(List<Long> ids) {
        
        return parcelleService.rechercherUnionParcelles(ids);
    }
}
