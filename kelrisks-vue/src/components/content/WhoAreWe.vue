<template>
  <div class="panel"
       id="haw_wrapper">
    <div @click="close()"
         class="close">Fermer
      <font-awesome-icon icon="times"
                         size="lg"/>
    </div>
    <h2>En savoir plus</h2>
    <div id="haw">
        <p class="section__subtitle">Qui sommes-nous ?</p>

        <p>ERRIAL est un produit qui grandit au sein de la Fabrique Numérique, l'incubateur des services numériques du Ministère de la transition écologique. Un incubateur qui s'inscrit
           directement dans la démarche portée par beta.gouv.fr.</p>

        <p class="section__subtitle">Pourquoi ERRIAL est un projet d'état</p>

        <p>L'État a pour mission d'administrer la gestion et l'information liées aux risques qu'ils soient naturels et industriels. Dans l'exercice de cette responsabilité, l'État met à disposition
           plusieurs bases de données publiques à destination des français, accessibles notamment via georisques.gouv.fr. Avec ERRIAL, l'État va plus loin en automatisant la recherche dans ces bases
           de données et en explicitant les résultats pour les rendre compréhensibles au plus grand nombre.</p>

        <p class="section__subtitle">Quelles bases de données sont interrogées par ERRIAL ?</p>

        <p>Actuellement, le service ERRIAL interroge les bases de données publiques suivantes :</p>

        <ul>
            <li>Basias : <a href="http://www.georisques.gouv.fr/dossiers/telechargement/basias"
                            rel="noopener noreferrer"
                            target="_blank">www.georisques.gouv.fr</a></li>
          <li>Basol : <a href="https://basol.developpement-durable.gouv.fr/recherche.php"
                         rel="noopener noreferrer"
                         target="_blank">basol.developpement-durable.gouv.fr</a></li>
          <li>S3IC : <a href="http://www.installationsclassees.developpement-durable.gouv.fr/"
                        rel="noopener noreferrer"
                        target="_blank">www.installationsclassees.developpement-durable.gouv.fr</a></li>
          <li>SIS : <a href="http://www.georisques.gouv.fr/les-secteurs-dinformations-des-sols-sis"
                       rel="noopener noreferrer"
                       target="_blank">www.georisques.gouv.fr</a></li>
      </ul>

      <p class="section__subtitle">Comment travaillons nous ?</p>

        <p>Nous travaillons avec des méthodes inspirées du Lean Startup. Sans cahier des charges, au contact permanent des utilisateurs, en amélioration continue et en cycles courts. L'évolution de
           ERRIAL et les prochaines fonctionnalités dépendent de vos besoins. Vous pouvez nous écrire à Bipcp.Srnh.Dgpr@developpement-durable.gouv.fr !</p>
    </div>
  </div>
</template>

<script>
import JQuery from 'jquery'

let $ = JQuery

export default {
  name: 'WhoAreWe',
  methods: {
    close () {
      $('#haw_wrapper').hide()
    },
    open () {
      $('#haw_wrapper').show()
    }
  }
}
</script>

<style scoped>
  #haw_wrapper {
    background : var(--theme-background-white);
    box-shadow : #CCCCCC -3px 3px 5px;
    display    : none;
    height     : 80%;
    left       : 20%;
    padding    : 0;
    position   : fixed;
    top        : 10%;
    width      : 60%;
    z-index    : 999;
  }

  #haw {
    scroll-behavior : auto;
    position        : relative;
    text-align      : justify;
    overflow        : auto;
    height          : 87%;
    padding         : 0 20px;
  }

  p {
    text-indent : unset;
  }

  ul li {
    list-style : none;
  }

  .close {
    position : absolute;
    top      : 15px;
    right    : 6px;
    cursor   : pointer;
  }
</style>
