<template>
  <HeaderCont />
  <router-view />
  <FooterCont />
</template>

<style lang="scss">
</style>

<script>
// @ is an alias to /src
import HeaderCont from "./components/HeaderCont.vue";
import FooterCont from "./components/FooterCont.vue";
export default {
  components: {
    HeaderCont,
    FooterCont,
  },
};
</script>
