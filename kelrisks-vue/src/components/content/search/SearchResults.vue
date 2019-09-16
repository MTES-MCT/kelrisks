<template>
    <section class="section section-white"
             id="section4">
        <a :href="this.env.apiPath + 'avis/pdf?' +
                            'codeINSEE=' + (tinyUrl.codeInsee ? tinyUrl.codeInsee : codeInsee) + '&' +
                            'nomAdresse=' + (tinyUrl.nomAdresse ? tinyUrl.nomAdresse : nomAdresse) + '&' +
                            'geolocAdresse=' + (tinyUrl.geolocAdresse ? tinyUrl.geolocAdresse.replace(/\|/, '%7C') : geolocAdresse.replace(/\|/, '%7C')) + '&' +
                            'codeParcelle=' + (tinyUrl.codeParcelle ? tinyUrl.codeParcelle : codeParcelle) + '&' +
                            'nomProprietaire=' + (tinyUrl.codeProprio ? tinyUrl.codeProprio : codeProprio)"
           @click="_paq.push(['trackEvent', 'Flow', 'Pdf'])"
           class="bouton"
           id="pdf"
           target="_blank">
            <font-awesome-icon icon="file-pdf"/>
            PDF
        </a>

        <a @click="copyLink"
           class="lien big"
           style="float: left">Copier l’URL</a>
        <input :value="env.basePath + '#/' + avis.summary.codeUrl"
               id="copyInput"
               style="position: absolute; left: -1000px; top: -1000px;"/>

        <div class="container bordered"
             id="summary_wrapper">
            <div id="summary">
                <div style="margin-bottom: 20px"><span class="title">Votre recherche </span><a @click="() => {  $emit('flow', -1)
                                                                                                _paq.push(['trackEvent', 'Flow', 'Avis', 'Modifier'])}"
                                                                                               class="lien"
                                                                                               v-show="visibility.modifier">Modifier</a>
                </div>
                <b>Adresse&nbsp;: </b><span v-if="avis.summary.adresse">{{avis.summary.adresse}}, {{avis.summary.commune.codePostal}} {{avis.summary.commune.nomCommune}}</span><span v-else-if="avis.summary.commune">{{avis.summary.commune.codePostal}}, {{avis.summary.commune.nomCommune}}</span><span v-else><i>n/a</i></span><br/>
                <b>Code parcelle&nbsp;: </b><span v-if="avis.summary.codeParcelle && avis.summary.codeParcelle !== ''">{{avis.summary.codeParcelle}}</span><span v-else><i>n/a</i></span><br/>
                <b>Raison
                   Sociale&nbsp;: </b><span v-if="avis.summary.nomProprietaire && avis.summary.nomProprietaire !== ''">{{avis.summary.nomProprietaire}}</span><span v-else><i>n/a</i></span><br/>
                <br>
                <a :href="env.basePath"
                   @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Nouvel'])"
                   class="lien">Nouvelle recherche</a>
            </div>
        </div>

        <div class="container bordered leaflet_wrapper">
            <leaflet :center="leaflet.center"
                     :data="leaflet.data"/>
        </div>

        <div class="container bordered"
             id="concordances_wrapper">

            <div class="tabWrapper">
                <div @click="tab.concordances.index = 'POLLUTION'"
                     class="tab"
                     v-bind:class="{ selected:tab.concordances.index === 'POLLUTION'}">Pollution
                </div>
                <div @click="tab.concordances.index = 'RISQUES'"
                     class="tab"
                     v-bind:class="{ selected:tab.concordances.index === 'RISQUES'}">Risques
                </div>
            </div>

            <template v-if="tab.concordances.index === 'POLLUTION'">

                <font-awesome-icon icon="exclamation"
                                   style="font-size: 50px; color: #F79D65; margin-bottom: 10px"
                                   v-if="concordances && concordances > 0"/>
                <font-awesome-icon icon="check"
                                   style="font-size: 50px; color: #86CB92; margin-bottom: 10px"
                                   v-else/>

                <p style="font-size: 20px; color: #2C3E50;"
                   v-if="concordances && concordances > 0">Votre terrain présente un risque de pollution</p>
                <p style="font-size: 20px; color: #2C3E50;"
                   v-else>Votre terrain ne semble pas pollué</p>

                <p style="font-size: 16px; color: #53657D;"
                   v-if="concordances && concordances > 0">Nous avons trouvé {{ concordances }} concordance{{ concordances > 1 ? 's': ''}} dans les bases de données</p>
                <p style="font-size: 16px; color: #53657D;"
                   v-else>Nous n'avons trouvé aucune concordance dans les bases de données</p>

                <div class="numbers_wrapper">
                    <big-number :any-match="(concordances && concordances > 0)"
                                :number-of="avis.basiasParcelle.numberOf"
                                info-text="Base des Anciens Sites Industriels et Activités de Services. C’est l’inventaire de toutes les activités industriels abandonnées ou non, susceptibles d'avoir engendré une pollution de l'environnement. L’inscription d’une parcelle à cet inventaire ne préjuge pas de l’existence d’une pollution."
                                label-text="Sites pollués BASIAS"/>

                    <big-number :any-match="(concordances && concordances > 0)"
                                :number-of="avis.basolParcelle.numberOf"
                                info-text="Base de données sur les sites et sols pollués. c’est l’inventaire des sites sur lesquels la présence d’une pollution a nécessité une action des pouvoirs publics soit à titre préventif soit à titre curatif."
                                label-text="Sites pollués BASOL"/>

                    <big-number :any-match="(concordances && concordances > 0)"
                                :number-of="avis.installationClasseeParcelle.numberOf"
                                info-text="Inventaire des activités régies par la réglementation des installations dites classées pour la protection de l’environnement en raison des risques, des pollutions et des nuisances que ces activités génèrent."
                                label-text="Installations classées"/>

                    <big-number :any-match="(concordances && concordances > 0)"
                                :number-of="avis.sisParcelle.numberOf"
                                info-text="Secteur d’information sur les sols. Lorsque l’Etat a connaissance d’une pollution des sols, il est tenu en application de l’article L 125-6 du code de l’environnement de délimiter ces secteurs par arrêté préfectoral. L’inscription d’une parcelle dans un secteur d’information sur les sols entraîne des obligations d’information des acquéreurs et des locataires et l’obligation en cas de changement d’usage (dépôt de permis de construire) de faire appel à un bureau d’étude spécialisés dans le domaine des sols pollués pour attester de la bonne prise en compte des pollutions des sols présentes."
                                label-text="SIS"/>
                </div>

                <a class="lien big"
                   href="mailto:Contact%20Kelrisks%20<contact@kelrisks.beta.gouv.fr>?subject=Signaler%20une%20erreur%20Kelrisks">Signaler une erreur</a>

            </template>

            <template v-if="tab.concordances.index === 'RISQUES'">

                <!--<font-awesome-icon icon="exclamation"
                                   style="font-size: 50px; color: #F79D65; margin-bottom: 10px"
                                   v-if="concordances_risques"/>
                <font-awesome-icon icon="check"
                                   style="font-size: 50px; color: #86CB92; margin-bottom: 10px"
                                   v-else/>

                <p style="font-size: 20px; color: #2C3E50;"
                   v-if="concordances_risques">Votre terrain présente un risque naturel<sup> (1)</sup></p>
                <p style="font-size: 20px; color: #2C3E50;"
                   v-else>Votre terrain ne semble pas présenter de risque naturel<sup> (1)</sup></p>-->

                <p style="font-size: 20px; color: #2C3E50;">Analyse de la parcelle donnée</p>

                <div class="numbers_wrapper">

                    <div class="icon-risque-wrapper">
                        <div class="icon-risque">
                            <img height="50"
                                 src="/images/icons/kelrisks/radon_ko.svg"
                                 v-if="this.avis.potentielRadon >= 3"
                                 width="50">
                            <img height="50"
                                 src="/images/icons/kelrisks/radon_ok.svg"
                                 v-else
                                 width="50">
                        </div>
                        <div class="icon-risque-label">Radon niveau {{this.avis.potentielRadon}}<sup style="font-size: 0.7em"> (*)</sup>
                            <div class="infobulle">Le Radon est un gaz radioactif, incolore, inodore et d'origine le plus souvent naturelle. Il est présent dans l'ensemble des sols du territoire dans
                                                   des proportions plus ou moins importances. Cancérigène pulmonaire, il peut présenter un risque pour la santé lorsqu’il s’accumule dans les bâtiments.
                                                   Les communes sont classées en fonction de leur potentiel radon selon trois catégories classées de 1 (faible) à 3 (fort).
                            </div>
                        </div>
                    </div>

                    <div class="icon-risque-wrapper">
                        <div class="icon-risque">
                            <img height="50"
                                 src="/images/icons/kelrisks/quake_ko.svg"
                                 v-if="this.avis.codeSismicite >= 3"
                                 width="50">
                            <img height="50"
                                 src="/images/icons/kelrisks/quake_ok.svg"
                                 v-else
                                 width="50">
                        </div>
                        <div class="icon-risque-label">Sismicité zone {{this.avis.codeSismicite}}<sup style="font-size: 0.7em"> (*)</sup>
                            <div class="infobulle">Un Zonage Sismique est une zone géographique dans laquelle la probabilité d’occurrence d’un séisme de caractéristiques données (magnitude, profondeur
                                                   focale) peut être considérée homogène en tout point. Il existe 5 catégories de zones classées de 1 (très faible) à 5 (la plus forte).
                            </div>
                        </div>
                    </div>

                    <div class="icon-risque-wrapper">
                        <div class="icon-risque">
                            <img height="50"
                                 src="/images/icons/kelrisks/ppr_ko.svg"
                                 v-if="this.avis.ppr.length > 0"
                                 width="50">
                            <img height="50"
                                 src="/images/icons/kelrisks/ppr_ok.svg"
                                 v-else
                                 width="50">
                        </div>
                        <div class="icon-risque-label">Plan de prévention des risques<sup style="font-size: 0.7em"> (*)</sup>
                            <div class="infobulle">Un PPR (plan de prévention des risques) est un document réalisé par l’État qui réglemente l’utilisation des sols, en fonction des risques auxquels
                                                   ils sont soumis. Cette réglementation va de l’interdiction de construire à la possibilité de construire sous certaines conditions en passant par
                                                   l'imposition d'aménagement aux constructions existantes. Les risques concernés par ce type de dispositif sont naturels (Inondations, mouvements de
                                                   terrains, incendies de forêt, avalanches, tempêtes, submersions marines, séismes, éruptions volcaniques, cyclones...) et/ou anthropiques/
                                                   technologiques/ miniers.
                            </div>
                        </div>
                    </div>

                </div>

                <p style="font-size: 20px; color: #2C3E50;">L’immeuble se situe dans une commune de sismicité classée en zone {{this.avis.codeSismicite}}</p>
                <p style="font-size: 20px; color: #2C3E50;">L’immeuble se situe dans une commune à potentiel radon classée en niveau {{this.avis.potentielRadon}}</p>
                <p style="font-size: 20px; color: #2C3E50;"
                   v-if="this.avis.ppr.length === 0">L’immeuble ne se situe dans aucun Plan de Prévention des Risques référencé</p>
                <p style="font-size: 20px; color: #2C3E50;"
                   v-else
                   v-bind:key="plan"
                   v-for="plan in avis.ppr">L’immeuble est situé dans le périmètre d’un {{ plan.categorie.famille.code }} de type {{ plan.categorie.libelle }},
                                            approuvé le {{ plan.dateValidite | formatDate('DD/MM/YYYY') }}.</p>

                <!--                <p class="renvoi">-->
                <!--                    <sup>(1)</sup> - Au regard des risques pour lesquels la recherche a été faite (Radon, Sismicité et PPR).</p>-->

                <div style="height: 20px"></div>
            </template>

        </div>

        <div class="container bordered"
             id="conclusion_wrapper"
             v-if="tab.concordances.index === 'POLLUTION'">
            <template v-if="avis.basiasProximiteParcelle.numberOf > 0 || avis.basiasRaisonSociale > 0">
                <div class="title">Voisinage :</div>
                <template v-if="avis.basiasProximiteParcelle.numberOf > 0">
                    <p>Dans le voisinage immédiat de la (ou des) parcelle(s), nous identifions des sites ayant accueilli par le passé une activité susceptible d'avoir pu générer une pollution des sols
                       (BASIAS).<br>
                       Vous pouvez consulter la fiche consacrée à cette activité industrielle à l'adresse suivante :</p>
                    <ul class="site-list">
                        <li :key="sibasias.id"
                            v-for="sibasias in avis.basiasProximiteParcelle.liste">
                            <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                               target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                        </li>
                    </ul>
                </template>
                <template v-if="avis.basiasRaisonSociale.numberOf > 0">
                    <p class="indent">{{ avis.basiasRaisonSociale.lib }}</p>
                    <ul class="site-list">
                        <li :key="sibasias.id"
                            v-for="sibasias in avis.basiasRaisonSociale.liste">
                            <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                               target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                        </li>
                    </ul>
                </template>
                <br/>
            </template>

            <div class="title">Conséquences :</div>
            <div id="conclusion0"
                 style="text-align: justify"
                 v-if="conclusion === 0">
                <p>Au regard de ces éléments, le propriétaire ou le bailleur n'est tenu à aucune obligation réglementaire en terme d'information acquéreur locataire au titre des pollutions
                   de sols d’origine industrielle.</p>
            </div>
            <div id="conclusion1"
                 style="text-align: justify"
                 v-if="conclusion === 1">
                <p>En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur ou au locataire conformément à la réglementation en vigueur (article L.
                   514-20
                   du code
                   de l’environnement et L 125-7 du code de l’Environnement si positif SIS).</p>
                <p>En outre, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la
                   réalisation
                   d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions. Nous vous rappelons que l'obligation de faire
                   appel
                   à un
                   bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne concerne que les attestations prévues aux
                   articles
                   L. 556-1
                   et L. 556-2 du code de l'environnement.</p>
                <p>Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le
                   COFRAC
                   (www.cofrac.fr) : à ce jour seul le LNE est accrédité.</p>
                <div style="width: 100%; text-align: center">
                    <a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                       class="bouton"
                       href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'
                       style="float: none; text-align: center">Accéder à la liste des bureaux d’études certifiés</a>
                </div>
            </div>
            <div id="conclusion2"
                 style="text-align: justify"
                 v-if="conclusion === 2">
                <p>En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur conformément aux articles L. 514-20 du code de l’environnement.</p>
                <p>Par ailleurs, ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>
                <p>Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la
                   réalisation
                   d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions. Nous vous rappelons que l'obligation de faire
                   appel
                   à un
                   bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne concerne que les attestations prévues aux
                   articles
                   L. 556-1
                   et L. 556-2 du code de l'environnement.</p>
                <p>Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le
                   COFRAC
                   (www.cofrac.fr) : à ce jour seul le LNE est accrédité.</p>
                <div style="width: 100%; text-align: center">
                    <a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                       class="bouton"
                       href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'
                       style="float: none; text-align: center">Accéder à la liste des bureaux d’études certifiés</a>
                </div>
            </div>
            <div id="conclusion3"
                 style="text-align: justify"
                 v-if="conclusion === 3">
                <p>Ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>
                <p>Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de destination du bien), la
                   réalisation
                   d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions. Nous vous rappelons que l'obligation de faire
                   appel
                   à un
                   bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne concerne que les attestations prévues aux
                   articles
                   L. 556-1
                   et L. 556-2 du code de l'environnement.</p>
                <p>Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont répertoriés par le
                   COFRAC
                   (www.cofrac.fr) : à ce jour seul le LNE est accrédité.</p>
                <div style="width: 100%; text-align: center">
                    <a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                       class="bouton"
                       href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'
                       style="float: none; text-align: center">Accéder à la liste des bureaux d’études certifiés</a>
                </div>
            </div>

        </div>

        <div class="container bordered"
             id="resume_wrapper"
             v-if="tab.concordances.index === 'POLLUTION'">
            <div class="title">Résumé et analyse à 100m :</div>
            <p><strong>Votre parcelle,</strong><br></p>
            <p>{{ '- ' + avis.basiasParcelle.lib }}</p>
            <template v-if="avis.basiasParcelle.numberOf > 0">
                <ul class="site-list">
                    <li :key="sibasias.id"
                        v-for="sibasias in avis.basiasParcelle.liste">
                        <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                           target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                    </li>
                </ul>
            </template>
            <p>{{ '- ' + avis.basolParcelle.lib }}</p>
            <template v-if="avis.basolParcelle.numberOf > 0">
                <ul class="site-list">
                    <li :key="sibasol.id"
                        v-for="sibasol in avis.basolParcelle.liste">
                        <a :href="'https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp=' + sibasol.numerobasol"
                           target="_blank">https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp={{ sibasol.numerobasol }}</a></li>
                </ul>
            </template>
            <p>{{ '- ' + avis.installationClasseeParcelle.lib }}</p>
            <template v-if="avis.installationClasseeParcelle.numberOf > 0">
                <ul class="site-list">
                    <li :key="ic.id"
                        v-for="ic in avis.installationClasseeParcelle.liste">
                        - {{ ic.nom }}
                    </li>
                </ul>
            </template>
            <p>{{ '- ' + avis.sisParcelle.lib }}</p>

            <template v-if="avis.basiasRayonParcelle.numberOf > 0 || avis.basolRayonParcelle.numberOf > 0 || avis.installationClasseeRayonParcelle.numberOf > 0">
                <strong>Dans un rayon de 100m&nbsp;:</strong>

                <template v-if="avis.basiasRayonParcelle.numberOf > 0">
                    <p v-if="avis.basiasRayonParcelle.numberOf === 1">Se trouve 1 site Basias dont la fiche est consultable en cliquant sur le lien suivant&nbsp;:</p>
                    <p v-else>Se trouvent {{ avis.basiasRayonParcelle.numberOf }} sites Basias dont les fiches sont consultables en cliquant sur les liens suivants&nbsp;:</p>
                    <ul class="site-list">
                        <li :key="sibasias.id"
                            v-for="sibasias in avis.basiasRayonParcelle.liste">
                            <a :href="'http://fiches-risques.brgm.fr/georisques/basias-synthetique/' + sibasias.identifiant"
                               target="_blank">http://fiches-risques.brgm.fr/georisques/basias-synthetique/{{ sibasias.identifiant }}</a>
                        </li>
                    </ul>
                </template>
                <template v-if="avis.basolRayonParcelle.numberOf > 0">
                    <p v-if="avis.basiasRayonParcelle.numberOf === 1">Se trouve 1 site Basol dont la fiche est consultable en cliquant sur le lien suivant&nbsp;:</p>
                    <p v-else>Se trouvent {{ avis.basolRayonParcelle.numberOf }} sites Basol dont les fiches sont consultables en cliquant sur les liens suivants&nbsp;:</p>
                    <ul class="site-list">
                        <li :key="sibasol.id"
                            v-for="sibasol in avis.basolRayonParcelle.liste">
                            <a :href="'https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp=' + sibasol.numerobasol"
                               target="_blank">https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp={{ sibasol.numerobasol }}</a></li>
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
        </div>

        <div class="container bordered"
             id="non_georef_wrapper"
             v-if="(tab.concordances.index === 'POLLUTION') && (avis.basiasNonGeorerencee.numberOf > 0 ||
                                                                avis.basolNonGeorerencee.numberOf > 0 ||
                                                                avis.installationClasseeNonGeorerencee.numberOf > 0 ||
                                                                avis.sisNonGeorerencee.numberOf > 0)">
            <div class="title">Installations sans références géographiques</div>
            <p>Dans la commune, nous avons également trouvé :</p>
            <ul class="site-list">
                <li v-if="avis.basiasNonGeorerencee.numberOf > 0">- {{ avis.basiasNonGeorerencee.numberOf }} installation(s) BASIAS</li>
                <li v-if="avis.basolNonGeorerencee.numberOf > 0">- {{ avis.basolNonGeorerencee.numberOf }} installation(s) BASOL</li>
                <li v-if="avis.installationClasseeNonGeorerencee.numberOf > 0">- {{ avis.installationClasseeNonGeorerencee.numberOf }} installation(s) classée(s)</li>
                <li v-if="avis.sisNonGeorerencee.numberOf > 0">- {{ avis.sisNonGeorerencee.numberOf }} secteur(s) d'information sur les sols</li>
            </ul>
            <p>qui ne possèdent pas de coordonnées géographiques identifiées. Sans localisations précises, elles sont localisées dans le centre de la commune par défaut. La présente analyse n'en tient
               donc pas compte.</p>
        </div>

        <div class="container"
             style="float: left">
            <div class="note_pied_page"></div>
        </div>

        <div style="clear: both; height: 100px;"></div>
    </section>
