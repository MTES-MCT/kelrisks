<template>
    <div class="leaflet_wrapper">
        <l-map :center="maxZoomCenter"
               :zoom="zoom"
               :id="'leafletMap_' + reference"
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
import mixinLeaflet from "./leaflet_common";

export default {
    name: "LeafletResults",
    mixins: [mixinLeaflet],
    components: {
        LMap,
        LTileLayer,
        LGeoJson
    },
    props: {
        data: {
            type: Object,
            default: () => {
                return {
                    parcelles: "",
                }
            }
        }
    },
    data: () => ({}),
    methods: {

        centerMap () {

            let map = this.$refs['leafletMap_' + this.reference].mapObject

            this.updateMapUntilFitsBounds(map, 'leafletMap_' + this.reference, this.$refs['parcelle_' + this.reference].getBounds(), true)
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

                this.crippleMap('leafletMap_' + this.reference)
                this.centerMap()
                // if (!this.isCenterDefault()) this.injectCustomZoomControl('leafletMap_' + this.reference)
            }
        )
        window.addEventListener('resize', this.centerMap)
    },
    beforeDestroy: function () {
        window.removeEventListener('resize', this.centerMap)
    }
    ,
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