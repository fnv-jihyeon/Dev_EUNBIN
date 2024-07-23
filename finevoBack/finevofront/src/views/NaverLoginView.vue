<template>
  <v-container>
    <div id="naverIdLogin" @click="sendTokenToServer"></div>
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

        this.sendTokenToServer(this.naverLogin.accessToken.accessToken);
      } else {
        console.log("Callback 처리에 실패했습니다.");
      }
    });
  },
  methods: {
    sendTokenToServer(accessToken) {
      axios.post('http://localhost:3000/api/naverlogin', { accessToken })
          .then((response) => {
            console.log("서버 응답:", response.data);
          })
          .catch((error) => {
            console.error("토큰 오류:", error);
          });
    }
,
    logout() {
      const accessToken = this.naverLogin.accessToken.accessToken;
      axios.post('http://localhost:3000/api/naverlogout', { accessToken })
          .then((response) => {
            console.log("로그아웃:", response.data);
          })
          .catch((error) => {
            console.error("로그아웃 중 오류가 발생하였습니다:", error);
          });
    }

  }
};
</script>
