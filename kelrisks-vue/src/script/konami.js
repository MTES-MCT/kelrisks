import JQuery from 'jquery'

let $ = JQuery

// 38 ▲, 38 ▲, 40 ▼, 40 ▼, 37 ◄, 39 ►, 37 ◄, 39 ►, 66 B, 65 A
const codeKey = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65]
const codeVal = ['▲', '▲', '▼', '▼', '◄', '►', '◄', '►', '<strong>B</strong>', '<strong>A</strong>']
let index = 0
let found = false

export default {

  init () {
    function isCode (which) {
      if (which === codeKey[index]) {
        $('#konami').append(codeVal[index])
        index++
      } else {
        index = 0
        $('#konami').html('')
      }

      return index === codeKey.length
    }

    function easterEgg () {
      found = true
      $('#konami').html('<a href="https://fr.wikipedia.org/wiki/Code_Konami" style="margin-left: 10px; color: black; font-size: 0.5em; font-family: \'Press Start 2P\', cursive; text-decoration: none;;">Code Konami</a>')
    }

    $('body').keydown(function (eventObject) {
      console.log(eventObject.which)

      if (!found) {
        if (isCode(eventObject.which)) {
          easterEgg()
        }
      }
    })
    $('body').append('<div style="position: fixed; bottom: 0; left: 0" id="konami"></div>')
    console.log('►►◄◄▲▲▼▼BA')
  }
}
