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
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        zoom: 16,
        bounds: null,
        reference: null,
        interval: null
    }),
    methods: {
        crippleMap () {

            let map = this.$refs['leafletMap_' + this.reference].mapObject

            map.zoomControl.disable()
            map.zoomControl.remove()
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

                if (!this.isCenterDefault() && this.center) {

                    if (bounds._northEast.lat < this.center[0]) bounds._northEast.lat = this.center[0] + 0.0015
                    if (bounds._northEast.lng < this.center[1]) bounds._northEast.lng = this.center[1] + 0.0015
                    if (bounds._southWest.lat > this.center[0]) bounds._southWest.lat = this.center[0] - 0.0015
                    if (bounds._southWest.lng > this.center[1]) bounds._southWest.lng = this.center[1] - 0.0015
                }

                if (!this.interval) {

                    this.interval = setInterval(() => {

                        map.fitBounds(bounds)
                        map.invalidateSize()

                        let mapWidth = map.getBounds()._northEast.lng - map.getBounds()._southWest.lng
                        let boundWidth = bounds._northEast.lng - bounds._southWest.lng
                        let widthFillPercent = boundWidth / mapWidth

                        let mapHeight = map.getBounds()._northEast.lat - map.getBounds()._southWest.lat
                        let boundHeight = bounds._northEast.lat - bounds._southWest.lat
                        let heightFillPercent = boundHeight / mapHeight

                        if (widthFillPercent > 0.25 || heightFillPercent > 0.25) {
                            clearInterval(this.interval)
                            this.interval = null
                        }
                    }, 1000);
                }
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

            this.crippleMap()
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