module.exports = {
  outputDir: "../src/main/resources/static",

  devServer: {
    port: 3000,
    proxy: {
      'api/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws:false,
      }
    }
  },

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    }
  }
};
