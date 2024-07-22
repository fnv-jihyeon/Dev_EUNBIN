<template>
  <v-container>
    <v-card>
      <v-card-title>마이페이지</v-card-title>
      <v-card-text>
        <div v-if="user"><br>
          <p><strong>▪️이름:</strong> {{ user.name }}</p><br>
          <p><strong>▪️아이디 :</strong> {{ user.userId }}</p><br>
          <p><strong>▪️이메일 :</strong> {{ user.email }}</p><br>

          <v-row>
            <v-col cols="12" sm="6">
              <v-form ref="editForm" v-if="editing" @submit.prevent="saveChanges">
                <v-text-field v-model="editedName" label="새로운 이름" required></v-text-field>
                <v-text-field v-model="editedEmail" label="새로운 이메일" required></v-text-field>
                <v-btn type="submit" color="primary">저장</v-btn>
                <v-btn type="button" color="error" @click="cancelEdit">취소</v-btn>
              </v-form>
              <v-btn v-else @click="startEdit" color="primary">정보 수정</v-btn>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12" sm="6">
              <v-form ref="passwordForm" v-if="changingPassword" @submit.prevent="changePassword">
                <v-text-field v-model="currentPassword" label="현재 비밀번호" type="password" required></v-text-field>
                <v-text-field v-model="newPassword" label="새로운 비밀번호" type="password" required></v-text-field>
                <v-text-field v-model="confirmPassword" label="새로운 비밀번호 확인" type="password" required></v-text-field>
                <v-btn type="submit" color="primary">비밀번호 변경</v-btn>
                <v-btn type="button" color="error" @click="cancelPasswordChange">취소</v-btn>
              </v-form>
              <v-btn v-else @click="startPasswordChange" color="primary">비밀번호 변경</v-btn>
            </v-col>
          </v-row>

        </div>
        <div v-else>
          <p>사용자 정보를 불러오는 중입니다...</p>
        </div>

      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: null,
      editing: false,
      changingPassword: false,
      editedName: '',
      editedEmail: '',
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    };
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const response = await axios.get('/api/user/info');
        if (response.status === 200 && response.data) {
          this.user = response.data;
        }
      } catch (error) {
        console.error('사용자 정보를 가져오는 중 오류가 발생했습니다:', error);
      }
    },
    startEdit() {
      this.editing = true;
      this.editedName = this.user.name;
      this.editedEmail = this.user.email;
    },
    async saveChanges() {
      try {
        const response = await axios.put('/api/user/info', {
          name: this.editedName,
          email: this.editedEmail,
        });
        if (response.status === 200 && response.data) {
          this.user.name = this.editedName;
          this.user.email = this.editedEmail;
          this.editing = false;
          console.log('사용자 정보가 성공적으로 업데이트 되었습니다.');
        }
      } catch (error) {
        console.error('사용자 정보를 업데이트하는 중 오류가 발생했습니다:', error);
      }
    },
    cancelEdit() {
      this.editing = false;
    },
    startPasswordChange() {
      this.changingPassword = true;
    },
    async changePassword() {
      if (this.newPassword !== this.confirmPassword) {
        alert('새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
        return;
      }

      try {
        const response = await axios.put('/api/user/changepw', {
          currentPassword: this.currentPassword,
          newPassword: this.newPassword
        });

        if (response.status === 200) { // 200 = OK. 성공적인 요청 수행
          alert('비밀번호가 성공적으로 변경되었습니다.');
          this.changingPassword = false;
          this.currentPassword = '';
          this.newPassword = '';
          this.confirmPassword = '';
        } else {
          alert('비밀번호 변경 중 오류가 발생했습니다.');
        }
      } catch (error) {
        console.error('비밀번호 변경 중 오류가 발생했습니다:', error);
        alert('비밀번호 변경 중 오류가 발생했습니다.');
      }
    },
    cancelPasswordChange() {
      this.changingPassword = false;
      this.currentPassword = '';
      this.newPassword = '';
      this.confirmPassword = '';
    }
  },
};
</script>

<style lang="scss">
</style>
