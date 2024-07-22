<template>
  <v-container class="admin-container">
    <v-row justify="center">
      <v-col cols="12">
        <v-card>
          <v-card-title>
            <v-row>
              <v-col>
                <h1>사용자 관리</h1>
              </v-col>
              <v-col class="text-right">
                <v-text-field v-model="searchQuery" placeholder="검색어를 입력하세요" solo></v-text-field>
              </v-col>
            </v-row>
          </v-card-title>

          <v-data-table
              :headers="headers"
              :items="filteredUsers"
              item-key="id"
              class="overflow-x-auto"
              :items-per-page="-1"
              hide-default-footer>
            <template v-slot:[`item.delete`]="{ item }">
              <v-btn icon @click="deleteUser(item.id)">
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      searchQuery: '',
      headers: [
        { text: 'ID', value: 'id' },
        { text: '사용자 ID', value: 'userId' },
        //{ text: '비밀번호', value: 'password' },
        { text: '이름', value: 'name' },
        { text: '이메일', value: 'email' },
        { text: '관리', value: 'delete' },
      ],
    };
  },
  computed: {
    filteredUsers() {
      if (this.searchQuery.trim() === '') {
        return this.users;
      } else {
        const query = this.searchQuery.toLowerCase();
        return this.users.filter(user =>
            user.name.toLowerCase().includes(query)
        );
      }
    },
  },
  mounted() {
    this.refreshUsers(); // 컴포넌트가 마운트될 때 사용자 데이터 불러옴
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('/api/users');
        this.users = response.data; // 사용자 데이터 업데이트
      } catch (error) {
        console.error('사용자 목록을 불러오는 데 실패했습니다:', error);
      }
    },
    async refreshUsers() {
      await this.fetchUsers();
    },
    filterUsers() {},
    async deleteUser(userId) {
      try {
        await axios.delete(`/api/user/${userId}`);
        this.refreshUsers(); // 오류 해결: 사용자 삭제 후 리프레시
        alert('사용자가 삭제되었습니다.');
      } catch (error) {
        console.error('사용자 삭제에 실패했습니다:', error);
        alert('사용자 삭제에 실패했습니다.');
      }
    }
  }
};
</script>

<style scoped>
.admin-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #fff;

}

h1 {
  color: #4C64D9;
}

.text-right {
  text-align: right;
}

v-data-table-footer{
  display: none;
}
</style>
