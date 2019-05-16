export default {
  data: () => ({
    informations: {
      hasError: false,
      errorList: [],
      hasWarning: false,
      warningList: [],
      hasInfo: false,
      infoList: [],
      hasSuccess: false,
      successList: []
    }
  }),
  methods: {
    checkInformations: function (info) {
      this.informations.hasError = info.hasError
      this.informations.errorList = info.errorList
      this.informations.hasInfo = info.hasInfo
      this.informations.infoList = info.infoList
      this.informations.hasSuccess = info.hasSuccess
      this.informations.successList = info.successList
      this.informations.hasWarning = info.hasWarning
      this.informations.warningList = info.warningList
    },
    clearErrors: function () {
      this.informations.hasError = false
      this.informations.errorList = []
      this.emitErrors()
    },
    sendError: function (error) {
      this.informations.hasError = true
      this.informations.errorList.push(error)
      this.emitErrors()
    },
    emitErrors: function () {
      this.$emit('errors', this.informations.errorList)
    },
    clearWarnings: function () {
      this.informations.hasWarning = false
      this.informations.warningList = []
      this.emitWarnings()
    },
    sendWarning: function (warning) {
      this.informations.hasWarning = true
      this.informations.warningList.push(warning)
      this.emitWarnings()
    },
    emitWarnings: function () {
      this.$emit('warnings', this.informations.warningList)
    },
    clearInfos: function () {
      this.informations.hasInfo = false
      this.informations.infoList = []
      this.emitInfos()
    },
    sendInfo: function (info) {
      this.informations.hasInfo = true
      this.informations.infoList.push(info)
      this.emitInfos()
    },
    emitInfos: function () {
      this.$emit('infos', this.informations.infoList)
    }
  }
}
