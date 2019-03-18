import JQuery from 'jquery'

let $ = JQuery

export default {
  /*
   * Permet de monter/descendre vers l'element cible
   * l'id est celui du champ visé ou son objet jQuery
   */
  scrollToElement (id, glow) {
    // console.log(id)
    if (JQuery.type(id) === 'string') {
      id = $('#' + id)
    }
    $('html, body').animate({
      scrollTop: (id.offset().top - 125)
    }, 750)

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
  }
}
