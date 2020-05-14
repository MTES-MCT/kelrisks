<template>
    <div>
        <section class="section section-white"
                 style="margin-bottom: 40px">

            <errors ref="resultsErrors"/>

            <br/>

            <div id="searchButtonsWrapper">
                <a @click="() => {  $emit('flow', -1)
                            _paq.push(['trackEvent', 'Flow', 'Avis', 'Modifier'])}"
                   class="bouton"
                   v-show="visibility.modifier">
                    <font-awesome-icon icon="chevron-left"/>
                    Modifier
                </a>

                <a :href="env.basePath"
                   @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Nouvel'])"
                   class="bouton">
                    <font-awesome-icon icon="search"/>
                    Nouvelle recherche
                </a>
            </div>

            <div id="actionButtonsWrapper">
                <input :value="env.basePath + '#/' + avis.summary.codeUrl"
                       id="copyInput"
                       style="position: absolute; left: -1000px; top: -1000px;"/>

                <a @click="copyLink"
                   class="bouton "
                   id="copyLink">
                    <font-awesome-icon icon="copy"/>
                    Partager le résultat
                </a>
                <a :href="this.env.apiPath + 'avis/pdf?' +
                            'codeINSEE=' + (tinyUrl.codeInsee ? tinyUrl.codeInsee : form.codeInsee) + '&' +
                            'nomAdresse=' + (tinyUrl.nomAdresse ? tinyUrl.nomAdresse : form.nomAdresse) + '&' +
                            'geolocAdresse=' + (tinyUrl.geolocAdresse ? tinyUrl.geolocAdresse.replace(/\|/, '%7C') : form.geolocAdresse.replace(/\|/, '%7C')) + '&' +
                            'codeParcelle=' + (tinyUrl.codeParcelle ? tinyUrl.codeParcelle : form.codeParcelle) + '&' +
                            'nomProprietaire=' + (tinyUrl.codeProprio ? tinyUrl.codeProprio : form.codeProprio)"
                   @click="_paq.push(['trackEvent', 'Flow', 'Pdf'])"
                   class="bouton success"
                   id="pdf"
                   target="_blank">
                    <!--                <font-awesome-icon icon="file-pdf"/>-->
                    Créer un état des risques et pollutions
                    <font-awesome-icon class="end"
                                       icon="chevron-right"/>
                </a>
            </div>

            <div class="container bordered"
                 id="summary_leaflet_wrapper">
                <div id="summary">
                    <div style="margin-bottom: 20px"><span class="title">Parcelle(s) </span></div>
                    <span class="rightAlign">Adresse&nbsp;: </span><b><span v-if="avis.summary.adresse">{{avis.summary.adresse}}, <br/><span class="rightAlign"/>{{avis.summary.commune.codePostal}} {{avis.summary.commune.nomCommune}}</span><span v-else-if="avis.summary.commune">{{avis.summary.commune.codePostal}}, {{avis.summary.commune.nomCommune}}</span><span v-else><i>n/a</i></span></b><br/>
                    <span class="rightAlign">Code parcelle&nbsp;: </span><b><span v-if="avis.summary.codeParcelle && avis.summary.codeParcelle !== ''">{{avis.summary.codeParcelle}}</span><span v-else><i>n/a</i></span></b><br/>
                    <br>
                </div>
                <div id="leaflet">
                    <leaflet :center="leaflet.center"
                             :data="leaflet.data"/>
                </div>
            </div>

        </section>

        <section class="section section-grey">

            <span class="title">Risques Principaux</span>

            <div class="clearfix"/>

            <risque :center="leaflet.center"
                    :description="'L’immeuble est situé dans le périmètre d’un ' +  plan.alea.familleAlea.famillePPR.libelle + ' de type ' + plan.alea.familleAlea.libelle + ' - ' + plan.alea.libelle + ', approuvé le '+formatDate(plan.dateApprobation)+'.<br/><br/>' +
                                  'Le plan de prévention des risques est un document réalisé par l’État qui réglemente l’utilisation des sols en fonction des risques auxquels ils sont soumis.<br/>' +
                                  '<a href=\'#recommendations_PPR\'>Lire les recommandations</a>'"
                    :leaflet-data="plan.assiette"
                    :logo-u-r-l="'/images/pictogrammes_risque/'+ getLogoRisque(plan.alea.familleAlea.code) +'.svg'"
                    :title="plan.alea.familleAlea.libelle"
                    style="font-size: 20px; color: #2C3E50;"
                    v-bind:key="'plan_' + index"
                    v-for="(plan, index) in avis.ppr"/>

            <risque :description="'Le zonage sismique est une zone géographique dans laquelle la probabilité d’occurrence d’un séisme de caractéristiques données (magnitude, profondeur focale) peut être considérée homogène en tout point.<br/>'+
                                  '<a href=\'#recommendations_sismicite\'>Lire les recommandations</a>'"
                    :legend-blocks="[
                        ['#D8D8D8', '1 - très faible'],
                        ['#FFD332', '2 - faible'],
                        ['#FF8000', '3 - modéré'],
                        ['#E02B17', '4 - moyen'],
                        ['#840505', '5 - fort']]"
                    :level="avis.codeSismicite + ''"
                    :level-max="'5'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_seisme_bleu.svg'"
                    :title="'Sismisité'"
                    v-if="hasSismisiteHaute"/>

            <risque :description="'Le zonage sismique est une zone géographique dans laquelle la probabilité d’occurrence d’un séisme de caractéristiques données (magnitude, profondeur focale) peut être considérée homogène en tout point.<br/>'+
                                  '<a href=\'#recommendations_sismicite\'>Lire les recommandations</a>'"
                    :legend-blocks="[
                        ['#D8D8D8', '1 - très faible'],
                        ['#FFD332', '2 - faible'],
                        ['#FF8000', '3 - modéré'],
                        ['#E02B17', '4 - moyen'],
                        ['#840505', '5 - fort']]"
                    :level="avis.codeSismicite + ''"
                    :level-max="'5'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_seisme_bleu.svg'"
                    :title="'Sismisité'"
                    v-if="hasSismisiteMoyenne"/>

            <risque :description="'Le radon est un gaz radioactif naturel inodore, incolore et inerte. Ce gaz est présent partout dans les sols et il s’accumule dans les espaces clos, notamment dans les bâtiments.<br/>'+
                                  '<a href=\'#recommendations_radon\'>Lire les recommandations</a>'"
                    :level="avis.potentielRadon + ''"
                    :level-max="'3'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_rn_bleu.svg'"
                    :title="'Radon'"
                    v-if="hasRadonHaut"/>

            <risque :description="'<p>Les pollutions des sols peuvent présenter un risque sanitaire lors des changements d\'usage des sols (travaux, aménagements changement d\'affectation des terrains) si elles ne sont pas prises en compte dans le cadre du projet.</p>'"
                    :detail="(avis.installationClasseeParcelle.numberOf > 0 ? '- La parcelle a accueilli une activité industrielle ou agricole relevant de la réglementation des installations classées pour la protection de l\'environnement. Cette activité a pu provoquer des pollutions, notamment des sols des eaux souterraines ou des eaux superficielles.</br>' : '') +
                             (avis.sisParcelle.numberOf > 0 ? '- La parcelle est située en secteur d\'information sur les sols.</br>' : '') +
                             (false ? '- La parcelle est affectée d\'une servitude d\'utilité publique au titre des installations classées au titre du L 515-12 du code de l\'environnement.' : '') +
                             '<p><a href=\'#recommendations_pollution\'>Lire les recommandations</a></p>'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_basias_bleu.svg'"
                    :title="'Pollution des sols'"
                    v-if="hasPollutionPrincipale"/>

            <risque :description="'La parcelle est concernée par un plan d\'exposition au bruit car elle est exposée aux nuisances d\'un aéroport.'"
                    :detail="(true ? '[A] - Le niveau d\'exposition au bruit de la parcelle est très fort (zone A en rouge).' : '')+
                             (false ? '[B] - Le niveau d\'exposition au bruit de la parcelle est fort (zone orange).' : '')+
                             (false ? '[C] - Le niveau d\'exposition au bruit de la parcelle est modéré (zone jaune).' : '')+
                             (false ? '[D] - Le niveau d\'exposition au bruit de la parcelle est faible (zone verte).' : '')"
                    :level="'A'"
                    :logo-u-r-l="'TODO'"
                    :title="'Bruit'"
                    v-if="true"/>

            <div class="clearfix"/>
        </section>

        <section class="section">

            <span class="title">Recommandations</span>

            <div class="recommandations_wrapper">

                <p>Pourquoi l'Etat des risques est important ?</p>
                <p>A chaque vente ou location d'un bien, le propriétaire est tenu d'informer l’acquéreur ou le locataire de son bien immobilier (bâti et non bâti) de l'état des risques et pollutions
                   auxquelles le bien immobilier est exposé. Cette obligation d'information est prévue par la loi du 30 juillet 2003. L'état des risques et pollutions doit dater de moins de six mois
                   lors de la signature de l'acte de vente ou du bail.</p><br/>
                <p>L'État des risques et pollution permet de faire un bilan des principaux risques pouvant affecter ce bien afin de bien informer les parties prenantes et de mettre en œuvre les
                   mesures de protection éventuelles.</p>

                <template v-if="hasPPR">
                    <h4 id="recommendations_PPR">Plans de Prévention des Risques</h4>
                    <p>Certains risques peuvent nécessiter de réaliser des travaux de mise en conformité de votre habitation. Pour le savoir, vous devez prendre connaissance du plan de prévention
                       associé à des risques accessible sur le site internet de votre préfecture.</p>
                </template>

                <template v-if="hasSismisite">
                    <h4 id="recommendations_sismicite">Sismicité</h4>
                    <!--                    <template v-if="this.hasSismisiteHaute"></template>-->
                    <!--                    <template v-if="this.hasSismisiteMoyenne"></template>-->
                    <p>Pour le bâti neuf, en fonction de la zone de sismicité (zone 2 "sismicité faible" à zone 5 "sismicité forte") et du type de construction (habitation individuelle, habitations
                       collectives, ERP, ...) des dispositions spécifiques s'appliquent selon la réglementation (arrêté du 22 octobre 2010).</p><br/>
                    <p>Un didactitiel est proposé sur le site du Plan Séisme pour connaître les dispositions à prendre en compte. Il est consultable à l'adresse suivante :</p>
                    <p>http://www.planseisme.fr/-Didacticiel-.html</p><br/>
                    <p>Pour le bâti existant ces dispositions ne s'appliquent que dans le cas de travaux lourds entrainant une augmentation de la surface habitable (pour plus de précisions :</p>
                    <p>http://www.planseisme.fr/Regles-parasismiques-applicables-aux-batiments-a-risque.html#existant).</p>
                </template>

                <template v-if="hasRadonHaut">
                    <h4 id="recommendations_radon">Radon</h4>
                    <p>Le bien est situé dans une zone à potentiel radon significatif. Il donc est fortement recommandé de procéder au mesurage du radon dans le bien afin de s'assurer que sa
                       concentration est inférieure au niveau de référence fixé à 300 Bq/m3, et idéalement la plus basse raisonnablement possible. Il est conseillé de faire appel à des
                       professionnels du bâtiment pour réaliser un diagnostic de la situation et vous aider à choisir les solutions les plus adaptées selon le type de logement et la mesure. Ces
                       solutions peuvent être mises en œuvre progressivement en fonction des difficultés de réalisation ou de leur coût. À l’issue des travaux, vous devrez réaliser de nouvelles
                       mesures de radon pour vérifier leur efficacité.</p>
                    <p>Une fiche d'informations sur le radon, le risque associé, son mesurage, les solutions techniques et les recommandations à suivre en fonction des résultats du mesurage est
                       disponible : </p>
                    <p>http://www.georisques.gouv.fr/sites/default/files/2018-Fiche%20d_information_sur_le_risque_potentiel_radon_DHUP-DGS-DGPR_102018_v3.pdf</p>
                </template>

                <template v-if="hasPollutionPrincipale">
                    <h4 id="recommendations_pollution">Pollution des sols</h4>
                    <p>En cas de vente ou de location, le propriétaire est tenu de communiquer les informations relatives aux pollutions des sols, à l'acquéreur ou au locataire. (en aide contextuelle
                       : article L. 514-20 du Code de l’Environnement et L 125-7 du Code de l’Environnement).</p>
                    <p>En cas de changement d'usage du terrain (travaux, constructions, changement d'affectation du bien), le maître d'ouvrage doit faire appel à un bureau d'étude qui devra attester
                       de la mise en oeuvre de mesures de gestion de la pollution des sols. Si elle est exigée lors d'un dépôt de permis de contruire ou d'aménager (en aide contextuelle article
                       L.556-1 du Code de l'Environnement), l'attestation devra être délivrée par une bureau d'étude certifiée. Pour vous accompagner dans vos démarches, une liste de bureaux d’études
                       certifiés dans le domaine des sols pollués est consultable à l'aide de ce lien :</p>
                    <p><a @click="_paq.push(['trackEvent', 'Flow', 'Avis', 'Bureau_Etude'])"
                          href='https://www.lne.fr/recherche-certificats/search/222'
                          style="float: none; text-align: center">Accéder à la liste des bureaux d’études certifiés</a></p>
                </template>
            </div>

            <div class="clearfix"/>
        </section>

        <section class="section">

            <div><span class="title">Autres risques ne faisant pas l'objet d'une obligation d'information</span></div>

            <risque :description="'Le radon est un gaz radioactif naturel inodore, incolore et inerte. Ce gaz est présent partout dans les sols et il s’accumule dans les espaces clos, notamment dans les bâtiments.'"
                    :level="avis.potentielRadon + ''"
                    :level-max="'3'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_rn_bleu.svg'"
                    :title="'Radon'"
                    v-if="hasRadonMoyen"/>

            <risque :description="'Les pollutions des sols peuvent présenter un risque sanitaire lors des changements d’usage des sols (travaux, aménagements changement d’affectation des terrains) si elles ne sont pas prises en compte dans le cadre du projet.'"
                    :detail="'<p>Dans un rayon de 500 m autour de votre parcelle, sont identifiés :</br>'+
                              (avis.installationClasseeRayonParcelle.numberOf > 0 ? '- '+ avis.installationClasseeRayonParcelle.numberOf +' sites référencés dans l\'inventaire des installations classées pour la protection de l\'environnement (ICPE)</br>' : '') +
                              (avis.basiasRayonParcelle.numberOf > 0 ? '- '+ avis.basiasRayonParcelle.numberOf +' sites potentiellement pollués, référencés dans l\'inventaire des sites ayant accueilli par le passé une activité qui a pu générer une pollution des sols (BASIAS).</br>' : '') +
                              (avis.basolRayonParcelle.numberOf > 0 ? '- '+ avis.basolRayonParcelle.numberOf +' sites pollués (BASOL - terrain pollué appelant une action des pouvoirs publics à titre curatif ou préventif, SIS - terrain placé en secteur d’information sur les sols, SUP - terrain pollué affecté d’une servitude d’utilité publique)</br></p>' : '</p>') +
                              (!hasPollutionPrincipale ? '<p>Parmi ces sites, ' + numberOfParcelleMatches + ' présentent une proximité forte avec votre parcelle. Dans le cas où vous souhaiteriez en savoir davantage, il est recommandé de faire réaliser une étude historique et, le cas échéant, des analyses de sols par un bureau d’étude spécialisé dans le domaine des sols pollués.</p>' : '') +
                              (hasPollutionCentroidCommune ? '<p>Les données disponibles mentionnent enfin la présence d’anciennes activités qui ont localisées dans le centre de la commune par défaut. La présente analyse n\'en tient donc pas compte. Le détail de ces données est consultable ici.</p>' : '')"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_basias_bleu.svg'"
                    :title="'Pollution des sols'"
                    :leaflet-data="[{ data : avis.installationClasseeRayonParcelle.liste.map(x => x.ewkt),
                                      color : '#AA0800'},
                                    { data : avis.basiasRayonParcelle.liste.map(x => x.ewkt),
                                      color : '#D9D900'},
                                    { data : avis.basolRayonParcelle.liste.map(x => x.ewkt),
                                      color : '#BF7000'}]"
                    v-if="true"/>

            <risque :description="'Votre bien est concerné par le risque inondation puisqu’il est situé en territoire à risque important d’inondation (TRI). Il s’agit d’un territoire exposé à un risque d’inondation sur lequel l\'État et les EPCI (établissement publics de coopération intercommunale) qui disposent de la compétence GEMAPI (gestion des milieux aquatiques et prévention des inondations) ont engagé une démarche d’identification et de gestion de ce risque pour anticiper et réduire l’impact d’une inondation.'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_inondation_bleu.svg'"
                    :title="'Inondations'"
                    v-if="hasTRI && !hasPPRi"/>

            <risque :description="'Votre bien est situé en dans un périmètre inondation figurant dans un atlas des zones inondables qui modélisent les potentiels risques à partir des dernières inondations connues.'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_inondation_bleu.svg'"
                    :title="'Inondations'"
                    v-if="hasAZI && !hasTRI && !hasPPRi"/>

            <risque :description="'Votre bien est situé à moins de [10 km] [20 km] d\'une installation nucléaire de base, installation dans laquelle une certaine quantité de substance ou de matière radioactives est présente (ex. réacteurs nucléaires de production d\'électricité (centrale nucléraire), installations de préparation, enrichissement, fabrication, traitement ou entreposage de combustibles nucléaires ; etc.)'"
                    :detail="'<p>Ces installations sont contrôlés par l\'Autorité de Sureté Nucléaire dont les rapports de contrôle sont consultables au lien suivant : <a href=\'https://www.asn.fr/Controler/Actualites-du-controle\'>https://www.asn.fr/Controler/Actualites-du-controle.</a></p>' +
                             '<p>Installation(s) concerné(e)  : <br/>' + getLibelleInstallationsNucleaires + '</p>'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_nucleaires_bleu.svg'"
                    :title="'Installations nucléaires de base'"
                    v-if="avis.nucleaires.installations.length > 0"/>

            <risque :center="leaflet.center"
                    :description="'Les sols argileux évoluent en fonction de leur teneur en eau. De fortes variations d\'eau (sécheresse ou d’apport massif d’eau) peuvent donc fragiliser progressivement les constructions (notamment les maisons individuelles aux fondations superficielles) suite à des gonflements et des tassements du sol. Le zonage \'argile\' identifie les zones exposées à ce phénomène de retrait-gonflement selon leur degré d’aléa afin de prévenir les sinistres.'"
                    :detail="(avis.lentillesArgile.niveauAlea === 3 ? 'Alea fort : La probabilité de survenue d’un sinistre est élevée et l’intensité des phénomènes attendus est forte. Veuillez consulter les recommandations au lien suivant pour prévenir les risques : https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3' : '')+
                             (avis.lentillesArgile.niveauAlea === 2 ? 'Alea moyen : La probabilité de survenue d’un sinistre est moyenne, l\'intensité attendue étant modérée' : '')+
                             (avis.lentillesArgile.niveauAlea === 1 ? 'Alea faible : La survenance de sinistres est possible en cas de sécheresse importante, mais ces désordres ne toucheront qu’une faible proportion des bâtiments (en priorité ceux qui présentent des défauts de construction ou un contexte local défavorable, avec par exemple des arbres proches ou une hétérogénéité du sous-sol)' : '')+
                             (avis.lentillesArgile.niveauAlea === 0 ? 'Alea nul : Aucune présence de sols argileux n\'a été identifiée selon les cartes géologiques actuelles.' : '')"
                    :leaflet-data="avis.lentillesArgile.multiPolygon"
                    :level="avis.lentillesArgile.niveauAlea + ''"
                    :level-max="'3'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_terre_bleu.svg'"
                    :title="'Argile'"
                    v-if="hasArgile"/>

            <risque :center="leaflet.center"
                    :description="'Une canalisation de matières dangereuses (gaz naturel, produits pétroliers ou chimiques) est située dans un rayon de 500m autour de votre parcelle. La carte représente les implantations présentes autour de votre localisation.'"
                    :leaflet-data="avis.canalisations"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_reseaux_canalisation_bleu.svg'"
                    :title="'Canalisations transport de matières dangereuses'"
                    v-if="avis.canalisations.length > 0"/>

            <div class="clearfix"/>
        </section>

        <section class="section">

            <span class="title">Cette parcelle n'est pas concernée par :</span>

            <div class="clearfix"/>

            <risque :description="'<br/>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques naturels.'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_seisme_bleu.svg'"
                    :title="'Naturels'"
                    style="width: calc(33% - 35px);"
                    v-if="!hasPPRN"/>

            <risque :description="'<br/>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques miniers.'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_cavite_bleu.svg'"
                    :title="'Miniers'"
                    style="width: calc(33% - 35px);"
                    v-if="!hasPPRM"/>

            <risque :description="'<br/>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques technologiques.'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_industrie_bleu.svg'"
                    :title="'Technologiques'"
                    style="width: calc(33% - 35px);"
                    v-if="!hasPPRT"/>

            <risque :description="'<p>Votre parcelle ne figure pas dans l’inventaire :</p>' +
                                      '<p>- des installations classées soumises à enregistrement ou à autorisation</br>' +
                                      '- des secteurs d’information sur les sols</br>' +
                                      '- des terrains pollués affectés d’une servitude d’utilité publique.</p>'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_basias_bleu.svg'"
                    :title="'Pollution des sols'"
                    v-if="!hasPollutionPrincipale"/>

            <risque :center="leaflet.center"
                    :description="'La parcelle n\'est pas concernée par un plan d\'exposition au bruit.'"
                    :leaflet-data="''"
                    :level="'A'"
                    :logo-u-r-l="'TODO'"
                    :title="'Bruit'"
                    v-if="true"/>

            <div class="clearfix"/>

            <div id="bottomButtonsWrapper">
                <a :href="this.env.apiPath + 'avis/pdf?' +
                            'codeINSEE=' + (tinyUrl.codeInsee ? tinyUrl.codeInsee : form.codeInsee) + '&' +
                            'nomAdresse=' + (tinyUrl.nomAdresse ? tinyUrl.nomAdresse : form.nomAdresse) + '&' +
                            'geolocAdresse=' + (tinyUrl.geolocAdresse ? tinyUrl.geolocAdresse.replace(/\|/, '%7C') : form.geolocAdresse.replace(/\|/, '%7C')) + '&' +
                            'codeParcelle=' + (tinyUrl.codeParcelle ? tinyUrl.codeParcelle : form.codeParcelle) + '&' +
                            'nomProprietaire=' + (tinyUrl.codeProprio ? tinyUrl.codeProprio : form.codeProprio)"
                   @click="_paq.push(['trackEvent', 'Flow', 'Pdf'])"
                   class="bouton success"
                   target="_blank">
                    <!--                <font-awesome-icon icon="file-pdf"/>-->
                    Créer un état des risques et pollutions
                    <font-awesome-icon class="end"
                                       icon="chevron-right"/>
                </a><br/>
                <span>Certains risques nécessitent de faire des travaux. Il est nécessaire de compléter ces informations pour finaliser l'état des risques et pollutions</span>
            </div>
        </section>
    </div>
