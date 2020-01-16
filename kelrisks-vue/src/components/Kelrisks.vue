<template>

    <div>
        <svg aria-hidden="true"
             focusable="false"
             style="display:none">
            <defs>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="copy"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M19.75 8.5V1H6.625L1 6.625V23.5h11.25V31H31V8.5H19.75zM6.625 3.651v2.974H3.651l2.974-2.974zm-3.75 17.974V8.5H8.5V2.875h9.375V8.5l-5.625 5.625v7.5H2.875zm15-10.474v2.974h-2.974l2.974-2.974zm11.25 17.974h-15V16h5.625v-5.625h9.375v18.75z"
                          fill-rule="nonzero"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="envelope"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <g fill-rule="nonzero">
                        <path d="M30.1 5.419c-9.355-.002-18.733-.005-28.1-.005A1.06 1.06 0 0 0 .975 6.439v19.122A1.06 1.06 0 0 0 2 26.586h28a1.061 1.061 0 0 0 1.025-1.025V6.439a1.056 1.056 0 0 0-.925-1.02zM3.025 7.464h25.95v17.072H3.025V7.464z"></path>
                        <path d="M30.06 9.513c.933.098 1.382 1.395.393 1.945L16.54 18.287c-.438.188-.479.178-.893 0L1.733 11.458c-1.743-.968-.065-2.254.894-1.842l13.466 6.61 13.562-6.651c.3-.094.312-.062.405-.062z"></path>
                    </g>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="facebook"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M28.188 1H3.812A2.821 2.821 0 0 0 1 3.813v24.375A2.821 2.821 0 0 0 3.813 31H16V17.875h-3.75v-3.75H16V12.25a5.635 5.635 0 0 1 5.625-5.625h3.75v3.75h-3.75a1.88 1.88 0 0 0-1.875 1.875v1.875h5.625l-.937 3.75H19.75V31h8.438A2.821 2.821 0 0 0 31 28.187V3.812A2.821 2.821 0 0 0 28.188 1z"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="github"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M16 1.371c-8.284 0-15 6.715-15 15 0 6.627 4.298 12.25 10.258 14.233.75.138 1.026-.326 1.026-.722 0-.357-.014-1.54-.021-2.793-4.174.907-5.054-1.77-5.054-1.77-.682-1.733-1.665-2.195-1.665-2.195-1.361-.931.103-.912.103-.912 1.506.106 2.299 1.546 2.299 1.546 1.338 2.293 3.509 1.63 4.365 1.247.134-.969.523-1.631.952-2.006-3.331-.379-6.834-1.666-6.834-7.413 0-1.638.586-2.976 1.546-4.027-.156-.378-.669-1.903.145-3.969 0 0 1.26-.403 4.126 1.537a14.453 14.453 0 0 1 3.755-.505c1.274.006 2.558.173 3.757.505 2.864-1.94 4.121-1.537 4.121-1.537.816 2.066.303 3.591.147 3.969.962 1.05 1.544 2.389 1.544 4.027 0 5.761-3.509 7.029-6.849 7.401.538.466 1.017 1.379 1.017 2.778 0 2.007-.018 3.623-.018 4.117 0 .399.27.867 1.03.72C26.707 28.616 31 22.996 31 16.371c0-8.285-6.716-15-15-15z"
                          fill-rule="nonzero"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="googleplus"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M28.188 1H3.812A2.821 2.821 0 0 0 1 3.813v24.375A2.821 2.821 0 0 0 3.813 31h24.375A2.821 2.821 0 0 0 31 28.187V3.812A2.821 2.821 0 0 0 28.187 1zM12.251 23.5a7.493 7.493 0 0 1-7.5-7.5c0-4.148 3.352-7.5 7.5-7.5 2.027 0 3.721.738 5.028 1.963l-2.039 1.958c-.557-.534-1.529-1.154-2.989-1.154-2.56 0-4.653 2.121-4.653 4.734s2.092 4.734 4.653 4.734c2.971 0 4.084-2.133 4.254-3.234h-4.253v-2.573h7.084c.064.375.117.75.117 1.243 0 4.289-2.872 7.33-7.201 7.33l-.001-.001zm15-7.5h-1.875v1.875h-1.875V16h-1.875v-1.875h1.875V12.25h1.875v1.875h1.875V16z"
                          fill-rule="nonzero"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="magnifier"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M30.07 26.529l-7.106-6.043c-.735-.662-1.521-.964-2.155-.936a11.194 11.194 0 0 0 2.691-7.299c0-6.214-5.036-11.25-11.25-11.25S1 6.037 1 12.251s5.036 11.25 11.25 11.25c2.786 0 5.334-1.012 7.299-2.691-.03.634.274 1.42.936 2.155l6.043 7.106c1.035 1.149 2.725 1.247 3.756.216 1.031-1.032.934-2.723-.216-3.756l.002-.002zm-17.82-6.78a7.5 7.5 0 1 1 0-15 7.5 7.5 0 0 1 0 15z"
                          fill-rule="nonzero"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="round-cross"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M16 .5C24.554.5 31.5 7.446 31.5 16S24.554 31.5 16 31.5.5 24.554.5 16 7.446.5 16 .5zm6.161 11.718a7.233 7.233 0 0 0-2.379-2.379L16 13.621l-3.782-3.782a7.233 7.233 0 0 0-2.379 2.379L13.621 16l-3.782 3.782a7.233 7.233 0 0 0 2.379 2.379L16 18.379l3.782 3.782a7.233 7.233 0 0 0 2.379-2.379L18.379 16l3.782-3.782z"
                          fill-rule="nonzero"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="rss"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M28.188 1H3.812A2.821 2.821 0 0 0 1 3.813v24.375A2.821 2.821 0 0 0 3.813 31h24.375A2.821 2.821 0 0 0 31 28.187V3.812A2.821 2.821 0 0 0 28.187 1zM9.175 25.352a2.541 2.541 0 0 1-2.549-2.537 2.553 2.553 0 0 1 2.549-2.543 2.55 2.55 0 0 1 2.549 2.543 2.541 2.541 0 0 1-2.549 2.537zm6.399.023a8.913 8.913 0 0 0-2.62-6.339 8.882 8.882 0 0 0-6.328-2.625v-3.668c6.961 0 12.633 5.666 12.633 12.633h-3.685v-.001zm6.51 0c0-8.526-6.932-15.469-15.451-15.469V6.239c10.546 0 19.13 8.589 19.13 19.137h-3.68v-.001z"
                          fill-rule="nonzero"></path>
                </symbol>
                <symbol clip-rule="evenodd"
                        fill-rule="evenodd"
                        id="twitter"
                        stroke-linejoin="round"
                        stroke-miterlimit="1.414"
                        viewBox="0 0 32 32"
                        xmlns="http://www.w3.org/2000/svg">
                    <path d="M31.003 6.695c-1.102.492-2.291.82-3.533.966a6.185 6.185 0 0 0 2.706-3.404 12.404 12.404 0 0 1-3.908 1.495 6.154 6.154 0 0 0-4.495-1.94 6.153 6.153 0 0 0-5.994 7.553A17.468 17.468 0 0 1 3.093 4.932a6.15 6.15 0 0 0-.831 3.094 6.147 6.147 0 0 0 2.736 5.122 6.16 6.16 0 0 1-2.789-.768v.076a6.154 6.154 0 0 0 4.94 6.034 6.149 6.149 0 0 1-2.783.106 6.177 6.177 0 0 0 5.748 4.277 12.347 12.347 0 0 1-9.117 2.549 17.4 17.4 0 0 0 9.44 2.766c11.32 0 17.513-9.381 17.513-17.514 0-.269-.005-.533-.017-.796a12.405 12.405 0 0 0 3.07-3.182v-.001z"
                          fill-rule="nonzero"></path>
                </symbol>
            </defs>
        </svg>

        <header class="navbar"
                role="navigation">
            <div class="navbar__container">
                <a class="navbar__home"
                   href="index.html">
                    <img alt="template.data.gouv.fr"
                         class="navbar__logo"
                         src="/images/logo-marianne.svg"/><span class="navbar__domain">kelrisks</span><img alt="data.gouv.fr"
                                                                                                           class="navbar__gouvfr"
                                                                                                           src="/images/pointbetagouvfr.svg"/>
                </a>

                <nav>
                    <ul class="nav__links">
                        <!--                        <li class="nav__item"><a @click="showStats()"-->
                        <!--                                                 id="stats">Stats</a></li>-->
                        <!--                        <li class="nav__item"><a href="/swagger-ui.html"-->
                        <!--                                                 id="api">{{ api.message }}</a></li>-->
                    </ul>
                </nav>
            </div>
        </header>

        <!--suppress CssUnknownTarget -->
        <div class="hero"
             role="banner"
             style="background-image: url('/images/banner-min.png'); background-size: auto; background-position-y: -30px">
            <div class="hero__container"
                 v-bind:class="{'contracted':flow.index > 1}">
                <h1 class="hero__white-background">Kelrisks</h1>
                <p class="hero__white-background"
                   v-bind:class="{'contracted':flow.index > 1}">Evaluez simplement et rapidement le risque de pollution de votre terrain</p>
                <!--                <p class="hero__white-background note"-->
                <!--                   v-bind:class="{'contracted':flow.index > 1}">(Île-de-France)*</p>-->
            </div>
        </div>

        <main id="main"
              role="main">

            <section class="section section-white"
                     id="section0"
                     v-show="flow.index === 0">
                <div class="container">
                    <div class="panel">
                        <h2 class="section__title">{{informations.errorList[0]}}</h2>
                        <p class="section__subtitle">{{informations.errorList[1]}}</p>
                    </div>
                </div>
                <br/>
                <br/>
            </section>

            <search-form-part1-vous-etes @flow="updateflow"
                                         v-show="flow.index === 1"/>
            <search-form-part3-parcelle :errors="searchResults.errors"
                                        :querying="flow.querying"
                                        :warnings="searchResults.warnings"
                                        @cgu="$refs.cgu.open()"
                                        @codeinsee="form.codeInsee = $event"
                                        @codeparcelle="form.codeParcelle = $event"
                                        @codeproprio="form.codeProprio = $event"
                                        @flow="updateflow"
                                        @geolocalisationadresse="form.geolocAdresse = $event"
                                        @getavis="getAvis"
                                        @nomadresse="form.nomAdresse = $event"
                                        v-show="flow.index === 2"/>
            <search-results :code-insee="form.codeInsee"
                            :code-parcelle="form.codeParcelle"
                            :code-proprio="form.codeProprio"
                            :geoloc-adresse="form.geolocAdresse"
                            :nom-adresse="form.nomAdresse"
                            @errors="searchErrors"
                            @flow="updateflow"
                            @loaded="loaded"
                            @loading="loading"
                            @requestfocus="renderAvis"
                            @setflow="setflow"
                            @warnings="searchWarnings"
                            ref="results"
                            v-show="flow.index === 3"/>

            <stats v-show="flow.index === 666"/>
        </main>

        <how-to class="clearfix container"
                v-show="flow.index <= 2"/>

        <!--        <div class="container">-->
        <!--            <p style="font-size: 0.8em; margin: 0 auto; text-align: left; color: #999999">(Île-de-France)* - Territoire d'expérimentation.</p>-->
        <!--        </div>-->

        <footer>
            <div>
                <div class="column">
                    <who-are-we ref="haw"/>
                    <a @click="$refs.haw.open()">Qui sommes nous ?</a>
                </div>
                <div class="column">
                    <c-g-u ref="cgu"/>
                    <a @click="$refs.cgu.open()">CGU</a>
                </div>
                <div class="column">
                    <a @click="showStats()">Stats</a>
                </div>
                <div class="column">
                    <a href="/swagger-ui.html">API</a>
                </div>
            </div>
            <div id="version">{{env.presentationVersion}}</div>
        </footer>

        <contact :timeout="45"/>

        <konami/>

        <div id="loading"
             v-show="flow.loading">
            <p>
                <font-awesome-icon icon="spinner"
                                   spin/>
                Chargement de votre recherche, merci de patienter...
            </p>
        </div>

    </div>
