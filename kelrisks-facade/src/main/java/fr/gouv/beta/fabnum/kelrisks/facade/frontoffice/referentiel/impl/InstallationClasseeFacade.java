package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IInstallationClasseeMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IInstallationClasseeService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.InstallationClasseeQO;

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
    public List<InstallationClasseeDTO> rechercherInstallationsAuCentroideCommune(String codePostal) {
        
        InstallationClasseeQO installationClasseeQO = new InstallationClasseeQO();
        
        installationClasseeQO.setCodePostal(codePostal);
        installationClasseeQO.setCentroideCommune(true);
        
        List<InstallationClasseeDTO> installationClasseeDTOs = installationClasseeMapper.toDTOs(installationClasseeService.rechercherAvecCritere(installationClasseeQO));
        
        return installationClasseeDTOs;
    }
    
    @Override
    public List<InstallationClasseeDTO> rechercherInstallationsDansPolygon(Geometry multiPolygon) {
        
        List<InstallationClasseeDTO> installationClasseeDTOs = installationClasseeMapper.toDTOs(installationClasseeService.rechercherSitesDansPolygon(multiPolygon));
        
        return installationClasseeDTOs;
    }
}
