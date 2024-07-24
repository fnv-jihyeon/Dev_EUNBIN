<template>
  <div>
    <p>로그인</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  mounted() {
    // URL 해시에서 파라미터 추출
    const hash = window.location.hash.substring(1);
    const params = new URLSearchParams(hash);
    const accessToken = params.get('access_token');

    if (accessToken) {
      console.log("Extracted Access Token:", accessToken);

      // 액세스 토큰을 localStorage에 저장
      localStorage.setItem('naver_access_token', accessToken);

      // 서버로 액세스 토큰 전송
      axios.post('http://localhost:3000/api/naverlogin', { accessToken })
          .then(response => {
            console.log('로그인 성공:', response.data);

            // 성공적으로 로그인한 후 홈 페이지로 리디렉션
            this.$router.push('/');
          })
          .catch(error => {
            console.error('로그인 오류:', error.response ? error.response.data : error.message);
          });
    }
  }
};
</script>
