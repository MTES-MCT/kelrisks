<template>
    <div class="leaflet"
         style="height: 600px;">
        <l-map :center="center"
               :zoom="18"
               ref="leafletParcelles">
            <l-tile-layer :url="url"/>
            <l-geo-json :geojson="parseJSONMap(data.parcelles)"
                        :options="parcellesOptions"
                        :options-style="parcellesStyleFunction"
                        v-if="data && currentZoom >= 17"/>
        </l-map>
    </div>
</template>

<script>
import {LGeoJson, LMap, LTileLayer} from 'vue2-leaflet';
import fetchWithError from "../../script/fetchWithError";

export default {
    name: "Leaflet",
    components: {
        LMap,
        LTileLayer,
        LGeoJson
    },
    props: {},
    data: () => ({
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        currentZoom: 18,
        bounds: null,
        center: [0, 0],
        data: null,
        selectedList: []
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

            fetchWithError("http://localhost:8080/api/cadastre/" + x + "/" + y, null, 1000 * 10)
                .then(stream => stream.json())
                .then(data => {

                    this.center = [48.917351, 2.443221]

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
        }
    },
    computed: {
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
    mounted () {
        this.$nextTick(() => {

            const map = this.$refs.leafletParcelles.mapObject
            // this.crippleMap(map)

            map.on('moveend', () => {
                this.updateParcelles(map.getCenter().lng, map.getCenter().lat)
            })
            map.on('zoomend', () => {
                this.currentZoom = map.getZoom()
            })
        })

        this.updateParcelles(2.443221, 48.917351)
    }
}
</script>

<style scoped>
    .leaflet {
        width  : 100%;
        height : 100%;
    }
</style>