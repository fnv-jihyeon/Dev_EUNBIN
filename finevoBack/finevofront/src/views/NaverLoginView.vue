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

              localStorage.removeItem('naver_access_token');
              this.$router.push('/');
            })
            .catch((error) => {
              // 서버에서 오류 메시지를 읽어옴
              const errorMessage = error.response?.data?.error || "알 수 없는 오류가 발생했습니다.";

              // 오류 메시지를 alert로 표시
              alert(errorMessage);
              // '기존에 회원가입한 이력이 없습니다.' 오류 메시지가 포함되어 있는 경우
              if (errorMessage.includes("기존에 회원가입한 이력이 없습니다")) {
                this.$router.push('/membership');
              }
            });
      }
    }
  }
};
</script>
