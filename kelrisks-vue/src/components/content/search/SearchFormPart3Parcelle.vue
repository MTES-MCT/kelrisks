<template>
    <section class="section section-white"
             id="section3">
        <div class="container">
            <div class="panel">
                <h2 class="section__title title">Rechercher votre terrain</h2>
                <p class="subtitle">Informations</p>
                <p style="font-size: 16px; color: rgb(83, 101, 125); text-align: center">Saisissez le code de la parcelle à analyser<br>(les recherches par code parcelle peuvent être plus pertinentes
                                                                                         que par adresse)</p>

                <errors :error-list="concatErrors(errors)"
                        :info-list="informations.infoList"
                        :success-list="informations.successList"
                        :warning-list="concatWarnings(warnings)"/>

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
                        <template slot="kr-option-label"
                                  slot-scope="slotProps">
                            {{ slotProps.option.properties.postcode + ' - ' + slotProps.option.properties.city}}
                        </template>
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
                              placeholder="BA-115 ou 912250000A0352"/>
                </div>

                <div class="clearfix"></div>
                <p class="subtitle"
                   style="margin: 25px 0 15px 0; color: #8A8F96">Ou</p>

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
                        <template slot="kr-helper"
                                  slot-scope="slotProps">
                            IBAN&nbsp;: {{ slotProps.option.properties.id }}
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
                              v-bind:source="env.apiPath + 'raison/autocomplete/' + codeInsee + '/'">
                        <template slot="kr-no-results"
                                  slot-scope="slotProps">
                            Aucun numéro trouvé pour "{{ slotProps.query }}"
                        </template>
                    </kr-input>
                </div>

                <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
                    <!--                    <a @click="$emit('flow', -1)"-->
                    <!--                       class="button">-->
                    <!--                        <font-awesome-icon icon="chevron-left"/>-->
                    <!--                        Précédent</a>-->
                    <button class="button"
                            name="subscribe"
                            type="submit"
                            v-if="querying">
                        <font-awesome-icon icon="spinner"
                                           spin/>
                        Recherche en cours...
                    </button>
                    <button @click="getAvis"
                            class="button"
                            id="submit"
                            name="subscribe"
                            type="submit"
                            v-else>
                        <font-awesome-icon icon="search"/>
                        Rechercher
                    </button>
                </div>

                <div style="width: 90%; margin: 50px 5% 0;">
                    <p id="cgu">En poursuivant votre navigation, vous acceptez nos <a v-on:click="$emit('cgu')">CGU</a>.</p>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import Errors from '../../../components/content/base/Errors'
import mixinErrors from '../../../components/mixins/errors'
import KrInput from '../../../components/ui/KrInput'

export default {
    name: 'SearchFormPart3',
    mixins: [mixinErrors],
    components: {
        Errors,
        KrInput
    },
    computed: {
        _paq: function () {
            return window._paq
        }
    },
    props: {
        querying: {
            type: Boolean,
            default: false
        },
        codeInsee: {
            type: String,
            default: ''
        },
        errors: {
            type: Array,
            default: () => []
        },
        warnings: {
            type: Array,
            default: () => []
        }
    },
    data: () => ({
        cgu: false,
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
        acceptCGU () {
            // sessionStorage.cguChecked = value.target.checked
        },
        onAdresseChanged (option) {
            this.$emit('adressechanged')
            this.$emit('codeinsee', option['properties']['citycode'])
            this.$emit('nomadresse', option['properties']['name'])
            this.$emit('geolocalisationadresse', option['geometry']['coordinates']['0'] + '|' + option['geometry']['coordinates']['1'])
        },
        onCodeParcelleChanged (value) {
            this.$emit('codeparcellechanged')
            this.$emit('codeparcelle', value)
        },
        onNomProprietaireSelected (value) {
            this.$emit('nomproprietaireselected')
            this.$emit('codeproprio', value.code)
        },
        onNomProprietaireChanged (value) {
            this.$emit('nomproprietairechanged')
            this.$emit('codeproprio', value)
        },
        onCodePostalSelected (option) {
            this.$emit('codepostalselected')
            this.$emit('codeinsee', option['properties']['citycode'])
            this.error.field.codeCommune = []
        },
        getAvis () {
            this.clearAll()
            this.error.field.codeCommune = []
            // if (!this.checkCodePostal()) return;
            if (!this.cgu) {
                this.sendWarning('Merci de bien vouloir accepter les Conditions générales d’utilisation.')
                return
            }
            this.$emit('getavis')
        }
    },
    mounted () {
        // if (sessionStorage.cguChecked !== undefined) this.cgu = sessionStorage.cguChecked
    }
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
