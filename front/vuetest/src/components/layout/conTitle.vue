<template>
    <div class="cont__title">
      <h2>{{ title }}</h2>
    </div>
  </template>
  <script>
  export default {
    props: {
      title: String,
    },
  };
  </script>
  <style lang="scss" scoped>
  .cont__title > h2 {
    text-align: center;
    font-size: 5vw;
    text-transform: uppercase;
    font-weight: 900;
    padding: 5%;
  }
  </style>
  