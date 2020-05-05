<template>
    <div class="leaflet_wrapper">
        <l-map :center="center"
               :ref="'leafletMap_' + reference"
               :zoom="zoom">
            <l-tile-layer :url="url"/>
            <l-geo-json :geojson="parseJSON(data)"
                        :options="risqueOptions"
                        :options-style="risqueStyleFunction"
                        :ref="'geoJson_' + reference"/>
        </l-map>
    </div>
</template>

<script>
import {LGeoJson, LMap, LTileLayer} from 'vue2-leaflet';

export default {
    name: "Leaflet",
    components: {
        LMap,
        LTileLayer,
        LGeoJson
    },
    props: {
        center: {
            type: Array,
            default: () => [48.8737762014, 2.2950365488]
        },
        data: {
            type: String,
            default: ''
        },
        arrayData: {
            type: Array,
            default: () => []
        }
    },
    data: () => ({
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        zoom: 16,
        bounds: null,
        reference: null
    }),
    methods: {
        crippleMap (map) {
            map.zoomControl.disable()
            map.touchZoom.disable()
            map.doubleClickZoom.disable()
            map.scrollWheelZoom.disable()
            map.boxZoom.disable()
            map.keyboard.disable()

            map.dragging.disable()
        },
        centerMap (map) {
            map.fitBounds(this.$refs['geoJson_' + this.reference].getBounds(), {maxZoom: 16});
        },
        parseJSON (data) {
            // console.log(data)
            if (data !== '' && data !== undefined) {
                return JSON.parse(data)
            }
            return {
                "type": "FeatureCollection",
                "features": []
            }
        },
        parseJSONMap (data) {
            // console.log(data)
            if (data !== '' && data !== undefined) {
                return data.map(x => JSON.parse(x))
            }
            return {
                "type": "FeatureCollection",
                "features": []
            }
        }
    },
    computed: {
        risqueOptions () {
            return {
                onEachFeature: this.onEachFeatureFunction,
            };
        },
        risqueStyleFunction () {
            // const fillColor = this.fillColor; // important! need touch fillColor in computed for re-calculate when change fillColor
            return () => {
                return {
                    weight: 2,
                    color: "#455674",
                    opacity: 0.8,
                    fillColor: "#455674",
                    fillOpacity: 0.2
                };
            };
        }
    },
    mounted () {

        this.reference = this._uid

        this.$nextTick(() => {

            const map = this.$refs['leafletMap_' + this.reference].mapObject
            this.crippleMap(map)
            this.centerMap(map)
        })
    }
}
</script>

<style scoped>
    .leaflet_wrapper {
        height : 100%;
        width  : 100%;
    }
</style>