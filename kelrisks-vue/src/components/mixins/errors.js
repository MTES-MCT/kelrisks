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
    }
  }
}
