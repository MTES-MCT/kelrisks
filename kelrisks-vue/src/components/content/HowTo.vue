<template>
    <div id="howto_wrapper">
        <h3>Kelrisks, comment ça marche ?</h3>
        <div class="box panel">
            <div class="content">
                <img height="75"
                     src="/images/icons/kelrisks/howTo1.svg"
                     width="75">
                <p>Vous trouvez le terrain concerné à l'aide de son adresse ou sa référence cadastrale.</p>
            </div>
        </div>
        <hr>
        <div class="box panel">
            <div class="content">
                <img height="75"
                     src="/images/icons/kelrisks/howTo2.svg"
                     width="75">
                <p>Kelrisks affiche le résultat de la recherche et les principaux risques et pollutions.</p>
            </div>
        </div>
        <hr>
        <div class="box panel">
            <div class="content">
                <img height="75"
                     src="/images/icons/kelrisks/howTo3.svg"
                     width="75">
                <p>Votre ERP, État des Risques et Pollutions est prêt à être téléchargé.</p>
                <p class="subtext"
                   style="color: #AAAAAA; font-size: 0.75em">* voir CGU pour les détails</p>
            </div>
        </div>
        <hr>
        <div class="box panel">
            <div class="content">
                <img height="75"
                     src="/images/icons/kelrisks/howTo4.svg"
                     width="75">
                <p>Les résultats sont téléchargeables et partageables</p>
                <p class="subtext"
                   style="color: #AAAAAA; font-size: 0.75em">* voir CGU pour les détails</p>
            </div>
        </div>
    </div>
</template>

<script>
import JQuery from 'jquery'

let $ = JQuery

export default {
    name: 'HowTo',
    mounted: function () {
        window.addEventListener('resize', this.handleResize)
    },
    beforeDestroy: function () {
        window.removeEventListener('resize', this.handleResize)
    },
    methods: {
        handleResize () {
            let width = $('#howto_wrapper div.box:first').width()
            $('#howto_wrapper svg').css('font-size', width / 247 * 3 + 'em')
            $('#howto_wrapper p').css('font-size', width / 247 + 'em').css('line-height', width / 247 * 1.5 + 'em')
            $('#howto_wrapper p.subtext').css('font-size', width / 247 * 0.75 + 'em')
        }
    }
}
</script>

<style scoped>
    #howto_wrapper {
        background-color : #EBEFF3;
        height           : 25%;
        margin           : 0 auto;
        padding          : 50px 200px 80px;
    }

    #howto_wrapper .content img {
        margin-top : 15px;
    }

    .box {
        background-color : #FFFFFF;
        border           : solid 1px rgba(181, 193, 208, 0.4);
        border-radius    : 10px;
        box-shadow       : 0 2px 6px 0 rgba(95, 108, 202, 0.14), 0 5px 4px -3px rgba(228, 229, 240, 0.13);
        float            : left;
        margin           : 0;
        padding          : calc(100% / 11 * 2) 0 0 0;
        width            : calc(100% / 11 * 2);
    }

    .content {
        bottom   : 0;
        left     : 0;
        margin   : 10px;
        position : absolute;
        right    : 0;
        top      : 0;
    }

    .content p {
        font-weight : bold;
        text-align  : center;
        text-indent : unset;
    }

    .content svg {
        color  : #003B80;
        margin : 10px 0;
    }

    hr {
        float      : left;
        margin-top : calc(100% / 11);
        width      : calc(100% / 11);
    }
</style>
