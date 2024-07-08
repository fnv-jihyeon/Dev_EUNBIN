<template>
  <div class="login-container">
    <h1 class="mb-4">Login</h1>
    <form @submit.prevent="login"> <!--리로드 방지 prevent 사용-->
      <div class="mb-3">
        <input type="text" v-model="id" class="form-control" placeholder="아이디를 입력하세요" required>
      </div>
      <div class="mb-3">
        <input type="password" v-model="password" class="form-control" placeholder="비밀번호를 입력하세요" required>
      </div>
      <button type="submit" class="btn btn-primary">로그인</button> <!--button의 기본값 submit. type 생략 가능 -->
    </form>
  </div>
</template>

<script>
import { authenticateUser } from '@/components/services/UserService'; //js경로 주의

export default {
  data() {
    return {
      id: '', 
      password: '' 
    };
  },
  methods: {
    login() {
      const user = authenticateUser(this.id, this.password);
      if (user) {
        alert('로그인 성공!');
        this.id = ''; //로그인 성공 후에 id, pw 초기화되도록
        this.password = '';
      } else {
        alert('로그인 실패: 다시 시도해주세요');
        this.id = ''; //실패하더라도 id, pw 초기화되도록
        this.password = '';
      }
    },

  }
};
</script>

<style scoped>

.login-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #fff;
  font-family: NanumSquareNeo;
}

h1{
  color: #4C64D9;
}

input{
  width: 100%;
  height: 48px;
  padding: 0 10px;
  box-sizing: border-box;
  margin-bottom: 16px;
  border: none;
  border-bottom: 1px solid #CCC;
  font-family: NanumSquareNeo;
}

button{
  width: 100%;
  height: 48px;
  padding: 0 10px;
  color: #fff;
  box-sizing: border-box;
  margin-bottom: 16px;
  border-radius: 6px;
  background-color: #4C64D9;
  border: none;
  font-family: NanumSquareNeo;
} 

</style>