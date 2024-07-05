<template>
    <div class="admin-container">
      <h1>사용자 관리</h1>
      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>PW</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.password }}</td>
            <td><button @click="deleteUser(user.id)" class="btn btn-danger">삭제</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import { getAllUsers, deleteUser } from '@/components/services/UserService'; // 외부 스크립트 파일로 연결해서 작성. 경로에 주의
  
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
  }
  </style>
  