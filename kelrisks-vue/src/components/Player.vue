<template>
  <div class="card">
    <div class="card-body">
      <h6 class="card-title">
        {{ player.name }} </h6>
      <p class="card-text">
      <div>
        {{ player.description }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'player',
  props: ['player']
}
</script>
