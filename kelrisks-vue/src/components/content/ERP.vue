<template>
    <div id="erp_wrapper">
        <div id="erp_text">
            <h1>État des Risques Réglementés pour l'Information des Acquéreurs et des Locataires</h1>
            <p>La loi du 30 juillet 2003 a institué une
                <b>obligation d'information des acquéreurs et locataires</b> (IAL) d'un bien immobilier sur certains
                <b>risques majeurs auxquels est exposé ce bien</b>.
               Les vendeurs et bailleurs de biens immobiliers, situés dans des zones réglementées par un ou des plans de prévention des risques
               (PPR), une zone de sismicité faible à forte, dans un secteur d’information sur les sols (pollution des sols), dans une zone à potentiel radon de niveau 3 (élevé), dans une zone d'un
               plan d'exposition au bruit d'un aéroport ou sur un terrain ayant accueilli une ancienne installation classée pour la protection de l'environnement (ICPE) soumise à autorisation ou
               enregistrement doivent en informer leurs acquéreurs ou locataires, au moyen d'un état des risques.
               Le non respect de ces obligations peut entrainer une annulation du contrat ou une réfaction du prix.</p>
            <p>Les informations qui doivent être rapportées sont arrêtées par le préfet de département. Ces arrêtés relatifs à l'état des risques pour les biens immobiliers sont consultables en
               Préfecture ou sur le site Internet de ces dernières.</p>
            <p>L'État met à disposition ci-dessus un outil permettant de remplir plus facilement cet état des risques en pré-remplissant automatiquement un certain nombre d'informations.
                <b>Il appartient ensuite au propriétaire du bien de vérifier l'exactitude de ces informations autant que de besoin et, le cas échéant, de le compléter à partir d'informations
                   disponibles sur le site internet de la préfecture ou d'informations dont il dispose sur le bien, notamment les sinistres que le bien a subis</b>.</p>
        </div>
        <div id="erp_picture">
            <img height="334"
                 src="/images/femmesCanape.png"
                 width="344">
        </div>
    </div>
</template>

<script>

export default {
    name: 'ERP'
}
</script>

<style scoped>
#erp_wrapper {
	background-color : #FFFFFF;
	height           : 100%;
	padding          : 5% 15%;
	text-align       : left;
	width            : 100%;
}

#erp_text {
	float  : left;
	height : 100%;
	width  : 55%;
}

#erp_text p {
	text-align : left;
	width      : 600px;
}

#erp_picture {
	float       : left;
	height      : 100%;
	margin-left : 7%;
	position    : relative;
	width       : 344px;
}

#erp_picture img {
	bottom   : 100px;
	margin   : auto;
	position : absolute;
	top      : 0;
}
</style>
