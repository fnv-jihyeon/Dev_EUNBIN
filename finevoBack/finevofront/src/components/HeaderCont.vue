<template>
  <v-container>
    <v-card>
      <v-tabs>
        <v-tab @click="$router.push('/login')">로그인</v-tab>
        <v-tab @click="$router.push('/admin')">관리자</v-tab>
        <v-tab @click="$router.push('/membership')">회원가입</v-tab>
        <v-tab @click="$router.push('/mypage')" v-if="isLoggedIn">마이페이지</v-tab>
        <v-tab v-if="isLoggedIn" disabled>{{ userName }}<br>님 환영합니다!</v-tab>
        <v-btn v-if="isLoggedIn" type="button" color="primary" @click="logout">로그아웃</v-btn>
      </v-tabs>
    </v-card>
  </v-container>
</template>

<script>
import axios from 'axios';
import router from "@/router";

export default {
  data() {
    return {
      userName: '',
    };
  },
  computed: {
    isLoggedIn() {
      return !!this.userName;
    },
  },
  created() { //computed  => 계산된 값을 출력하는 용도 cf.watch => 조건 충족 시 함수 실행을 위한 트리거
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const response = await axios.get('/api/user/info');
        if (response.status === 200 && response.data) {
          this.userName = response.data.name;
          this.email = response.data.email;
        }
      } catch (error) {
        console.error('사용자 정보를 가져오는 중 오류가 발생했습니다:', error);
      }
    },
    async logout() {
      try {
        const response = await axios.post('/api/logout');
        alert('로그아웃 성공!');
        router.go(0); //사용자 이름 출력 위한 페이지 리로드
        console.log('서버 응답:', response.data);
        //this.$router.push({name: 'home'})  // 메인으로 이동
      } catch (error) {
        console.error('로그아웃 실패:', error);
        alert('로그아웃에 실패했습니다.');
      }
    }
  },
};
</script>

<style lang="scss">
a {
  text-decoration: none;
  color: black;
}
</style>