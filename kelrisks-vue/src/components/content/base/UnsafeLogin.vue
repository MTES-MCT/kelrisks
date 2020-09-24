<template>
    <div v-if="!loggedIn"
         id="unsafeLogin_wrapper">
        <section class="section section-white">
            <h1>Usage réservé à l'équipe ERRIAL</h1>
            <kr-input label="Mot de passe"
                      name="password"
                      @delayedquery="checkPassword"/>
        </section>
    </div>
</template>

<script>
import KrInput from "@/components/ui/KrInput";

export default {
    name: "UnsafeLogin",
    components: {KrInput},
    data: () => ({
        PASSWORD_HASH: -1403490666,
        loggedIn: false
    }),
    methods: {
        hash (string) {
            let hash = 0, i, chr;
            for (i = 0; i < string.length; i++) {
                chr = string.charCodeAt(i);
                hash = ((hash << 5) - hash) + chr;
                hash |= 0;
            }
            return hash;
        },
        isCorrect (password) {
            return this.hash(password) === this.PASSWORD_HASH
        },
        checkPassword (value) {
            this.loggedIn = this.isCorrect(value)
            if (this.loggedIn) sessionStorage.setItem('ERRIAL_loggedIn', value)
        }
    },
    mounted () {
        let ERRIAL_loggedIn = sessionStorage.getItem('ERRIAL_loggedIn')
        if (ERRIAL_loggedIn && ERRIAL_loggedIn !== 'undefined') {
            this.checkPassword(ERRIAL_loggedIn)
        }
    }
}
</script>

<style scoped>
#unsafeLogin_wrapper {
	backdrop-filter : blur(2px);
	background      : rgba(0, 0, 0, 0.5);
	height          : 100%;
	padding         : 350px 200px;
	position        : fixed;
	top             : 0;
	width           : 100%;
	z-index         : 999999;
}
</style>