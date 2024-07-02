<template>
    <div>
      <p>입력해보세요</p>
      <!-- v-bind는 템플릿을 통해 변수를 변경시킬 수 없음 (abbr값은 변경 안됨 - 일방작용)-->
      <input type="text" v-bind:value="abbr" />
      <!-- v-model은 템플릿을 통해 변수를 변경시킬 수 있음 (normal값은 변경 됨 - 양방향 작용)-->
      <input type="text" v-model="normal" />
      <hr />
      <abbr :title="normal">{{ abbr }}</abbr>
      <button :disabled="isButtonDisabled">버튼</button>
      <button @click="count++">숫자 세기: {{ count }}</button>
      <p v-if="seen">볼 수 있는 상태</p>
  
    </div>
  </template>
  
  <script>
  import { ref } from 'vue'
  
  export default {
    setup() {
      const abbr = ref('수정 불가 값')
      const normal = ref('수정 가능 값')
      const isButtonDisabled = ref(false)  // isButtonDisabled 변수 추가
      return {
        abbr,
        normal,
        isButtonDisabled
      }
    },
    data() {
      return {
        count: 0,
        seen:true
        
      }
    }
  }
  </script>
  
  <style scoped>
  button {
    font-weight: bold;
  }
  </style>