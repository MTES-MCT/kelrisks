<template>
    <section class="section section-white"
             id="section2">
        <div class="container">
            <div class="panel">
                <h2 class="section__title">Votre terrain</h2>
                <p class="section__subtitle">1/2 - Saissez votre commune</p>

                <errors :error-list="informations.errorList"
                        :info-list="informations.infoList"
                        :success-list="informations.successList"
                        :warning-list="informations.warningList"/>

                <br/>

                <div style="width: 50%; margin-left: 25%">
                    <kr-input :errors="checks.codeCommuneError"
                              :start-at="3"
                              @selected="onCodePostalChanged"
                              label="Nom de commune ou Code postal"
                              name="codePostal"
                              :get-option-label-function="data => { return data['codePostal'] }"
                              :get-option-value-function="data => { return data['codeINSEE'] }"
                              v-bind:source="env.apiPath + 'adresse/commune/autocomplete/'">
                        <template slot="kr-option-label"
                                  slot-scope="slotProps">
                            {{ slotProps.option.codePostal + ' - ' + slotProps.option.nomCommune}}
                        </template>
                        <template slot="kr-helper"
                                  slot-scope="slotProps">
                            {{ slotProps.option.nomCommune }}
                        </template>
                        <template slot="kr-no-results"
                                  slot-scope="slotProps">
                            Aucune commune trouvé pour "{{ slotProps.query }}"
                        </template>
                    </kr-input>
                </div>

                <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
                    <a @click="$emit('flow', -1)"
                       class="button">
                        <font-awesome-icon icon="chevron-left"/>
                        Précédent</a>
                    <a @click="checkCodePostal()"
                       class="button">Suivant
                        <font-awesome-icon :style="{margin : '0 0 0 10px'}"
                                           icon="chevron-right"/>
                    </a>
                </div>
                <!--</form>-->
            </div>
        </div>
    </section>
</template>

<script>
import Errors from '@/components/content/base/Errors'
import KrInput from '../../../components/ui/KrInput'
import mixinErrors from '@/components/mixins/errors'

export default {
    mixins: [mixinErrors],
    components: {
        Errors,
        KrInput
    },
    name: 'SearchFormPart2',
    data: () => ({
        codeINSEE: '',
        checks: {
            codeCommuneError: []
        },
        env: {
            basePath: process.env.VUE_APP_PATH,
            apiPath: process.env.VUE_APP_API_PATH
        }
    }),
    methods: {
        checkCodePostal () {
            if (/^\d{5}$/.test(this.codeINSEE)) {
                this.checks.codeCommuneError = []
                this.$emit('flow', 1)
                this.$emit('codeinsee', this.codeINSEE)
                this._paq.push(['trackEvent', 'Flow', 'Informations 1/2', 'OK'])
            } else {
                this.checks.codeCommuneError = ['Merci de bien vouloir sélectionner une commune au moyen de l\'autocomplétion.']
                this._paq.push(['trackEvent', 'Flow', 'Informations 1/2', 'Erreur Autocomplétion'])
            }
        },
        onCodePostalChanged (value) {
            this.checks.codeCommuneError = []
            this.codeINSEE = value.codeINSEE
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

</style>
