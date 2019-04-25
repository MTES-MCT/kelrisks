<template>
  <div id="howto_wrapper">
    <h2>Kelrisks, comment ça marche ?</h2>
    <div class="box panel">
      <div class="content">
        <font-awesome-icon icon="map-marked-alt"
                           size="3x"/>
        <p>Je Saisis le code postal du terrain concerné</p>
      </div>
    </div>
    <hr>
    <div class="box panel">
      <div class="content">
        <font-awesome-icon icon="home"
                           size="3x"/>
        <p>Son adresse ou le code parcelle</p>
      </div>
    </div>
    <hr>
    <div class="box panel">
      <div class="content">
        <font-awesome-icon icon="check-circle"
                           size="3x"/>
        <p>Hop, Kelrisks affiche le résultat de la recherche parmi les bases de données* de l'État !</p>
        <p class="subtext"
           style="color: #AAAAAA; font-size: 0.75em">* voir CGU pour les détails</p>
      </div>
    </div>
  </div>
</template>

<script>
import JQuery from 'jquery'

let $ = JQuery

export default {
  name: 'HowTo',
  mounted: function () {
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy: function () {
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize () {
      let width = $('#howto_wrapper div.box:first').width()
      $('#howto_wrapper svg').css('font-size', width / 247 * 3 + 'em')
      $('#howto_wrapper p').css('font-size', width / 247 + 'em').css('line-height', width / 247 * 1.5 + 'em')
      $('#howto_wrapper p.subtext').css('font-size', width / 247 * 0.75 + 'em')
    }
  }
}
</script>

<style scoped>
  #howto_wrapper {
    height : 25%;
    margin : 0 auto 25px;
  }

  .box {
    width   : 25%;
    margin  : 0;
    padding : 25% 0 0 0;
    float   : left;
  }

  .content {
    position : absolute;
    top      : 0;
    left     : 0;
    bottom   : 0;
    right    : 0;
    margin   : 10px;
  }

  .content p {
    font-weight : bold;
    text-indent : unset;
    text-align  : center;
  }

  .content svg {
    margin : 10px 0;
    color  : #003B80;
  }

  hr {
    width      : 12.5%;
    margin-top : 12.5%;
    float      : left;
  }
</style>
