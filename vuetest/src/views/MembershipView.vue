<template>
    <div class="membership-container">
      <h1 class="mb-4">회원가입</h1>
      <form @submit.prevent="membership"> <!--리로드 방지 prevent 사용 실행해본결과 수정 필요 제출 후 텍스트가 폼에 유지되는 문제-->
        <div class="mb-3">
          <input type="text" v-model="id" class="form-control" placeholder="사용하실 아이디를 입력하세요" required>
          <!--
          <button type="submit" class="btn btn-primary">중복확인</button>
          -->
        </div>
        <div class="mb-3">
          <input type="password" v-model="password" class="form-control" placeholder="사용하실 비밀번호를 입력하세요" required>
        </div>
        <div class="mb-3">
            <input type="name" v-model="name" class="form-control" placeholder="이름을 입력하세요" required>
          </div>
          <div class="mb-3">
            <input type="number" v-model="number" class="form-control" placeholder="전화번호를 입력하세요" required>
          </div>
          <div class="mb-3">
            <input type="email" v-model="email" class="form-control" placeholder="이메일을 입력하세요" required>
          </div>
        <button type="button" @click="register" class="btn btn-secondary">회원가입</button>
      </form>
    </div>
  </template>
  
  <script>
  
  </script>
  
  <style scoped>
  .membership-container {
    max-width: 400px;
    margin: 100px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background: #fff;
  }
  /* 하단 input type = number일 때 표시되는 화살표 삭제 코드 */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
  </style>
  