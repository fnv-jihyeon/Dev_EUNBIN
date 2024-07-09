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
    <div class="mb-3">
      <button @click="findId" class="btn btn-secondary">아이디 찾기</button>
      <button @click="findPassword" class="btn btn-secondary">비밀번호 찾기</button>
    </div>
  </div>
</template>

<script>
import { authenticateUser, getAllUsers } from '@/components/services/UserService'; //js경로 주의
//import { EventBus } from '@/eventBus';

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
        //EventBus.$emit('use-eventbus',alert('로그인 성공!'));
        alert('로그인 성공');
        this.id = ''; //로그인 성공 후에 id, pw 초기화되도록
        this.password = '';
      } else {
        alert('로그인 실패: 다시 시도해주세요');
        this.id = ''; //실패하더라도 id, pw 초기화되도록
        this.password = '';
      }
    },
    findId() {
      const users = getAllUsers();
      const email = prompt('가입한 이메일을 입력하세요');
      const name = prompt('이름을 입력하세요');
      const user = users.find(user => user.email === email && user.name === name);
      if (user) {
        alert(`아이디는 ${user.id} 입니다`);
      } else {
        alert('일치하는 정보가 없습니다');
      }
    },
    findPassword() {
      const users = getAllUsers();
      const id = prompt('아이디를 입력하세요');
      const email = prompt('가입한 이메일을 입력하세요');
      const name = prompt('이름을 입력하세요');
      const user = users.find(user => user.id === id && user.email === email && user.name === name);
      if (user) {
        alert(`비밀번호는 ${user.password} 입니다`);
      } else {
        alert('일치하는 정보가 없습니다');
      }
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
  font-family: NanumSquareNeo;
}

h1 {
  color: #4C64D9;
}

input {
  width: 100%;
  height: 48px;
  padding: 0 10px;
  box-sizing: border-box;
  margin-bottom: 16px;
  border: none;
  border-bottom: 1px solid #CCC;
  font-family: NanumSquareNeo;
}

button {
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

.btn-secondary {
  background-color: #6c757d;
}
</style>
