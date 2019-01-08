'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  VUE_APP_PATH: '"http://localhost:8081/"',
  VUE_APP_API_PATH: '"http://localhost:8080/api"'
})
