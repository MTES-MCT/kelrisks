<template>
  <div class="bc-wrapper">
    <div class="bc-label">{{ labelText }}</div>
    <div class="bc-number">
      {{ numberOf }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'big-number',

  props: {
    labelText: {
      type: String,
      required: true
    },
    id: {
      type: String,
      required: false
    },
    numberOf: {
      type: Number,
      default: 0,
      required: true
    }
  }
}
</script>

<style>
  .bc-wrapper {
    border        : solid 1px #555555;
    border-radius : 2px;
    display       : inline-block;
    margin        : 0 7px 30px 7px;
  }

  .bc-label {
    border-bottom    : solid 1px #555555;
    background-color : #555555;
    color            : #FFFFFF;
    padding          : 10px;
  }

  .bc-number {
    margin      : 10px;
    display     : inline-block;
    width       : 150px;
    font-size   : 7em;
    line-height : 1.2em;
  }
</style>
