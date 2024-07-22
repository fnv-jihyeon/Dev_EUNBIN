<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-card class="pa-5" max-width="600">
      <v-card-title class="text-center">회원가입</v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid" @submit.prevent="register">
          <v-text-field
              v-model="userId"
              label="아이디"
              required
              :rules="userIdRules"
              prepend-icon="mdi-account"
          ></v-text-field>
          <v-btn color="primary" @click="checkIdDuplicate">중복확인</v-btn>
          <v-text-field
              v-model="password"
              label="비밀번호"
              type="password"
              required
              :rules="passwordRules"
              prepend-icon="mdi-lock"
          ></v-text-field>
          <v-text-field
              v-model="confirmPassword"
              label="비밀번호 확인"
              type="password"
              required
              :rules="confirmPasswordRules"
              prepend-icon="mdi-lock-check"
          ></v-text-field>
          <v-text-field
              v-model="name"
              label="이름"
              required
              :rules="nameRules"
              prepend-icon="mdi-account"
          ></v-text-field>
          <v-text-field
              v-model="email"
              label="이메일"
              required
              :rules="emailRules"
              prepend-icon="mdi-email"
          ></v-text-field>
          <v-btn type="submit" color="primary" :disabled="!valid">회원가입</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      password: '',
      confirmPassword: '',
      name: '',
      email: '',
      valid: false,
      isIdChecked: false, // 중복 확인 여부 상태
      userIdRules: [
        v => !!v || '아이디를 입력하세요',
        v => /^[a-zA-Z0-9]*$/.test(v) || '아이디는 영문+숫자만 입력 가능합니다.',
        v => !(v && v.length >= 15) || '아이디는 15자 이상 입력할 수 없습니다.'
      ],
      passwordRules: [v => !!v || '비밀번호를 입력하세요'],
      confirmPasswordRules: [v => v === this.password || '비밀번호가 일치하지 않습니다'],
      nameRules: [
        v => !!v || '이름을 입력하세요',
        v => !(v && v.length >= 30) || '이름은 30자 이상 입력할 수 없습니다.',
        v => !/[~!@#$%^&*()_+|<>?:{}]/.test(v) || '이름에는 특수문자를 사용할 수 없습니다.'
      ],
      emailRules: [
        v => !!v || '이메일을 입력하세요',
        v => /.+@.+\..+/.test(v) || '유효한 이메일 주소를 입력하세요'
      ],
      users: [],
      searchQuery: '',
      headers: [
        { text: 'ID', value: 'userId' },
        { text: '이름', value: 'name' },
        { text: '이메일', value: 'email' },
        { text: '관리', value: 'actions', sortable: false },
      ],
    };
  },
  computed: {
    filteredUsers() {
      if (this.searchQuery.trim() === '') {
        return this.users;
      } else {
        const query = this.searchQuery.toLowerCase();
        return this.users.filter(user =>
            user.name.toLowerCase().includes(query)
        );
      }
    },
  },
  mounted() {
    this.refreshUsers(); // 컴포넌트가 마운트될 때 회원 리프레시
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('/api/users');
        this.users = response.data;
      } catch (error) {
        console.error('사용자 목록을 불러오는 데 실패했습니다:', error);
      }
    },
    async refreshUsers() {
      await this.fetchUsers();
    },
    async register() {
      if (!this.isIdChecked) {
        alert('아이디 중복 확인을 해주세요.');
        return;
      }
      try {
        const newUser = {
          userId: this.userId,
          password: this.password,
          name: this.name,
          email: this.email,
        };
        await axios.post('/api/register', newUser);
        alert('회원가입이 완료되었습니다.');
        this.refreshUsers(); // 회원가입 후 리프레시
        this.$refs.form.reset(); // 폼 초기화
        this.isIdChecked = false; // 중복 확인 상태 초기화
      } catch (error) {
        console.error('회원가입에 실패했습니다:', error);
        alert('회원가입에 실패했습니다.');
      }
    },
    async deleteUser(userId) {
      try {
        await axios.delete(`/api/user/${userId}`);
        this.refreshUsers(); // 사용자 삭제 후 리프레시
        alert('사용자가 삭제되었습니다.');
      } catch (error) {
        console.error('사용자 삭제에 실패했습니다:', error);
        alert('사용자 삭제에 실패했습니다.');
      }
    },
    async checkIdDuplicate() {
      try {
        const response = await axios.get(`/api/users/${this.userId}/exists`);
        if (response.data) {
          alert('이미 사용 중인 아이디입니다.');
          this.isIdChecked = false;
        } else {
          alert('사용 가능한 아이디입니다.');
          this.isIdChecked = true;
        }
      } catch (error) {
        console.error('아이디 중복 확인에 실패했습니다:', error);
        alert('아이디 중복 확인에 실패했습니다.');
        this.isIdChecked = false;
      }
    }
  },
};
</script>

<style scoped>
.v-card {
  width: 100%;
}
</style>
