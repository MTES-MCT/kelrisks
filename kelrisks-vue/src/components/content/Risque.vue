<template>
    <div class="risque_wrapper"
         v-if="hasTitle || hasDescription || hasLogo">
        <div class="risque_summary"
             v-bind:class="{'no-logo':!hasLogo}">
            <div class="risque_summary__icon"
                 v-if="hasTitle || hasLogo">
                <!--suppress HtmlUnknownTarget -->
                <img :alt="title"
                     :src="logoURL"
                     height="130"
                     v-if="hasLogo"
                     width="130">
                <span>{{title}}</span>
            </div>
            <div class="risque_summary_legend__level"
                 v-if="hasLevel">
                <span>{{level}}</span>
                <span v-if="levelMax !== ''">/</span>
                <span>{{levelMax}}</span>
            </div>
            <div class="risque_summary__legend"
                 v-if="hasLegend">
                <div class="risque_summary_legend__blocks">
                    <template v-for="(block, index) in legendBlocks">
                        <div :style="'background-color: ' + block[0]"
                             class="risque_summary_legend_blocks__block"
                             v-bind:key="'block_' + index"></div>
                        <div class="risque_summary_legend_blocks__text"
                             v-bind:key="'text_' + index">{{block[1]}}
                        </div>
                    </template>
                </div>
            </div>
            <div class="risque_summary__text"
                 v-bind:style="{flex: !hasLevel && !hasLegend ? '1' : 'auto'}"
                 v-if="hasDescription">
                <p class="description"
                   v-html="description"></p></div>
            <div class="risque_summary__text detail"
                 style="flex: auto;"
                 v-if="hasDetail">
                <p v-html="detail"></p></div>

            <div class="clearfix"></div>
        </div>
        <div class="risque_map"
             v-if="leafletData">
            <leaflet :max-zoom-center="maxZoomCenter"
                     :parcelle="parcelle"
                     :data="leafletData"/>
        </div>
    </div>
</template>

<script>
import Leaflet from "./leaflet/LeafletRisque";

export default {
    name: 'Risque',
    components: {Leaflet},
    props: {
        maxZoomCenter: {
            type: Array,
            default: null
        },
        parcelle: {
            type: [String, Array],
            default: () => []
        },
        logoURL: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: ''
        },
        level: {
            type: String,
            default: ''
        },
        levelMax: {
            type: String,
            default: ''
        },
        legendBlocks: {
            type: Array,
            default: () => []
        },
        description: {
            type: String,
            default: ''
        },
        detail: {
            type: String,
            default: ''
        },
        leafletData: {
            type: [String, Array],
            default: undefined
        },
    },
    methods: {},
    computed: {
        hasLegend: function () {
            return this.legendBlocks.length > 0
        },
        hasLevel: function () {
            return this.level !== ''
        },
        hasLogo: function () {
            return this.logoURL !== ''
        },
        hasTitle: function () {
            return this.title !== ''
        },
        hasDescription: function () {
            return this.description !== ''
        },
        hasDetail: function () {
            return this.detail !== ''
        }
    }
}
</script>

<style scoped>
    .risque_wrapper {
        background     : #FFFFFF;
        border         : 2px solid #BAB9B9;
        color          : #26353F;
        display        : flex;
        flex-basis     : calc(50% - 40px);
        flex-direction : column;
        margin         : 20px;
    }

    .risque_summary {
        display   : flex;
        flex      : 1 0 auto;
        flex-wrap : wrap;
        padding   : 9px 22px;
    }

    div[class^="risque_summary_"] {
    }

    .risque_summary__icon {
        display        : flex;
        flex-direction : column;
        margin-right   : 22px;
        width          : 175px;
    }

    .risque_summary__icon > img {
        float  : left;
        height : 130px;
        margin : 7px auto;
        width  : 130px;
    }

    .risque_summary__icon > span {
        clear          : both;
        float          : left;
        font-size      : 24px;
        font-stretch   : normal;
        font-style     : normal;
        font-weight    : bold;
        letter-spacing : normal;
        line-height    : 1;
        text-align     : center;
        width          : 100%;
    }

    .risque_summary.no-logo {
        flex : unset;
    }

    .risque_summary.no-logo .risque_summary__icon {
        width : 100%;
    }

    .risque_summary.no-logo .risque_summary__icon span {
        text-align : left;
    }

    .risque_summary__legend {
    }

    .risque_summary_legend__level {
        margin : 28px 4% 0 0;
    }

    .risque_summary_legend__level > span:nth-of-type(1) {
        font-size      : 120px;
        font-stretch   : normal;
        font-style     : normal;
        font-weight    : bold;
        letter-spacing : normal;
        line-height    : 1;
    }

    .risque_summary_legend__level > span:nth-of-type(2),
    .risque_summary_legend__level > span:nth-of-type(3) {
        font-size      : 24px;
        font-stretch   : normal;
        font-style     : normal;
        font-weight    : bold;
        letter-spacing : normal;
        line-height    : 1;
    }

    .risque_summary_legend__blocks {
        margin : 37px 0 0 0;
    }

    .risque_summary_legend__blocks > div {
        float : left;
    }

    .risque_summary_legend__blocks > div:nth-child(2n+1) {
        clear : both;
    }

    .risque_summary_legend__blocks > span {
        float          : left;
        font-size      : 15px;
        font-stretch   : normal;
        font-style     : normal;
        font-weight    : normal;
        letter-spacing : normal;
        line-height    : 1.42;
    }

    .risque_summary_legend_blocks__block {
        border : 1px solid #979797;
        height : 15px;
        margin : 3px 5px 0 0;
        width  : 30px;
    }

    .risque_summary_legend_blocks__text {

    }

    .risque_summary__text {
        margin-top : 10px;
    }

    .risque_summary__text p.description {
        margin-bottom : 0;
        text-align    : left;
    }

    .risque_map {
        background-color : #2C3E50;
        flex             : 0 0 255px;
        margin-top       : 10px;
        width            : 100%;
    }

    .clearfix:after {
        clear      : both;
        content    : '';
        display    : block;
        height     : 0;
        visibility : hidden;
    }
</style>