</template>

<script>
import Errors from '../../../components/content/base/Errors'
import Leaflet from "../LeafletResults";
import Risque from "../Risque";
import moment from "moment"

export default {
    name: 'SearchResults',
    components: {
        Risque,
        Leaflet,
        Errors
    },
    props: {
        form: {
            type: Object,
            default: () => {
            }
        },
        avis: {
            type: Object,
            default: () => {
            }
        },
        leaflet: {
            type: Object,
            default: () => {
            }
        },
        tinyUrl: {
            type: Object,
            default: () => {
            }
        }
    },
    data: () => ({
        loading: false,
        rendered: false,
        mounted: false,
        visibility: {
            details: false,
            modifier: true
        },
        env: {
            basePath: process.env.VUE_APP_PATH,
            apiPath: process.env.VUE_APP_API_PATH
        },
        tab: {
            concordances: {
                pollution: {
                    vu: false
                },
                index: 'POLLUTION'
            }
        }
    }),
    methods: {
        formatDate (date) {
            return moment(date).format('DD/MM/YYYY')
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
                        this.$refs.searchErrors.sendError("Oups! Votre recherche n'a pas été trouvée :-(.")
                        this.$refs.searchErrors.sendError("Si vous l'avons perdue, veuillez bien vouloir nous en excuser.")
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
            this._paq.push(['trackEvent', 'Flow', 'Copy Link']);
            let copyInput = document.getElementById("copyInput");
            copyInput.select();
            document.execCommand("copy");
            alert("URL copiée : " + copyInput.value);
        },
        hasTypePPR (type) {
            for (let plan in this.avis.ppr) {
                plan = this.avis.ppr[plan]
                if (plan.alea.familleAlea.famillePPR.code === type) return true
            }
            return false
        },
        getLogoRisque (codeAlea) {

            switch (codeAlea) {
                case '12' :
                    return 'ic_terre_bleu'
                default :
                    console.log(codeAlea)
                    return 'ic_basias_bleu'
            }
        }
    },
    computed: {
        hasPollutionPrincipale: function () {
            return this.avis.installationClasseeParcelle.numberOf > 0 ||
                this.avis.sisParcelle.numberOf > 0
        },
        hasPollutionNonReglementaire: function () {
            return this.avis.installationClasseeParcelle.numberOf > 0 ||
                this.avis.sisParcelle.numberOf > 0
        },
        numberOfParcelleMatches: function () {
            return this.avis.installationClasseeParcelle.numberOf +
                this.avis.basolParcelle.numberOf +
                this.avis.basiasParcelle.numberOf +
                this.avis.sisParcelle.numberOf
        },
        hasPollutionCentroidCommune: function () {
            return this.avis.basiasNonGeorerencee.numberOf > 0 ||
                this.avis.basolNonGeorerencee.numberOf > 0 ||
                this.avis.installationClasseeNonGeorerencee.numberOf > 0 ||
                this.avis.sisNonGeorerencee.numberOf > 0
        },
        concordances: function () {
            var numberOfInstallationClasseeParcelle = isNaN(this.avis.installationClasseeParcelle.numberOf) ? 0 : this.avis.installationClasseeParcelle.numberOf
            var numberOfBasolParcelle = isNaN(this.avis.basolParcelle.numberOf) ? 0 : this.avis.basolParcelle.numberOf
            var numberOfBasiasParcelle = isNaN(this.avis.basiasParcelle.numberOf) ? 0 : this.avis.basiasParcelle.numberOf
            var numberOfSisParcelle = isNaN(this.avis.sisParcelle.numberOf) ? 0 : this.avis.sisParcelle.numberOf

            return numberOfInstallationClasseeParcelle + numberOfBasolParcelle + numberOfBasiasParcelle + numberOfSisParcelle
        },
        concordances_risques: function () {
            return this.avis.potentielRadon >= 3 || this.avis.codeSismicite >= 3
        },
        _paq: function () {
            return window._paq
        },
        hasRadonHaut: function () {
            return this.avis.potentielRadon === 3
        },
        hasRadonMoyen: function () {
            return this.avis.potentielRadon === 2
        },
        hasArgile: function () {
            return this.avis.lentillesArgile !== undefined && this.avis.lentillesArgile !== null
        },
        hasSismisiteHaute: function () {
            return this.avis.codeSismicite >= 3
        },
        hasSismisiteMoyenne: function () {
            return this.avis.codeSismicite === 2
        },
        hasSismisite: function () {
            return this.hasSismisiteMoyenne || this.hasSismisiteHaute
        },
        hasPPR: function () {
            return this.avis.ppr.length > 0
        },
        hasPPRN: function () {
            if (!this.hasPPR) return false
            return this.hasTypePPR('PPRN')
        },
        hasPPRM: function () {
            if (!this.hasPPR) return false
            return this.hasTypePPR('PPRM')
        },
        hasPPRT: function () {
            if (!this.hasPPR) return false
            return this.hasTypePPR('PPRT')
        },
        hasPPRi: function () {
            for (let plan in this.avis.ppr) {
                plan = this.avis.ppr[plan]
                if (plan.alea.familleAlea.code === '11') return true // ('11', 'Inondation');
            }
            return false
        },
        hasTRI: function () {
            if (this.avis.TRIs === null) return false
            return this.avis.TRIs.length > 0
        },
        hasAZI: function () {
            if (this.avis.AZIs === null) return false
            return this.avis.AZIs.length > 0
        },
        getLibelleInstallationsNucleaires () {
            let libelle = ''

            for (let installation in this.avis.nucleaires.installations) {
                installation = this.avis.nucleaires.installations[installation]
                libelle += '- ' + installation.nomInstallation + ' (' + installation.libCommune + ')<br/>'
            }

            return libelle
        }
    },
    mounted () {
        this.mounted = true
        let codeAvis = this.$route.params.codeAvis
        if (codeAvis !== undefined) this.loadAvis(codeAvis)

        this.$nextTick(() => {
            this.$refs.resultsErrors.sendSuccess('Participez à améliorer Kelrisks en répondant à ce court questionnaire (durée 3min)<a target=\'_blank\' style=\'display:inline-block; margin: 0 10px; min-width: 0; float: none;\' class=\'bouton\' href=\'https://docs.google.com/forms/d/e/1FAIpQLSd3tB_gWGZsucCihp4VYDqv0Vxq61nqnpQJeMkI17nY39St_w/viewform?usp=sf_link\'>Répondre</a>')
        })
    }
}
</script>