</template>

<script>
import Contact from '../components/content/Contact'
import CGU from '../components/content/CGU'
import HowTo from '../components/content/HowTo'
import WhoAreWe from '../components/content/WhoAreWe'
import Konami from '../components/content/Konami'
import mixinErrors from '../components/mixins/errors'
import SearchFormPart1VousEtes from '../components/content/search/SearchFormPart1VousEtes'
import SearchFormPart3Parcelle from '../components/content/search/SearchFormPart3Parcelle'
import SearchResults from '../components/content/search/SearchResults'
import Stats from '../components/content/Stats'
import fetchWithError from '../script/fetchWithError'
// import KrSelect from '../components/ui/KrSelect'
// import KrInput from './ui/KrInput';

export default {
    name: 'Kelrisks',
    mixins: [mixinErrors],
    data: () => ({
        flow: {
            index: 2,
            querying: false,
            loading: false
        },
        searchResults: {
            errors: [],
            warnings: []
        },
        form: {
            codeInsee: '',
            nomAdresse: '',
            geolocAdresse: '',
            codeProprio: '',
            codeParcelle: ''
        },
        api: {
            message: 'API'
        },
        env: {
            presentationVersion: process.env.VUE_APP_VERSION,
            version: '',
            apiPath: process.env.VUE_APP_API_PATH
        }
    }),
    components: {
        Stats,
        SearchResults,
        SearchFormPart3Parcelle,
        SearchFormPart1VousEtes,
        Contact,
        CGU,
        HowTo,
        WhoAreWe,
        Konami
    },
    methods: {
        updateflow (value) {
            this.flow.index += value
        },
        setflow (value) {
            this.flow.index = value
        },
        getAvis () {
            this.flow.querying = true
            this.$refs.results.getAvis()
        },
        renderAvis () {
            this.loaded()
            this.flow.querying = false
            this.flow.index = 3
        },
        loading () {
            this.searchResults.errors = []
            this.flow.loading = true
        },
        loaded () {
            this.flow.loading = false
        },
        debug () {
            // console.log(value)
        },
        searchErrors (value) {
            if (value.length > 0) {
                this.flow.querying = false
            }
            if (this.flow.loading) this.informations.errorList = value
            else this.searchResults.errors = value
        },
        searchWarnings (value) {
            if (value.length > 0) {
                this.flow.querying = false
            }
            if (this.flow.loading) this.informations.warningList = value
            else this.searchResults.warnings = value
        },
        showStats () {
            this.flow.index = 666
        }
    },
    computed: {
        _paq: function () {
            return window._paq
        }
    },
    beforeDestroy () {
    },
    mounted () {
        fetchWithError(this.env.apiPath + 'appversion/')
            .then(stream => stream.json())
            .then(value => {
                let currentAppVersion = value.entity
                let localAppVersion = localStorage.getItem('localKelrisksVersion')
                // console.log('localAppVersion : ' + localAppVersion)
                // console.log('currentAppVersion : ' + currentAppVersion)
                if (localAppVersion !== 'undefined') {
                    // console.log('Version Found !')
                    if (localAppVersion !== currentAppVersion) {
                        // console.log('Is mismatch :-(')
                        localStorage.setItem('localKelrisksVersion', currentAppVersion)
                        window.location.reload(true)
                    } else {
                        // console.log('Is match :-)')
                    }
                } else {
                    // console.log('Version NOT Found !')
                    localStorage.setItem('localKelrisksVersion', currentAppVersion)
                }
            })
    }
}
</script>

