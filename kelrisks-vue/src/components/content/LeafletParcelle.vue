<template>
    <div class="leaflet">
        <l-map :center="center"
               :zoom="18"
               ref="leafletParcelles">
            <l-tile-layer :url="url"/>
            <l-geo-json :geojson="parseJSONMap(data.parcelles)"
                        :options="parcellesOptions"
                        :options-style="parcellesStyleFunction"
                        v-if="data && currentZoom >= 17"/>
            <l-marker :icon="createAdresseIcon"
                      :lat-lng="center"
                      v-if="center"/>
        </l-map>
    </div>
</template>

<script>
import {LGeoJson, LMap, LMarker, LTileLayer} from 'vue2-leaflet';
import fetchWithError from "../../script/fetchWithError";
import {icon} from "leaflet";

export default {
    name: "Leaflet",
    components: {
        LMap,
        LTileLayer,
        LMarker,
        LGeoJson
    },
    props: {
        center: {
            type: Array,
            default: () => [48.8737762014, 2.2950365488]
        },
        parcelleFound: {
            type: String,
            default: undefined
        }
    },
    data: () => ({
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        currentZoom: 18,
        bounds: null,
        data: null,
        selectedList: []
    }),
    methods: {
        invalidate () {
            // console.log('invalidated')
            this.$refs.leafletParcelles.mapObject.invalidateSize()
        },
        crippleMap (map) {
            map.zoomControl.disable()
            map.touchZoom.disable()
            map.doubleClickZoom.disable()
            map.scrollWheelZoom.disable()
            map.boxZoom.disable()
            map.keyboard.disable()

            map.dragging.disable()
        },
        parseJSON (data) {
            if (data !== '') {
                return JSON.parse(data)
            }
            return {
                "type": "FeatureCollection",
                "features": []
            }
        },
        parseJSONMap (data) {
            if (data !== '') {
                return data.map(x => JSON.parse(x))
            }
            return {
                "type": "FeatureCollection",
                "features": []
            }
        },
        updateParcelles (x, y) {

            fetchWithError(this.env.apiPath + "cadastre/" + x + "/" + y, null, 1000 * 10)
                .then(stream => stream.json())
                .then(data => {

                    this.data = {
                        parcelles: data.entity
                    }
                })
        },
        initLayerClick (layer) {

            if (this.selectedList.includes(layer.id)) {
                layer.setStyle({fillColor: '#FF0000', color: '#FF0000'})
                layer.selected = true
            } else {
                layer.selected = false
            }

            layer.on('click', () => {
                layer.selected = !layer.selected

                layer.selected ? layer.setStyle({fillColor: '#FF0000', color: '#FF0000'}) : layer.setStyle({fillColor: '#455674', color: '#455674'})

                if (layer.id) {
                    if (layer.selected) {
                        this.selectedList.push(layer.id)
                    } else {
                        this.selectedList = this.selectedList.filter(function (element) {
                            return element !== layer.id;
                        })
                    }
                }

                this.$emit('parcelleselected', this.selectedList)
            });

            this.$emit('parcelleselected', this.selectedList)
        }
    },
    computed: {
        createAdresseIcon () {
            return icon({
                iconUrl: '/images/leaflet/adresse.svg',
                shadowUrl: '/images/leaflet/shadow.png',
                iconSize: [35, 35], // width and height of the image in pixels
                shadowSize: [30, 22], // width, height of optional shadow image
                iconAnchor: [17, 35], // point of the icon which will correspond to marker's location
                shadowAnchor: [0, 24],  // anchor point of the shadow. should be offset
                popupAnchor: [0, 0] // point from which the popup should open relative to the iconAnchor
            })
        },
        parcellesOptions () {
            return {
                onEachFeature: this.onEachFeatureFunction,
            };
        },
        parcellesStyleFunction () {
            return () => {
                return {
                    weight: 2,
                    color: "#455674",
                    opacity: 0.6,
                    fillColor: "#455674",
                    fillOpacity: 0.1
                };
            };
        },
        onEachFeatureFunction () {
            return (feature, layer) => {
                layer.bindTooltip(
                    () => {
                        let divs = ''
                        let value = feature.properties['code']
                        let label = 'Code'
                        divs = divs.concat('<div>', label, ' : ', value, '</div>')
                        return divs
                    },
                    {permanent: false, sticky: true}
                );

                if (feature.properties['code']) {
                    layer.id = feature.properties['code']
                }

                this.initLayerClick(layer)
            };
        }
    },
    watch: {
        parcelleFound: function () {
            if (this.parcelleFound && this.parcelleFound !== '') {
                this.selectedList = [this.parcelleFound]
            } else this.selectedList = []
        }
    },
    mounted () {
        this.$nextTick(() => {

            const map = this.$refs.leafletParcelles.mapObject

            map.on('moveend', () => {
                this.updateParcelles(map.getCenter().lng, map.getCenter().lat)
            })
            map.on('zoomend', () => {
                this.currentZoom = map.getZoom()
            })
        })

        this.updateParcelles(this.center[0], this.center[1])
    }
}
</script>

<style scoped>
    .leaflet {
        width  : 100%;
        height : 100%;
    }
</style>