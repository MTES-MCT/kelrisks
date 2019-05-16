<template>
  <div>
    <ul class="error"
        v-show="errorList.length > 0">
      <li :key="error"
          v-for="error in errorList">
        <font-awesome-icon icon="bomb"/>
        {{ error }}
      </li>
    </ul>
    <ul class="warning"
        v-show="warningList.length > 0">
      <li :key="warning"
          v-for="warning in warningList">
        <font-awesome-icon icon="exclamation"/>
        {{ warning }}
      </li>
    </ul>
    <ul class="info"
        v-show="infoList.length > 0">
      <li :key="info"
          v-for="info in infoList">
        <font-awesome-icon icon="info"/>
        {{ info }}
      </li>
    </ul>
    <ul class="success"
        v-show="successList.length > 0">
      <li :key="success"
          v-for="success in successList">
        <font-awesome-icon icon="thumbs-up"/>
        {{ success }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'errors',
  props: {
    errorList: {
      type: Array,
      default: () => []
    },
    warningList: {
      type: Array,
      default: () => []
    },
    infoList: {
      type: Array,
      default: () => []
    },
    successList: {
      type: Array,
      default: () => []
    }
  }
}
</script>

<style scoped>
  ul {
    margin     : 5px 5%;
    padding    : 5px 15px;
    text-align : left;
    list-style : none;
  }

  ul.error {
    color         : #7F0000;
    background    : rgba(127, 0, 0, 0.13);
    border        : rgba(127, 0, 0, 0.53) 2px solid;
    border-radius : 3px;
  }

  ul.success {
    color         : #1B5E20;
    background    : rgba(27, 94, 32, 0.13);
    border        : rgba(27, 94, 32, 0.53) 2px solid;
    border-radius : 3px;
  }

  ul.warning {
    color         : #BC5100;
    background    : rgba(188, 81, 0, 0.13);
    border        : rgba(188, 81, 0, 0.53) 2px solid;
    border-radius : 3px;
  }

  ul.info {
    color         : #0D47A1;
    background    : rgba(13, 71, 161, 0.13);
    border        : rgba(13, 71, 161, 0.53) 2px solid;
    border-radius : 3px;
  }
</style>
