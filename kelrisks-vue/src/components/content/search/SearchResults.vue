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
                <a @click="$emit('flow', 1)"
                   class="bouton success"
                   id="pdf"
                   target="_blank">
                    Créer un état des risques
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
                    <leaflet :max-zoom-center="leaflet.center"
                             :data="leaflet.data"/>
                </div>
            </div>

        </section>

        <section class="section section-grey v-flex">

            <span class="title">Risques Principaux</span>

            <risque :description="'L’immeuble est situé dans le périmètre d’un ' +  plan.alea.familleAlea.famillePPR.libelle + ' de type ' + plan.alea.familleAlea.libelle + ' - ' + plan.alea.libelle +
                                  (plan.dateApprobation ? ', approuvé le ' + formatDate(plan.dateApprobation) : ', prescrit le ' + formatDate(plan.datePrescription)) +'.<br/>' +
                                   plan.dateApprobation ? 'Un PPR approuvé est un PPR définitivement adopté.' :
                                   plan.dateApplicationAnticipee ? 'Un PPR anticipé est un PPR non encore approuvé mais dont les règles sont  déjà à appliquer, par anticipation.' :
                                   'Un PPR prescrit est un PPR en cours d\'élaboration sur la commune dont le périmètre et les règles sont en cours d\'élaboration.' + '<br/><br/>' +
                                  'Le plan de prévention des risques est un document réalisé par l’Etat qui interdit de construire dans les zones les plus exposées et encadre les constructions dans les autres zones exposées.<br/>' +
                                  '<a href=\'#recommendations_PPR\'>Lire les recommandations</a>'"
                    :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    :leaflet-data="[{ data : plan.assiettes,
                                      color : '#840505'}]"
                    :logo-u-r-l="'/images/pictogrammes_risque/'+ getLogoRisque(plan.alea.familleAlea.code) +'.svg'"
                    :title="plan.alea.familleAlea.libelle"
                    style="font-size: 20px; color: #2C3E50;"
                    v-bind:key="'plan_' + index"
                    v-for="(plan, index) in avis.ppr"/>

            <risque :description="'Un tremblement de terre ou séisme, est un ensemble de secousses et de déformations brusques de l\'écorce terrestre (surface de la Terre). Le zonage sismique détermine l\'importance de l\'exposition au risque sismique.<br/>'+
                                  '<a href=\'#recommendations_sismicite\'>Lire les recommandations</a>'"
                    :leaflet-data="typeof avis.summary.commune.communesLimitrophes.map ===  'function' ?
                                   [{ data : avis.summary.commune.codeZoneSismicite === '1' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#D8D8D8'},
                                    { data : avis.summary.commune.codeZoneSismicite === '2' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#FFD332'},
                                    { data : avis.summary.commune.codeZoneSismicite === '3' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#FF8000'},
                                    { data : avis.summary.commune.codeZoneSismicite === '4' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#E02B17'},
                                    { data : avis.summary.commune.codeZoneSismicite === '5' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#840505'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '1').map(x => x.multiPolygon),
                                      color : '#D8D8D8'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '2').map(x => x.multiPolygon),
                                      color : '#FFD332'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '3').map(x => x.multiPolygon),
                                      color : '#FF8000'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '4').map(x => x.multiPolygon),
                                      color : '#E02B17'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.codeZoneSismicite === '5').map(x => x.multiPolygon),
                                      color : '#840505'}] :
                                    undefined"
                    :legend-blocks="[
                        ['#D8D8D8', '1 - très faible'],
                        ['#FFD332', '2 - faible'],
                        ['#FF8000', '3 - modéré'],
                        ['#E02B17', '4 - moyen'],
                        ['#840505', '5 - fort']]"
                    :level="avis.summary.commune.codeZoneSismicite + ''"
                    :level-max="'5'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_seisme_bleu.svg'"
                    :title="'Sismicité'"
                    :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    v-if="hasSismiciteHaute || hasSismiciteMoyenne"/>

            <risque :description="'Le radon est un gaz radioactif naturel inodore, incolore et inerte. Ce gaz est présent partout dans les sols et il s’accumule dans les espaces clos, notamment dans les bâtiments.<br/>'+
                                  '<a href=\'#recommendations_radon\'>Lire les recommandations</a>'"
                    :leaflet-data="typeof avis.summary.commune.communesLimitrophes.map ===  'function' ?
                                   [{ data : avis.summary.commune.classePotentielRadon === '1' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#FFD332'},
                                    { data : avis.summary.commune.classePotentielRadon === '2' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#FF8000'},
                                    { data : avis.summary.commune.classePotentielRadon === '3' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#840505'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '1').map(x => x.multiPolygon),
                                      color : '#FFD332'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '2').map(x => x.multiPolygon),
                                      color : '#FF8000'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '3').map(x => x.multiPolygon),
                                      color : '#840505'}] :
                                    undefined"
                    :legend-blocks="[
                        ['#FFD332', '1 : zones à potentiel radon faible'],
                        ['#FF8000', '2 : zones à potentiel radon moyen'],
                        ['#840505', '3 : zones à potentiel radon significatif']]"
                    :level="avis.summary.commune.classePotentielRadon + ''"
                    :level-max="'3'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_rn_bleu.svg'"
                    :title="'Radon'"
                    :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    v-if="hasRadonHaut"/>

            <risque :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    :description="'<p>Les pollutions des sols peuvent présenter un risque sanitaire lors des changements d\'usage des sols (travaux, aménagements changement d\'affectation des terrains) si elles ne sont pas prises en compte dans le cadre du projet.</p>'"
                    :detail="(avis.installationClasseeParcelle.numberOf > 0 ? '- La parcelle a accueilli une activité industrielle ou agricole relevant de la réglementation des installations classées pour la protection de l\'environnement. Cette activité a pu provoquer des pollutions, notamment des sols des eaux souterraines ou des eaux superficielles.</br>Installation(s) concerné(e)  : <br/>' + getLibelleInstallationsNucleaires : '') +
                             (avis.sisParcelle.numberOf > 0 ? '- La parcelle est située en secteur d\'information sur les sols.</br>' : '') +
                             (false ? '- La parcelle est affectée d\'une servitude d\'utilité publique au titre des installations classées au titre du L 515-12 du code de l\'environnement.' : '') +
                             '<p><a href=\'#recommendations_pollution\'>Lire les recommandations</a></p>'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_basias_bleu.svg'"
                    :title="'Pollution des sols'"
                    v-if="hasPollutionPrincipale"/>

            <risque :description="'La parcelle est concernée par un plan d\'exposition au bruit car elle est exposée aux nuisances d\'un aéroport.'"
                    :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    :detail="(avis.zonePlanExpositionBruit === 'A' ? 'Le niveau d\'exposition au bruit de la parcelle est très fort (zone A en rouge). La zone A est principalement inconstructible.' : '') +
                             (avis.zonePlanExpositionBruit === 'B' ? 'Le niveau d\'exposition au bruit de la parcelle est fort (zone B en orange). La zone B est principalement inconstructible.' : '') +
                             (avis.zonePlanExpositionBruit === 'C' ? 'Le niveau d\'exposition au bruit de la parcelle est modéré (zone C en jaune). Certaines constructions sont autorisées sous conditions.' : '') +
                             (avis.zonePlanExpositionBruit === 'D' ? 'Le niveau d\'exposition au bruit de la parcelle est faible (zone D en verte). Les nouveaux logements sont autorisés à condition qu’ils fassent l’objet d’une isolation phonique.' : '')"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_bruit_bleu.svg'"
                    :leaflet-data="typeof avis.plansExpositionBruit.map ===  'function' ?
                                   [{ data : avis.plansExpositionBruit.filter(x => x.zone === 'D').map(x => x.multiPolygon),
                                      color : '#058E0C'},
                                    { data : avis.plansExpositionBruit.filter(x => x.zone === 'C').map(x => x.multiPolygon),
                                      color : '#FFD332'},
                                    { data : avis.plansExpositionBruit.filter(x => x.zone === 'B').map(x => x.multiPolygon),
                                      color : '#FF8000'},
                                    { data : avis.plansExpositionBruit.filter(x => x.zone === 'A').map(x => x.multiPolygon),
                                      color : '#840505'}] :
                                    undefined"
                    :legend-blocks="[
                        ['#840505', 'A - très fort'],
                        ['#FF8000', 'B - fort'],
                        ['#FFD332', 'C - modéré'],
                        ['#058E0C', 'D - faible']]"
                    :level="avis.zonePlanExpositionBruit"
                    :title="'Bruit'"
                    v-if="hasPEB"/>

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
                    <p>Certains risques peuvent nécessiter de réaliser des travaux obligatoires de mise en conformité de votre habitation. Pour le savoir, vous devez prendre connaissance du plan de
                       prévention, consultable sur le site internet de votre préfecture.</p>
                    <p>Si votre bien est concerné par une obligation de travaux, vous pouvez bénéficier d'une aide de l'Etat, dans le cadre du Fonds de prévention des risques naturels majeurs (FPRNM).
                       Pour plus de renseignements, contacter la direction départementale des territoires (DDT) de votre département.</p>
                    <p>Pour savoir ce qu'il faut faire en cas de survenance du risque, consulter le Dossier d'information communal sur les risques majeurs (DICRIM) auprès de votre commune.</p>
                </template>

                <template v-if="hasSismicite">
                    <h4 id="recommendations_sismicite">Sismicité</h4>
                    <template v-if="hasSismiciteHaute">
                        <p>Pour le bâti neuf et pour certains travaux lourds sur le bâti existant, en fonction de la zone de sismicité et du type de construction (habitation individuelle, habitations
                           collectives, établissement recevant du public) des dispositions spécifiques à mettre en oeuvre s'appliquent lors de la construction. Un guide interactif est proposé pour
                           identifier précisément les dispositions à prendre en compte selon votre localisation, type d'habitat et projet :<br/></p>
                        <a href="http://www.planseisme.fr/-Didacticiel-.html">Didacticiel de la règlementation parasismique</a>
                        <p>Pour connaitre les consignes à appliquer en cas de séisme , vous pouvez consulter le site :<br/></p>
                        <a href="http://www.planseisme.fr/Que-faire-en-cas-de-seisme.html">Que faire en cas de séisme ?</a>
                    </template>
                    <template v-if="hasSismiciteMoyenne">
                        <p>Pour certains bâtiments de taille importante ou sensibles, des dispositions spécifiques à mettre en oeuvre s'appliquent lors de la construction (arrêté du 22 octobre 2010).
                           Un guide interactif est proposé sur le site Plan Séisme pour identifier précisément les dispositions à prendre en compte selon votre localisation et projet. Il est
                           consultable à l'adresse suivante :<br/></p>
                        <a href="http://www.planseisme.fr/-Didacticiel-.html">Didacticiel de la règlementation parasismique</a>
                    </template>
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
                    <p><a href="http://www.georisques.gouv.fr/sites/default/files/2018-Fiche%20d_information_sur_le_risque_potentiel_radon_DHUP-DGS-DGPR_102018_v3.pdf">Information sur le risque
                                                                                                                                                                        potentiel radon</a>.</p>
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

        <section class="section v-flex">

            <span class="title">risques ne faisant pas l'objet d'une obligation d'information</span>

            <risque :description="'Le radon est un gaz radioactif naturel inodore, incolore et inerte. Ce gaz est présent partout dans les sols et il s’accumule dans les espaces clos, notamment dans les bâtiments.'"
                    :level="avis.potentielRadon + ''"
                    :level-max="'3'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_rn_bleu.svg'"
                    :title="'Radon'"
                    :leaflet-data="typeof avis.summary.commune.communesLimitrophes.map ===  'function' ?
                                   [{ data : avis.summary.commune.classePotentielRadon === '1' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#FFD332'},
                                    { data : avis.summary.commune.classePotentielRadon === '2' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#FF8000'},
                                    { data : avis.summary.commune.classePotentielRadon === '3' ? [avis.summary.commune.multiPolygon] : [],
                                      color : '#840505'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '1').map(x => x.multiPolygon),
                                      color : '#FFD332'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '2').map(x => x.multiPolygon),
                                      color : '#FF8000'},
                                    { data : avis.summary.commune.communesLimitrophes.filter(x => x.classePotentielRadon === '3').map(x => x.multiPolygon),
                                      color : '#840505'}] :
                                    undefined"
                    :legend-blocks="[
                        ['#FFD332', 'Zone 1 : zones à potentiel radon faible'],
                        ['#FF8000', 'Zone 2 : zones à potentiel radon faible mais sur lesquelles des facteurs géologiques particuliers peuvent faciliter le transfert du radon vers les bâtiments'],
                        ['#840505', 'Zone 3 : zones à potentiel radon significatif']]"
                    :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    v-if="hasRadonMoyen"/>

            <risque :description="'Les pollutions des sols peuvent présenter un risque sanitaire lors des changements d’usage des sols (travaux, aménagements changement d’affectation des terrains) si elles ne sont pas prises en compte dans le cadre du projet.'"
                    :detail="'<p>Dans un rayon de 500 m autour de votre parcelle, sont identifiés :</br>'+
                              (avis.installationClasseeRayonParcelle.numberOf > 0 ? '- '+ avis.installationClasseeRayonParcelle.numberOf +' sites référencés dans l\'inventaire des installations classées pour la protection de l\'environnement (ICPE)</br>' : '') +
                              (avis.basiasRayonParcelle.numberOf > 0 ? '- '+ avis.basiasRayonParcelle.numberOf +' sites potentiellement pollués, référencés dans l\'inventaire des sites ayant accueilli par le passé une activité qui a pu générer une pollution des sols (BASIAS).</br>' : '') +
                              (avis.basolRayonParcelle.numberOf > 0 ? '- '+ avis.basolRayonParcelle.numberOf +' sites pollués (BASOL - terrain pollué appelant une action des pouvoirs publics à titre curatif ou préventif, SIS - terrain placé en secteur d’information sur les sols, SUP - terrain pollué affecté d’une servitude d’utilité publique)</br></p>' : '</p>') +
                              (!hasPollutionPrincipale && numberOfParcelleMatches > 0 ? '<p>' + numberOfParcelleMatches + ' site(s) présente(nt) une proximité forte avec votre parcelle. Dans le cas où vous souhaiteriez en savoir davantage, il est recommandé de faire réaliser une étude historique et, le cas échéant, des analyses de sols par un bureau d’étude spécialisé dans le domaine des sols pollués.</p>' : '') +
                              (hasPollutionCentroidCommune ? '<p>Les données disponibles mentionnent enfin la présence d’anciennes activités qui ont localisées dans le centre de la commune par défaut. La présente analyse n\'en tient donc pas compte. Le détail de ces données est consultable ici.</p>' : '')"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_basias_bleu.svg'"
                    :title="'Pollution des sols'"
                    :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    :leaflet-data="[{ data : avis.installationClasseeRayonParcelle.liste.map(x => x.ewkt),
                                      color : '#8E0800'},
                                    { data : avis.basiasRayonParcelle.liste.map(x => x.ewkt),
                                      color : '#9E9E00'},
                                    { data : avis.basolRayonParcelle.liste.map(x => x.ewkt),
                                      color : '#925600'}]"
                    v-if="hasPollutionNonReglementaire"/>

            <risque :description="'Votre bien est situé dans un territoire exposé à un risque important d\'inondation (TRI) sur lequel l\'Etat et les collectivités territoriales ont engagé une démarche d\'identification et de gestion de ce risque pour anticiper et réduire l’impact d\'une éventuelle inondation. Pour plus d\'information, renseignez-vous auprès de la commune ou consultez le Plan de Gestion des Risques d\'Inondation (PGRI).'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_inondation_bleu.svg'"
                    :title="'Inondations'"
                    v-if="hasTRI && !hasPPRi"/>

            <risque :description="'Votre bien est situé sur une commune figurant dans un atlas des zones inondables qui modélisent les potentiels risques à partir des dernières inondations connues.'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_inondation_bleu.svg'"
                    :title="'Inondations'"
                    v-if="hasAZI && !hasTRI && !hasPPRi"/>

            <risque :description="'Votre bien est situé à moins de ' + (avis.hasCentraleNucleaire ? '20 km' :  '10 km') + ' d\'une installation nucléaire de base, installation dans laquelle une certaine quantité de substance ou de matière radioactives est présente (ex. réacteurs nucléaires de production d\'électricité (centrale nucléraire), installations de préparation, enrichissement, fabrication, traitement ou entreposage de combustibles nucléaires ; etc.)'"
                    :detail="'<p>Ces installations sont contrôlées par l\'Autorité de Sureté Nucléaire dont les rapports de contrôle sont consultables au lien suivant : <a href=\'https://www.asn.fr/Controler/Actualites-du-controle\'>https://www.asn.fr/Controler/Actualites-du-controle.</a></p>' +
                             '<p>Installation(s) concerné(e)  : <br/>' + getLibelleInstallationsNucleaires + '</p>'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_nucleaires_bleu.svg'"
                    :title="'Installations nucléaires de base'"
                    v-if="avis.nucleaires.installations.length > 0"/>

            <risque :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    :description="'Les sols argileux évoluent en fonction de leur teneur en eau. De fortes variations d\'eau (sécheresse ou d’apport massif d’eau) peuvent donc fragiliser progressivement les constructions (notamment les maisons individuelles aux fondations superficielles) suite à des gonflements et des tassements du sol, et entrainer des dégâts pouvant être importants. Le zonage \'argile\' identifie les zones exposées à ce phénomène de retrait-gonflement selon leur degré d’aléa.'"
                    :detail="(avis.niveauArgile === 3 ? 'Aléa fort : La probabilité de survenue d’un sinistre est élevée et l’intensité des phénomènes attendus est forte. Les constructions, notamment les maisons individuelles, doivent être réalisées en suivant des prescriptions constructives ad hoc. Pour plus de détails</br><a href=\'https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3\'>Sols argileux sécheresse et construction</a>' : '') +
                             (avis.niveauArgile === 2 ? 'Aléa moyen : La probabilité de survenue d’un sinistre est moyenne, l\'intensité attendue étant modérée.  Les constructions, notamment les maisons individuelles, doivent être réalisées en suivant des prescriptions constructives ad hoc. Pour plus de détails :</br><a href=\'https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3\'>Sols argileux sécheresse et construction</a>' : '') +
                             (avis.niveauArgile === 1 ? 'la survenance de sinistres est possible en cas de sécheresse importante, mais ces désordres ne toucheront qu’une faible proportion des bâtiments (en priorité ceux qui présentent des défauts de construction ou un contexte local défavorable, avec par exemple des arbres proches ou une hétérogénéité du sous-sol). Il est conseillé, notamment pour la construction d\'une maison individuelle, de réaliser une étude de sols pour déterminer si des prescriptions constructives spécifiques sont nécessaires. Pour plus de détails :</br><a href=\'https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3\'>Sols argileux sécheresse et construction</a>' : '') +
                             (avis.niveauArgile === 0 ? 'Aléa nul : aucune présence de sols argileux n\'a été identifiée selon les cartes géologiques actuelles. Toutefois il peut y avoir des poches ponctuelles de sols argileux.' : '') "
                    :leaflet-data="[{ data : avis.lentillesArgile.filter(x => x.niveauAlea === 1).map(x => x.multiPolygon),
                                      color : '#FFD332'},
                                    { data : avis.lentillesArgile.filter(x => x.niveauAlea === 2).map(x => x.multiPolygon),
                                      color : '#FF8000'},
                                    { data : avis.lentillesArgile.filter(x => x.niveauAlea === 3).map(x => x.multiPolygon),
                                      color : '#840505'}]"
                    :legend-blocks="[
                        ['#FFD332', 'Aléa faible'],
                        ['#FF8000', 'Aléa moyen'],
                        ['#840505', 'Aléa fort']]"
                    :level="avis.niveauArgile + ''"
                    :level-max="'3'"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_terre_bleu.svg'"
                    :title="'Argile'"
                    v-if="hasArgile"/>

            <risque :parcelle="leaflet.data.parcelles"
                    :max-zoom-center="leaflet.center"
                    :description="'Une canalisation de matières dangereuses (gaz naturel, produits pétroliers ou chimiques) est située dans un rayon de 500m autour de votre parcelle. La carte représente les implantations présentes autour de votre localisation.'"
                    :leaflet-data="[{ data : avis.canalisations,
                                      color : '#2A4999'}]"
                    :logo-u-r-l="'/images/pictogrammes_risque/ic_reseaux_canalisation_bleu.svg'"
                    :title="'Canalisations transport de matières dangereuses'"
                    v-if="avis.canalisations.length > 0"/>

            <div class="clearfix"/>
        </section>

        <section class="section v-flex">

            <span class="title">Cette parcelle n'est pas concernée par :</span>

            <risque :description="'<br/>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques naturels.'"
                    :title="'Naturels'"
                    style="width: calc(33% - 35px);"
                    v-if="!hasPPRN"/>

            <risque :description="'<br/>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques miniers.'"
                    :title="'Miniers'"
                    style="width: calc(33% - 35px);"
                    v-if="!hasPPRM"/>

            <risque :description="'<br/>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques technologiques.'"
                    :title="'Technologiques'"
                    style="width: calc(33% - 35px);"
                    v-if="!hasPPRT"/>

            <risque :description="'<p>Votre parcelle ne figure pas dans l’inventaire :</p>' +
                                      '<p>- des installations classées soumises à enregistrement ou à autorisation</br>' +
                                      '- des secteurs d’information sur les sols</br>' +
                                      '- des terrains pollués affectés d’une servitude d’utilité publique.</p>'"
                    :title="'Pollution des sols'"
                    v-if="!hasPollutionPrincipale"/>

            <risque :description="'La parcelle n\'est pas concernée par un plan d\'exposition au bruit.'"
                    :title="'Bruit'"
                    v-if="!hasPEB"/>

            <div class="clearfix"/>

            <div id="bottomButtonsWrapper">
                <a @click="$emit('flow', 1)"
                   class="bouton success"
                   target="_blank">
                    <!--                <font-awesome-icon icon="file-pdf"/>-->
                    Créer un état des risques
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
import Leaflet from "../leaflet/LeafletResults";
import Risque from "../Risque";
import moment from "moment"
import mixinAvisHas from "../../mixins/avisHas";

