<template>
    <div>
        <section class="section section-white">

            <errors ref="resultsERRIAL"/>

            <br/>

            <div id="searchButtonsWrapper">
                <a @click="$emit('flow', -1)"
                   class="bouton">
                    <font-awesome-icon icon="chevron-left"/>
                    Modifier l'État des risques
                </a>

                <a :href="env.basePath"
                   class="bouton">
                    <font-awesome-icon icon="search"/>
                    Nouvelle recherche
                </a>
            </div>

            <div id="actionButtonsWrapper"></div>

            <div class="container bordered">
                <div>
                    <div style="margin-bottom: 20px"><span class="title">Parcelle(s) </span></div>
                    <span class="rightAlign">Adresse&nbsp;: </span><b><span v-if="avis.summary.adresse">{{ avis.summary.adresse }}, <br/><span class="rightAlign"/>{{ avis.summary.commune.codePostal }} {{
                        avis.summary.commune.nomCommune
                                                                                                        }}</span><span v-else-if="avis.summary.commune">{{
                        avis.summary.commune.codePostal
                                                                                                                                                        }}, {{ avis.summary.commune.nomCommune }}</span><span v-else><i>n/a</i></span></b><br/>
                    <span class="rightAlign">Code parcelle&nbsp;: </span><b><span v-if="avis.summary.codeParcelle && avis.summary.codeParcelle !== ''">{{
                        avis.summary.codeParcelle
                                                                                                                                                       }}</span><span v-else><i>n/a</i></span></b><br/>
                    <br>
                </div>
            </div>

            <div class="container bordered ppr">
                <div>
                    <div class="errial_title"><span class="title">Télécharger l'ERRIAL « État des Risques Réglementés - Information des Acquéreurs et des Locataires »</span></div>
                    <p>Il appartient au propriétaire du bien de vérifier l'exactitude de ces informations autant que de besoin et, le cas échéant, de le compléter à partir d'informations disponibles
                       sur le site internet de la préfecture ou d'informations dont il dispose sur le bien, notamment les sinistres que le bien a subis.</p>

                    <div style="margin: 0 auto 25px; width: 350px;">
                        <leaflet :data="currentData"
                                 :max-zoom-center="leaflet.center"
                                 :min-zoom="14"
                                 :parcelle="leaflet.data.parcelles"
                                 @png="pngGenerated"
                                 v-show="currentData !== ''"/>
                    </div>
                    <div id="pdf"
                         @click="getPdf">
                        <a><span style="font-size: 5em;"><font-awesome-icon icon="file-pdf"/></span></a><br/>
                        <a>État des risques au format PDF</a>
                    </div>
                    <!--                    <p>En savoir plus sur les risques, consulter <a href="https://www.georisques.gouv.fr/">Géorisque</a>.</p>-->
                </div>
            </div>

        </section>
    </div>
</template>

<script>

import Errors from "../base/Errors";
import Leaflet from "../leaflet/LeafletPdf";
import fetchWithError from "../../../script/fetchWithError";
import moment from "moment"
import mixinAvisHas from "../../mixins/avisHas";

