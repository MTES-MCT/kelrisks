<template>
    <div class="panel"
         id="cgu_wrapper">
        <div @click="close()"
             class="close">Fermer
            <font-awesome-icon icon="times"
                               size="lg"/>
        </div>
        <h2>Conditions générales d’utilisation</h2>
        <div id="cgu">
            <p class="section__subtitle">Présentation</p>

            <p>Kelrisks est un service numérique du Ministère de la Transition écologique et solidaire.</p>

            <p>Ce service est destiné à tout professionnel qui désire évaluer la pollution d'une parcelle dans le cadre de son activité, ainsi qu'à tout particulier qui souhaite exercer son
               droit à
               l'information.</p>

            <p>Le présent document a pour objet de régler les relations entre les différents intervenants sur le service. Il définit les conditions et modalités d’utilisation des services
               https://kelrisks.beta.gouv.fr/#/ et https://kelrisks.beta.gouv.fr/swagger-ui.html sur Internet.</p>

            <p>Le service est développé et opéré par la Fabrique numérique des ministères de la Transition écologique et solidaire et de la Cohésion des territoires, avec l’appui de l’incubateur
               de
               services
               numériques beta.gouv.fr de la direction interministérielle du numérique et du système d’information et de communication de l’État (DINSIC).</p>

            <p>L’utilisation du service est libre, facultative et gratuite.</p>

            <p>Toute utilisation du service est subordonnée à l’acceptation préalable et au respect intégral des présentes conditions générales d’utilisation (CGU) par l’utilisateur.
               Vocabulaire</p>

            <p>« Nous » se réfère à l’éditeur de Kelrisks.<br>
               « Vous » se réfère à un utilisateur de Kelrisks.<br>
               « Le service » se réfère indistinctement à l’application web et à l’interface de programmation applicative (API).</p>
            <p class="section__subtitle">Objet</p>
            <p>Le service est composé, d’une part, d’une application web qui vous permet de :</p>

            <ul>
                <li>- connaitre en quelques minutes le référencement d'une parcelle dans l'une des bases de données de l'Etat relatives à la pollution des sols.</li>
                <li>- Editer la recherche en PDF</li>
                <li>- Avoir des informations sur le voisinage direct (100m) de la parcelle.</li>
            </ul>

            <p>D’autre part, une interface de programmation applicative (API) qui vous permet de :</p>

            <ul>
                <li>- utiliser les informations publiques relatives à la pollution des sols dans un format ouvert et réutilisable ;</li>
            </ul>

            <p class="section__subtitle">Qualité des données publiées</p>

            <p>Nous publions sur Kelrisks des données publiques à titre informatif. Seules les publications légales font foi. Nos données sources et les recherches générées avec Kelrisks
               proviennent
               des bases
               de données suivantes :</p>

            <ul>
                <li>- Basias : http://www.georisques.gouv.fr/dossiers/inventaire-historique-des-sites-industriels-et-activites-de-service-basias#/</li>
                <li>- Basol :https://basol.developpement-durable.gouv.fr/</li>
                <li>- SIS : http://www.georisques.gouv.fr/dossiers/pollution-des-sols-sis-et-anciens-sites-industriels</li>
                <li>- Base d'adresse nationale : https://adresse.data.gouv.fr/</li>
                <li>- Cadastre national : https://cadastre.data.gouv.fr/</li>
            </ul>

            <p>Par conséquent, les informations publiées sur Kelrisks ne sauraient engager la responsabilité de leur éditeur ou d’un quelconque service de l’État, l’utilisateur y ayant recours
               en
               ayant
               conscience des potentielles erreurs ou omissions qu’elles peuvent comporter.</p>

            <p>Aussi, ces informations brutes et utilisées sans compétences juridique officielle n'ont pas de valeur juridique pour le propriétaire de la parcelle.</p>

            <p>Pour toute requête dont le résultat vous parait anormal, vous pouvez nous écrire par courriel à contact@kelrisks.beta.gouv.fr en nous précisant les paramètres de la recherche et
               en
               joignant le
               résultat généré sous forme de PDF.</p>

            <h3 style="text-align: center">Propriété intellectuelle</h3>

            <p class="section__subtitle">Code source</p>

            <p>Le code source de l’application web et de l’interface de programmation applicative (API) sont libres et peuvent donc être vérifiés et améliorés par chacun.</p>

            <p>Ils sont disponibles sur Github à l'adresse suivante :</p>

            <p><a href="https://github.com/MTES-MCT/kelrisks">Github</a></p>

            <p>Données administratives publiques relatives aux domaines et aux titres miniers
               Les informations publiques délivrées par le service le sont sous Licence Ouverte V 2.0. Vous êtes donc libres de réutiliser ces informations sous réserve de mentionner la
               paternité de
               l’information et la date de dernière mise à jour de l’information utilisée. Par exemple sur la forme « Ministère de la Transition écologique et solidaire – Données originales
               téléchargées sur
                <a href="https://kelrisks.beta.gouv.fr">kelrisks</a>, mise à jour du 01 septembre 2018 ».</p>

            <h3 style="text-align: center">Vie privée</h3>

            <p class="section__subtitle">Données à caractère personnel</p>

            <p>Kelrisks ne stocke aucune données personnelles sur ses serveurs (les adresses par exemple). Par conséquent le service est en conformité avec la législation en vigueur sur les
               données
               personnelles.</p>

            <p class="section__subtitle">Cookies</p>

            <p>Nous déposons des cookies de mesure d’audience (nombre de visites, pages consultées), respectant les conditions d’exemption du consentement de l’internaute définies par la
               recommandation «
               Cookies » de la Commission nationale informatique et libertés (CNIL). Cela signifie, notamment, que ces cookies ne servent qu’à la production de statistiques anonymes et ne
               permettent
               pas de
               suivre votre navigation sur d’autres sites.</p>

            <p class="section__subtitle">Evolution du service</p>

            <p>Nous pouvons faire évoluer Kelrisks sans information préalable ou préavis. Nous ajoutons régulièrement des données, peaufinons l’interface et modifions des formulations sur la
               base de
               vos
               retours et des évolutions réglementaires et législatives.</p>

            <p>Les évolutions sont documentées et disponibles dans la rubrique nouveautés et sur Github.</p>

            <p class="section__subtitle">Disponibilité du service</p>

            <p>Nous pouvons suspendre l’accès à Kelrisks sans information préalable ni préavis, notamment pour des raisons de maintenance. Nous mettons l’application à jour régulièrement.
               L’indisponibilité ne
               dépasse généralement pas une dizaine de secondes. Nous mettons Kelrisks à disposition sans garantie sur sa disponibilité. Même si nous faisons de notre mieux pour que le service
               soit
               toujours
               opérationnel, cela signifie que d’éventuelles indisponibilités n’ouvriront pas droit à compensation financière.</p>

            <p>Nous nous réservons également le droit de bloquer, sans information préalable ni compensation financière, les usages mettant en péril l’utilisation du logiciel par d’autres
               usagers.
               Cela nous
               permet d’anticiper d’éventuelles attaques par déni de service.</p>

            <p class="section__subtitle">Evolution des conditions d’utilisation</p>

            <p>Les termes des présentes conditions d’utilisation peuvent être amendés à tout moment, sans préavis, en fonction des modifications apportées au service, de l’évolution de la
               législation
               ou pour
               tout autre motif jugé nécessaire.</p>

            Nous rendons librement accessible l’historique de ces conditions.
        </div>
    </div>
</template>

<script>
import JQuery from 'jquery'

let $ = JQuery

export default {
    name: 'CGU',
    methods: {
        close () {
            $('#cgu_wrapper').hide()
        },
        open () {
            $('#cgu_wrapper').show()
        }
    }
}
</script>

<style scoped>
    #cgu_wrapper {
        background : var(--theme-background-white);
        box-shadow : #CCCCCC -3px 3px 5px;
        display    : none;
        height     : 80%;
        left       : 20%;
        padding    : 0;
        position   : fixed;
        top        : 10%;
        width      : 60%;
        z-index    : 999;
    }

    #cgu {
        height          : 87%;
        overflow        : auto;
        padding         : 0 20px;
        position        : relative;
        scroll-behavior : auto;
        text-align      : justify;
    }

    p {
        text-indent : unset;
    }

    ul li {
        list-style : none;
    }

    .close {
        cursor   : pointer;
        position : absolute;
        right    : 6px;
        top      : 15px;
    }
</style>
