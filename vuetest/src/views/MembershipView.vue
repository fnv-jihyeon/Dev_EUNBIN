<template>
  <div class="membership-container">
    <h1 class="mb-4">회원가입</h1>
    <form @submit.prevent="register">
      <div class="mb-3">
        <input type="text" v-model="id" class="form-control" placeholder="사용하실 아이디를 입력하세요" required pattern="[A-Za-z0-9]+" title="아이디는 영어와 숫자만 입력 가능합니다."> <!-- 숫자만은 불가능하게 수정해야함-->
        <button type="button" class="btn btn-secondary" @click="checkIdAvailability">중복 확인</button>
      </div>
      <div class="mb-3">
        <input type="password" v-model="password" class="form-control" placeholder="사용하실 비밀번호를 입력하세요" required>
      </div>
      <div class="mb-3">
        <input type="text" v-model="name" class="form-control" placeholder="이름을 입력하세요" required>
      </div>
      <div class="mb-3">
        <input type="text" v-model="number" class="form-control" placeholder="전화번호를 입력하세요" required pattern="\d+" title="전화번호는 숫자만 입력 가능합니다.">
      </div>
      <div class="mb-3">
        <input type="email" v-model="email" class="form-control" placeholder="이메일을 입력하세요" required>
      </div>
      <button type="submit" class="btn btn-secondary">회원가입</button>
    </form>
  </div>
</template>

<script>
import { registerUser, checkDuplicateId } from '@/components/services/UserService';

export default {
  data() {
    return {
      id: '',
      password: '',
      name: '',
      number: '',
      email: '',
      isIdChecked: false, 
    };
  },
  methods: {
    async register() {
      // 비밀번호 유효성 검사
      if (!this.isPasswordValid(this.password)) {
        alert('비밀번호는 영어 대문자, 소문자, 특수문자 중 최소 두 가지 이상을 포함해야 합니다.');
        return;
      }

      // 입력 값이 모두 채워져 있는지 확인 + 중복 확인 필수 
      if (this.id && this.password && this.name && this.number && this.email) {
        if (!this.isIdChecked) {
          alert('아이디 중복 확인을 해주세요.');
          return;
        }

        // 회원가입 처리
        registerUser(this.id, this.password, this.name, this.number, this.email);
        alert('회원가입 성공!');
        // 폼 초기화
        this.id = '';
        this.password = '';
        this.name = '';
        this.number = '';
        this.email = '';
        this.isIdChecked = false; // 중복 확인 상태 초기화에 필요
      } else {
        alert('모든 필드를 입력하세요.');
      }
    },
    isPasswordValid(password) {
      // 비밀번호가 영어 대문자, 소문자, 특수문자 중 최소 두 가지 이상을 포함하는지 검사
      const hasUpperCase = /[A-Z]/.test(password);
      const hasLowerCase = /[a-z]/.test(password);
      const hasSpecialChar = /[!@#$%^&*()\-_=+{};:,<.>]/.test(password);

      // 최소 두 가지 이상을 포함해야 유효성을 만족
      return (
        (hasUpperCase && hasLowerCase) ||
        (hasUpperCase && hasSpecialChar) ||
        (hasLowerCase && hasSpecialChar)
      );
    },
    async checkIdAvailability() {
      if (!this.id) {
        alert('아이디를 입력하세요.');
        return;
      }

      const isDuplicate = await checkDuplicateId(this.id);
      if (isDuplicate) {
        alert('이미 사용 중인 아이디입니다. 다른 아이디를 입력해 주세요.');
        this.isIdChecked = false;
      } else {
        alert('사용 가능한 아이디입니다.');
        this.isIdChecked = true;
      }
    }
  }
};
</script>

<style scoped>
.membership-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #fff;
  font-family: NanumSquareNeo;

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
}

/* 하단 input type = number일 때 표시되는 화살표 삭제 코드 */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>