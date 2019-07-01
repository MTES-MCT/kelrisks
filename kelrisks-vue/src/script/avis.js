export default {

  getBasiasParcelle (value) {
    // console.log('getBasiasParcelle')
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasSurParcelleDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'N’est pas référencée dans l\'inventaire des sites ayant accueilli par le passé une activité susceptible d\'avoir pu généré une pollution des sols (BASIAS).'
    } else {
      avis.lib = 'est référencée dans l\'inventaire des sites ayant accueilli par le passé une activité susceptible d\'avoir pu généré une pollution des sols (BASIAS).'
      value.entity.siteIndustrielBasiasSurParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasiasProximiteParcelle (value) {
    // console.log('getBasiasProximiteParcelle')
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasProximiteParcelleDTOs.length
    if (avis.numberOf !== 0) {
      avis.lib = avis.numberOf === 1
          ? 'dans le voisinage immédiat de la (ou des) parcelle(s), un site  ayant accueilli par le passé une activité susceptible d\'avoir pu générer une pollution des sols (BASIAS). Vous pouvez consulter la fiche consacrée à cette activité industrielle à l\'adresse suivante : '
          : 'dans le voisinage immédiat de la (ou des) parcelle(s), des sites  ayant accueilli par le passé une activité susceptible d\'avoir pu générer une pollution des sols (BASIAS). Vous pouvez consulter les fiches consacrée à cette activité industrielle à l\'adresse suivante : '
      value.entity.siteIndustrielBasiasProximiteParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasiasRaisonSocialeParcelle (value) {
    // console.log('getBasiasRaisonSociale')
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasiasParRaisonSocialeDTOs.length
    if (avis.numberOf !== 0) {
      avis.lib = 'un site dont la localisation est imprécise mais ayant potentiellement appartenu au même propriétaire (' + value.entity.nomProprietaire + ') : '
      value.entity.siteIndustrielBasiasParRaisonSocialeDTOs.forEach(function (element) {
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

  getBasolParcelle (value) {
    let avis = {
      lib: '',
      liste: []
    }
    avis.numberOf = value.entity.siteIndustrielBasolSurParcelleDTOs.length
    if (avis.numberOf === 0) {
      avis.lib = 'N’est pas référencée dans l\'inventaire des sites pollués BASOL.'
    } else {
      avis.lib = 'Est référencée dans l\'inventaire des sites pollués BASOL.'
      value.entity.siteIndustrielBasolSurParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getBasolProximiteParcelle () {
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
      avis.lib = 'Est référencée dans l\'inventaire des installations classées sous le nom de :'
      value.entity.installationClasseeSurParcelleDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  },

  getSISSurParcelle () {
    let avis = {
      lib: '',
      liste: []
    }
    // avis.numberOf = value.entity.installationClasseeSurParcelleDTOs.length
    avis.numberOf = 0
    // if (avis.numberOf === 0) {
    avis.lib = 'N’est pas situé en secteur d’information sur les sols (SIS).'
    // } else {
    //   avis.lib = 'Est référencée dans l\'inventaire des installations classées sous le nom de + nom de l’activité'
    //   value.entity.installationClasseeSurParcelleDTOs.forEach(function (element) {
    //     avis.liste.push(element)
    //   }, this)
    // }
    return avis
  },

  getICProximiteParcelle () {
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
      avis.lib = 'Le résultat de cette recherche ne tient pas compte des ' + avis.numberOf + ' sites identifiés sur la commune qui n’ont pu être géolocalisés faute d’une information suffisante : '
      value.entity.installationClasseeNonGeorerenceesDTOs.forEach(function (element) {
        avis.liste.push(element)
      }, this)
    }
    return avis
  }
}
