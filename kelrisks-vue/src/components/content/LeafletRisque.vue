<template>
    <div class="leaflet_wrapper">
        <l-map :center="center"
               v-if="reference !== null"
               :ref="'leafletMap_' + reference"
               :zoom="zoom">
            <l-tile-layer :url="url"/>
            <l-geo-json :geojson="getData(data)"
                        :options="featureOptions"
                        :options-style="styleFunction('#455674')"
                        :ref="'lGeoJson_' + reference"
                        v-if="typeof data === 'string'"/>
            <l-geo-json :geojson="getData(json.data)"
                        :key="json.color + '_' + index"
                        :options="featureOptions"
                        :options-style="styleFunction(json.color)"
                        :ref="'lGeoJson_' + reference + '_' + index"
                        v-else
                        v-for="(json, index) in data"/>
        </l-map>
    </div>
</template>

<script>
import {icon, marker} from "leaflet";
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
            type: [String, Array],
            default: () => []
        }
    },
    data: () => ({
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        zoom: 16,
        bounds: null,
        reference: null,
    }),
    methods: {
        crippleMap () {

            let map = this.$refs['leafletMap_' + this.reference].mapObject

            map.zoomControl.disable()
            map.touchZoom.disable()
            map.doubleClickZoom.disable()
            map.scrollWheelZoom.disable()
            map.boxZoom.disable()
            map.keyboard.disable()
            map.dragging.disable()
        },
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
                map.fitBounds(bounds)
            }
        },
        getData (data) {
            if (typeof data === 'string') return this.parseJSON(data)
            return this.parseJSONMap(data)
        },
        parseJSON (json) {
            if (json !== '' && json !== undefined) {
                return JSON.parse(json)
            }
            return {
                "type": "FeatureCollection",
                "features": []
            }
        },
        parseJSONMap (jsonMap) {
            if (jsonMap !== '' && jsonMap !== undefined && jsonMap.length > 0) {
                return jsonMap.map(x => JSON.parse(x))
            }
            return {
                "type": "FeatureCollection",
                "features": []
            }
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

        this.reference = this._uid

        this.$nextTick(() => {

            this.crippleMap()
            this.centerMap()
        })
    },
    watch: {
        data: function () {
            this.centerMap()
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