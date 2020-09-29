<template>
    <div class="leaflet_wrapper leaflet_pdf">
        <l-map :center="maxZoomCenter"
               :id="'leafletMap_' + reference"
               :ref="'leafletMap_' + reference"
               :zoom="zoom"
               v-if="reference !== null">
            <l-tile-layer :ref="'lTile_' + reference"
                          :url="url"
                          @load="tilesLoaded = true"
                          @loading="tilesLoaded = false"/>
            <l-geo-json :geojson="getData(parcelle)"
                        :options="featureOptions"
                        :options-style="styleFunction('#455674')"
                        :ref="'lGeoJson_Parcelle_' + reference"
                        v-if="parcelle && parcelle.length !== 0"/>
            <l-geo-json :geojson="getData(data)"
                        :options="featureOptions"
                        :options-style="styleFunction('#455674')"
                        :ref="'lGeoJson_' + reference"
                        v-if="typeof data === 'string' && data.length !== 0"/>
            <l-geo-json :geojson="getData(json.data)"
                        :key="json.color + '_' + index"
                        :options="featureOptions(json.color)"
                        :options-style="styleFunction(json.color)"
                        :ref="'lGeoJson_' + reference + '_' + index"
                        v-else-if="data.length !== 0"
                        v-for="(json, index) in data"/>
        </l-map>
    </div>
</template>

<script>
import {divIcon, marker} from "leaflet";
import {LGeoJson, LMap, LTileLayer} from 'vue2-leaflet';
import mixinLeaflet from "./leaflet_common";
import domtoimage from "dom-to-image";

export default {
    name: "LeafletPdf",
    mixins: [mixinLeaflet],
    components: {
        LMap,
        LTileLayer,
        LGeoJson
    },
    props: {
        parcelle: {
            type: [String, Array],
            default: () => []
        },
        data: {
            type: [String, Array],
            default: () => []
        }
    },
    methods: {
        centerMap () {

            let map = this.$refs['leafletMap_' + this.reference].mapObject

            let bounds = null

            for (let ref in this.$refs) {

                if (!ref.includes('lGeoJson_')) continue

                let lGeoJson = Array.isArray(this.$refs[ref]) ? this.$refs[ref][0] : this.$refs[ref]

                if (lGeoJson && typeof lGeoJson.getBounds === 'function') {

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

                if (!this.isCenterDefault() && this.maxZoomCenter) {

                    if (bounds._northEast.lat < this.maxZoomCenter[0]) bounds._northEast.lat = this.maxZoomCenter[0] + 0.0015
                    if (bounds._northEast.lng < this.maxZoomCenter[1]) bounds._northEast.lng = this.maxZoomCenter[1] + 0.0015
                    if (bounds._southWest.lat > this.maxZoomCenter[0]) bounds._southWest.lat = this.maxZoomCenter[0] - 0.0015
                    if (bounds._southWest.lng > this.maxZoomCenter[1]) bounds._southWest.lng = this.maxZoomCenter[1] - 0.0015
                }

                this.updateMapUntilFitsBounds(map, 'leafletMap_' + this.reference, bounds, true, false)
            }
        },
        getData (data) {
            console.log(('getData : ' + data).substr(0, 100))
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
        exportToPng () {
            console.log('exportToPng')

            if (this.mapCentered) {
                console.log('export !')

                setTimeout(() => {
                    domtoimage.toPng(document.getElementById('leafletMap_' + this.reference), {})
                        .then((dataUrl) => {
                            let img = new Image();
                            img.src = dataUrl;
                            // document.body.appendChild(img);
                            this.$emit('png', dataUrl)
                        })
                        .catch(function (error) {
                            console.error('oops, something went wrong!', error);
                        })
                }, 250)
            }
        }
    },
    computed: {
        featureOptions () {
            return color => ({
                onEachFeature: this.onEachFeatureFunction,
                pointToLayer: this.createIcon(color)
            });
        },
        createIcon () {

            return myCustomColour => {

                const markerHtmlStyles =
                    ' background-color: ' + myCustomColour + '99;' +
                    ' width: 1.5rem;' +
                    ' height: 1.5rem;' +
                    ' display: block;' +
                    ' left: -0.75rem;' +
                    ' top: -0.75rem;' +
                    ' position: relative;' +
                    ' border-radius: 1.5rem 1.5rem 0;' +
                    ' transform: rotate(45deg);' +
                    ' border: 1px solid #FFFFFF99'

                return (feature, latlng) => {
                    let myIcon = divIcon({
                        className: "my-custom-pin",
                        iconAnchor: [0, 24],
                        labelAnchor: [-6, 0],
                        popupAnchor: [0, -36],
                        html: '<span style="' + markerHtmlStyles + '" />'
                    })
                    return marker(latlng, {icon: myIcon})
                };
            }
        }
    },
    mounted () {

        this.reference = this._uid

        this.$nextTick(() => {

            this.crippleMap('leafletMap_' + this.reference)

            let map = this.$refs['leafletMap_' + this.reference].mapObject

            map.on('zoomend', () => {
                this.currentZoom = map.getZoom()
            })
        })
    },
    watch: {
        data: function () {
            console.log(this.data)

            this.mapCentered = false

            if (this.data !== []) {
                setTimeout(() => {

                    this.$refs['leafletMap_' + this.reference].mapObject.invalidateSize()
                    this.centerMap()
                }, 250);
            }
        },
        mapCentered: function () {
            if (this.mapCentered) this.exportToPng()
        }
    }
}
</script>

<style>
.leaflet_wrapper {
	height : 300px;
	width  : 349px;
}

.leaflet_pdf div.leaflet-control-attribution {
	display : none !important;
}
</style>