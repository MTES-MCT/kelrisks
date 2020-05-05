<template>
    <div id="erp_wrapper">
        <div id="erp_text">
            <h1>État des risques et pollutions</h1>
            <h2>L’information des acquéreurs et des locataires</h2>
            <p>Le Code de l’environnement a institué une obligation dans le cadre de l'information des acquéreurs et locataires (IAL) sur les risques naturels et technologiques majeurs. <b>Les
                                                                                                                                                                                             vendeurs et
                                                                                                                                                                                             bailleurs
                                                                                                                                                                                             de biens
                                                                                                                                                                                             immobiliers,
                                                                                                                                                                                             de toute
                                                                                                                                                                                             nature,</b>
               situés dans des zones couvertes par un ou des plans de prévention des risques (PPR), une zone de sismicité modérée ou forte ou dans un secteur d’information sur les sols ou dans une
               zone à potentiel radon de niveau 3 <b>doivent informer au plus tôt leurs acquéreurs ou locataires potentiels de l’existence de ces servitudes.</b><br/>
               Les informations qui doivent être rapportées sont arrêtées par le préfet de département. Ces arrêtés relatifs à l'état des risques naturels et technologiques majeurs pour les biens
               immobiliers sont consultables en Préfecture ou sur les sites Internet de ces dernières.</p>
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
        padding          : 5% 15%;
        text-align       : left;
        width            : 100%;
    }

    #erp_text {
        float : left;
        width : 55%;
    }

    #erp_text p {
        text-align : left;
        width      : 600px;
    }

    #erp_picture {
        float       : left;
        margin-left : 7%;
    }
</style>
