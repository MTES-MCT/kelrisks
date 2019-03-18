<template>
  <div class="bc-wrapper">
    <div class="bc-label">{{ labelText }}</div>
    <div class="bc-checkbox"
         v-bind:class="{'bc-red':level === 3,
                          'bc-orange':level === 2,
                          'bc-yellow':level === 1,
                          'bc-green':level === 0}">
      <font-awesome-icon icon="check"
                         v-if="checked"/>
      <font-awesome-icon icon="exclamation-triangle"
                         style="margin-left: -7px;"
                         v-else/>
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
    },
    level: {
      type: Number,
      default: 3,
      validator: (value) => {
        return [0, 1, 2, 3].indexOf(value) > -1
      }
    }
  }
  // computed: {
  // levelColor: function () {
  //   if (this.level === 3) return ''
  // }
  // }
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

  .bc-checkbox {
    margin        : 10px;
    display       : inline-block;
    border-radius : 6px;
    width         : 150px;
    height        : 150px;
    font-size     : 7em;
    padding       : 10px 19px;
    box-shadow    : #777777 -2px 2px 4px;
  }

  .bc-red {
    background : hsl(0, 50%, 90%);
    color      : hsl(0, 50%, 50%);
  }

  .bc-orange {
    background : hsl(30, 66%, 90%);
    color      : hsl(30, 66%, 50%);
  }

  .bc-yellow {
    background : hsl(55, 66%, 90%);
    color      : hsl(55, 66%, 60%);
  }

  .bc-green {
    background : hsl(125, 50%, 90%);
    color      : hsl(125, 50%, 50%);
  }
</style>
