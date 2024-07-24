<template>
  <v-container>
    <div id="naverIdLogin"></div>
    <button type="button" @click="logout">로그아웃</button>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      naverLogin: null
    };
  },
  mounted() {
    // 네이버 로그인 SDK 초기화
    this.naverLogin = new window.naver.LoginWithNaverId({
      clientId: "50PITAaw8WSkVynGQgS6",
      callbackUrl: "http://localhost:3000/login/oauth2/code/naver",
      isPopup: false,
      loginButton: {
        color: "green",
        type: 3,
        height: 60
      }
    });

    this.naverLogin.init();

    this.naverLogin.getLoginStatus((status) => {
      if (status) {
        console.log("Login Status:", status);
        console.log("User Info:", this.naverLogin.user);
        console.log("Access Token:", this.naverLogin.accessToken.accessToken);

        // 로그인 성공 후 액세스 토큰을 localStorage에 저장
        localStorage.setItem('naver_access_token', this.naverLogin.accessToken.accessToken);
      }
    });
  },
  methods: {
    logout() {
      const accessToken = localStorage.getItem('naver_access_token');
      if (accessToken) {
        console.log("Logout Access Token:", accessToken);

        // 네이버 로그아웃 요청
        axios.post('http://localhost:3000/api/naverlogout', { accessToken })
            .then((response) => {
              console.log("로그아웃:", response.data);

              // 로그아웃 후 localStorage에서 액세스 토큰 제거
              localStorage.removeItem('naver_access_token');
              this.$router.push('/');
            })
            .catch((error) => {
              console.error("로그아웃 중 오류가 발생하였습니다:", error.response ? error.response.data : error.message);
            });
      }
    }
  }
};
</script>
