module.exports = {

  chainWebpack: (config) => { //컴파일 플래그 알림 지우기
    config.plugin('define').tap((definitions) => {
      Object.assign(definitions[0], {
        __VUE_OPTIONS_API__: 'true',
        __VUE_PROD_DEVTOOLS__: 'false',
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
      })
      return definitions
    })
  },
  outputDir: "../src/main/resources/static",

  devServer: {
    port: 3000,
    proxy: {
      'api/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws:false, //웹소켓 안 쓰므로 false
      }
    }
  },

  pluginOptions: {
    vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		}
  }

};