export default {
    name: 'SearchResults',
    mixins: [mixinAvisHas],
    components: {
        Risque,
        Leaflet,
        Errors
    },
    props: {
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
        getLogoRisque (codeAlea) {

            switch (codeAlea) {
                case '11' :
                    return 'ic_inondation_bleu'
                case '12' :
                    return 'ic_terre_bleu'
                case '21' :
                    return 'ic_industrie_bleu'
                default :
                    console.log(codeAlea)
                    return 'ic_basias_bleu'
            }
        }
    },
    computed: {
        _paq: function () {
            return window._paq
        },
        getLibelleInstallationsclassees () {
            let libelle = ''

            for (let installation in this.avis.installationClasseeParcelle.liste) {
                installation = this.avis.installationClasseeParcelle[installation]
                libelle += '- ' + installation.nom + ' (' + installation.commune + ')<br/>'
            }

            return libelle
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
            // this.$refs.resultsErrors.sendSuccess('Participez à améliorer Kelrisks en répondant à ce court questionnaire (durée 3min)<a target=\'_blank\' style=\'display:inline-block; margin: 0 10px; min-width: 0; float: none;\' class=\'bouton\' href=\'https://docs.google.com/forms/d/e/1FAIpQLSd3tB_gWGZsucCihp4VYDqv0Vxq61nqnpQJeMkI17nY39St_w/viewform?usp=sf_link\'>Répondre</a>')
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
        flex       : 0 0 100%;
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

    section.v-flex {
        display   : flex;
        flex-wrap : wrap;
    }

    section.v-flex > span {
        flex       : 0 0 100%;
        text-align : left;
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

    .container.bordered {
        background-color : #FFFFFF;
        border           : 1px solid #CCCCCC;
        border-radius    : 2px;
        /*float            : left;*/
        padding          : 20px;
    }

    sup {
        font-size : 0.6em;
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

    .infobulle:hover {

        display : block;
    }

</style>
