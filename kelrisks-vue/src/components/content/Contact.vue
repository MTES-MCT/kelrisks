<template>
  <div class="panel hidden"
       id="contact">
    <font-awesome-icon @click="openCloseContact()"
                       class="close"
                       icon="caret-down"
                       size="lg"
                       v-show="opened"/>
    <font-awesome-icon @click="openCloseContact()"
                       class="close"
                       icon="caret-up"
                       size="lg"
                       v-show="!opened"/>
    <p @click="openCloseContact()"
       class="section__subtitle">Une remarque? Une Erreur?</p>
    <textarea id="contactContent"
              title="Remarque/Suggestion/Problème?"></textarea>
    <kr-input label="Vous souhaitez être recontacté ?"
              name="contactMail"
              placeholder="jean.dupont@gmail.com"/>
    <button @click="sendMail()"
            class="button">Envoyer
    </button>
  </div>
</template>

<script>
import JQuery from 'jquery'
import KrInput from "../ui/KrInput";

let $ = JQuery

export default {
  name: 'Contact',
  components: {KrInput},
  props: {
    timeout: {
      type: Number,
      default: 30
    }
  },
  data: () => ({
    opened: false,
    timesUp: false,
    countDownInstance: null,
    env: {
      basePath: process.env.VUE_APP_PATH,
      startTime: (new Date()).getTime()
    }
  }),
  methods: {
    openCloseContact () {
      if (this.opened) this.closeContact()
      else this.openContact()
    },
    closeContact () {
      $('#contact').addClass('hidden')
      this.opened = false
    },
    openContact () {
      $('#contact').removeClass('hidden')
      this.opened = true
      clearTimeout(this.countDownInstance)
    },
    countDown () {
      this.timesUp = (new Date()).getTime() - this.env.startTime > 1000 * this.timeout
      if (this.timesUp) {
        this.openContact()
      }
    },
    sendMail () {
      fetch(this.env.basePath + 'mail', {
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({content: $('#contactContent').val(), mail: $('#contactMail').val()})
      }).then(stream => stream.json())
              .then(() => {
                this.closeContact()
                $('#contactContent').val('')
                // console.log(value)
              })
    }
  },
  mounted () {
    this.countDownInstance = setTimeout(() => {
      this.countDown()
    }, 1000)
  }
}
</script>

<style scoped>
  #contact {
    transition : bottom 0.33s ease;
    width      : 400px;
    position   : fixed;
    bottom     : -1px;
    right      : 10px;
    padding    : 24px 14px;
    /* pour passer au dessus de '1000', le z-index de leaflet */
    z-index    : 1001;
  }

  #contact.hidden {
    bottom : -235px;
  }

  #contact .close {
    position : absolute;
    top      : 15px;
    right    : 6px;
    cursor   : pointer;
  }

  #contact p.section__subtitle {
    cursor        : pointer;
    margin-bottom : 15px;
    margin-top    : -10px;
  }

  #contact textarea {
    margin-bottom : 10px;
    resize        : none;
  }

  #contact input {
    margin-bottom : 20px;
    resize        : none;
  }
</style>
