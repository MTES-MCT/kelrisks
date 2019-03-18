import Vue from 'vue'
import Router from 'vue-router'
import Kelrisks from '@/components/Kelrisks'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Kelrisks',
      component: Kelrisks
    }
  ]
})
