<template>
  <div>
    <ul class="error">
      <li :key="error"
          v-for="error in errorList">
        {{ error }}
      </li>
    </ul>
    <ul class="warning">
      <li :key="warning"
          v-for="warning in warningList">
        {{ warning }}
      </li>
    </ul>
    <ul class="info">
      <li :key="info"
          v-for="info in infoList">
        {{ info }}
      </li>
    </ul>
    <ul class="success">
      <li :key="success"
          v-for="success in successList">
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

</style>
