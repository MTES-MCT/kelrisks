<template>
  <section class="section section-white"
           id="section4">
    <div class="container">
      <div class="panel clearfix">
        <h2 class="section__title"
            v-if="concordances && concordances > 0">La recherche Kelrisks a trouvé {{ concordances }} concordance{{ concordances > 1 ? 's': ''}} dans les bases de données</h2>
        <h2 class="section__title"
            v-else>La recherche Kelrisks n'a trouvé aucune concordance dans les bases de données</h2>
        <p class="section__subtitle">Attention, même en l'absence de concordance, la présente recherche peut présenter des informations relatives à l'environnement de votre parcelle</p>

        <!--<hr/>-->

        <br/>

        <div id="summary_wrapper">
          <div id="summary">
            <div class="section__subtitle"><strong>Votre recherche : </strong></div>
            Code postal&nbsp;: <span v-if="avis.summary.commune.codePostal && avis.summary.commune.codePostal !== ''">{{avis.summary.commune.codePostal}}</span><span v-else><i>n/a</i></span><br/>
            Rue&nbsp;:
            <span v-if="avis.summary.adresse && avis.summary.adresse.rue.nomVoie && avis.summary.adresse.rue.nomVoie !== ''">{{avis.summary.adresse.rue.nomVoie}}</span><span v-else><i>n/a</i></span><br/>
            N°&nbsp;: <span v-if="avis.summary.adresse && avis.summary.adresse.numero && avis.summary.adresse.numero !== ''">{{avis.summary.adresse.numero}}</span><span v-else><i>n/a</i></span><br/>
            Code parcelle&nbsp;: <span v-if="avis.summary.codeParcelle && avis.summary.codeParcelle !== ''">{{avis.summary.codeParcelle}}</span><span v-else><i>n/a</i></span><br/>
            Raison Sociale&nbsp;: <span v-if="avis.summary.nomProprietaire && avis.summary.nomProprietaire !== ''">{{avis.summary.nomProprietaire}}</span><span v-else><i>n/a</i></span><br/>
            <hr/>
            <div style="text-align: center; padding-top: 20px;">
              <a @click="$emit('flow', -1)
                         _paq.push(['trackEvent', 'Flow', 'Avis', 'Modifier'])"
                 class="button"
                 v-show="visibility.modifier">
                <font-awesome-icon icon="undo"/>
                Modifier</a>
              <br v-show="visibility.modifier"/>
              <a :href="this.env.apiPath + 'avis/pdf?' + 'codeINSEE=' + codeInsee + '&' + 'nomVoie=' + codeVoie + '&' + 'idBAN=' + idBan + '&' + 'codeParcelle=' + codeParcelle + '&' + 'nomProprietaire=' + codeProprio"
                 @click="_paq.push(['trackEvent', 'Flow', 'Pdf'])"
                 class="button warning"
                 id="pdf"
                 target="_blank">
                <font-awesome-icon icon="file-pdf"/>
                Pdf
              </a>
              <hr/>
              <p style="font-size: 0.9em;text-indent: 0; text-align: center; margin-bottom: 0;"><strong>Lien rapide</strong></p>
              <p style="font-size: 0.9em;text-indent: 0; text-align: center; margin-top: 0;">{{ env.basePath }}#/{{ avis.summary.codeUrl }}</p>
            </div>
          </div>
          <div style="text-align: center; padding-top: 20px;">
            <a :href="env.basePath"
               @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Nouvel'])"
               class="button">
              <font-awesome-icon icon="undo"/>
              Nouvelle recherche</a><br/>
          </div>
        </div>
        <div id="avis">
          <!--Recherche de sites <br/>-->

          <big-number :number-of="avis.basiasParcelle.numberOf"
                      label-text="Sites polués BASIAS"/>

          <big-number :number-of="avis.basolParcelle.numberOf"
                      label-text="Sites polués BASOL"/>

          <big-number :number-of="avis.installationClasseeParcelle.numberOf"
                      label-text="Installations classées"/>

          <big-number :number-of="avis.sisParcelle.numberOf"
                      label-text="SIS"/>
          <br/>

          <template v-if="avis.basiasProximiteParcelle.numberOf > 0 || avis.basiasRaisonSociale > 0">
            <p class="indent">Par ailleurs nous identifions&nbsp;: </p>
            <template v-if="avis.basiasProximiteParcelle.numberOf > 0">
              <p class="indent"> - {{ avis.basiasProximiteParcelle.lib }}</p>
              <ul class="site-list">
                <li :key="sibasias.id"
                    v-for="sibasias in avis.basiasProximiteParcelle.liste">
                  <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                     target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                </li>
              </ul>
            </template>
            <template v-if="avis.basiasRaisonSociale.numberOf > 0">
              <p class="indent"> - {{ avis.basiasRaisonSociale.lib }}</p>
              <ul class="site-list">
                <li :key="sibasias.id"
                    v-for="sibasias in avis.basiasRaisonSociale.liste">
                  <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                     target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                </li>
              </ul>
            </template>
            <br/>
          </template>

          <p class="section__subtitle"><strong>Conséquences</strong></p>

          <div id="conclusion0"
               style="text-align: justify"
               v-if="conclusion === 0">
            <p>Au regard de ces éléments, le propriétaire ou le bailleur n'est tenu à aucune obligation réglementaire en terme d'information acquéreur locataire au titre des pollutions de sols
               d’origine industrielle.</p>
          </div>
          <div id="conclusion1"
               style="text-align: justify"
               v-if="conclusion === 1">
            <p>En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur ou au locataire conformément à la réglementation en vigueur (article L. 514-20 du code
               de l’environnement et L 125-7 du code de l’Environnement si positif SIS).</p>
            <p>En outre, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la réalisation
               d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions. Nous vous rappelons que l'obligation de faire appel à un
               bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne concerne que les attestations prévues aux articles L. 556-1
               et L. 556-2 du code de l'environnement.</p>
            <p>Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le COFRAC
               (www.cofrac.fr) : à ce jour seul le LNE est accrédité et la liste des bureaux d'études certifiés par le LNE est disponible en cliquant sur ce lien
               (<a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                   href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>www.lne.fr</a>)</p>
          </div>
          <div id="conclusion2"
               style="text-align: justify"
               v-if="conclusion === 2">
            <p>En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur conformément aux articles L. 514-20 du code de l’environnement.</p>
            <p>Par ailleurs, ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>
            <p>Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la réalisation
               d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions. Nous vous rappelons que l'obligation de faire appel à un
               bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne concerne que les attestations prévues aux articles L. 556-1
               et L. 556-2 du code de l'environnement.</p>
            <p>Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le COFRAC
               (www.cofrac.fr) : à ce jour seul le LNE est accrédité et la liste des bureaux d'études certifiés par le LNE est disponible en cliquant sur ce lien
               (<a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                   href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>www.lne.fr</a>)</p>
          </div>
          <div id="conclusion3"
               style="text-align: justify"
               v-if="conclusion === 3">
            <p>Ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>
            <p>Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la réalisation
               d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions. Nous vous rappelons que l'obligation de faire appel à un
               bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne concerne que les attestations prévues aux articles L. 556-1
               et L. 556-2 du code de l'environnement.</p>
            <p>Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le COFRAC
               (www.cofrac.fr) : à ce jour seul le LNE est accrédité et la liste des bureaux d'études certifiés par le LNE est disponible en cliquant sur ce lien
               (<a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                   href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>www.lne.fr</a>)</p>
          </div>

          <br/>

          <section class="section section-white"
                   id="details"
                   style="text-align: left">

            <p @click="showHideContent()
                           _paq.push(['trackEvent', 'Flow', 'Avis', 'Détails'])"
               class="section__subtitle"
               style="margin-bottom: 0; cursor: pointer;">Détails et analyse à 100m</p>
            <a @click="showHideContent()
                           _paq.push(['trackEvent', 'Flow', 'Avis', 'Détails'])"
               style="position: absolute; top: 25px; right: 25px; text-decoration: none; background: none">
              <font-awesome-icon icon="caret-down"
                                 size="2x"
                                 v-if="!visibility.details"/>
              <font-awesome-icon icon="caret-up"
                                 size="2x"
                                 v-else/>
            </a>
            <div class="section_content"
                 v-if="visibility.details">
              <br/>
              Votre parcelle, <br/>
              <p>{{ avis.basiasParcelle.lib }}</p>
              <template v-if="avis.basiasParcelle.numberOf > 0">
                <ul class="site-list">
                  <li :key="sibasias.id"
                      v-for="sibasias in avis.basiasParcelle.liste">
                    - <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                         target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                  </li>
                </ul>
              </template>
              <p>{{ avis.basolParcelle.lib }}</p>
              <template v-if="avis.basolParcelle.numberOf > 0">
                <ul class="site-list">
                  <li :key="sibasol.id"
                      v-for="sibasol in avis.basolParcelle.liste">
                    - <a :href="'https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp=' + sibasol.numerobasol"
                         target="_blank">https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp={{ sibasol.numerobasol }}</a></li>
                </ul>
              </template>
              <p>{{ avis.installationClasseeParcelle.lib }}</p>
              <template v-if="avis.installationClasseeParcelle.numberOf > 0">
                <ul class="site-list">
                  <li :key="ic.id"
                      v-for="ic in avis.installationClasseeParcelle.liste">
                    - {{ ic.nom }}
                  </li>
                </ul>
              </template>
              <p>{{ avis.sisParcelle.lib }}</p>

              <template v-if="avis.basiasRayonParcelle.numberOf > 0 || avis.basolRayonParcelle.numberOf > 0 || avis.installationClasseeRayonParcelle.numberOf > 0">
                <p>Pour information, dans un rayon de 100m&nbsp;:</p>

                <template v-if="avis.basiasRayonParcelle.numberOf > 0">
                  <p v-if="avis.basiasRayonParcelle.numberOf === 1">Se trouve 1 site Basias dont la fiche est consultable en cliquant sur le lien suivant&nbsp;:</p>
                  <p v-else>Se trouvent {{ avis.basiasRayonParcelle.numberOf }} sites Basias dont les fiches sont consultables en cliquant sur les liens suivants&nbsp;:</p>
                  <ul class="site-list">
                    <li :key="sibasias.id"
                        v-for="sibasias in avis.basiasRayonParcelle.liste">
                      - <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                           target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                    </li>
                  </ul>
                </template>
                <template v-if="avis.basolRayonParcelle.numberOf > 0">
                  <p v-if="avis.basiasRayonParcelle.numberOf === 1">Se trouve 1 site Basol dont la fiche est consultable en cliquant sur le lien suivant&nbsp;:</p>
                  <p v-else>Se trouvent {{ avis.basolRayonParcelle.numberOf }} sites Basol dont les fiches sont consultables en cliquant sur les liens suivants&nbsp;:</p>
                  <ul class="site-list">
                    <li :key="sibasol.id"
                        v-for="sibasol in avis.basolRayonParcelle.liste">
                      - <a :href="'https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp=' + sibasol.numerobasol"
                           target="_blank">https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp={{ sibasol.numerobasol }}</a></li>
                  </ul>
                </template>
                <template v-if="avis.installationClasseeRayonParcelle.numberOf > 0">
                  <p v-if="avis.installationClasseeRayonParcelle.numberOf === 1">Se trouve 1 installation classée&nbsp;: </p>
                  <p v-else>Se trouvent {{ avis.installationClasseeRayonParcelle.numberOf }} installations classées&nbsp;: </p>
                  <ul class="site-list">
                    <li :key="ic.id"
                        v-for="ic in avis.installationClasseeRayonParcelle.liste">
                      - {{ ic.nom }}
                    </li>
                  </ul>
                </template>
              </template>
              <!--Se trouvent {{ avis. }} SIS&nbsp;: <br/>-->

              <template v-if="avis.installationClasseeCommune.numberOf > 0"><p>Enfin, nous avons trouvé {{ avis.installationClasseeCommune.numberOf }} installation(s) classée(s) non géoréférencées
                                                                               dans la commune.</p></template>
            </div>
          </section>
        </div>
      </div>
      <div class="note_pied_page">
        <p>(1) L'obligation de faire appel à un bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 concerne les attestations
           prévues aux articles L. 556-1 et L. 556-2 du code de l'environnement et exigées à l'article R 431-16 du code de l'urbanisme (alinéa n et o).</p>
        <p>(2) Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le COFRAC
           (www.cofrac.fr). A ce jour seul le LNE est accrédité pour délivrer la certification relative aux sols pollués et la liste des bureaux d'études certifiés par le LNE est disponible en
           cliquant sur ce lien (https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr)</p>
      </div>
    </div>
  </section>
