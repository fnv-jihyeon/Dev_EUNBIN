<template>
  <v-container class="d-flex justify-center align-center fill-height">
    <v-card class="pa-5" max-width="400">
      <v-card-title class="text-center">로그인</v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid" @submit.prevent="login">
          <v-text-field
              v-model="userId"
              label="아이디"
              required
              prepend-icon="mdi-account"
          ></v-text-field>
          <v-text-field
              v-model="password"
              label="비밀번호"
              type="password"
              required
              prepend-icon="mdi-lock"
          ></v-text-field>
          <v-btn type="submit" color="primary" :disabled="!valid">로그인</v-btn>
          <v-btn text @click="showResetPasswordDialog">비밀번호 재설정</v-btn>
        </v-form>
      </v-card-text>
    </v-card>

    <v-dialog v-model="resetPasswordDialog" max-width="400">
      <v-card>
        <v-card-title class="text-center">비밀번호 재설정</v-card-title>
        <v-card-text>
          <v-form ref="resetPasswordForm" @submit.prevent="resetPassword">
            <v-text-field
                v-model="resetUserId"
                label="아이디"
                required
            ></v-text-field>
            <v-text-field
                v-model="resetEmail"
                label="이메일"
                type="email"
                required
            ></v-text-field>
            <v-text-field
                v-model="resetNewPassword"
                label="새로운 비밀번호"
                type="password"
                required
            ></v-text-field>
            <v-text-field
                v-model="resetConfirmPassword"
                label="새로운 비밀번호 확인"
                type="password"
                required
            ></v-text-field>
            <v-btn type="submit" color="primary">확인</v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  data() {
    return {
      valid: false,
      userId: "",
      password: "",
      resetPasswordDialog: false,
      resetUserId: "",
      resetEmail: "",
      resetNewPassword: "",
      resetConfirmPassword: "",
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/api/login', { userId: this.userId, password: this.password });
        alert('로그인 성공!');
        router.go(0);
        console.log('서버 응답:', response.data);
      } catch (error) {
        console.error('로그인 실패:', error);
        alert('로그인에 실패했습니다.');
      }
    },
    showResetPasswordDialog() {
      this.resetPasswordDialog = true;
    },
    async resetPassword() {
      if (this.resetNewPassword !== this.resetConfirmPassword) {
        alert('새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
        return;
      }

      try {
        const response = await axios.put('/api/user/resetpw', {
          userId: this.resetUserId,
          email: this.resetEmail,
          newPassword: this.resetNewPassword
        });
        if (response.status === 200) {
          alert('비밀번호가 성공적으로 변경되었습니다.');
          this.resetPasswordDialog = false;
          this.resetUserId = "";
          this.resetEmail = "";
          this.resetNewPassword = "";
          this.resetConfirmPassword = "";
        } else {
            alert('비밀번호 재설정 중 오류가 발생했습니다.');
        }
      } catch (error) {
        console.error('비밀번호 재설정 중 오류가 발생했습니다:', error);
        alert('비밀번호 재설정 중 오류가 발생했습니다.');
      }
    }
  }
};
</script>

<style scoped>
.v-card {
  width: 100%;
}
</style>
