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
        <p style="color: #AAAAAA; font-size: 0.75em">* voir CGU pour les détails</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HowTo'
}
</script>

<style scoped>
  #howto_wrapper {
    width  : 52.5%;
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
