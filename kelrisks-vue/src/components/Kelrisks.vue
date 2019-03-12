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
               src="static/images/logo-marianne.svg"/><span class="navbar__domain">kelrisks</span><img alt="data.gouv.fr"
                                                                                                       class="navbar__gouvfr"
                                                                                                       src="static/images/pointdatagouvfr.svg"/>
        </a>

        <nav>
          <ul class="nav__links">
            <li class="nav__item"><a href="swagger-ui.html"
                                     id="api">{{ api.message }}</a></li>
          </ul>
        </nav>
      </div>
    </header>

    <!--suppress CssUnknownTarget -->
    <div class="hero"
         role="banner"
         style="background-image: url('static/images/banner-min.png'); background-size: auto; background-position-y: -30px">
      <div class="hero__container"
           v-bind:class="{'contracted':flow.index > 1}">
        <h1 class="hero__white-background">Kelrisks</h1>
        <p class="hero__white-background"
           v-bind:class="{'contracted':flow.index > 1}">Evaluez simplement et rapidement le risque de pollution de votre terrain</p>
        <p class="hero__white-background note"
           v-bind:class="{'contracted':flow.index > 1}">(Essonne)*</p>
      </div>
    </div>

    <main id="main"
          role="main">

      <section class="section section-white"
               id="section1"
               v-show="flow.index === 1">
        <div class="container">
          <div class="panel">
            <h2 class="section__title">Vous êtes ?</h2>
            <p class="section__subtitle">Afin de vous délivrer un niveau d'information qui corresponde au mieux à votre besoin</p>

            <!--<hr/>-->

            <br/>
            <a @click="flowNext()
                       _paq.push(['trackEvent', 'Flow', 'Demandeur', 'Particulier'])
                       form.categorieDemandeur = 1"
               class="button">
              <font-awesome-icon icon="user"/>
              Un particulier</a>
            <a @click="flowNext()
                       _paq.push(['trackEvent', 'Flow', 'Demandeur', 'Professionnel'])
                       form.categorieDemandeur = 2"
               class="button">
              <font-awesome-icon icon="briefcase"/>
              Un professionnel</a>
          </div>
        </div>
      </section>

      <section class="section section-white"
               id="section2"
               v-show="flow.index === 2">
        <div class="container">
          <div class="panel">
            <h2 class="section__title">Votre terrain</h2>
            <p class="section__subtitle">1/2 - Saissez votre commune</p>

            <!--<hr/>-->

            <ul class="error">
              <li :key="error"
                  v-for="error in informations.errorList">
                {{ error }}
              </li>
            </ul>
            <ul class="warning">
              <li :key="warning"
                  v-for="warning in informations.warningList">
                {{ warning }}
              </li>
            </ul>
            <ul class="info">
              <li :key="info"
                  v-for="info in informations.infoList">
                {{ info }}
              </li>
            </ul>
            <ul class="success">
              <li :key="success"
                  v-for="success in informations.successList">
                {{ success }}
              </li>
            </ul>

            <br/>

            <!--<div style="width: 100%; display: flex; justify-content: center;">-->
            <!--<autocomplete @result="onCodePostalChanged"-->
            <!--id="codepostal"-->
            <!--label-text="Code Postal"-->
            <!--name="codePostal"-->
            <!--v-bind:source="env.apiPath + '/adresse/commune/autocomplete/'"/>-->
            <!--</div>-->

            <div style="width: 50%; margin-left: 25%">
              <kr-input :errors="checks.codeCommuneError"
                        @selected="onCodePostalChanged"
                        label="Nom de commune ou Code postal"
                        name="codePostal"
                        option-label-property="codePostal"
                        option-value-property="codeINSEE"
                        :start-at="3"
                        v-bind:source="env.apiPath + '/adresse/commune/autocomplete/'">
                <template slot="kr-option-label"
                          slot-scope="slotProps">
                  {{ slotProps.option.codePostal + ' - ' + slotProps.option.nomCommune}}
                </template>
                <template slot="kr-helper"
                          slot-scope="slotProps">
                  {{ slotProps.option.nomCommune }}
                </template>
                <template slot="kr-no-results"
                          slot-scope="slotProps">
                  Aucune commune trouvé pour "{{ slotProps.query }}"
                </template>
              </kr-input>
            </div>

            <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
              <a @click="flowPrevious()"
                 class="button">
                <font-awesome-icon icon="chevron-left"/>
                Précédent</a>
              <a @click="checkCodePostal()"
                 class="button">Suivant
                <font-awesome-icon :style="{margin : '0 0 0 10px'}"
                                   icon="chevron-right"/>
              </a>
            </div>
            <!--</form>-->
          </div>
        </div>
      </section>

      <section class="section section-white"
               id="section3"
               v-show="flow.index === 3">
        <div class="container">
          <div class="panel">
            <h2 class="section__title">Votre terrain</h2>
            <p class="section__subtitle">2/2 - Informations complémentaires</p>

            <!--<hr/>-->

            <ul class="error">
              <li :key="error"
                  v-for="error in informations.errorList">
                {{ error }}
              </li>
            </ul>
            <ul class="warning">
              <li :key="warning"
                  v-for="warning in informations.warningList">
                {{ warning }}
              </li>
            </ul>
            <ul class="info">
              <li :key="info"
                  v-for="info in informations.infoList">
                {{ info }}
              </li>
            </ul>
            <ul class="success">
              <li :key="success"
                  v-for="success in informations.successList">
                {{ success }}
              </li>
            </ul>

            <br/>

            <div style="width: 40%; float: left; margin-left: 5%">
              <kr-input :start-at="2"
                        @selected="onNomVoieChanged"
                        label="Nom voie"
                        name="nomVoie"
                        v-bind:source="env.apiPath + '/adresse/voie/autocomplete/' + form.codeINSEE + '/'">
                <template slot="kr-no-results"
                          slot-scope="slotProps">
                  Aucune voie trouvée pour "{{ slotProps.query }}"
                </template>
              </kr-input>

              <kr-input :start-at="1"
                        @selected="onNumeroChanged"
                        label="Numéro voie"
                        name="numero"
                        v-bind:source="env.apiPath + '/adresse/numero/autocomplete/' + form.codeINSEE + '/' + form.nomVoie + '/'">
                <template slot="kr-no-results"
                          slot-scope="slotProps">
                  Aucun numéro trouvé pour "{{ slotProps.query }}"
                </template>
                <template slot="kr-helper"
                          slot-scope="slotProps">
                  IBAN&nbsp;: {{ slotProps.option.id }}
                </template>
              </kr-input>
            </div>

            <div style="width: 10%; float: left; margin-top: 35px">
              <p class="section__subtitle">Ou</p>
            </div>
            <div style="width: 40%; float: left; margin-right: 5%">
              <kr-input label="Code Parcelle"
                        name="codeparcelle"
                        placeholder="BA-115 ou 912250000A0352"
                        v-model="form.parcelle"/>
            </div>

            <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
              <p class="section__subtitle">Optionnel</p>
            </div>

            <div style="width: 90%; margin-left: 5%">
              <kr-input :start-at="3"
                        label="Nom de l'ancien propriétaire / Raison sociale"
                        name="raisonSociale"
                        @query="onNomProprietaireChanged"
                        @selected="onNomProprietaireSelected"
                        v-bind:source="env.apiPath + '/raison/autocomplete/' + form.codeINSEE + '/'">
                <template slot="kr-no-results"
                          slot-scope="slotProps">
                  Aucun numéro trouvé pour "{{ slotProps.query }}"
                </template>
              </kr-input>
            </div>

            <div style="width: 100%; display: flex; justify-content: center; margin-top: 40px;">
              <a @click="flowPrevious()"
                 class="button">
                <font-awesome-icon icon="chevron-left"/>
                Précédent</a>
              <button type="submit"
                      class="button"
                      name="subscribe"
                      v-if="avis.querying">
                <font-awesome-icon icon="spinner"
                                   spin/>
                Recherche en cours...
              </button>
              <button @click="getAvis()"
                      class="button"
                      id="submit"
                      name="subscribe"
                      type="submit"
                      v-else>
                <font-awesome-icon icon="search"/>
                Rechercher
              </button>
              <!--<i class="fas fa-search left"></i>-->
            </div>
            <!--</form>-->
          </div>
        </div>
      </section>

      <section class="section section-white"
               id="section4"
               v-show="flow.index === 4">
        <div class="container">
          <div class="panel clearfix">
            <h2 class="section__title"
                v-if="concordances && concordances > 0">La recherche Kelrisks a trouvé {{ concordances }} concordance{{ concordances > 1 ? 's': ''}} dans les bases de données</h2>
            <h2 class="section__title"
                v-else>La recherche Kelrisks n'a trouvé aucune concordance dans les bases de données</h2>
            <p class="section__subtitle">Attention, même en l'absence de concordance, la présente recherche peut présenter des informations relatives à l'environnement de votre parcelle</p>

            <!--<hr/>-->

            <br/>

            <div id="summary_wrapper">
              <div id="summary">
                <div class="section__subtitle"><strong>Votre recherche : </strong></div>
                Code postal&nbsp;: <span v-if="form.codePostal && form.codePostal !== ''">{{form.codePostal}}</span><span v-else><i>n/a</i></span><br/>
                Rue&nbsp;: <span v-if="form.nomVoieLib && form.nomVoieLib !== ''">{{form.nomVoieLib}}</span><span v-else><i>n/a</i></span><br/>
                N°&nbsp;: <span v-if="form.numeroVoieLib && form.numeroVoieLib !== ''">{{form.numeroVoieLib}}</span><span v-else><i>n/a</i></span><br/>
                Code parcelle&nbsp;: <span v-if="form.parcelle && form.parcelle !== ''">{{form.parcelle}}</span><span v-else><i>n/a</i></span><br/>
                Raison Sociale&nbsp;: <span v-if="form.proprio && form.proprio !== ''">{{form.proprio}}</span><span v-else><i>n/a</i></span><br/>
                <hr/>
                <div style="text-align: center; padding-top: 20px;">
                  <a @click="flowPrevious()
                             _paq.push(['trackEvent', 'Flow', 'Avis', 'Modifier'])"
                     class="button">
                    <font-awesome-icon icon="undo"/>
                    Modifier</a><br/>
                  <a :href="this.env.apiPath + '/avis/pdf?' + 'codeINSEE=' + this.form.codeINSEE + '&' + 'nomVoie=' + this.form.nomVoie + '&' + 'idBAN=' + this.form.idBAN + '&' + 'codeParcelle=' + this.form.parcelle + '&' + 'nomProprietaire=' + this.form.proprio"
                     class="button warning"
                     @click="_paq.push(['trackEvent', 'Flow', 'Pdf'])"
                     id="pdf"
                     target="_blank">
                    <font-awesome-icon icon="file-pdf"/>
                    Pdf
                  </a>
                </div>
              </div>
              <div style="text-align: center; padding-top: 20px;">
                <a :href="env.basePath"
                   @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Nouvel'])"
                   class="button">
                  <font-awesome-icon icon="undo"/>
                  Nouvelle recherche</a><br/>
              </div>
            </div>
            <div id="avis">
              <!--Recherche de sites <br/>-->

              <big-number :number-of="avis.basiasParcelle.numberOf"
                          label-text="Sites polués BASIAS"/>

              <big-number :number-of="avis.basolParcelle.numberOf"
                          label-text="Sites polués BASOL"/>

              <big-number :number-of="avis.installationClasseeParcelle.numberOf"
                          Compte
                          ce
                          de
                          précède
                          qui
                          tenu
                          label-text="Installations classées"/>

              <big-number :number-of="avis.installationClasseeParcelle.numberOf"
                          label-text="SIS"/>
              <br/>

              <template v-if="avis.basiasProximiteParcelle.numberOf > 0 || avis.basiasRaisonSociale > 0">
                <p class="indent">Par ailleurs nous identifions : </p>
                <template v-if="avis.basiasProximiteParcelle.numberOf > 0">
                  <p class="indent"> - {{ avis.basiasProximiteParcelle.lib }}</p>
                  <ul class="site-list">
                    <li :key="ic.id"
                        v-for="ic in avis.basiasProximiteParcelle.liste">
                      <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + ic.identifiant"
                         target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ ic.identifiant }}</a>
                    </li>
                  </ul>
                </template>
                <template v-if="avis.basiasRaisonSociale.numberOf > 0">
                  <p class="indent"> - {{ avis.basiasRaisonSociale.lib }}</p>
                  <ul class="site-list">
                    <li :key="ic.id"
                        v-for="ic in avis.basiasRaisonSociale.liste">
                      <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + ic.identifiant"
                         target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ ic.identifiant }}</a>
                    </li>
                  </ul>
                </template>
                <br/>
              </template>

              <p class="section__subtitle"><strong>Conséquences</strong></p>

              <div id="conclusion0"
                   style="text-align: justify"
                   v-if="avis.basiasParcelle.numberOf === 0 && avis.basolParcelle.numberOf === 0 && avis.installationClasseeParcelle.numberOf === 0 && avis.basiasProximiteParcelle.numberOf === 0">
                <p>Au regard de ces éléments, le propriétaire ou le bailleur n'est tenu à aucune obligation réglementaire en terme d'information acquéreur locataire au titre des pollutions de sols
                   d’origine industrielle.</p>
              </div>
              <div id="conclusion1"
                   style="text-align: justify"
                   v-if="avis.basiasParcelle.numberOf > 0 && (avis.basolParcelle.numberOf > 0 || avis.installationClasseeParcelle.numberOf > 0)">
                <p>En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur ou au locataire conformément à la réglementation en vigueur (article L. 514-20 du code
                   de l’environnement et L 125-7 du code de l’Environnement si positif SIS).</p>
                <p>En outre compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien) , la réalisation
                   d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions.</p>
                <p>Nous vous rappellons que seul les bureau d'études disposant de la certification à la norme NF 31-620 sont compétent pour délivrer les attestations exigées au titre du code de
                   l'urbanisme. Vous trouverez en cliquant sur ce lien (<a href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr</a>)
                   la liste de ces bureaux d'étude</p>
              </div>
              <div id="conclusion2"
                   style="text-align: justify"
                   v-if="avis.basiasParcelle.numberOf > 0 && avis.basolParcelle.numberOf === 0 && avis.installationClasseeParcelle.numberOf === 0">
                <p>En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur conformément aux articles L. 514-20 du code de l’environnement.</p>
                <p>Par ailleurs, ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>
                <p>Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la réalisation
                   d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions.</p>
                <p>Nous vous rappellons que seul les bureau d'études disposant de la certification à la norme NF 31-620 sont compétent pour délivrer les attestations exigées au titre du code de
                   l'urbanisme. Vous trouverez en cliquant sur ce lien (<a href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr</a>)
                   la liste de ces bureaux d'étude</p>
              </div>
              <div id="conclusion3"
                   style="text-align: justify"
                   v-if="avis.basiasParcelle.numberOf === 0 && avis.basolParcelle.numberOf === 0 && avis.installationClasseeParcelle.numberOf === 0 && avis.basiasProximiteParcelle.numberOf > 0">
                <p>Ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>
                <p>Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de vente ou de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la
                   réalisation d'une étude historique dans un souci d'une meilleure prise en compte d'éventuelles pollutions.</p>
                <p>Nous vous rappellons que seul les bureau d'études disposant de la certification à la norme NF 31-620 sont compétent pour délivrer les attestations exigées au titre du code de
                   l'urbanisme. Vous trouverez en cliquant sur ce lien (<a href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr</a>)
                   la liste de ces bureaux d'étude</p>
              </div>

              <br/>

              <section class="section section-white"
                       id="details"
                       style="text-align: left">

                <p @click="showHideContent()
                           _paq.push(['trackEvent', 'Flow', 'Avis', 'Détails'])"
                   class="section__subtitle"
                   style="margin-bottom: 0; cursor: pointer;">Détails et analyse à 100m</p>
                <a @click="showHideContent()
                           _paq.push(['trackEvent', 'Flow', 'Avis', 'Détails'])"
                   style="position: absolute; top: 25px; right: 25px; text-decoration: none; background: none">
                  <font-awesome-icon icon="caret-down"
                                     size="2x"
                                     v-if="!visibility.details"/>
                  <font-awesome-icon icon="caret-up"
                                     size="2x"
                                     v-else/>
                </a>
                <div class="section_content"
                     v-if="visibility.details">
                  <br/>
                  Votre parcelle, <br/>
                  <p>{{ avis.basiasParcelle.lib }}</p>
                  <template v-if="avis.basiasParcelle.numberOf > 0">
                    <ul class="site-list">
                      <li :key="ic.id"
                          v-for="ic in avis.basiasParcelle.liste">
                        - <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + ic.identifiant"
                             target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ ic.identifiant }}</a>
                      </li>
                    </ul>
                  </template>
                  <p>{{ avis.basolParcelle.lib }}</p>
                  <template v-if="avis.basolParcelle.numberOf > 0">
                    <ul class="site-list">
                      <li :key="ic.id"
                          v-for="ic in avis.basolRayonParcelle.liste">
                        - <a>https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp={{ ic.identifiant }}</a></li>
                    </ul>
                  </template>
                  <p>{{ avis.installationClasseeParcelle.lib }}</p>
                  <template v-if="avis.installationClasseeParcelle.numberOf > 0">
                    <ul class="site-list">
                      <li :key="ic.id"
                          v-for="ic in avis.installationClasseeParcelle.liste">
                        - {{ ic.nom }}
                      </li>
                    </ul>
                  </template>
                  <p>{{ avis.sisParcelle.lib }}</p>

                  <template v-if="avis.basiasRayonParcelle.numberOf > 0 || avis.basolRayonParcelle.numberOf > 0 || avis.installationClasseeRayonParcelle.numberOf > 0">
                    <p>Pour information, dans un rayon de 100m&nbsp;:</p>

                    <template v-if="avis.basiasRayonParcelle.numberOf > 0">
                      <p v-if="avis.basiasRayonParcelle.numberOf === 1">Se trouve 1 site Basias dont la fiche est consultable en cliquant sur le lien suivant&nbsp;:</p>
                      <p v-else>Se trouvent {{ avis.basiasRayonParcelle.numberOf }} sites Basias dont les fiches sont consultables en cliquant sur les liens suivants&nbsp;:</p>
                      <ul class="site-list">
                        <li :key="ic.id"
                            v-for="ic in avis.basiasRayonParcelle.liste">
                          - <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + ic.identifiant"
                               target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ ic.identifiant }}</a>
                        </li>
                      </ul>
                    </template>
                    <template v-if="avis.basolRayonParcelle.numberOf > 0">
                      <p v-if="avis.basiasRayonParcelle.numberOf === 1">Se trouve 1 site Basol dont la fiche est consultable en cliquant sur le lien suivant&nbsp;:</p>
                      <p v-else>Se trouvent {{ avis.basolRayonParcelle.numberOf }} sites Basol dont les fiches sont consultables en cliquant sur les liens suivants&nbsp;:</p>
                      <ul class="site-list">
                        <li :key="ic.id"
                            v-for="ic in avis.basolRayonParcelle.liste">
                          - <a>https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp={{ ic.identifiant }}</a></li>
                      </ul>
                    </template>
                    <template v-if="avis.installationClasseeRayonParcelle.numberOf > 0">
                      <p v-if="avis.installationClasseeRayonParcelle.numberOf === 1">Se trouve 1 installation classée&nbsp;: </p>
                      <p v-else>Se trouvent {{ avis.installationClasseeRayonParcelle.numberOf }} installations classées&nbsp;: </p>
                      <ul class="site-list">
                        <li :key="ic.id"
                            v-for="ic in avis.installationClasseeRayonParcelle.liste">
                          - {{ ic.nom }}
                        </li>
                      </ul>
                    </template>
                  </template>
                  <!--Se trouvent {{ avis. }} SIS&nbsp;: <br/>-->

                  <template v-if="avis.installationClasseeCommune.numberOf > 0"><p>Enfin, nous avons trouvé {{ avis.installationClasseeCommune.numberOf }} installation(s) classée(s) non géoréférencées
                                                                                   dans la commune.</p></template>
                </div>
              </section>
            </div>
          </div>
        </div>
      </section>

    </main>

    <div style="width: 100%;">
      <p style="font-size: 0.8em; text-align: center;">(Essonne)* - Territoire d'expérimentation.</p>
    </div>

    <div class="panel hidden"
         id="contact">
      <font-awesome-icon @click="openCloseContact()"
                         class="close"
                         icon="caret-down"
                         size="lg"
                         v-show="contact.opened"/>
      <font-awesome-icon @click="openCloseContact()"
                         icon="caret-up"
                         size="lg"
                         class="close"
                         v-show="!contact.opened"/>
      <p @click="openCloseContact()"
         class="section__subtitle">Une remarque? Une suggestion?</p>
      <textarea id="contactContent"
                title="Remarque/Suggestion/Problème?"></textarea>
      <button @click="sendMail()"
              class="button">Envoyer
      </button>
    </div>

  </div>