</template>

<script>
import BigNumber from '../../../components/ui/BigNumber'
import avis from '../../../script/avis'
import fetchWithError from '../../../script/fetchWithError'
import functions from '../../../script/fonctions'
import mixinErrors from '../../mixins/errors'
import Leaflet from "../Leaflet";

export default {
    mixins: [mixinErrors],
    name: 'SearchResults',
    components: {
        Leaflet,
        BigNumber
    },
    props: {
        codeParcelle: {
            type: String,
            default: ''
        },
        codeInsee: {
            type: String,
            default: ''
        },
        nomAdresse: {
            type: String,
            default: ''
        },
        geolocAdresse: {
            type: String,
            default: ''
        },
        codeProprio: {
            type: String,
            default: ''
        }
    },
    data: () => ({
        visibility: {
            details: false,
            modifier: true
        },
        env: {
            basePath: process.env.VUE_APP_PATH,
            apiPath: process.env.VUE_APP_API_PATH
        },
        avis: {
            summary: {
                adresse: {
                    rue: {}
                },
                commune: {}
            },
            querying: false,
            loading: false,
            rendered: false,
            basiasNbOf: 0,
            basiasParcelle: {},
            basiasProximiteParcelle: {},
            basiasRayonParcelle: {},
            basiasRaisonSociale: {},
            basiasNonGeorerencee: {},
            basolNbOf: 0,
            basolParcelle: {},
            basolProximiteParcelle: {},
            basolRayonParcelle: {},
            basolNonGeorerencee: {},
            s3ICNbOf: 0,
            installationClasseeParcelle: {},
            installationClasseeProximiteParcelle: {},
            installationClasseeRayonParcelle: {},
            installationClasseeNonGeorerencee: {},
            sisParcelle: {},
            sisNonGeorerencee: {},
            codeSismicite: 0,
            potentielRadon: 0,
            ppr: {}
        },
        tinyUrl: {
            codeParcelle: undefined,
            codeInsee: undefined,
            nomAdresse: undefined,
            geolocAdresse: undefined,
            codeProprio: undefined
        },
        leaflet: {
            data: undefined,
            center: [0, 0]
        },
        tab: {
            concordances: {
                index: 'POLLUTION'
            }
        }
    }),
    methods: {
        showHideContent () {
            // console.log(this.visibility.details)
            this.visibility.details = !this.visibility.details
            // console.log(this.visibility.details)
        },
        getAvis () {
            this.clearAll()

            if (this.codeParcelle === '' && this.geolocAdresse === '') {
                this.sendError('Merci de bien vouloir choisir une rue/numéro ou entrer une parcelle.')
            }

            let parcelle = this.tinyUrl.codeParcelle ? this.tinyUrl.codeParcelle : this.codeParcelle
            let insee = this.tinyUrl.codeInsee ? this.tinyUrl.codeInsee : this.codeInsee
            let nom = this.tinyUrl.nomAdresse ? this.tinyUrl.nomAdresse : this.nomAdresse
            let geoloc = this.tinyUrl.geolocAdresse ? this.tinyUrl.geolocAdresse : this.geolocAdresse
            let proprio = this.tinyUrl.codeProprio ? this.tinyUrl.codeProprio : this.codeProprio

            let url = this.env.apiPath + 'avis?' + 'codeINSEE=' + insee + '&' + 'codeParcelle=' + parcelle + '&' + 'nomAdresse=' + nom + '&' + 'geolocAdresse=' + geoloc.replace(/\|/, '%7C') + '&' + 'nomProprietaire=' + proprio
            fetchWithError(url, null, 1000 * 20)
                .then(stream => stream.json())
                .then(value => {
                    // console.log(value.entity)
                    this.checkInformations(value.entity)
                    if (this.informations.hasError) {
                        this._paq.push(['trackEvent', 'Flow', 'Informations', 'Erreur'])
                        this.emitErrors()
                        return
                    }
                    if (this.informations.hasWarning) {
                        this._paq.push(['trackEvent', 'Flow', 'Informations', 'Warning'])
                        this.emitWarnings()
                        return
                    }
                    this.avis.summary = value.entity.summary
                    this._paq.push(['trackEvent', 'Flow', 'Informations', 'OK'])

                    this.$emit('requestfocus')

                    this.avis.basiasParcelle = avis.getBasiasParcelle(value)
                    this.avis.basiasProximiteParcelle = avis.getBasiasProximiteParcelle(value)
                    this.avis.basiasRaisonSociale = avis.getBasiasRaisonSocialeParcelle(value)
                    this.avis.basiasRayonParcelle = avis.getBasiasRayonParcelle(value)
                    this.avis.basiasNonGeorerencee = avis.getBasiasNonGeoreferencees(value)

                    this.avis.basolParcelle = avis.getBasolParcelle(value)
                    this.avis.basolProximiteParcelle = avis.getBasolProximiteParcelle(value)
                    this.avis.basolRayonParcelle = avis.getBasolRayonParcelle(value)
                    this.avis.basolNonGeorerencee = avis.getBasolNonGeoreferencees(value)

                    this.avis.installationClasseeParcelle = avis.getICSurParcelle(value)
                    this.avis.installationClasseeProximiteParcelle = avis.getICProximiteParcelle(value)
                    this.avis.installationClasseeRayonParcelle = avis.getICRayonParcelle(value)
                    this.avis.installationClasseeNonGeorerencee = avis.getICNonGeoreferencees(value)

                    this.avis.sisParcelle = avis.getSISSurParcelle(value)
                    this.avis.sisNonGeorerencee = avis.getSISNonGeoreferencees(value)

                    this.leaflet.data = value.entity.leaflet
                    this.leaflet.center = [parseFloat(value.entity.leaflet.center.y), parseFloat(value.entity.leaflet.center.x)]

                    this.avis.codeSismicite = value.entity.codeZoneSismicite
                    this.avis.potentielRadon = value.entity.classePotentielRadon

                    this.avis.ppr = value.entity.planPreventionRisquesDTOs

                    functions.scrollToElement('main', false)
                    this._paq.push(['trackEvent', 'Flow', 'Avis', 'Rendu'])
                })
                .catch(() => {
                    // console.log(e)
                    this.sendError('Votre requête n\'a pu aboutir dans un délais raisonnable, merci de réessayer ou de nous le signaler au moyen du formulaire de contact.')
                })
        },
        loadAvis (codeAvis) {
            // console.log('loadAvis')
            this.visibility.modifier = false
            this.$emit('loading')
            this.$emit('setflow', 0)
            fetch(this.env.apiPath + 'url?' + 'code=' + codeAvis)
                .then(stream => stream.json())
                .then(value => {
                    if (value.status === 422) {
                        // console.log('Wrong code')
                        this.sendError("Oups! Votre recherche n'a pas été trouvée :-(.")
                        this.sendError("Si vous l'avons perdue, veuillez bien vouloir nous en excuser.")
                        this.$emit('loaded')
                        return
                    }
                    // console.log(value.entity.url)
                    let array = value.entity.url.split('|&|')
                    // console.log(array)

                    this.tinyUrl.codeParcelle = array[0]
                    this.tinyUrl.codeInsee = array[1]
                    this.tinyUrl.nomAdresse = array[2]
                    this.tinyUrl.geolocAdresse = array[3]
                    this.tinyUrl.codeProprio = array[4]

                    this.getAvis()
                })
        },
        copyLink () {
            let copyInput = document.getElementById("copyInput");
            copyInput.select();
            document.execCommand("copy");
            alert("URL copiée : " + copyInput.value);
        }
    },
    computed: {
        conclusion: function () {
            let conclusionNumber = 0

            if (this.avis.basiasParcelle.numberOf === 0 &&
                this.avis.basolParcelle.numberOf === 0 &&
                this.avis.installationClasseeParcelle.numberOf === 0 &&
                this.avis.sisParcelle.numberOf === 0) conclusionNumber = 0

            if (this.avis.basiasParcelle.numberOf === 0 &&
                this.avis.basolParcelle.numberOf === 0 &&
                this.avis.installationClasseeParcelle.numberOf === 0 &&
                this.avis.sisParcelle.numberOf === 0 &&
                this.avis.basiasProximiteParcelle.numberOf > 0) conclusionNumber = 3

            if (this.avis.basiasParcelle.numberOf > 0 ||
                this.avis.basolParcelle.numberOf > 0 ||
                this.avis.installationClasseeParcelle.numberOf > 0 ||
                this.avis.sisParcelle.numberOf > 0) conclusionNumber = 1

            if (this.avis.basiasParcelle.numberOf > 0 &&
                this.avis.basolParcelle.numberOf === 0 &&
                this.avis.installationClasseeParcelle.numberOf === 0 &&
                this.avis.sisParcelle.numberOf === 0) conclusionNumber = 2

            return conclusionNumber
        },
        concordances: function () {
            return this.avis.installationClasseeParcelle.numberOf + this.avis.basolParcelle.numberOf + this.avis.basiasParcelle.numberOf + this.avis.sisParcelle.numberOf
        },
        concordances_risques: function () {
            return this.avis.potentielRadon >= 3 || this.avis.codeSismicite >= 3
        },
        _paq: function () {
            return window._paq
        }
    },
    mounted () {
        let codeAvis = this.$route.params.codeAvis
        // console.log('codeAvis : ' + codeAvis + ' && is undefined ? ' + (codeAvis === undefined))
        if (codeAvis !== undefined) this.loadAvis(codeAvis)
    }
}
</script>

