export default {

  getBasiasParcelle (value) {
    console.log('getBasiasParcelle')
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasSurParcelleDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'n’est pas référencée dans l\'inventaire des sites ayant accueilli par le passé une activité susceptible d\'avoir pu généré une pollution des sols (BASIAS).'
    } else {
      avis.lib = 'est référencée dans l\'inventaire des sites ayant accueilli par le passé une activité susceptible d\'avoir pu généré une pollution des sols (BASIAS).'
      value.entity.siteIndustrielBasiasSurParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasiasProximiteParcelle (value) {
    console.log('getBasiasProximiteParcelle')
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasProximiteParcelleDTOs.length
    if (avis.numberOf === 0) {
    } else {
      avis.lib = 'Par ailleurs, nous identifions dans le voisinage immédiat de la (ou des) parcelle(s), un site ayant accueilli par le passé une activité susceptible d\'avoir pu générer une pollution des sols (BASIAS). Vous pouvez consulter la fiche consacrée à cette activité industrielle à l\'adresse suivante : '
      value.entity.siteIndustrielBasiasProximiteParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasiasRayonParcelle (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasRayonParcelleDTOs.length
    if (avis.numberOf > 0) {
      value.entity.siteIndustrielBasiasRayonParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    // console.log(avis)
    return avis
  },

  getBasiasRaisonSociale (value) {
    console.log('getBasiasRaisonSociale')
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasParRaisonSocialeDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'Aucun site Basias trouvé par raison sociale'
    } else {
      avis.lib = 'Sites Basias trouvé par raison sociale : '
      value.entity.siteIndustrielBasiasParRaisonSocialeDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasolParcelle (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasolSurParcelleDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'N’est pas référencée dans l\'inventaire des sites pollués BASOL.'
    } else {
      avis.lib = 'Est référencée dans l\'inventaire des sites pollués BASOL  + lien vers fiche Basol.'
      value.entity.siteIndustrielBasolSurParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasolProximiteParcelle (value) {
    return {numberOf: 0, lib: ''}
  },

  getBasolRayonParcelle (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasolRayonParcelleDTOs.length
    if (avis.numberOf > 0) {
      value.entity.siteIndustrielBasolRayonParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getICSurParcelle (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.installationClasseeSurParcelleDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'N’est pas référencée dans l\'inventaire des installations classées en fonctionnement ou arrêtées.'
    } else {
      avis.lib = 'Est référencée dans l\'inventaire des installations classées sous le nom de + nom de l’activité'
      value.entity.installationClasseeSurParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getICProximiteParcelle (value) {
    return {numberOf: 0, lib: ''}
  },

  getICRayonParcelle (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.installationClasseeRayonParcelleDTOs.length
    if (avis.numberOf > 0) {
      value.entity.installationClasseeRayonParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getICNonGeoreferencees (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.installationClasseeNonGeorerenceesDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'Aucune Installation Classée non géoréférencée dans la commune'
    } else {
      avis.lib = 'Installation(s) Classée(s) non géoréférencée(s) trouvée(s) dans la commune : '
      value.entity.installationClasseeNonGeorerenceesDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  }
}