<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Nunito+Sans&display=swap');

    #searchButtonsWrapper {
        float : left;
    }

    #searchButtonsWrapper a,
    #actionButtonsWrapper a,
    #bottomButtonsWrapper a {
        display : inline-block;
        float   : none;
    }

    #bottomButtonsWrapper {
        margin-top : 25px;
        text-align : center;
    }

    #actionButtonsWrapper {
        float : right;
    }

    @media (min-width : 630px) {
        #searchButtonsWrapper a:last-of-type,
        #actionButtonsWrapper a:last-of-type {
            margin-right : 0;
        }
    }

    @media (max-width : 1350px) {

        #searchButtonsWrapper {
            text-align : center;
            width      : 100%;
        }

        #actionButtonsWrapper {
            text-align : center;
            width      : 100%;
        }
    }

    @media (max-width : 630px) {
        #searchButtonsWrapper a,
        #actionButtonsWrapper a {
            margin-left  : 10px;
            margin-right : 10px;
        }
    }

    .container {
        max-width : unset;
    }

    #summary_leaflet_wrapper {
        display : flex;
        padding : 0;
        width   : 100%;
    }

    #summary {
        float      : left;
        padding    : 20px;
        text-align : left;
        width      : calc(50%);
    }

    #summary span {
        line-height : 25px;
    }

    #summary span.rightAlign {
        display       : inline-block;
        padding-right : 5px;
        text-align    : right;
        width         : 150px;
    }

    #leaflet {
        float   : left;
        height  : 400px;
        padding : 0 !important;
        width   : calc(50%);
    }

    @media (max-width : 1000px) {

        #summary_leaflet_wrapper {
            display : block;
        }

        #summary {
            clear : both;
            float : left;
            width : 100%;
        }

        #leaflet {
            clear       : both;
            float       : left;
            height      : 210px;
            margin-left : 0;
            margin-top  : 20px;
            width       : 100%;
        }
    }

    .recommandations_wrapper {
        margin : auto;
        width  : 60%;
    }

    .recommandations_wrapper h4 {
        margin-bottom : 0;
        margin-top    : 20px;
    }

    .recommandations_wrapper p {
        margin-bottom : 0;
        margin-top    : 0;
    }

    .tabWrapper {
        border-bottom           : none;
        border-top-left-radius  : 2px;
        border-top-right-radius : 2px;
        /*border                  : 1px solid #CCCCCC;*/
        float                   : left;
        left                    : -1px;
        margin-top              : 19px;
        position                : absolute;
        top                     : -4.1em;
    }

    .tabWrapper .tab {
        background-color : #F8F8F8;
        border-bottom    : 1px solid #FFFFFF;
        border-left      : 1px solid #CCCCCC;
        border-top       : 1px solid #CCCCCC;
        cursor           : pointer;
        float            : left;
        padding          : 10px 20px 9px;
    }

    .tabWrapper .tab:first-child {
        border-top-left-radius : 2px;
    }

    .tabWrapper .tab:last-child {
        border-right            : 1px solid #CCCCCC;
        border-top-right-radius : 2px;
    }

    .tabWrapper .tab.selected {
        background-color : #0053B3;
        border-bottom    : 1px solid #003B80;
        border-left      : 1px solid #003B80;
        border-top       : 1px solid #003B80;
        color            : #FFFFFF;
        padding          : 10px 20px;
    }

    .tabWrapper .tab:last-child.selected {
        border-right : 1px solid #003B80;
    }

    #concordances_wrapper {
        border-top              : 3px solid #003B80;
        border-top-left-radius  : unset;
        border-top-right-radius : 2px;
        float                   : left;
        margin-top              : calc(20px + 4em);
        padding                 : 30px 0 0 0 !important;
        text-align              : center;
        width                   : 100%;
    }

    #concordances_wrapper p {
        margin     : 5px;
        text-align : center;
    }

    #concordances_wrapper .numbers_wrapper {
        width            : 100%;
        background-color : #FAFAFA;
        padding-bottom   : 10px;
        margin           : 30px 0;
    }

    @media (max-width : 500px) {
        #concordances_wrapper .numbers_wrapper {
            display : none;
        }
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