</template>

<script>
import BigNumber from './BigNumber'
import functions from '../script/fonctions'
import konami from '../script/konami'
import avis from '../script/avis'
import KrInput from './KrInput'
import JQuery from 'jquery'

let $ = JQuery

export default {
  name: 'Kelrisks',
  data: () => ({
    visibility: {
      details: false
    },
    informations: {
      hasError: false,
      errorList: [],
      hasWarning: false,
      warningList: [],
      hasInfo: false,
      infoList: [],
      hasSuccess: false,
      successList: []
    },
    flow: {
      index: 1
    },
    form: {
      categorieDemandeur: 0,
      communes: [],
      communeLib: '',
      codePostal: null,
      codeINSEE: null,
      nomVoieLib: '',
      nomVoie: '',
      numeroVoieLib: '',
      idBAN: '',
      parcelle: '',
      proprio: ''
    },
    checks: {
      codeCommuneError: []
    },
    api: {
      message: 'API'
    },
    avis: {
      querying: false,
      rendered: false,
      basiasNbOf: 0,
      basiasParcelle: {},
      basiasProximiteParcelle: {},
      basiasRayonParcelle: {},
      basiasRaisonSociale: {},
      basolNbOf: 0,
      basolParcelle: {},
      basolProximiteParcelle: {},
      basolRayonParcelle: {},
      s3ICNbOf: 0,
      installationClasseeParcelle: {},
      installationClasseeProximiteParcelle: {},
      installationClasseeRayonParcelle: {},
      installationClasseeCommune: {},
      sisParcelle: {}
    },
    contact: {
      opened: false,
      timesUp: false,
      countDown: null
    },
    env: {
      basePath: process.env.VUE_APP_PATH,
      apiPath: process.env.VUE_APP_API_PATH,
      startTime: (new Date()).getTime()
    }
  }),
  components: {
    KrInput,
    BigNumber
  },
  methods: {
    openCloseContact () {
      if (this.contact.opened) this.closeContact()
      else this.openContact()
    },
    closeContact () {
      $('#contact').addClass('hidden')
      this.contact.opened = false
    },
    openContact () {
      $('#contact').removeClass('hidden')
      this.contact.opened = true
    },
    countDown () {
      this.contact.timesUp = (new Date()).getTime() - this.env.startTime > 1000 * 30
      if (this.contact.timesUp) {
        this.openContact()
        clearInterval(this.contact.countDown)
      }
    },
    sendMail () {
      fetch(this.env.basePath + 'mail', {
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({content: $('#contactContent').val()})
      }).then(stream => stream.json())
        .then(value => {
          this.closeContact()
          $('#contactContent').val('')
          console.log(value)
        })
    },
    showHideContent () {
      // console.log(this.visibility.details)
      this.visibility.details = !this.visibility.details
      // console.log(this.visibility.details)
    },
    onCodePostalChanged (value) {
      this.form.codeINSEE = value.codeINSEE
      this.form.codePostal = value.codePostal
      this.form.nomVoie = ''
      this.form.nomVoieLib = ''
      this.form.idBAN = ''
      this.form.numeroVoieLib = ''
    },
    onNomVoieChanged (value) {
      this.form.nomVoie = value.code
      this.form.nomVoieLib = value.libelle
      this.form.idBAN = ''
      this.form.numeroVoieLib = ''
    },
    onNumeroChanged (value) {
      this.form.idBAN = value.code
      this.form.numeroVoieLib = value.libelle
    },
    onNomProprietaireSelected (value) {
      this.form.proprio = value.code
    },
    onNomProprietaireChanged (value) {
      this.form.proprio = value
    },
    flowNext () {
      this.flow.index++
      functions.scrollToElement('main', false)
    },
    flowPrevious () {
      this.flow.index--
      functions.scrollToElement('main', false)
    },
    checkCodePostal () {
      this.informations.errorList = []
      if (/^\d{5}$/.test(this.form.codeINSEE)) {
        this.checks.codeCommuneError = []
        this.flowNext()
        this._paq.push(['trackEvent', 'Flow', 'Informations 1/2', 'OK'])
      } else {
        this.checks.codeCommuneError = ['Merci de bien vouloir sélectionner une commune au moyen de l\'autocomplétion.']
        this._paq.push(['trackEvent', 'Flow', 'Informations 1/2', 'Erreur Autocomplétion'])
      }
    },
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
    getAvis () {
      this.avis.querying = true
      fetch(this.env.apiPath + '/avis?' + 'codeINSEE=' + this.form.codeINSEE + '&' + 'nomVoie=' + this.form.nomVoie + '&' + 'idBAN=' + this.form.idBAN + '&' + 'codeParcelle=' + this.form.parcelle + '&' + 'nomProprietaire=' + this.form.proprio)
        .then(stream => stream.json())
        .then(value => {
          this.avis.querying = false
          this.checkInformations(value.entity)
          if (this.informations.hasError) {
            this._paq.push(['trackEvent', 'Flow', 'Informations 2/2', 'Erreur'])
            return
          }

          this._paq.push(['trackEvent', 'Flow', 'Informations 2/2', 'OK'])

          this.flow.index++

          this.avis.basiasParcelle = avis.getBasiasParcelle(value)
          this.avis.basiasProximiteParcelle = avis.getBasiasProximiteParcelle(value)
          this.avis.basiasRaisonSociale = avis.getBasiasRaisonSocialeParcelle(value)
          this.avis.basiasRayonParcelle = avis.getBasiasRayonParcelle(value)

          this.avis.basolParcelle = avis.getBasolParcelle(value)
          this.avis.basolProximiteParcelle = avis.getBasolProximiteParcelle(value)
          this.avis.basolRayonParcelle = avis.getBasolRayonParcelle(value)

          this.avis.installationClasseeParcelle = avis.getICSurParcelle(value)
          this.avis.installationClasseeProximiteParcelle = avis.getICProximiteParcelle(value)
          this.avis.installationClasseeRayonParcelle = avis.getICRayonParcelle(value)
          this.avis.installationClasseeCommune = avis.getICNonGeoreferencees(value)

          this.avis.sisParcelle = avis.getSISSurParcelle(value)

          functions.scrollToElement('main', false)
          this._paq.push(['trackEvent', 'Flow', 'Avis', 'Rendu'])
        })
    }
  },
  computed: {
    concordances: function () {
      return this.avis.installationClasseeParcelle.numberOf + this.avis.basolParcelle.numberOf + this.avis.basiasParcelle.numberOf
    },
    _paq: function () {
      return window._paq
    }
  },
  mounted () {
    this.contact.countDown = setInterval(() => {
      this.countDown()
    }, 1000)
    konami.init()
  },
  beforeDestroy () {
    clearInterval(this.contact.countDown)
  }
}
</script>

