<template>
    <section class="section section-white"
             id="section3">
        <div class="container">
            <div class="panel">
                <h2 class="section__title title">Rechercher votre terrain</h2>
                <p class="subtitle">Saisissez le code de la parcelle à analyser</p>
                <p style="font-size: 16px; color: rgb(83, 101, 125); text-align: center">
                    <b>(les recherches par code parcelle sont plus pertinentes que par adresse)</b>
                </p>

                <errors ref="searchErrors"/>

                <br/>

                <div style="width: 40%; margin: 0 5%; float: left">
                    <kr-input :errors="error.field.codeCommune"
                              :get-option-label-function="(option => {return option['properties']['postcode'] + ' - ' + option['properties']['city']})"
                              :get-option-value-function="(option => {return option['properties']['citycode']})"
                              :get-results-list-function="(data => {return data['features']})"
                              :start-at="3"
                              @selected="onCodePostalSelected"
                              label="Nom de commune ou Code postal"
                              name="codePostal"
                              v-bind:source="'https://api-adresse.data.gouv.fr/search/?type=municipality&limit=10&q='">
                        <!--suppress JSUnresolvedVariable -->
                        <template slot="kr-option-label"
                                  slot-scope="slotProps">
                            {{ slotProps.option.properties.postcode + ' - ' + slotProps.option.properties.city}}
                        </template>
                        <!--suppress JSUnresolvedVariable -->
                        <template slot="kr-helper"
                                  slot-scope="slotProps">
                            INSEE&nbsp;: {{ slotProps.option.properties.citycode }}
                        </template>
                        <template slot="kr-no-results"
                                  slot-scope="slotProps">
                            Aucune commune trouvé pour "{{ slotProps.query }}"
                        </template>
                    </kr-input>
                </div>

                <div style="width: 40%; margin: 0 5%; float: left">
                    <kr-input @query="onCodeParcelleChanged"
                              label="Code Parcelle"
                              name="codeparcelle"
                              placeholder="BA-115 ou 912250000A0352">
                        <template slot="kr-helper">BA-115 ou 912250000A0352
                        </template>
                    </kr-input>
                </div>

                <div class="clearfix"></div>

                <p class="subtitle"
                   style="margin: 30px 0 15px 0; color: #8A8F96">Ou</p>

                <div style="width: 40%; margin-left: 30%">
                    <!--                <div style="width: 40%; float: left; margin-left: 5%">-->
                    <kr-input :get-option-label-function="(option => {return option['properties']['name'] + ', ' + option['properties']['postcode'] + ' ' + option['properties']['city']})"
                              :get-option-value-function="(option => {return option['properties']['id']})"
                              :get-results-list-function="(data => {return data['features']})"
                              :start-at="3"
                              @selected="onAdresseChanged"
                              label="Adresse complète"
                              name="adresse"
                              v-bind:source="'https://api-adresse.data.gouv.fr/search/?type=housenumber&limit=10&q='">
                        <template slot="kr-no-results"
                                  slot-scope="slotProps">
                            Aucune adresse trouvée pour "{{ slotProps.query }}"
                        </template>
                    </kr-input>
                </div>

                <div class="clearfix"></div>
                <p class="subtitle"
                   style="margin: 25px 0 0 5%; text-align: left; color: #8A8F96">Optionnel</p>

                <div style="width: 90%; margin-left: 5%">
                    <kr-input :start-at="3"
                              @query="onNomProprietaireChanged"
                              @selected="onNomProprietaireSelected"
                              label="Nom de l'ancien propriétaire / Raison sociale"
                              name="raisonSociale"
                              v-bind:source="env.apiPath + 'raison/autocomplete/' + form.codeInsee + '/'">
                        <template slot="kr-no-results"
                                  slot-scope="slotProps">
                            Aucun numéro trouvé pour "{{ slotProps.query }}"
                        </template>
                    </kr-input>
                </div>

                <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
                    <button class="button"
                            name="subscribe"
                            type="submit"
                            v-if="querying">
                        <font-awesome-icon icon="spinner"
                                           spin/>
                        Recherche en cours...
                    </button>
                    <button @click="search"
                            class="bouton"
                            id="submit"
                            name="subscribe"
                            type="submit"
                            v-else>
                        <font-awesome-icon icon="search"/>
                        Rechercher
                    </button>
                </div>

                <Leaflet @parcelleselected="onSelectedParcellesChanged"/>

                <div style="width: 90%; margin: 50px 5% 0;">
                    <p id="cgu">En poursuivant votre navigation, vous acceptez nos <a v-on:click="$emit('cgu')">CGU</a>.</p>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import Errors from '../../../components/content/base/Errors'
import mixinAvis from '../../../components/mixins/avis'
import KrInput from '../../../components/ui/KrInput'
import Leaflet from "../LeafletParcelle";

export default {
    name: 'SearchFormParcelle',
    mixins: [mixinAvis],
    components: {
        Leaflet,
        Errors,
        KrInput
    },
    computed: {
        _paq: function () {
            return window._paq
        }
    },
    props: {},
    data: () => ({
        cgu: true,
        error: {
            field: {
                codeCommune: []
            }
        },
        env: {
            basePath: process.env.VUE_APP_PATH,
            apiPath: process.env.VUE_APP_API_PATH
        }
    }),
    methods: {
        onAdresseChanged (option) {
            this.form.codeInsee = option['properties']['citycode']
            this.form.nomAdresse = option['properties']['name']
            this.form.geolocAdresse = option['geometry']['coordinates']['0'] + '|' + option['geometry']['coordinates']['1']
        },
        onCodeParcelleChanged (value) {
            this.form.codeParcelle = value
        },
        onNomProprietaireSelected (value) {
            this.form.codeProprio = value.code
        },
        onNomProprietaireChanged (value) {
            this.form.codeProprio = value.code
        },
        onCodePostalSelected (option) {
            this.form.codeInsee = option['properties']['citycode']
        },
        onSelectedParcellesChanged (array) {
            console.log(array)
        },
        search () {
            this.getAvis();
        },
    },
}
</script>

<style scoped>

    #cgu {
        color      : rgb(138, 143, 150);
        font-size  : 0.85em;
        margin     : 0;
        text-align : center;
    }

    #cgu a {
        text-decoration : none;
    }
</style>