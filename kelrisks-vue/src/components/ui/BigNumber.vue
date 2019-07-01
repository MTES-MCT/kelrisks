<template>
    <div class="bc-wrapper">
        <div class="bc-number"
             v-bind:class="{ notfound: !anyMatch,
                             found: anyMatch && numberOf > 0 }">{{ numberOf }}
        </div>
        <div class="bc-label">{{ labelText }}</div>
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

    .notfound {
        color : #86CB92;
    }

    .found {
        color : #F79D65;
    }
</style>
