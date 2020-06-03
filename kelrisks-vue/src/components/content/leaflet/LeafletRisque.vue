<template>
    <div class="leaflet_wrapper">
        <l-map :center="center"
               v-if="reference !== null"
               :ref="'leafletMap_' + reference"
               :zoom="zoom">
            <l-tile-layer :url="url"/>
            <l-geo-json :geojson="getData(parcelle)"
                        :options="featureOptions"
                        :options-style="styleFunction('#455674')"
                        :ref="'lGeoJson_Parcelle_' + reference"/>
            <l-geo-json :geojson="getData(data)"
                        :ref="'lGeoJson_' + reference"
                        :options="featureOptions"
                        :options-style="styleFunction('#455674')"
                        v-if="typeof data === 'string'"/>
            <l-geo-json :geojson="getData(json.data)"
                        :ref="'lGeoJson_' + reference + '_' + index"
                        v-else
                        :key="json.color + '_' + index"
                        :options="featureOptions"
                        :options-style="styleFunction(json.color)"
                        v-for="(json, index) in data"/>
        </l-map>
    </div>
</template>

<script>
import {icon, marker} from "leaflet";
import {LGeoJson, LMap, LTileLayer} from 'vue2-leaflet';
import mixinLeaflet from "./leaflet_common";

export default {
    name: "Leaflet",
    mixins: [mixinLeaflet],
    components: {
        LMap,
        LTileLayer,
        LGeoJson
    },
    props: {
        center: {
            type: Array,
            default: () => [0, 0]
        },
        parcelle: {
            type: [String, Array],
            default: () => []
        },
        data: {
            type: [String, Array],
            default: () => []
        }
    },
    data: () => ({
    }),
    methods: {
        centerMap () {

            let map = this.$refs['leafletMap_' + this.reference].mapObject

            let bounds = null

            for (let ref in this.$refs) {

                if (!ref.includes('lGeoJson_')) continue

                let lGeoJson = Array.isArray(this.$refs[ref]) ? this.$refs[ref][0] : this.$refs[ref]

                if (typeof lGeoJson.getBounds === 'function') {

                    if (Object.prototype.hasOwnProperty.call(lGeoJson.getBounds(), "_northEast")) {
                        if (bounds === null) bounds = lGeoJson.getBounds()

                        if (bounds._northEast.lat < lGeoJson.getBounds()._northEast.lat) bounds._northEast.lat = lGeoJson.getBounds()._northEast.lat
                        if (bounds._northEast.lng < lGeoJson.getBounds()._northEast.lng) bounds._northEast.lng = lGeoJson.getBounds()._northEast.lng
                        if (bounds._southWest.lat > lGeoJson.getBounds()._southWest.lat) bounds._southWest.lat = lGeoJson.getBounds()._southWest.lat
                        if (bounds._southWest.lng > lGeoJson.getBounds()._southWest.lng) bounds._southWest.lng = lGeoJson.getBounds()._southWest.lng

                    }
                }
            }

            if (bounds !== null) {

                if (!this.isCenterDefault() && this.center) {

                    if (bounds._northEast.lat < this.center[0]) bounds._northEast.lat = this.center[0] + 0.0015
                    if (bounds._northEast.lng < this.center[1]) bounds._northEast.lng = this.center[1] + 0.0015
                    if (bounds._southWest.lat > this.center[0]) bounds._southWest.lat = this.center[0] - 0.0015
                    if (bounds._southWest.lng > this.center[1]) bounds._southWest.lng = this.center[1] - 0.0015
                }

                this.updateMapUntilFitsBounds(map, bounds)
            }
        },
        getData (data) {
            if (typeof data === 'string') return this.parseJSON(data)
            return this.parseJSONMap(data)
        },
        styleFunction (color) {

            if (!color) color = "#455674"

            return () => {
                return {
                    weight: 2,
                    color: color,
                    opacity: 0.8,
                    fillColor: color,
                    fillOpacity: 0.2
                };
            };
        },
        onEachFeatureFunction () {
            return (feature, layer) => {
                layer.bindTooltip(
                    () => {
                        let divs = ''
                        for (let property in feature.properties) {
                            let value = feature.properties[property]
                            let label = "";
                            if (property.startsWith("'")) {
                                label = property.substring(1, property.length - 1)
                            } else {
                                label = property.replace(/([A-Z])/gm, function (v) {
                                    return ' ' + v.toLowerCase()
                                }).replace(/(^.)/gm, function (v) {
                                    return v.toUpperCase()
                                })
                            }
                            divs = divs.concat('<div>', label, ' : ', value, '</div>')
                        }

                        return divs
                    },
                    {permanent: false, sticky: true}
                );
                layer.on('click', function (e) {
                    console.log(e)
                })
            };
        },
        isCenterDefault () {
            return this.center && this.center[0] === 0 && this.center[1] === 0
        }
    },
    computed: {
        featureOptions () {
            return {
                onEachFeature: this.onEachFeatureFunction,
                pointToLayer: this.createIcon
            };
        },
        createIcon () {
            return (feature, latlng) => {
                let myIcon = icon({
                    iconUrl: '/images/leaflet/adresse.svg',
                    shadowUrl: '/images/leaflet/shadow.png',
                    iconSize: [35, 35], // width and height of the image in pixels
                    shadowSize: [30, 22], // width, height of optional shadow image
                    iconAnchor: [17, 35], // point of the icon which will correspond to marker's location
                    shadowAnchor: [0, 24],  // anchor point of the shadow. should be offset
                    popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
                })
                return marker(latlng, {icon: myIcon})
            };
        }
    },
    mounted () {

        // console.log(this.reference + " => mounted")

        this.reference = this._uid

        this.$nextTick(() => {

            // console.log(this.reference + " => $nextTick")

            this.crippleMap('leafletMap_' + this.reference)
            this.centerMap()
        })
        window.addEventListener('resize', this.centerMap)
    },
    beforeDestroy: function () {
        window.removeEventListener('resize', this.centerMap)
    },
    watch: {
        data: function () {

            setTimeout(() => {

                // console.log(this.reference + " => setTimeout")

                this.$refs['leafletMap_' + this.reference].mapObject.invalidateSize()
                this.centerMap()
            }, 2000);
        }
    }
}
</script>

<style scoped>
    .leaflet_wrapper {
        height : 100%;
        width  : 100%;
    }
</style>