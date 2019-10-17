<template>
    <canvas height="100"
            id="radarWrapper"
            width="100">

    </canvas>
</template>

<script>
export default {
    name: "RadarChart",
    props: {
        dimensions: {
            type: Number,
            required: true,
            default: 5
        },
        steps: {
            type: Number,
            required: false,
            default: 5
        }
    },
    data () {
        return {}
    },
    mounted () {

        const radar = document.getElementById('radarWrapper');
        if (radar.getContext) {

            let ctx = radar.getContext('2d');

            ctx.beginPath();

            ctx.moveTo(50, 50);

            for (let s = 0; s < this.steps; s++) {
                for (let d = 0; d < this.dimensions; d++) {

                    s + d;

                }
            }

        }
    }
}
</script>

<style scoped>

</style>