import JQuery from 'jquery'

let $ = JQuery

export default {
    /*
     * Permet de monter/descendre vers l'element cible
     * l'id est celui du champ visé ou son objet jQuery
     */
    scrollToElement (id, speed, delay, glow) {
        // console.log(id)
        // console.log(delay === undefined)
        if (JQuery.type(id) === 'string') {
            id = $('#' + id)
        }
        // console.log(delay ? 'true' : 'false');
        $('html, body').delay(delay !== undefined ? delay : 500).animate({
            scrollTop: (id.offset().top - 125)
        }, speed !== undefined ? speed : 500)

        if (glow) {
            this.targetGlow(id)
        }
    },

    /*
     * Permet de faire briller (box-shadow) 3 fois l'élément cible afin de bien le voir. Reste en surbrillance à la fin.
     */
    targetGlow (id) {
        if (JQuery.type(id) === 'string') {
            id = $('#' + id)
        }

        $('.glow').removeClass('glow')
        id.addClass('glow')
    },

    exists (value) {

        return !(typeof (value) === 'undefined' ||
            value === null ||
            isNaN(value) ||
            value === '');
    }
}
