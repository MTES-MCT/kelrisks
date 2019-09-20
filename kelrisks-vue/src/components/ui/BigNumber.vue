<template>
    <div class="bc-wrapper">
        <div class="bc-number"
             v-bind:class="{ notfound: !anyMatch,
                             found: anyMatch && numberOf > 0 }">
            <a v-bind:href="'#'+ id + '_anchor'"
               v-if="id">{{ numberOf }}</a>
            <template v-else>{{ numberOf }}</template>
        </div>
        <div class="bc-label">{{ labelText }}<sup style="font-size: 0.7em"
                                                  v-if="infoText"> (*)</sup>
            <div class="infobulle"
                 v-if="infoText">{{ infoText }}
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'big-number',

    props: {
        labelText: {
            type: String,
            required: true
        },
        infoText: {
            type: String,
            required: false
        },
        id: {
            type: String,
            required: false
        },
        numberOf: {
            type: Number,
            default: 0,
            required: true
        },
        anyMatch: {
            type: Boolean,
            default: false,
            required: true
        }
    }
}
</script>

<style>
    .bc-wrapper {
        display : inline-block;
        width   : 25%;
    }

    .bc-label {
        color : #2C3E50;
    }

    .bc-number {
        margin      : 10px;
        display     : inline-block;
        font-size   : 60px;
        font-weight : 500;
        color       : #53657D;
    }

    .bc-number a {
        text-decoration : none;
        font-size       : 60px;
        font-weight     : 500;
        color           : #53657D;
    }

    .notfound,
    .notfound a {
        color : #86CB92;
    }

    .found,
    .found a {
        color : #F79D65;
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

    .bc-wrapper:hover .infobulle {
        display : block;
    }

    .infobulle:hover {

        display : block;
    }
</style>