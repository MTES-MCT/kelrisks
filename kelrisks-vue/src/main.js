// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router'
// Font Awesome @ https://fontawesome.com/how-to-use/on-the-web/using-with/vuejs
import {library} from '@fortawesome/fontawesome-svg-core'
import {
    faBomb,
    faBriefcase,
    faCaretDown,
    faCaretUp,
    faCheck,
    faCheckCircle,
    faChevronLeft,
    faChevronRight,
    faExclamation,
    faExclamationTriangle,
    faFilePdf,
    faHome,
    faInfo,
    faMapMarkedAlt,
    faSearch,
    faSpinner,
    faThumbsUp,
    faTimes,
    faTruckMoving,
    faUndo,
    faUser
} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import moment from 'moment'

import {Icon} from 'leaflet'
import 'leaflet/dist/leaflet.css'

// Leaflet
// this part resolve an issue where the markers would not appear
delete Icon.Default.prototype._getIconUrl;

Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});

// Font-Awesome
library.add(faUser, faInfo, faThumbsUp, faBomb, faExclamation, faBriefcase, faChevronLeft, faTruckMoving, faChevronRight, faSearch, faSpinner, faUndo, faCheck, faTimes, faExclamationTriangle, faFilePdf, faCaretDown, faCaretUp, faCheckCircle, faMapMarkedAlt, faHome)
Vue.component('font-awesome-icon', FontAwesomeIcon)

// Vue Material @ https://vuematerial.io/getting-started
// Vue.use(VueMaterial)

Vue.config.productionTip = false

Vue.filter('formatDate', function (value, format) {
    if (value) {
        return moment(String(value)).format(format)
    }
})

/* eslint-disable no-new */
new Vue({
    render: h => h(App),
    router
}).$mount('#app')