export default {
    name: 'TelechargerERRIAL',
    mixins: [mixinAvisHas],
    components: {
        Leaflet,
        Errors
    },
    props: {
        leaflet: {
            type: Object,
            default: () => {
            }
        },
        form: {
            type: Object,
            default: () => {
            }
        },
        errial: {
            type: String,
            default: ""
        }
    },
    data: () => ({
        env: {
            basePath: process.env.VUE_APP_FRONT_PATH,
            apiPath: process.env.VUE_APP_BACK_API_PATH
        },
        dataList: [],
        currentData: '',
        currentPngName: '',
        pngList: [],
    }),
    methods: {
        generatePngs () {
            console.log('generatePngs')

            this.dataList = []

            this.dataList.push([
                [{
                    data: this.leaflet.data.parcelles,
                    color: '#455674'
                }],
                'carte-parcelle'])

            for (let plan in this.avis.ppr) {
                plan = this.avis.ppr[plan]
                this.dataList.push([
                    [{
                        data: plan.assiettes,
                        color: '#840505'
                    }],
                    plan.alea.familleAlea.code])
            }

            if (this.hasSismicite) this.dataList.push([
                typeof this.avis.summary.commune.communesLimitrophes.map === 'function' ?
                    [{data: this.avis.summary.commune.codeZoneSismicite === '1' ? [this.avis.summary.commune.multiPolygon] : [], color: '#D8D8D8'},
                        {data: this.avis.summary.commune.codeZoneSismicite === '2' ? [this.avis.summary.commune.multiPolygon] : [], color: '#FFD332'},
                        {data: this.avis.summary.commune.codeZoneSismicite === '3' ? [this.avis.summary.commune.multiPolygon] : [], color: '#FF8000'},
                        {data: this.avis.summary.commune.codeZoneSismicite === '4' ? [this.avis.summary.commune.multiPolygon] : [], color: '#E02B17'},
                        {data: this.avis.summary.commune.codeZoneSismicite === '5' ? [this.avis.summary.commune.multiPolygon] : [], color: '#840505'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '1').map(x => x.multiPolygon), color: '#D8D8D8'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '2').map(x => x.multiPolygon), color: '#FFD332'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '3').map(x => x.multiPolygon), color: '#FF8000'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '4').map(x => x.multiPolygon), color: '#E02B17'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '5').map(x => x.multiPolygon), color: '#840505'}] :
                    undefined,
                "SISMICITE"])

            if (this.hasRadonHaut || this.hasRadonMoyen) this.dataList.push([
                typeof this.avis.summary.commune.communesLimitrophes.map === 'function' ?
                    [{data: this.avis.summary.commune.classePotentielRadon === '1' ? [this.avis.summary.commune.multiPolygon] : [], color: '#FFD334'},
                        {data: this.avis.summary.commune.classePotentielRadon === '2' ? [this.avis.summary.commune.multiPolygon] : [], color: '#FF8002'},
                        {data: this.avis.summary.commune.classePotentielRadon === '3' ? [this.avis.summary.commune.multiPolygon] : [], color: '#840507'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '1').map(x => x.multiPolygon), color: '#FFD334'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '2').map(x => x.multiPolygon), color: '#FF8004'},
                        {data: this.avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '3').map(x => x.multiPolygon), color: '#840507'}] :
                    undefined,
                "RADON"])

            if (this.hasPEB) this.dataList.push([
                typeof this.avis.plansExpositionBruit.map === 'function' ?
                    [{data: this.avis.plansExpositionBruit.filter(x => x.zone === 'D').map(x => x.multiPolygon), color: '#058E0C'},
                        {data: this.avis.plansExpositionBruit.filter(x => x.zone === 'C').map(x => x.multiPolygon), color: '#FFD332'},
                        {data: this.avis.plansExpositionBruit.filter(x => x.zone === 'B').map(x => x.multiPolygon), color: '#FF8000'},
                        {data: this.avis.plansExpositionBruit.filter(x => x.zone === 'A').map(x => x.multiPolygon), color: '#840505'}] :
                    undefined,
                "PEB"])

            if (this.hasPollutionNonReglementaire) this.dataList.push([
                typeof this.avis.plansExpositionBruit.map === 'function' ?
                    [{data: this.avis.installationClasseeRayonParcelle.liste.map(x => x.ewkt), color: '#8E0800'},
                        {data: this.avis.basiasRayonParcelle.liste.map(x => x.ewkt), color: '#9E9E00'},
                        {data: this.avis.basolRayonParcelle.liste.map(x => x.ewkt), color: '#925600'}] :
                    undefined,
                "POLLUTION_NON_REG"])

            if (this.hasArgile) this.dataList.push([
                typeof this.avis.plansExpositionBruit.map === 'function' ?
                    [{data: this.avis.lentillesArgile.filter(x => x.niveauAlea === 1).map(x => x.multiPolygon), color: '#FFE340'},
                        {data: this.avis.lentillesArgile.filter(x => x.niveauAlea === 2).map(x => x.multiPolygon), color: '#FF9020'},
                        {data: this.avis.lentillesArgile.filter(x => x.niveauAlea === 3).map(x => x.multiPolygon), color: '#841520'}] :
                    undefined,
                "ARGILE"])

            if (this.hasCanalisations) this.dataList.push([
                typeof this.avis.plansExpositionBruit.map === 'function' ?
                    [{data: this.avis.canalisations, color: '#2A4999'}] :
                    undefined,
                "CANALISATIONS"])

            this.feedLeaflet();
        },
        pngGenerated (png) {
            console.log('pngGenerated')
            console.log(png)

            this.pushCurrentPng(png)
        },
        debounceFetchPdf () {
            if (this.debounce) clearTimeout(this.debounce);
            this.debounce = setTimeout(() => {
                // do the work here
                this.fetchPdf()
                this._paq.push(['trackEvent', 'Flow', 'Pdf'])
            }, 100);
        },
        getPdf () {
            console.log('getPdf')

            this.generatePngs()
        },
        feedLeaflet () {
            console.log("feedLeaflet")

            console.log(this.dataList)

            while (this.dataList.length !== 0) {

                let data = this.dataList.shift()

                // console.log(data)

                if (data && data.length > 0) {
                    this.currentPngName = data[1]
                    this.currentData = data[0]
                    return
                }
            }

            this.currentData = ''
            this.debounceFetchPdf()
        },
        fetchPdf () {
            // console.log('fetchPdf')

            fetchWithError(this.env.apiPath + 'avis/pdf?' +
                'codeINSEE=' + this.form.codeInsee + '&' +
                'codeParcelle=' + this.form.selectedParcellesList.join(',') + '&' +
                'errial=' + this.avis.errial,
                {
                    method: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.pngList)
                },
                1000 * 30)
                .then(resp => resp.arrayBuffer())
                .then(resp => {
                    return new Blob([resp], {type: "application/pdf"})
                })
                .then(resp => {
                    const fileURL = window.URL.createObjectURL(resp)
                    const link = document.createElement('a')
                    link.href = fileURL
                    link.download = "Kelrisks_Parcelle_" + this.form.codeParcelle + "_(" + this.avis.summary.commune.codePostal + ")_" + moment(new Date()).format('DD/MM/YYYY') + ".pdf"
                    link.click()
                    // window.location.assign(fileURL);
                })
        },
        pushCurrentPng (png) {
            console.log('Push currentPng')

            if (png) this.pngList.push({name: this.currentPngName, png: png})
            this.feedLeaflet()
        }
    },
    computed: {
        _paq: function () {
            return window._paq
        }
    }
}
</script>

