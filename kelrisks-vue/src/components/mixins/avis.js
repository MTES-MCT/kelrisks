import fetchWithError from "../../script/fetchWithError";
import avisHelper from "../../script/avisHelper";
import functions from "../../script/fonctions";

export default {
    data: () => ({
        querying: false,
        tinyUrl: {
            codeParcelle: undefined,
            codeInsee: undefined,
            nomAdresse: undefined,
            geolocAdresse: undefined,
            codeProprio: undefined
        },
        form: {
            codeInsee: '',
            nomAdresse: '',
            geolocAdresse: '',
            codeProprio: '',
            codeParcelle: ''
        },
        leaflet: {
            data: undefined,
            center: [0, 0]
        },
        avis: {
            summary: {
                adresse: {
                    rue: {}
                },
                commune: {}
            },
            basiasNbOf: 0,
            basiasParcelle: {},
            basiasProximiteParcelle: {},
            basiasRayonParcelle: {},
            basiasRaisonSociale: {},
            basiasNonGeorerencee: {},
            basolNbOf: 0,
            basolParcelle: {},
            basolProximiteParcelle: {},
            basolRayonParcelle: {},
            basolNonGeorerencee: {},
            s3ICNbOf: 0,
            installationClasseeParcelle: {},
            installationClasseeProximiteParcelle: {},
            installationClasseeRayonParcelle: {},
            installationClasseeNonGeorerencee: {},
            sisParcelle: {},
            sisNonGeorerencee: {},
            codeSismicite: 0,
            potentielRadon: 0,
            ppr: {}
        },
    }),
    methods: {
        getAvis () {
            this.$refs.searchErrors.clearAll()

            let parcelle = this.tinyUrl.codeParcelle ? this.tinyUrl.codeParcelle : this.form.codeParcelle
            let insee = this.tinyUrl.codeInsee ? this.tinyUrl.codeInsee : this.form.codeInsee
            let nom = this.tinyUrl.nomAdresse ? this.tinyUrl.nomAdresse : this.form.nomAdresse
            let geoloc = this.tinyUrl.geolocAdresse ? this.tinyUrl.geolocAdresse : this.form.geolocAdresse
            let proprio = this.tinyUrl.codeProprio ? this.tinyUrl.codeProprio : this.form.codeProprio

            if (!parcelle && !geoloc) {
                this.$refs.searchErrors.sendError('Merci de bien vouloir choisir une rue/numéro ou entrer une parcelle.')
                return
            }

            if (parcelle && !insee && !geoloc) {
                this.$refs.searchErrors.sendWarning('Dans le cas d\'une recherche par code parcelle, merci de choisir une commune parmi les résultats proposés dans le champ.')
                return
            }

            let url = this.env.apiPath + 'avis?' + 'codeINSEE=' + insee + '&' + 'codeParcelle=' + parcelle + '&' + 'nomAdresse=' + nom + '&' + 'geolocAdresse=' + geoloc.replace(/\|/, '%7C') + '&' + 'nomProprietaire=' + proprio

            this.querying = true

            fetchWithError(url, null, 1000 * 20)
                .then(stream => stream.json())
                .then(value => {

                    this.$refs.searchErrors.checkInformations(value.entity)

                    if (this.$refs.searchErrors.hasError) {
                        this._paq.push(['trackEvent', 'Flow', 'Informations', 'Erreur'])
                        // this.$refs.searchErrors.emitErrors()
                        return
                    }

                    if (this.$refs.searchErrors.hasWarning) {
                        this._paq.push(['trackEvent', 'Flow', 'Informations', 'Warning'])
                        // this.$refs.searchErrors.emitWarnings()
                        return
                    }

                    this.avis.summary = value.entity.summary
                    this._paq.push(['trackEvent', 'Flow', 'Informations', 'OK'])

                    this.avis.basiasParcelle = avisHelper.getBasiasParcelle(value)
                    this.avis.basiasProximiteParcelle = avisHelper.getBasiasProximiteParcelle(value)
                    this.avis.basiasRaisonSociale = avisHelper.getBasiasRaisonSocialeParcelle(value)
                    this.avis.basiasRayonParcelle = avisHelper.getBasiasRayonParcelle(value)
                    this.avis.basiasNonGeorerencee = avisHelper.getBasiasNonGeoreferencees(value)

                    this.avis.basolParcelle = avisHelper.getBasolParcelle(value)
                    this.avis.basolProximiteParcelle = avisHelper.getBasolProximiteParcelle(value)
                    this.avis.basolRayonParcelle = avisHelper.getBasolRayonParcelle(value)
                    this.avis.basolNonGeorerencee = avisHelper.getBasolNonGeoreferencees(value)

                    this.avis.installationClasseeParcelle = avisHelper.getICSurParcelle(value)
                    this.avis.installationClasseeProximiteParcelle = avisHelper.getICProximiteParcelle(value)
                    this.avis.installationClasseeRayonParcelle = avisHelper.getICRayonParcelle(value)
                    this.avis.installationClasseeNonGeorerencee = avisHelper.getICNonGeoreferencees(value)

                    this.avis.sisParcelle = avisHelper.getSISSurParcelle(value)
                    this.avis.sisNonGeorerencee = avisHelper.getSISNonGeoreferencees(value)

                    this.leaflet.data = value.entity.leaflet
                    this.leaflet.center = [parseFloat(value.entity.leaflet.center.y), parseFloat(value.entity.leaflet.center.x)]

                    this.avis.codeSismicite = value.entity.codeZoneSismicite
                    this.avis.potentielRadon = value.entity.classePotentielRadon

                    this.avis.ppr = value.entity.planPreventionRisquesDTOs

                    functions.scrollToElement('app', 500, 0, false)
                    this._paq.push(['trackEvent', 'Flow', 'Avis', 'Rendu'])

                    this.querying = false

                    this.$emit('flow', 1)
                    this.$emit('form', this.form)
                    this.$emit('avis', this.avis)
                    this.$emit('leaflet', this.leaflet)
                    this.$emit('tinyUrl', this.tinyUrl)
                })
                .catch(() => {

                    this.$refs.searchErrors.sendError('Votre requête n\'a pu aboutir dans un délais raisonnable, merci de réessayer ou de nous le signaler au moyen du formulaire de contact.')

                    this.querying = false
                })
        }
    }
}
