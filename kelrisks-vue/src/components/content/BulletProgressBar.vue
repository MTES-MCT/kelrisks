<template>
    <div id="bpr_wrapper">
        <template v-for="(step, index) in steps">
            <div class="brp_bullets_separator"
                 v-bind:class="{'brp_step_done':currentStep >= index + 1}"
                 v-bind:key="'sep_' + index"
                 v-if="index > 0"></div>
            <div class="brp_bullet_wrapper"
                 @click="$emit('bulletclick', index + 1)"
                 v-bind:key="'num_' + index">
                <div class="brp_bullet_number"
                     v-bind:class="{'brp_step_done':currentStep > index + 1, 'brp_step_current':currentStep === index + 1}">{{ index + 1 }}
                </div>
                <div class="brp_bullet_label">{{ step }}</div>
            </div>
        </template>
    </div>
</template>

<script>

export default {
    name: 'BulletProgressBar',
    props: {
        steps: {
            type: Array,
            default: () => ["Waiting for steps"]
        },
        currentStep: {
            type: Number,
            default: 1
        }
    },
    methods: {}
}
</script>

<style scoped>
    #bpr_wrapper {
        display : flex;
        padding : 5px;
    }

    .brp_bullet_wrapper {
        margin  : 0 -1px;
        width   : 50px;
        z-index : 100;
    }

    .brp_bullet_number {
        border        : solid 6px #CAD1DB;
        border-radius : 25px;
        color         : #CAD1DB;
        font-size     : 1.7em;
        height        : 50px;
        padding       : 7px 0 0 1px;
        width         : 50px;
    }

    .brp_bullet_number.brp_step_current {
        border-color : #0053B3;
        color        : #0053B3;
    }

    .brp_bullet_number.brp_step_done {
        background-color : #0053B3;
        border-color     : #0053B3;
        color            : #FFFFFF;
    }

    .brp_bullet_label {
        display         : flex;
        justify-content : center;
        margin-top      : 10px;
        white-space     : nowrap;
    }

    .brp_bullets_separator {
        background-color : #CAD1DB;
        flex             : 1;
        height           : 15px;
        margin-top       : 17px;
        z-index          : 99;
    }

    .brp_bullets_separator.brp_step_done {
        background-color : #0053B3;
    }

</style>
