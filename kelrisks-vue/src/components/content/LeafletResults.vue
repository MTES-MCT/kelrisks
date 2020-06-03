<template>
    <div class="leaflet_wrapper">
        <l-map :center="center"
               :zoom="zoom"
               :ref="'leafletMap_' + reference">
            <l-tile-layer :url="url"/>
            <l-geo-json :geojson="parseJSONMap(data.parcelles)"
                        :options="parcelleOptions"
                        :options-style="parcelleStyleFunction"
                        :ref="'parcelle_' + reference"/>
            <l-geo-json :geojson="parseJSON(data.adresse)"
                        :options="adresseOptions"
                        v-if="data.adresse"/>
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
            type: Object,
            default: () => {
                return {
                    parcelles: "",
                }
            }
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

            if (!this.interval) {

                this.interval = setInterval(() => {

                    if (this.$refs['parcelle_' + this.reference]) {

                        let mapWidth = map.getBounds()._northEast.lng - map.getBounds()._southWest.lng
                        let boundWidth = this.$refs['parcelle_' + this.reference].getBounds()._northEast.lng - this.$refs['parcelle_' + this.reference].getBounds()._southWest.lng
                        let widthFillPercent = boundWidth / mapWidth

                        let mapHeight = map.getBounds()._northEast.lat - map.getBounds()._southWest.lat
                        let boundHeight = this.$refs['parcelle_' + this.reference].getBounds()._northEast.lat - this.$refs['parcelle_' + this.reference].getBounds()._southWest.lat
                        let heightFillPercent = boundHeight / mapHeight

                        map.fitBounds(this.$refs['parcelle_' + this.reference].getBounds(), {maxZoom: 30});
                        this.$refs['leafletMap_' + this.reference].mapObject.invalidateSize()

                        if (widthFillPercent > 0.25 || heightFillPercent > 0.25) {
                            clearInterval(this.interval)
                            this.interval = null
                        }
                    }
                }, 1000);
            }

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
        parcelleOptions () {
            return {
                onEachFeature: this.onEachFeatureFunction,
            };
        },
        adresseOptions () {
            return {
                onEachFeature: this.onEachFeatureFunction,
                pointToLayer: this.createAdresseIcon
            };
        },
        parcelleStyleFunction () {
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
        createAdresseIcon () {
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
        },
    },
    mounted () {

        this.reference = this._uid

        this.$nextTick(() => {

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