<style>
    html, body {
        height           : 100%;
        background-color : #FAFAFA;
        font-family      : 'Nunito Sans', sans-serif;
    }

    body {
        overflow-y : scroll;
    }

    .title {
        margin-top    : 0;
        font-weight   : 900;
        font-size     : 1.7em;
        color         : #53657D;
        margin-bottom : 20px;
        text-align    : center;
    }

    .subtitle {
        margin-top    : 0;
        font-size     : 1.3em;
        font-weight   : normal;
        color         : #8A8F96;
        margin-bottom : 20px;
        text-align    : center;
    }

    p {
        font-size   : 16px;
        font-weight : normal;
        color       : #222933;
        font-family : 'Nunito Sans', sans-serif;

    }

    .hero__container p {
        transition : all 0.5s;
    }

    .hero__container p.contracted {
        visibility       : collapse;
        margin           : -0.7em;
        padding          : 0;
        background-color : transparent;
        color            : transparent;
    }

    .hero__container {
        transition : height 0.66s;
        height     : 38vh;
        min-height : 12vh;
    }

    .hero__container.contracted {
        height : 12vh;
    }

    p.note {
        font-size : 1em; color : #555555;
    }

    .panel {
        overflow : visible;
        position : relative;
    }

    i {
        color : #777777;
    }

    section.section {
        position       : relative;
        padding-bottom : 25px;
    }

    .section__subtitle {
        margin-bottom : 40px;
    }

    #section0 {
        min-height : 100px;
    }

    p {
        /*text-indent : 12px;*/
        text-align : justify;
    }

    .clearfix:after {
        content    : '';
        display    : block;
        height     : 0;
        clear      : both;
        visibility : hidden;
    }

    .note_pied_page {
        /*width  : 53%;*/
        margin : 0 auto;
    }

    .note_pied_page p {
        font-size : 0.8em;
        color     : #999999;
    }

    #loading {
        position         : fixed;
        width            : 100%;
        height           : 100%;
        background-color : rgba(0, 0, 0, 0.73);
        top              : 0;
        left             : 0;
    }

    #loading p {
        color      : white;
        text-align : center;
        font-size  : 2em;
        margin-top : 25.5%;
    }

    footer {
        /*border-top: #CCCCCC solid 1px;*/
        width  : 60%;
        margin : 30px auto;
    }

    footer a {
        text-decoration : none;
        /*color           : #2C3E50;*/
    }

    footer .column {
        float   : left;
        padding : 15px;
        width   : 25%;
    }

    footer .column:nth-last-child(n+2) {
        border-right : #CCCCCC solid 1px;
    }

    #version {
        font-family : "Bradley Hand ITC", Arial, sans-serif;
        padding-top : 65px;
    }

    .bouton {
        background-color : #0053B3;
        border-bottom    : solid 3px #003B80;
        border-radius    : 2px;
        color            : #FFFFFF;
        display          : block;
        float            : left;
        font-size        : var(--space-s);
        margin-bottom    : 20px;
        margin-right     : 20px;
        padding          : 0.35em 1.75em;
        text-decoration  : none;
    }

    .bouton:hover {
        background-color : #003B80;
    }

    .lien {
        background      : none;
        color           : #0053B3;
        cursor          : pointer;
        padding         : 9px 0;
        text-decoration : none;
    }

    .lien.big {
        font-size : 1.3em;
        z-index   : 10;
    }

    .lien:hover {
        background : none;
        color      : #003B80;
    }

    svg {
        margin : 0 10px;
    }

    .button svg,
    .bouton svg {
        color  : #FFFFFF;
        margin : 0 10px 0 0;
    }
</style>
