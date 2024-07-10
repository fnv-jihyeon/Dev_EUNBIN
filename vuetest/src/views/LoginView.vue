<template>
  <div class="login-container">
    <h1 class="mb-4">Login</h1>
    <form @submit.prevent="login">
      <div class="mb-3">
        <input type="text" v-model="id" @input="validateId" class="form-control" placeholder="아이디를 입력하세요" required>
      </div>
      <div class="mb-3">
        <input type="password" v-model="password" class="form-control" placeholder="비밀번호를 입력하세요" required>
      </div>
      <button type="submit" class="btn btn-primary">로그인</button>
    </form>
    <div class="mb-3">
      <button @click="findId" class="btn btn-secondary">아이디 찾기</button>
      <button @click="findPassword" class="btn btn-secondary">비밀번호 찾기</button>
    </div>
  </div>
</template>

<script>
import { authenticateUser, getAllUsers } from '@/components/services/UserService';
import mitt from '@/mitt';

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
        mitt.emit('login-success'); // 로그인 성공 시 이벤트 발생
        alert('로그인되었습니다');
        this.id = '';
        this.password = '';
      } else {
        alert('로그인 실패: 다시 시도해주세요');
        this.id = '';
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
    },
    validateId(event) {
      const regex = /^[a-zA-Z0-9]*$/; // 영숫자만 허용하는 정규 표현식
      if (!regex.test(event.target.value)) {
        event.target.value = this.id; // 이전 값으로 되돌리도록
      } else {
        this.id = event.target.value;
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
