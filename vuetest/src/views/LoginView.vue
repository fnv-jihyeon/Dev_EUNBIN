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
      <button type="button" @click="register" class="btn btn-secondary">회원가입</button>  <!--현재는 회원가입 버튼 누르면 그 자체가 제출되어서 회원가입 되도록 -->
    </form>
  </div>
</template>

<script>
import { authenticateUser, registerUser } from '@/components/services/UserService'; //js경로 주의

export default {
  data() {
    return {
      id: '', 
      password: '' //로그인, 회원가입 버튼 눌렀을 경우 폼 초기화되도록 설계 필요.
    };
  },
  methods: {
    login() {
      const user = authenticateUser(this.id, this.password);
      if (user) {
        alert('로그인 성공!');
      } else {
        alert('로그인 실패: 잘못된 아이디나 비밀번호');
      }
    },
    register() {
      registerUser(this.id, this.password);
      alert('회원가입 성공!');
    }
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
}
/*
button{
  background-color: #686a6849; 
  color: #000000; 
  border: none;
  padding: 10px 20px;
  text-decoration-line: none;
  font-size: 10px;
  border-radius: 5px;
  height: 30px; 
  min-width: 40px; 
  vertical-align: middle; 
  margin-right: 10px;
} 
*/
</style>