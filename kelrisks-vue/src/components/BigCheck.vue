<template>
  <div class="bc-wrapper">
    <div class="bc-label">{{ labelText }}</div>
    <div class="bc-checkbox"
         v-show="!checked">
      <div class="bc-times">
        <font-awesome-icon icon="times"/>
      </div>
    </div>
    <div class="bc-checkbox"
         v-show="checked">
      <div class="bc-check">
        <font-awesome-icon icon="check"/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'big-check',

  props: {
    labelText: {
      type: String,
      required: true
    },
    id: {
      type: String,
      required: false
    },
    checked: {
      type: Boolean,
      default: false,
      required: true
    }
  }
}
</script>

<style>
  .bc-wrapper {
    border        : solid 1px hsl(0, 0%, 66%);
    border-radius : 4px;
    display       : inline-block;
  }

  .bc-label {
    background-color : hsl(0, 0%, 75%);
    padding          : 10px;
  }

  .bc-checkbox > div {
    margin        : 15px;
    display       : inline-block;
    border-radius : 65px;
    width         : 130px;
    height        : 130px;
    font-size     : 4em;
  }

  .bc-checkbox > div.bc-times {
    padding    : 32px 45px;
    background : hsl(0, 50%, 66%);
    color      : hsl(0, 66%, 50%);
  }

  .bc-checkbox > div.bc-check {
    padding    : 32px 36px;
    background : hsl(125, 50%, 75%);
    color      : hsl(125, 65%, 40%);
  }
</style>