</template>

<script>
import BigNumber from '../../ui/BigNumber'
import avis from '../../../script/avis'
import fetchWithError from '../../../script/fetchWithError'
import functions from '../../../script/fonctions'
import mixinErrors from '../../mixins/errors'

export default {
  mixins: [mixinErrors],
  name: 'SearchResults',
  components: {BigNumber},
  props: {
    codeParcelle: {
      type: String,
      default: ''
    },
    codeInsee: {
      type: String,
      default: ''
    },
    codeVoie: {
      type: String,
      default: ''
    },
    idBan: {
      type: String,
      default: ''
    },
    codeProprio: {
      type: String,
      default: ''
    }
  },
  data: () => ({
    visibility: {
      details: false,
      modifier: true
    },
    env: {
      basePath: process.env.VUE_APP_PATH,
      apiPath: process.env.VUE_APP_API_PATH
    },
    avis: {
      summary: {
        adresse: {
          rue: {}
        },
        commune: {}
      },
      querying: false,
      loading: false,
      rendered: false,
      basiasNbOf: 0,
      basiasParcelle: {},
      basiasProximiteParcelle: {},
      basiasRayonParcelle: {},
      basiasRaisonSociale: {},
      basolNbOf: 0,
      basolParcelle: {},
      basolProximiteParcelle: {},
      basolRayonParcelle: {},
      s3ICNbOf: 0,
      installationClasseeParcelle: {},
      installationClasseeProximiteParcelle: {},
      installationClasseeRayonParcelle: {},
      installationClasseeCommune: {},
      sisParcelle: {}
    }
  }),
  methods: {
    showHideContent () {
      // console.log(this.visibility.details)
      this.visibility.details = !this.visibility.details
      // console.log(this.visibility.details)
    },
    getAvis () {
      this.clearErrors()

      if (this.codeParcelle === '' && this.idBan === '') {
        this.sendError('Merci de bien vouloir choisir une rue/numéro ou entrer une parcelle.')
      }

      let url = this.env.apiPath + 'avis?' + 'codeINSEE=' + this.codeInsee + '&' + 'nomVoie=' + this.codeVoie + '&' + 'idBAN=' + this.idBan + '&' + 'codeParcelle=' + this.codeParcelle + '&' + 'nomProprietaire=' + this.codeProprio
      fetchWithError(url, null, 1000 * 20)
        .then(stream => stream.json())
        .then(value => {
          this.checkInformations(value.entity)
          if (this.informations.hasError) {
            this._paq.push(['trackEvent', 'Flow', 'Informations 2/2', 'Erreur'])
            this.emitErrors()
            return
          }
          this.avis.summary = value.entity.summary
          this._paq.push(['trackEvent', 'Flow', 'Informations 2/2', 'OK'])

          this.$emit('requestfocus')

          this.avis.basiasParcelle = avis.getBasiasParcelle(value)
          this.avis.basiasProximiteParcelle = avis.getBasiasProximiteParcelle(value)
          this.avis.basiasRaisonSociale = avis.getBasiasRaisonSocialeParcelle(value)
          this.avis.basiasRayonParcelle = avis.getBasiasRayonParcelle(value)

          this.avis.basolParcelle = avis.getBasolParcelle(value)
          this.avis.basolProximiteParcelle = avis.getBasolProximiteParcelle(value)
          this.avis.basolRayonParcelle = avis.getBasolRayonParcelle(value)

          this.avis.installationClasseeParcelle = avis.getICSurParcelle(value)
          this.avis.installationClasseeProximiteParcelle = avis.getICProximiteParcelle(value)
          this.avis.installationClasseeRayonParcelle = avis.getICRayonParcelle(value)
          this.avis.installationClasseeCommune = avis.getICNonGeoreferencees(value)

          this.avis.sisParcelle = avis.getSISSurParcelle(value)

          functions.scrollToElement('main', false)
          this._paq.push(['trackEvent', 'Flow', 'Avis', 'Rendu'])
        })
        .catch((e) => {
          this.sendError('Votre requête n\'a pu aboutir dans un délais raisonnable, merci de réessayer ou de nous le signaler au moyen du formulaire de contact.')
        })
    },
    loadAvis (codeAvis) {
      // console.log('loadAvis')
      this.visibility.modifier = false
      this.$emit('loading')
      this.$emit('setflow', 0)
      fetch(this.env.apiPath + 'url?' + 'code=' + codeAvis)
        .then(stream => stream.json())
        .then(value => {
          // console.log(value.entity.url)
          let array = value.entity.url.split('|&|')
          this.codeParcelle = array[0]
          this.codeInsee = array[1]
          this.codeVoie = array[2]
          this.idBan = array[3]
          this.codeProprio = array[4]
          // console.log(array)

          this.getAvis()
        })
    }
  },
  computed: {
    conclusion: function () {
      let conclusionNumber = 0

      if (this.avis.basiasParcelle.numberOf === 0 &&
        this.avis.basolParcelle.numberOf === 0 &&
        this.avis.installationClasseeParcelle.numberOf === 0 &&
        this.avis.sisParcelle.numberOf === 0) conclusionNumber = 0

      if (this.avis.basiasParcelle.numberOf === 0 &&
        this.avis.basolParcelle.numberOf === 0 &&
        this.avis.installationClasseeParcelle.numberOf === 0 &&
        this.avis.sisParcelle.numberOf === 0 &&
        this.avis.basiasProximiteParcelle.numberOf > 0) conclusionNumber = 3

      if (this.avis.basiasParcelle.numberOf > 0 ||
        this.avis.basolParcelle.numberOf > 0 ||
        this.avis.installationClasseeParcelle.numberOf > 0 ||
        this.avis.sisParcelle.numberOf > 0) conclusionNumber = 1

      if (this.avis.basiasParcelle.numberOf > 0 &&
        this.avis.basolParcelle.numberOf === 0 &&
        this.avis.installationClasseeParcelle.numberOf === 0 &&
        this.avis.sisParcelle.numberOf === 0) conclusionNumber = 2

      return conclusionNumber
    },
    concordances: function () {
      return this.avis.installationClasseeParcelle.numberOf + this.avis.basolParcelle.numberOf + this.avis.basiasParcelle.numberOf
    },
    _paq: function () {
      return window._paq
    }
  },
  mounted () {
    let codeAvis = this.$route.params.codeAvis
    // console.log('codeAvis : ' + codeAvis + ' && is undefined ? ' + (codeAvis === undefined))
    if (codeAvis !== undefined) this.loadAvis(codeAvis)
  }
}
</script>

<style scoped>
  #summary_wrapper {
    float      : left;
    width      : 30%;
    text-align : left;
  }

  #summary, #avis, #details {
    background-color : #FFFFFF;
    border           : 1px solid #CCCCCC;
    border-radius    : 2px;
    /*float            : left;*/
    padding          : 30px 20px;
  }

  #summary {
    padding : 30px 20px 0;
  }

  #avis {
    float : right;
    width : 68%;
  }
</style>
