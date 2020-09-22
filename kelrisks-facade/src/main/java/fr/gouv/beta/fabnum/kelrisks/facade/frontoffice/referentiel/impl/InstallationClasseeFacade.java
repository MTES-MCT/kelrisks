package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IInstallationClasseeMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IInstallationClasseeService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.enums.PrecisionEnum;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.InstallationClasseeQO;

import java.util.Arrays;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstallationClasseeFacade extends AbstractFacade implements IGestionInstallationClasseeFacade {
    
    @Autowired
    IInstallationClasseeMapper  installationClasseeMapper;
    @Autowired
    IInstallationClasseeService installationClasseeService;
    
    @Override
    public List<InstallationClasseeDTO> rechercherInstallationsSurParcelle(String codeParcelle) {
        
        List<InstallationClasseeDTO> installationClasseeDTOs = installationClasseeMapper.toDTOs(installationClasseeService.rechercherInstallationsSurParcelle(codeParcelle));
        
        return installationClasseeDTOs;
    }
    
    @Override
    public List<InstallationClasseeDTO> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance) {
        
        List<InstallationClasseeDTO> installationClasseeDTOs = installationClasseeMapper.toDTOs(installationClasseeService.rechercherInstallationsDansRayonCentroideParcelle(codeParcelle, distance));
        
        return installationClasseeDTOs;
    }
    
    @Override
    public List<InstallationClasseeDTO> rechercherInstallationsAvecFaiblePrecisionDeGeolocalisation(String codeINSEE) {

        InstallationClasseeQO installationClasseeQO = new InstallationClasseeQO();
    
        installationClasseeQO.setCodeINSEE(codeINSEE);
        List<String> precision = Arrays.asList(PrecisionEnum.S3IC_COMMUNE.getCode(), null);
        installationClasseeQO.setPrecisions(precision);
    
        List<InstallationClasseeDTO> installationClasseeDTOs = installationClasseeMapper.toDTOs(installationClasseeService.rechercherAvecCritere(installationClasseeQO));
    
        return installationClasseeDTOs;
    }
    
    @Override
    public List<InstallationClasseeDTO> rechercherInstallationsDansPolygons(List<Geometry<?>> multiPolygon) {
    
        return installationClasseeMapper.toDTOs(installationClasseeService.rechercherSitesDansPolygons(multiPolygon));
    }
    
    @Override
    public List<InstallationClasseeDTO> rechercherInstallationsSurParcelles(List<String> codes) {
        
        return installationClasseeMapper.toDTOs(installationClasseeService.rechercherInstallationsSurParcelles(codes));
    }
    
    @Override
    public List<AutocompleteDTO> rechercherRaisonsSociales(String codeINSEE, String query) {
        
        return installationClasseeMapper.toAutocompleteDTOs(installationClasseeService.rechercherRaisonsSociales(codeINSEE, query));
    }
    
    @Override
    public List<InstallationClasseeDTO> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        return installationClasseeMapper.toDTOs(installationClasseeService.rechercherSitesDansPolygon(polygon));
    }
}
