<template>
  <div class="admin-container">
    <h1>사용자 관리</h1>
    <!--
    <div>
      <input type="text" placeholder="검색어를 입력하세요">
      <button>검색</button>
    </div>
    -->
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>PW</th>
          <th>이름</th>
          <th>전화번호</th>
          <th>이메일</th>
          <th>관리</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.password }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.number }}</td>
          <td>{{ user.email }}</td>
          <td><button @click="deleteUser(user.id)" class="btn btn-danger">X</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getAllUsers, deleteUser } from '@/components/services/UserService';

export default {
  data() {
    return {
      users: []
    };
  },
  created() {
    this.users = getAllUsers();
  },
  methods: {
    deleteUser(id) {
      deleteUser(id);
      this.users = getAllUsers();
    }
  }
};
</script>

<style scoped>
.admin-container {
  max-width: 600px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #fff;
  font-family: NanumSquareNeo;

  h1{
    color: #4C64D9;
  }
table{
  border: 1px #a39485 solid;
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  text-align: center;
}
td {
  border-bottom: 1px solid rgba(0,0,0,.1);
  background: #fff;
  font-size: 12px;
}
th{
  background: #d4d8ed;

}

button{
  background: #d4d8ed;
  border-radius: 5px;
  border: none;
} 
input{
  border: none;
  color: #fff;
  border-bottom: 1px solid #CCC;
  text-align: center;

}
}
</style>