<style>
  body {
    overflow-y : scroll;
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

  section {
    position : relative;
  }

  .section__subtitle {
    margin-bottom : 40px;
  }

  #summary_wrapper {
    float      : left;
    width      : 30%;
    text-align : left;
  }

  #summary, #avis, #details {
    background-color : #FFFFFF;
    border           : 1px solid #CCCCCC;
    border-radius    : 2px;
    /*float            : left;*/
    padding          : 30px 20px;
  }

  #avis {
    float : right;
    width : 68%;
  }

  p {
    text-indent : 12px;
    text-align  : justify;
  }

  .site-list {
    list-style : none;
  }

  .clearfix:after {
    content    : '';
    display    : block;
    height     : 0;
    clear      : both;
    visibility : hidden;
  }

  #contact {
    transition : bottom 0.33s ease;
    width      : 400px;
    position   : fixed;
    bottom     : -1px;
    right      : 10px;
  }

  #contact.hidden {
    bottom : -167px;
  }

  #contact .close {
    position : absolute;
    top      : 15px;
    right    : 6px;
    cursor   : pointer;
  }

  #contact p.section__subtitle {
    cursor        : pointer;
    margin-bottom : 15px;
    margin-top    : -10px;
  }

  #contact textarea {
    margin-bottom : 20px;
    resize        : none;
  }
</style>
