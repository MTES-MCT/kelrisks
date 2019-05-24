<template>
  <section class="section section-white"
           id="section1">
    <div class="container">
      <div class="panel">
        <h2 class="section__title">Vous êtes ?</h2>
        <p class="section__subtitle">Afin de vous délivrer un niveau d'information qui corresponde au mieux à votre besoin</p>

        <br/>
        <a @click="$emit('categorieDemandeur', 'particulier')
                   $emit('flow', 1)
                   _paq.push(['trackEvent', 'Flow', 'Demandeur', 'Particulier'])"
           class="button">
          <font-awesome-icon icon="user"/>
          Un particulier</a>
        <a @click="$emit('categorieDemandeur', 'professionnel')
                   $emit('flow', 1)
                   _paq.push(['trackEvent', 'Flow', 'Demandeur', 'Professionnel'])"
           class="button">
          <font-awesome-icon icon="briefcase"/>
          Un professionnel</a>
      </div>
    </div>
  </section>
</template>

<script>
import mixinErrors from '@/components/mixins/errors'

export default {
  mixins: [mixinErrors],
  name: 'SearchFormPart1',
  computed: {
    _paq: function () {
      return window._paq
    }
  }
}
</script>

<style scoped>

</style>
