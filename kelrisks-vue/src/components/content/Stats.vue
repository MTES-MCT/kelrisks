<template>
  <div class="wrapper">
    <h2 class="section__title">Statistiques 2019</h2><!--TODO : Année dynamique--><br>
    <br>
    <div class="histogram_wrapper">
      <div class="histogram_title_wrapper">
        Utilisateurs
      </div>
      <div class="histogram_bars_wrapper">
        <div class="histogram_bar"
             v-bind:style="{height: professionnel_pct + '%'}">
          <div class="histogram_bar_text">{{professionnel_nb}}</div>
        </div>
        <div class="histogram_bar"
             v-bind:style="{height: particulier_pct + '%'}">
          <div class="histogram_bar_text">{{particulier_nb}}</div>
        </div>
      </div>
      <div class="histogram_labels_wrapper">
        <div class="histogram_label">Professionnels</div>
        <div class="histogram_label">Particuliers</div>
      </div>
    </div>
    <div class="value_wrapper">
      <div class="value_title_wrapper">
        Avis rendus
      </div>
      <div class="value_text_wrapper">
        {{ avis_rendu_nb }}
      </div>
    </div>
    <div class="value_wrapper">
      <div class="value_title_wrapper">
        Avis téléchargés
      </div>
      <div class="value_text_wrapper">
        {{ pdf_nb }}
      </div>
    </div>
    <div class="value_wrapper">
      <div class="value_title_wrapper">
        Bureaux d'Étude consultés
      </div>
      <div class="value_text_wrapper">
        {{ bureau_etude_nb }}
      </div>
    </div>
    <div style="clear: both"></div>
                                                     <!--    {{ this.stats }}-->
  </div>
</template>
<script>
import fetchWithError from '../../script/fetchWithError'

export default {
  name: 'Stats',
  data: () => ({
    stats: {},
    professionnel_pct: 0,
    professionnel_nb: 0,
    particulier_pct: 0,
    particulier_nb: 0,
    avis_rendu_nb: 0,
    pdf_nb: 0,
    bureau_etude_nb: 0
  }),
  methods: {
    getStats () {
      let idSite = '1'
      let period = 'year'
      let date = 'yesterday'
      let url = 'https://kelrisks.beta.gouv.fr/mat/?module=API&idSite=' + idSite + '&method=Events.getAction&secondaryDimension=eventName&flat=1&period=' + period + '&date=' + date + '&format=json'
      fetchWithError(url, null, 1000 * 20)
        .then(stream => stream.json())
        .then(value => {
          this.stats = value
          let demandeurs = this.getEventAction('Demandeur')
          // console.log(demandeurs)
          this.professionnel_nb = demandeurs.events.Professionnel.nb_events
          this.professionnel_pct = 100 / demandeurs.nb_events_sum * demandeurs.events.Professionnel.nb_events
          this.particulier_nb = demandeurs.events.Particulier.nb_events
          this.particulier_pct = 100 / demandeurs.nb_events_sum * demandeurs.events.Particulier.nb_events

          let avis = this.getEventAction('Avis')
          this.avis_rendu_nb = avis.events.Rendu.nb_events

          let pdf = this.getEventAction('Pdf')
          // console.log(pdf)
          this.pdf_nb = pdf.events.undefined.nb_events

          let bureauEtude = this.getEventAction('Bureau_Etude')
          // console.log(bureauEtude)
          this.bureau_etude_nb = bureauEtude.events.Bureau_Etude.nb_events
        })
    },
    getEventAction (actionName) {
      let action = {
        name: actionName,
        nb_events_sum: 0,
        nb_events_max: 0,
        events: {}
      }
      for (let key in this.stats) {
        let statAction = this.stats[key]
        if (statAction.Events_EventAction === actionName) {
          let event = {}

          event.nb_events = statAction.nb_events
          event.nb_visits = statAction.nb_visits

          action.nb_events_sum += event.nb_events
          action.nb_events_max = event.nb_events > action.nb_events_max ? event.nb_events : action.nb_events_max

          action.events[statAction.Events_EventName] = event
        }
      }
      if (action.name) return action
      return null
    }
  },
  mounted () {
    this.getStats()
  }
}
</script>

<style scoped>
  .wrapper {
    font-weight : bold;
    margin      : 100px 12.5%;
  }

  .histogram_wrapper {
    margin           : 20px;
    width            : calc(40% - 40px);
    height           : 290px;
    background-color : white;
    box-shadow       : #999999 0 2px 4px;
    border-radius    : 2px;
    padding          : 20px;
    float            : left;
  }

  .value_wrapper {
    margin           : 20px;
    width            : calc(30% - 40px);
    height           : 125px;
    background-color : white;
    box-shadow       : #999999 0 2px 4px;
    border-radius    : 2px;
    padding          : 20px;
    float            : left;
  }

  .histogram_bars_wrapper {
    width           : 100%;
    height          : calc(100% - 45px);display : flex;
    align-items     : flex-end;
    justify-content : flex-end;
    padding         : 10px 0 15px 0;
  }

  .histogram_bar {
    width                   : 50%;
    background-color        : #4B9EFF;
    margin                  : 0 10px;
    border-top-left-radius  : 5px;
    border-top-right-radius : 5px;
  }

  .histogram_bar_text {
    margin-top : -25px;
  }

  .histogram_labels_wrapper {
    width  : 100%;
    height : 25px;
  }

  .histogram_label {
    height        : 25px;
    width         : 50%;
    float         : left;
    margin-bottom : 10px;
  }

  .value_text_wrapper {
    width         : 100%;
    height        : 25px;
    font-size     : 3em;
    margin-bottom : 10px;
  }

  .histogram_title_wrapper {
    width       : 100%;
    height      : 25px;
    font-weight : initial;
    line-height : 10px;
  }

  .value_title_wrapper {
    width       : 100%;
    height      : 45px;
    font-weight : initial;
    line-height : 10px;
  }
</style>
