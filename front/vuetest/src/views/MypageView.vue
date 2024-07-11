<template>
  <div class="mypage-container">
    <h1 class="mb-4">마이페이지</h1>
    <form @submit.prevent="checkPassword">
      <div class="mb-3">
        <input type="text" v-model="id" class="form-control" placeholder="아이디" required>
      </div>
      <div class="mb-3">
        <input type="password" v-model="password" class="form-control" placeholder="비밀번호" required>
      </div>
      <button type="submit" class="btn btn-primary">비밀번호 확인</button>
    </form>

    <div v-if="isVerified">
      <form @submit.prevent="updateUser">
        <div class="mb-3">
          <input type="text" v-model="name" class="form-control" placeholder="이름" required>
        </div>
        <div class="mb-3">
          <input type="email" v-model="email" class="form-control" placeholder="이메일" required>
        </div>
        <div class="mb-3">
          <input type="tel" v-model="tel" class="form-control" placeholder="전화번호" required>
        </div>
        <div class="mb-3">
          <input type="password" v-model="newPassword" class="form-control" placeholder="새 비밀번호">
        </div>
        <button type="submit" class="btn btn-primary">정보 수정</button>
      </form>
    </div>
  </div>
</template>

<script>
import { getUser, updateUser, authenticateUser } from '@/components/services/UserService';

export default {
  data() {
    return {
      id: '', 
      password: '',
      name: '',
      email: '',
      tel: '',
      newPassword: '',
      isVerified: false
    };
  },
  created() {
    this.loadUserData();
  },
  methods: {
    loadUserData() { // 현재 로그인된 사용자 정보 가져오기
      const user = getUser(); 
      if (user) {  
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.tel = user.tel;
      } else {
        alert('로그인된 사용자가 없습니다.'); 
      }
    },
    checkPassword() {
      const user = authenticateUser(this.id, this.password); // 비밀번호 확인
      if (user) {
        this.isVerified = true; // 비밀번호가 맞으면 정보 수정 가능
        alert('비밀번호가 확인되었습니다.'); 
      } else {
        alert('비밀번호가 틀렸습니다.');
      }
    },
    updateUser() {
      const updatedUser = {
        id: this.id,
        password: this.password, // 기존 비밀번호 사용
        name: this.name,
        email: this.email,
        tel: this.tel
      };

      if (this.newPassword) {
        updatedUser.password = this.newPassword; // 새 비밀번호가 있으면 새 비밀번호로 변경
      }

      const success = updateUser(updatedUser); // 사용자 정보 업데이트
      if (success) {
        alert('정보가 성공적으로 수정되었습니다.');
      } else {
        alert('정보 수정에 실패했습니다.');
      }
    }
  }
};
</script>

<style scoped>
.mypage-container {
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
</style>
