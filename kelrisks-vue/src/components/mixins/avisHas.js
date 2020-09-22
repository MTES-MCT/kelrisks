export default {
    props: {
        avis: {
            type: Object,
            default: () => {
            }
        },
    },
    data: () => ({}),
    methods: {
        hasTypePPR (type) {
            for (let plan in this.avis.ppr) {
                plan = this.avis.ppr[plan]
                if (plan.alea.familleAlea.famillePPR.code === type) return true
            }
            return false
        },
    },
    computed: {
        hasPollutionPrincipale: function () {
            return this.avis.installationClasseeParcelle.numberOf > 0 ||
                this.avis.sisParcelle.numberOf > 0
        },
        hasPollutionNonReglementaire: function () {
            return this.avis.installationClasseeRayonParcelle.numberOf > 0 || this.avis.basiasRayonParcelle.numberOf > 0 || this.avis.basolRayonParcelle.numberOf > 0
        },
        numberOfParcelleMatches: function () {
            return this.avis.installationClasseeParcelle.numberOf +
                this.avis.basolParcelle.numberOf +
                this.avis.basiasParcelle.numberOf +
                this.avis.sisParcelle.numberOf
        },
        hasPollutionCentroidCommune: function () {
            return this.avis.basiasNonGeorerencee.numberOf > 0 ||
                this.avis.basolNonGeorerencee.numberOf > 0 ||
                this.avis.installationClasseeNonGeorerencee.numberOf > 0 ||
                this.avis.sisNonGeorerencee.numberOf > 0
        },
        concordances: function () {
            var numberOfInstallationClasseeParcelle = isNaN(this.avis.installationClasseeParcelle.numberOf) ? 0 : this.avis.installationClasseeParcelle.numberOf
            var numberOfBasolParcelle = isNaN(this.avis.basolParcelle.numberOf) ? 0 : this.avis.basolParcelle.numberOf
            var numberOfBasiasParcelle = isNaN(this.avis.basiasParcelle.numberOf) ? 0 : this.avis.basiasParcelle.numberOf
            var numberOfSisParcelle = isNaN(this.avis.sisParcelle.numberOf) ? 0 : this.avis.sisParcelle.numberOf

            return numberOfInstallationClasseeParcelle + numberOfBasolParcelle + numberOfBasiasParcelle + numberOfSisParcelle
        },
        concordances_risques: function () {
            return this.avis.potentielRadon >= 3 || this.avis.codeSismicite >= 3
        },
        hasRadonHaut: function () {
            return this.avis.summary.commune.classePotentielRadon === '3'
        },
        hasRadonMoyen: function () {
            return this.avis.summary.commune.classePotentielRadon === '2'
        },
        hasArgile: function () {
            return this.avis.lentillesArgile !== undefined && this.avis.lentillesArgile !== null && this.avis.lentillesArgile.length > 0
        },
        hasPEB: function () {
            return this.avis.zonePlanExpositionBruit !== undefined && this.avis.zonePlanExpositionBruit !== null
        },
        hasSismiciteTresHaute: function () {
            return this.avis.summary.commune.codeZoneSismicite === '4' ||
                this.avis.summary.commune.codeZoneSismicite === '5'
        },
        hasSismiciteHaute: function () {
            return this.avis.summary.commune.codeZoneSismicite === '3'
        },
        hasSismiciteMoyenne: function () {
            return this.avis.summary.commune.codeZoneSismicite === '2'
        },
        hasSismicite: function () {
            return this.hasSismiciteMoyenne || this.hasSismiciteHaute || this.hasSismiciteTresHaute
        },
        hasPPR: function () {
            return this.avis.ppr.length > 0
        },
        hasPPRN: function () {
            if (!this.hasPPR) return false
            return this.hasTypePPR('PPRN')
        },
        hasPPRM: function () {
            if (!this.hasPPR) return false
            return this.hasTypePPR('PPRM')
        },
        hasPPRT: function () {
            if (!this.hasPPR) return false
            return this.hasTypePPR('PPRT')
        },
        hasPPRi: function () {
            for (let plan in this.avis.ppr) {
                plan = this.avis.ppr[plan]
                if (plan.alea.familleAlea.code === '11') return true // ('11', 'Inondation');
            }
            return false
        },
        hasTRI: function () {
            if (this.avis.TRIs === null) return false
            return this.avis.TRIs.length > 0
        },
        hasAZI: function () {
            if (this.avis.AZIs === null) return false
            return this.avis.AZIs.length > 0
        }
    }
}
