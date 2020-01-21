<template>
    <div>
        <ul class="error"
            v-show="errorList.length > 0">
            <li :key="error"
                v-for="error in errorList">
                <font-awesome-icon icon="bomb"/>
                <span v-html="error"/>
            </li>
        </ul>
        <ul class="warning"
            v-show="warningList.length > 0">
            <li :key="warning"
                v-for="warning in warningList">
                <font-awesome-icon icon="exclamation"/>
                <span v-html="warning"/>
            </li>
        </ul>
        <ul class="info"
            v-show="infoList.length > 0">
            <li :key="info"
                v-for="info in infoList">
                <font-awesome-icon icon="info"/>
                <span v-html="info"/>
            </li>
        </ul>
        <ul class="success"
            v-show="successList.length > 0">
            <li :key="success"
                v-for="success in successList">
                <font-awesome-icon icon="thumbs-up"/>
                <span v-html="success"/>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: 'errors',
    props: {},
    data: () => ({
        hasError: false,
        errorList: [],
        hasWarning: false,
        warningList: [],
        hasInfo: false,
        infoList: [],
        hasSuccess: false,
        successList: []
    }),
    methods: {
        clearAll: function () {
            this.clearErrors()
            this.clearWarnings()
            this.clearSuccesses()
            this.clearInfos()
        },
        checkInformations: function (info) {

            this.hasError = info.hasError
            this.errorList = info.errorList
            this.hasInfo = info.hasInfo
            this.infoList = info.infoList
            this.hasSuccess = info.hasSuccess
            this.successList = info.successList
            this.hasWarning = info.hasWarning
            this.warningList = info.warningList
        },
        clearErrors: function () {
            this.hasError = false
            this.errorList = []
            this.emitErrors()
        },
        sendError: function (error) {
            this.hasError = true
            this.errorList.push(error)
            this.emitErrors()
        },
        emitErrors: function () {
            this.$emit('errors', this.errorList)
        },
        clearWarnings: function () {
            this.hasWarning = false
            this.warningList = []
            this.emitWarnings()
        },
        sendWarning: function (warning) {
            this.hasWarning = true
            this.warningList.push(warning)
            this.emitWarnings()
        },
        emitWarnings: function () {
            this.$emit('warnings', this.warningList)
        },
        clearSuccesses: function () {
            this.hasSuccess = false
            this.successList = []
            this.emitSuccesses()
        },
        sendSuccess: function (success) {
            this.hasSuccess = true
            this.successList.push(success)
            this.emitSuccesses()
        },
        emitSuccesses: function () {
            this.$emit('successes', this.successList)
        },
        clearInfos: function () {
            this.hasInfo = false
            this.infoList = []
            this.emitInfos()
        },
        sendInfo: function (info) {
            this.hasInfo = true
            this.infoList.push(info)
            this.emitInfos()
        },
        emitInfos: function () {
            this.$emit('infos', this.infoList)
        },
        concatErrors: function (errors) {
            return this.errorList.concat(errors)
        },
        concatWarnings: function (warnings) {
            return this.warningList.concat(warnings)
        },
        concaInfos: function (infos) {
            return this.infoList.concat(infos)
        }
    },
    computed: {}
}
</script>

<style scoped>
    ul {
        list-style : none;
        margin     : 5px 5%;
        padding    : 5px 15px;
        text-align : left;
    }

    ul.error {
        background    : rgba(127, 0, 0, 0.13);
        border        : rgba(127, 0, 0, 0.53) 2px solid;
        border-radius : 3px;
        color         : #7F0000;
    }

    ul.success {
        background    : rgba(27, 94, 32, 0.13);
        border        : rgba(27, 94, 32, 0.53) 2px solid;
        border-radius : 3px;
        color         : #1B5E20;
    }

    ul.warning {
        background    : rgba(188, 81, 0, 0.13);
        border        : rgba(188, 81, 0, 0.53) 2px solid;
        border-radius : 3px;
        color         : #BC5100;
    }

    ul.info {
        background    : rgba(13, 71, 161, 0.13);
        border        : rgba(13, 71, 161, 0.53) 2px solid;
        border-radius : 3px;
        color         : #0D47A1;
    }
</style>