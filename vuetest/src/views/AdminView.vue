<template>
  <div class="admin-container">
    <h1>사용자 관리</h1>
    <div class="table-header">
      <div>
        <button @click="refreshUsers">전체보기</button>
        <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요">
        <button @click="filterUsers">검색</button>
      </div>
    </div>
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
        <tr v-for="user in filteredUsers" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.password }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.tel }}</td>
          <td>{{ user.email }}</td>
          <td><button @click="deleteUser(user.id)" class="btnX">X</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getAllUsers, deleteUser, filterUsers } from '@/components/services/UserService';

export default {
  data() {
    return {
      users: [],
      searchQuery: '',
      filteredUsers: []
    };
  },
  created() {
    this.refreshUsers();
  },
  methods: {
    deleteUser(id) {
      deleteUser(id);
      this.refreshUsers();
    },
    filterUsers() {
      this.filteredUsers = filterUsers(this.users, this.searchQuery);
    },
    refreshUsers() {
      this.users = getAllUsers();
      this.filteredUsers = this.users;
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
}

h1 {
  color: #4C64D9;
}

.table {
  border: 1px #a39485 solid;
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  text-align: center;
}

.table-header {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

td {
  border-bottom: 1px solid rgba(0, 0, 0, .1);
  background: #fff;
  font-size: 12px;
}

th {
  background: #d4d8ed;
}

button {
  background: #d4d8ed;
  border-radius: 5px;
  border: none;
  font-family: NanumSquareNeo;
}

input {
  border: none;
  color: #000000;
  border-bottom: 1px solid #CCC;
  text-align: center;
  font-family: NanumSquareNeo;
  margin-right: 10px;
}
</style>