<!--
A fully spec-compliant TodoMVC implementation
https://todomvc.com/
-->

<script>
const STORAGE_KEY = 'vue-todomvc' //로컬에 저장할 키 값

const filters = { //할 일 목록을 필터링하는 객체 
  all: (todos) => todos, //모든 일 반환
  active: (todos) => todos.filter((todo) => !todo.completed), //완료되지 않은 일 반환
  completed: (todos) => todos.filter((todo) => todo.completed) //완료된 일 
}

export default { //앱 초기 상태의 데이터
  data: () => ({ 
    todos: JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]'), //로컬에서 할 일 목록 초기화
    editedTodo: null, //편집 중인 할 일 
    visibility: 'all' // 초기 필터링 옵션
  }),

  watch: { //할 일 목록 변경 시 로컬에 반영
    todos: {
      handler(todos) { //로컬에 할 일 반영 
        localStorage.setItem(STORAGE_KEY, JSON.stringify(todos))
      },
      deep: true //객체의 깊은 변경 감지 
    }
  },

  mounted() { //vue 인스턴스가 마운트된 후 실행
    window.addEventListener('hashchange', this.onHashChange) //url 해시 변경 감지
    this.onHashChange() //초기 해시 값에 따라 보여지는 할 일 필터링 설정
  },

  computed: { //계산된 속성 정의
    filteredTodos() { //현재 보이는 할 일 목록 반환
      return filters[this.visibility](this.todos)
    },
    remaining() { //완료되지 않은 할 일의 개수 반환
      return filters.active(this.todos).length
    }
  },

  methods: {
    toggleAll(e) { //전체 할 일의 완료 상태 
      this.todos.forEach((todo) => (todo.completed = e.target.checked))
    },

    addTodo(e) { //새로운 할 일 추가
      const value = e.target.value.trim()
      if (!value) { //입력값이 없으면 리턴
        return
      }
      this.todos.push({ //할 일 목록에 새로운 할 일 추가
        id: Date.now(), 
        title: value,
        completed: false
      })
      e.target.value = '' //입력 필드 비움
    },

    removeTodo(todo) {
      this.todos.splice(this.todos.indexOf(todo), 1)
    },

    editTodo(todo) { //할일 편집 모드
      this.beforeEditCache = todo.title //편집 전 원래 할 일 목록 저장
      this.editedTodo = todo //편집 중인 할 일 객체 생성
    },

    doneEdit(todo) { //할 일 편집 완료
      if (!this.editedTodo) { //편집 중인 할 일이 없으면 종료
        return
      }
      this.editedTodo = null //편집 종료
      todo.title = todo.title.trim() //할 일 제목 공백 제거
      if (!todo.title) {
        this.removeTodo(todo) //할 일 목록이 없다면 삭제
      }
    },

    cancelEdit(todo) { //편집 취소
      this.editedTodo = null
      todo.title = this.beforeEditCache //원래 할 일 제목으로 복원
    },

    removeCompleted() { //완료된 할 일 삭제
      this.todos = filters.active(this.todos)
    },

    onHashChange() { //url 해시값 변경 시
      var visibility = window.location.hash.replace(/#\/?/, '') //해시값에서 보이는 할 일 옵션 추출
      if (filters[visibility]) {
        this.visibility = visibility //유효해 보이는 할 일 옵션 설정
      } else {
        window.location.hash = ''
        this.visibility = 'all'
      }
    }
  }
}
</script>

<template>
  <section class="todoapp">
    <header class="header">
      <h1>Todos</h1>
      <input
        class="new-todo"
        autofocus
        placeholder="What needs to be done?"
        @keyup.enter="addTodo"
      >
    </header>
    <section class="main" v-show="todos.length">
      <input
        id="toggle-all"
        class="toggle-all"
        type="checkbox"
        :checked="remaining === 0"
        @change="toggleAll"
      >
      <label for="toggle-all">Mark all as complete</label>
      <ul class="todo-list">
        <li
          v-for="todo in filteredTodos"
          class="todo"
          :key="todo.id"
          :class="{ completed: todo.completed, editing: todo === editedTodo }"
        >
          <div class="view">
            <input class="toggle" type="checkbox" v-model="todo.completed">
            <label @dblclick="editTodo(todo)">{{ todo.title }}</label>
            <button class="destroy" @click="removeTodo(todo)"></button>
          </div>
          <input
            v-if="todo === editedTodo"
            class="edit"
            type="text"
            v-model="todo.title"
            @vue:mounted="({ el }) => el.focus()"
            @blur="doneEdit(todo)"
            @keyup.enter="doneEdit(todo)"
            @keyup.escape="cancelEdit(todo)"
          >
        </li>
      </ul>
    </section>
    <footer class="footer" v-show="todos.length">
      <span class="todo-count">
        <strong>{{ remaining }}</strong>
        <span>{{ remaining === 1 ? ' item' : ' items' }} left</span>
      </span>
      <ul class="filters">
        <li>
          <a href="#/all" :class="{ selected: visibility === 'all' }">All</a>
        </li>
        <li>
          <a href="#/active" :class="{ selected: visibility === 'active' }">Active</a>
        </li>
        <li>
          <a href="#/completed" :class="{ selected: visibility === 'completed' }">Completed</a>
        </li>
      </ul>
      <button class="clear-completed" @click="removeCompleted" v-show="todos.length > remaining">
        Clear completed
      </button>
    </footer>
  </section>
</template>

<style>
@import "https://unpkg.com/todomvc-app-css@2.4.1/index.css";
</style>