<template>
  <font-awesome-icon icon="truck-moving"
                     id="egg"
                     size="3x"/>
</template>

<script>
import JQuery from 'jquery'

let $ = JQuery

const codeKey = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65]
const codeVal = ['▲', '▲', '▼', '▼', '◄', '►', '◄', '►', '<strong>B</strong>', '<strong>A</strong>']
let index = 0
let found = false

export default {
  name: 'Konami',
  methods: {
    init () {
      let jqBody = $('body')
      jqBody.keydown((eventObject) => {
        // console.log(eventObject.which)
        // console.log(this)
        if (!found) {
          if (this.isCode(eventObject.which)) {
            this.easterEgg()
          }
        }
      })
      jqBody.append('<div style="position: fixed; bottom: 0; left: 0" id="konami"></div>')
      console.log('►►◄◄▲▲▼▼BA')
    },
    easterEgg () {
      found = true
      $('#konami').html('<a href="https://fr.wikipedia.org/wiki/Code_Konami" style="margin-left: 10px; color: black; font-size: 0.5em; font-family: \'Press Start 2P\', cursive; text-decoration: none;;">Code Konami</a>')
      $('#egg')
        .queue(function (next) {
          $(this).css('color', '#555555')
          next()
        })
        .animate({left: '100%'}, 5000, 'swing')
    },
    isCode (which) {
      if (which === codeKey[index]) {
        $('#konami').append(codeVal[index])
        index++
      } else {
        index = 0
        $('#konami').html('')
      }

      return index === codeKey.length
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style scoped>
  svg {
    position   : fixed;
    left       : 0;
    bottom     : 0;
    transition : all 1s;
    color      : #55555500;
  }
</style>