<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Nunito+Sans&display=swap');

    #section4 {
        padding        : 30px 140px 0;
        text-align     : left;
        font-family    : 'Nunito Sans', sans-serif;
        font-size      : 16px;
        font-weight    : normal;
        font-style     : normal;
        font-stretch   : normal;
        line-height    : normal;
        letter-spacing : normal;
    }

    .container {
        max-width : unset;
    }

    .bouton {
        float            : left;
        border-bottom    : solid 3px #003B80;
        border-radius    : 2px;
        background-color : #0053B3;
        font-weight      : 900;
        color            : #FFFFFF;
        padding          : 9px 20px;
        text-decoration  : none;
        margin-right     : 20px;
        margin-bottom    : 20px;
    }

    .bouton:hover {
        background-color : #003B80;
    }

    .lien {
        padding         : 9px 0;
        background      : none;
        height          : 22px;
        color           : #0053B3;
        text-decoration : none;
    }

    .lien.big {
        font-weight : 900;
        z-index     : 10;
    }

    .lien:hover {
        background : none;
        color      : #003B80;
    }

    #summary_wrapper {
        clear      : both;
        float      : left;
        width      : calc(50% - 10px);
        text-align : left;
    }

    .leaflet_wrapper {
        float       : left;
        width       : calc(50% - 10px);
        margin-left : 20px;
        padding     : 0 !important;
        height      : 209px;
    }

    .tabWrapper {
        position                : absolute;
        top                     : -4em;
        left                    : -1px;
        border                  : 1px solid #CCCCCC;
        border-bottom           : none;
        border-top-left-radius  : 2px;
        border-top-right-radius : 2px;
        float                   : left;
        margin-top              : 20px;
    }

    .tabWrapper .tab {
        float            : left;
        padding          : 10px 20px;
        border-left      : 1px solid #CCCCCC;
        background-color : #F8F8F8;
        border-bottom    : 1px solid #CCCCCC;
        cursor           : pointer;
    }

    .tabWrapper .tab:first-child {
        border-left : none;
    }

    .tabWrapper .tab.selected {
        background-color : #0053B3;
        color            : #FFFFFF;
        border-bottom    : 1px solid #FFFFFF;
    }

    #concordances_wrapper {
        margin-top             : calc(20px + 4em);
        float                  : left;
        border-top-left-radius : unset;
        padding                : 30px 0 0 0 !important;
        width                  : 100%;
        text-align             : center;
    }

    #concordances_wrapper p {
        margin     : 5px;
        text-align : center;
    }

    #concordances_wrapper .numbers_wrapper {
        width            : 100%;
        background-color : #FAFAFA;
        height           : 145px;
        margin           : 30px 0;
    }

    #conclusion_wrapper, #resume_wrapper, #non_georef_wrapper {
        float      : left;
        margin-top : 20px;
        padding    : 30px 20px !important;
        width      : 100%;
        text-align : left;
    }

    .container.bordered {
        background-color : #FFFFFF;
        border           : 1px solid #CCCCCC;
        border-radius    : 2px;
        /*float            : left;*/
        padding          : 20px;
    }

    .site-list {
        list-style : none;
        padding    : 0;
        margin     : 0;
    }

    .site-list li {
        margin-bottom : 3px;
    }

    .icon-risque-wrapper {
        display : inline-block;
        width   : 25%;
    }

    .icon-risque {
        margin      : 10px;
        display     : inline-block;
        height      : 80px;
        padding-top : 20px;
        color       : #53657D;
    }

    .icon-risque-label {
        color : #2C3E50;
    }

    sup {
        font-size : 0.6em;
    }

    .renvoi {
        font-size   : 0.8em;
        margin      : 30px 10px 10px !important;
        text-align  : left !important;
        color       : rgb(153, 153, 153);
        line-height : 1.1em;
    }

    .infobulle {
        position         : absolute;
        width            : calc(25% - 10px);
        display          : none;
        background-color : #FFFFFF;
        border           : 1px solid #CCCCCC;
        border-radius    : 2px;
        text-align       : justify;
        z-index          : 1;
        /*position: sticky;*/
        padding          : 5px;
        margin           : 5px;
    }

    .icon-risque-wrapper:hover .infobulle {
        display : block;
    }

    .infobulle:hover {

        display : block;
    }

</style>
