import Vue from 'vue'
import Router from 'vue-router'
import Kelrisks from '@/components/Kelrisks'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/:codeAvis',
      props: true,
      name: 'Kelrisks TinyURL',
      component: Kelrisks
    },
    {
      path: '/',
      name: 'Kelrisks',
      component: Kelrisks
    }
  ]
})
