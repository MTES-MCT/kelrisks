<template>
    <div>
        <section class="section section-white">

            <errors ref="resultsERRIAL"/>

            <br/>

            <div id="searchButtonsWrapper">
                <a @click="$emit('flow', -1)"
                   class="bouton">
                    <font-awesome-icon icon="chevron-left"/>
                    Revenir au résultat
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

            <div class="container bordered"
                 id="aide"
                 v-if="avis.ppr && avis.ppr.length > 0">
                <div>
                    <div style="margin-bottom: 20px"><span class="title">Aide</span></div>
                    <p>Le bien est sur une parcelle concernée par un ou plusieurs Plans de Prévention des Risques (PPR). Il peut être concerné par l’obligation de réaliser certains travaux. Vous devez
                       consulter le PPR sur le site de la préfecture afin de vous assurer de cela. S’il existe des prescriptions de travaux, il est nécessaire de préciser si les travaux ont été
                       réalisés ou non. Si le bien a fait l'objet d'un ou plusieurs sinistres indemnisés au titre des catastrophes naturelles ou des catastrophes technologiques, les sinistres survenus
                       doivent être indiqués.</p>
                    <p>Si vous ne connaissez pas ces informations, choisissez « À préciser plus tard », vous pourrez les remplir sur le document final.</p>
                    <p>Si les informations saisies ne sont pas sincères, cela peut entraîner des risques juridiques par la suite.</p><br>
                </div>
            </div>

            <template v-if="avis.ppr && avis.ppr.length > 0">
                <div class="container bordered ppr"
                     v-bind:key="'plan_' + index"
                     v-for="(plan, index) in avis.ppr">
                    <div>
                        <div class="errial_title"><span class="title">{{ plan.alea.familleAlea.libelle }}</span></div>
                        <p>Rappel du risque : {{ plan.alea.familleAlea.libelle }},
                           {{ plan.alea.libelle }}{{ (plan.dateApprobation ? ', PPR approuvé le ' + formatDate(plan.dateApprobation) : ', PPR prescrit le ' + formatDate(plan.datePrescription)) }}.</p>
                        <div class="text_wrapper"><b>L'immeuble est concerné par des prescriptions de travaux dans le règlement du {{ plan.alea.familleAlea.famillePPR.libelle }}</b></div>
                        <div class="input_wrapper">
                            <label><input :name="'pre_' + plan.idGaspar"
                                          value="1"
                                          type="radio">Oui</label>
                            <label><input :name="'pre_' + plan.idGaspar"
                                          value="0"
                                          type="radio">Non</label>
                            <label><input :name="'pre_' + plan.idGaspar"
                                          value="-1"
                                          checked="checked"
                                          type="radio">À préciser plus tard</label></div>

                        <div class="text_wrapper"><b>Si oui, les travaux prescrits ont été réalisés</b></div>
                        <div class="input_wrapper">
                            <label><input :name="'tra_' + plan.idGaspar"
                                          value="1"
                                          type="radio">Oui</label>
                            <label><input :name="'tra_' + plan.idGaspar"
                                          value="0"
                                          type="radio">Non</label>
                            <label><input :name="'tra_' + plan.idGaspar"
                                          checked="checked"
                                          value="-1"
                                          type="radio">À préciser plus tard</label></div>
                    </div>
                </div>
            </template>

            <div class="container bordered ppr">
                <div>
                    <div class="errial_title"><span class="title">Information relative aux sinistres indemnisés par l'assurance suite à une catastrophe naturelle, minière ou technologique</span></div>
                    <p>Le bien a-t-il fait l'objet d'indemnisation par une assurance suite à des dégâts liés à une catastrophe ?</p>
                    <div class="input_wrapper">
                        <label><input name="cat"
                                      value="1"
                                      type="radio">Oui</label>
                        <label><input name="cat"
                                      value="0"
                                      type="radio">Non</label>
                        <label><input checked="checked"
                                      name="cat"
                                      value="-1"
                                      type="radio">À préciser plus tard</label></div>
                </div>
            </div>

            <div id="bottomButtonsWrapper">
                <a @click="download"
                   class="bouton success"
                   target="_blank">
                    Télécharger l'État des risques
                    <font-awesome-icon class="end"
                                       icon="chevron-right"/>
                </a>
            </div>

        </section>
    </div>
</template>

<script>

import Errors from "../base/Errors";
import JQuery from 'jquery'
import moment from "moment";

let $ = JQuery

export default {
    name: 'CompleterERRIAL',
    components: {
        Errors
    },
    props: {
        avis: {
            type: Object,
            default: () => {
            }
        }
    },
    computed: {
        _paq: function () {
            return window._paq
        }
    },
    methods: {
        formatDate (date) {
            return moment(date).format('DD/MM/YYYY')
        },
        download () {

            let errial = ''

            $("[name^='pre_']:checked").each(function () {
                if ($(this).val() !== "-1") {
                    errial += $(this).attr('name') + ":" + $(this).val() + ";"
                }
            })

            $("[name^='tra_']:checked").each(function () {
                if ($(this).val() !== "-1") {
                    errial += $(this).attr('name') + ":" + $(this).val() + ";"
                }
            })

            let catnat = $("[name='cat']:checked")

            if (catnat.val() !== "-1") {
                errial += "cat:" + catnat.val() + ";"
            }

            this.$emit('errial', errial)
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

.text_wrapper {
	clear          : both;
	flex           : 1 0 50%;
	padding-bottom : 25px;
	text-align     : end;
}

.input_wrapper {
	flex : 1 0 350px;
}

.input_wrapper label {
	display     : inline-block;
	margin-left : 20px;
}

#aide {
	background-color : #DAF5E7;
	border-color     : #5CD495;
}
</style>
