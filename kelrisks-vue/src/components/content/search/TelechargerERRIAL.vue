<template>
    <div>
        <section class="section section-white">

            <errors ref="resultsERRIAL"/>

            <br/>

            <div id="searchButtonsWrapper">
                <a @click="$emit('flow', -1)"
                   class="bouton">
                    <font-awesome-icon icon="chevron-left"/>
                    Modifier l'État des risques et pollution
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
                    <span class="rightAlign">Adresse&nbsp;: </span><b><span v-if="avis.summary.adresse">{{avis.summary.adresse}}, <br/><span class="rightAlign"/>{{avis.summary.commune.codePostal}} {{avis.summary.commune.nomCommune}}</span><span v-else-if="avis.summary.commune">{{avis.summary.commune.codePostal}}, {{avis.summary.commune.nomCommune}}</span><span v-else><i>n/a</i></span></b><br/>
                    <span class="rightAlign">Code parcelle&nbsp;: </span><b><span v-if="avis.summary.codeParcelle && avis.summary.codeParcelle !== ''">{{avis.summary.codeParcelle}}</span><span v-else><i>n/a</i></span></b><br/>
                    <br>
                </div>
            </div>

            <div class="container bordered ppr">
                <div>
                    <div class="errial_title"><span class="title">Télécharger l'ERP « État des risques et pollution »</span></div>
                    <p>Le propriétaire du bien concerné par l’état des risques et pollution est tenu de relire et compléter si nécessaire ces informations afin de s’assurer qu’elles sont
                       exhaustives.</p>
                    <div id="pdf">
                        <a><span style="font-size: 5em;"><font-awesome-icon icon="file-pdf"/></span></a><br/>
                        <a>État des risques et pollution au format PDF</a>
                    </div>
                    <p>En savoir plus sur les risques non règlementaires, consulter <a>Géorisque : Mieux connaître les risques sur le territoire</a>.</p>
                </div>
            </div>

            <div id="bottomButtonsWrapper"></div>

        </section>
    </div>
</template>

<script>

import Errors from "../base/Errors";

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
    data: () => ({
        env: {
            basePath: process.env.VUE_APP_PATH,
            apiPath: process.env.VUE_APP_API_PATH
        },
    }),
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
