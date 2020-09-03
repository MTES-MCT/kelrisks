// vue.config.js
module.exports = {
    pages: {
        index: {
            entry: 'src/main.js',
            template: 'public/index.html',
            filename: 'index.html',
            title: 'Evaluez simplement et rapidement les risques de votre bien - kelrisks.data.gouv.fr'
        }
    },
    configureWebpack: {
        resolve: {
            alias: {
                // '@': 'src',
            }
        }
    }
};