<style scoped>

#searchButtonsWrapper {
	float : left;
}

#searchButtonsWrapper a,
#actionButtonsWrapper a,
#bottomButtonsWrapper a {
	display : inline-block;
	float   : none;
}

#bottomButtonsWrapper {
	flex       : 0 0 100%;
	margin-top : 25px;
	text-align : center;
}

#actionButtonsWrapper {
	float : right;
}

@media (min-width : 630px) {
	#searchButtonsWrapper a:last-of-type,
	#actionButtonsWrapper a:last-of-type {
		margin-right : 0;
	}
}

@media (max-width : 1350px) {

	#searchButtonsWrapper {
		text-align : center;
		width      : 100%;
	}

	#actionButtonsWrapper {
		text-align : center;
		width      : 100%;
	}
}

@media (max-width : 630px) {
	#searchButtonsWrapper a,
	#actionButtonsWrapper a {
		margin-left  : 10px;
		margin-right : 10px;
	}
}

.container {
	max-width : unset;
}

.container.bordered {
	background-color : #FFFFFF;
	border           : 1px solid #CCCCCC;
	border-radius    : 2px;
	display          : flex;
	margin-bottom    : 20px;
	padding          : 20px;
	text-align       : left;
	width            : 100%;
}

.container.bordered span {
	line-height : 25px;
}

.container.bordered span.rightAlign {
	display       : inline-block;
	padding-right : 5px;
	text-align    : right;
	width         : 150px;
}

.container.bordered.ppr > div {
	display   : flex;
	flex-wrap : wrap;
}

.container.bordered.ppr div.errial_title {
	flex          : 1 0 100%;
	margin-bottom : 20px;
}

.container.bordered.ppr p {
	flex : 1 0 100%;
}

#pdf {
	margin     : 25px;
	text-align : center;
	width      : 100%;
}
</style>
