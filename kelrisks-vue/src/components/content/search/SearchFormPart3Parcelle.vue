<template>
  <section class="section section-white"
           id="section3">
    <div class="container">
      <div class="panel">
        <h2 class="section__title">Votre terrain</h2>
        <p class="section__subtitle">2/2 - Informations complémentaires</p>

        <errors :error-list="getErrors"
                :info-list="informations.infoList"
                :success-list="informations.successList"
                :warning-list="informations.warningList"/>

        <br/>

        <div style="width: 40%; float: left; margin-left: 5%">
          <kr-input :start-at="2"
                    @selected="onNomVoieChanged"
                    label="Nom voie"
                    name="nomVoie"
                    v-bind:source="env.apiPath + '/adresse/voie/autocomplete/' + codeInsee + '/'">
            <template slot="kr-no-results"
                      slot-scope="slotProps">
              Aucune voie trouvée pour "{{ slotProps.query }}"
            </template>
          </kr-input>

          <kr-input :start-at="1"
                    @selected="onNumeroChanged"
                    label="Numéro voie"
                    name="numero"
                    v-bind:source="env.apiPath + '/adresse/numero/autocomplete/' + codeInsee + '/' + codeVoie + '/'">
            <template slot="kr-no-results"
                      slot-scope="slotProps">
              Aucun numéro trouvé pour "{{ slotProps.query }}"
            </template>
            <template slot="kr-helper"
                      slot-scope="slotProps">
              IBAN&nbsp;: {{ slotProps.option.code }}
            </template>
          </kr-input>
        </div>

        <div style="width: 10%; float: left; margin-top: 35px">
          <p class="section__subtitle">Ou</p>
        </div>
        <div style="width: 40%; float: left; margin-right: 5%">
          <kr-input label="Code Parcelle"
                    name="codeparcelle"
                    placeholder="BA-115 ou 912250000A0352"
                    @query="onCodeParcelleChanged"/>
        </div>

        <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
          <p class="section__subtitle">Optionnel</p>
        </div>

        <div style="width: 90%; margin-left: 5%">
          <kr-input :start-at="3"
                    @query="onNomProprietaireChanged"
                    @selected="onNomProprietaireSelected"
                    label="Nom de l'ancien propriétaire / Raison sociale"
                    name="raisonSociale"
                    v-bind:source="env.apiPath + '/raison/autocomplete/' + codeInsee + '/'">
            <template slot="kr-no-results"
                      slot-scope="slotProps">
              Aucun numéro trouvé pour "{{ slotProps.query }}"
            </template>
          </kr-input>
        </div>

        <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
          <a @click="$emit('flow', -1)"
             class="button">
            <font-awesome-icon icon="chevron-left"/>
            Précédent</a>
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
      </div>
    </div>
  </section>
</template>

<script>
import Errors from '../base/errors'
import mixinErrors from '../../mixins/errors'
import KrInput from '../../ui/KrInput'

export default {
  mixins: [mixinErrors],
  name: 'SearchFormPart3',
  components: {
    Errors,
    KrInput
  },
  computed: {
    _paq: function () {
      return window._paq
    },
    getErrors: function () {
      let errorSum = this.informations.errorList.concat(this.errors)
      console.log(errorSum)
      return errorSum
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
    }
  },
  data: () => ({
    codeVoie: '',
    codeNumero: '',
    codeParcelle: '',
    env: {
      basePath: process.env.VUE_APP_PATH,
      apiPath: process.env.VUE_APP_API_PATH
    }
  }),
  methods: {
    onNomVoieChanged (value) {
      this.$emit('nomvoiechanged')
      this.$emit('codevoie', value.code)
      this.$emit('nomvoie', value.libelle)
      this.codeVoie = value.code
    },
    onNumeroChanged (value) {
      this.$emit('numerochanged')
      this.$emit('codenumero', value.code)
      this.$emit('nomnumero', value.libelle)
      this.codeNumero = value.code
    },
    onCodeParcelleChanged (value) {
      this.$emit('codeparcellechanged')
      this.$emit('codeparcelle', value)
      this.codeParcelle = value
    },
    onNomProprietaireSelected (value) {
      this.$emit('nomproprietaireselected')
      this.$emit('codeproprio', value.code)
    },
    onNomProprietaireChanged (value) {
      this.$emit('nomproprietairechanged')
      this.$emit('codeproprio', value)
    },
    getAvis () {
      this.$emit('getavis')
    }
  }
}
</script>

<style scoped>

</style>
