export default {
    data: () => ({
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        zoom: 16,
        bounds: null,
        reference: null,
        interval: null
    }),
    methods: {
        crippleMap (ref) {

            let map = this.$refs[ref].mapObject

            map.zoomControl.disable()
            map.zoomControl.remove()
            map.touchZoom.disable()
            map.doubleClickZoom.disable()
            map.scrollWheelZoom.disable()
            map.boxZoom.disable()
            map.keyboard.disable()

            map.dragging.disable()
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
        updateMapUntilFitsBounds (map, bounds) {

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
    }
}
