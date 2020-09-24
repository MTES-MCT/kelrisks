import JQuery from 'jquery'

let $ = JQuery

export default {
    data: () => ({
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        zoom: 16,
        bounds: null,
        reference: null,
        interval: null,
        currentZoom: null,
        minZoomCenter: {x: null, y: null},
        intermediateZoomLevels: [],
        mapCentered: false,
        tilesLoaded: true,
        zooming: false,
        currentMaxZoom: 18,
        currentMinZoom: null
    }),
    props: {
        maxZoomCenter: {
            type: Array,
            default: () => [0, 0]
        },
        maxZoom: {default: () => null},
        minZoom: {default: () => null}
    },
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
        zoomIn (map, zoom) {

            this.zooming = true

            let zoomInControl = $("#leafletMap_" + this.reference + " a.leaflet-control-zoom-in")
            let zoomOutControl = $("#leafletMap_" + this.reference + " a.leaflet-control-zoom-out");

            if (this.currentZoom >= this.currentMaxZoom) return

            if (zoomOutControl.hasClass('leaflet-disabled')) {

                zoomOutControl.removeClass('leaflet-disabled')
            }

            if (zoom) this.currentZoom = zoom
            else ++this.currentZoom

            if (this.currentZoom === this.currentMaxZoom) zoomInControl.addClass('leaflet-disabled')
            if (this.currentZoom === this.currentMinZoom) zoomOutControl.addClass('leaflet-disabled')

            console.log(this.intermediateZoomLevels)
            console.log(this.currentZoom)

            map.setView({lat: this.intermediateZoomLevels[this.currentZoom].y, lng: this.intermediateZoomLevels[this.currentZoom].x}, this.currentZoom, {animation: true});
        },
        zoomOut (map, zoom) {

            this.zooming = true

            let zoomInControl = $("#leafletMap_" + this.reference + " a.leaflet-control-zoom-in")
            let zoomOutControl = $("#leafletMap_" + this.reference + " a.leaflet-control-zoom-out");

            if (this.currentZoom <= this.currentMinZoom) return

            if (zoomInControl.hasClass('leaflet-disabled')) {

                zoomInControl.removeClass('leaflet-disabled')
            }

            if (zoom) this.currentZoom = zoom
            else --this.currentZoom

            if (this.currentZoom === this.currentMaxZoom) zoomInControl.addClass('leaflet-disabled')
            if (this.currentZoom === this.currentMinZoom) zoomOutControl.addClass('leaflet-disabled')

            map.setView({lat: this.intermediateZoomLevels[this.currentZoom].y, lng: this.intermediateZoomLevels[this.currentZoom].x}, this.currentZoom, {animation: true});
        },
        injectCustomZoomControl (ref) {

            let map = this.$refs[ref].mapObject

            let control = "<div class=\"leaflet-control-zoom leaflet-bar leaflet-control\">" +
                "   <a class=\"leaflet-control-zoom-in  leaflet-disabled\" title=\"Zoom in\" role=\"button\" aria-label=\"Zoom in\">+</a>" +
                "   <a class=\"leaflet-control-zoom-out leaflet-disabled\" title=\"Zoom out\" role=\"button\" aria-label=\"Zoom out\">−</a>" +
                "</div>"

            $("#leafletMap_" + this.reference + " div.leaflet-top.leaflet-left").html(control)

            let zoomInControl = $("#leafletMap_" + this.reference + " a.leaflet-control-zoom-in")
            let zoomOutControl = $("#leafletMap_" + this.reference + " a.leaflet-control-zoom-out");

            if (this.currentMaxZoom > this.currentZoom) zoomInControl.removeClass('leaflet-disabled')
            if (this.currentMinZoom < this.currentZoom) zoomOutControl.removeClass('leaflet-disabled')

            zoomInControl.click((e) => {

                e.stopPropagation()
                this.zoomIn(map);
            });

            zoomOutControl.click((e) => {

                e.stopPropagation()
                this.zoomOut(map);
            });
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
        getBoundsCenter (bounds) {
            let center = {x: null, y: null}

            center.x = bounds._southWest.lng + Math.abs(bounds._northEast.lng - bounds._southWest.lng) / 2
            center.y = bounds._southWest.lat + Math.abs(bounds._northEast.lat - bounds._southWest.lat) / 2

            return center
        },
        initIntermediateZoomLevels () {

            console.log("initIntermediateZoomLevels")

            console.log(this.currentMaxZoom)

            this.intermediateZoomLevels[this.currentMaxZoom] = {x: this.maxZoomCenter[1], y: this.maxZoomCenter[0]}

            let deltaX = Math.abs(this.minZoomCenter.x - this.maxZoomCenter[1])
            let deltaY = Math.abs(this.minZoomCenter.y - this.maxZoomCenter[0])
            let remainingX = deltaX
            let remainingY = deltaY
            let currentDeltaX = 0
            let currentDeltaY = 0

            const increaseRatio = 3 / 4

            console.log(this.currentMinZoom)

            for (let zoomLevel = this.currentMinZoom; zoomLevel < this.currentMaxZoom; zoomLevel++) {

                let currentCenter = {x: null, y: null}

                currentDeltaX += remainingX * increaseRatio
                currentDeltaY += remainingY * increaseRatio

                currentCenter.x = this.minZoomCenter.x + (this.minZoomCenter.x < this.maxZoomCenter[1] ? currentDeltaX : -currentDeltaX)
                currentCenter.y = this.minZoomCenter.y + (this.minZoomCenter.y < this.maxZoomCenter[0] ? currentDeltaY : -currentDeltaY)

                remainingX = deltaX - currentDeltaX
                remainingY = deltaY - currentDeltaY

                this.intermediateZoomLevels[zoomLevel] = currentCenter
            }
        },
        initMapZoom (map, mapRef, injectZoomControls) {

            if (!this.currentMinZoom) {

                this.currentMinZoom = map.getZoom()

                this.minZoomCenter = this.getBoundsCenter(map.getBounds())
                this.intermediateZoomLevels[this.currentMinZoom] = this.minZoomCenter

                this.currentZoom = this.currentMinZoom

                if (injectZoomControls) this.injectCustomZoomControl(mapRef)
            }

            this.initIntermediateZoomLevels();

            if (this.minZoom) {
                console.log("zoomIn")
                this.currentMinZoom = this.minZoom
                console.log(this.currentZoom)
                console.log(this.currentMinZoom)
                if (this.currentZoom < this.currentMinZoom) this.zoomIn(map, this.currentMinZoom)
            }
            if (this.maxZoom) {
                this.currentMaxZoom = this.maxZoom
                if (this.currentZoom > this.currentMaxZoom) this.zoomOut(map, this.currentMaxZoom)
            }
            if (!this.zooming) this.mapCentered = true
        },
        updateMapUntilFitsBounds (map, mapRef, bounds, initZoom, injectZoomControls) {

            this.mapCentered = false

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

                    if (widthFillPercent > 0.25 || heightFillPercent > 0.25 || this.tilesLoaded) {

                        clearInterval(this.interval)
                        this.interval = null

                        setTimeout(() => {

                            if (!this.isCenterDefault() && this.maxZoomCenter && (initZoom || injectZoomControls)) {
                                console.log("(!this.isCenterDefault() && this.maxZoomCenter && (initZoom || injectZoomControls))")
                                this.initMapZoom(map, mapRef, injectZoomControls)
                            } else {
                                console.log("else")
                                this.mapCentered = true;
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        },
        isCenterDefault () {
            return this.maxZoomCenter && this.maxZoomCenter[0] === 0 && this.maxZoomCenter[1] === 0
        }
    },
    watch: {
        tilesLoaded: function () {

            console.log("tilesLoaded : this.tilesLoaded = " + this.tilesLoaded + ", this.zooming = " + this.zooming)

            if (this.tilesLoaded && this.zooming) {
                this.zooming = false
                this.mapCentered = true
            }
        }
    }

}
