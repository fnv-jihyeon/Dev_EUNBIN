<!--
FLIP list transitions with the built-in <TransitionGroup>.
https://aerotwist.com/blog/flip-your-animations/
-->

<script>
import { shuffle } from 'lodash-es'

const getInitialItems = () => [1, 2, 3, 4, 5] //const 상수 선언 - 재할당 가능
let id = getInitialItems().length + 1 //let 변수 선언 - 재할당 불가능

export default {
  data() {
    return {
      items: getInitialItems()
    }
  },
  methods: {
    insert() { //랜덤 insert 
      const i = Math.round(Math.random() * this.items.length) //Math.random(): 0~1 사이의 소수 난수 생성, items 배열 길이에 곱하여 인덱스를 정수로 반올림
      this.items.splice(i, 0, id++) //items 배열의 i 인덱스 위치에 id 값을 추가하고, id값 1 증가, i = 추가할 요소의 인덱스, 0 = 삭제할 요소의 인덱스, id ++ = 추가할 요소
    },
    reset() { //배열 초기화
      this.items = getInitialItems() //items 배열을 초기 상태로 설정
      id = getInitialItems().length + 1 //id를 초기 상태에 맞게 설정
    },
    shuffle() { //배열을 섞는 메서드
      this.items = shuffle(this.items) //items 배열을 섞은 결과로 업데이트
    },
    remove(item) { //제거
      const i = this.items.indexOf(item) //배열의 인덱스 탐색
      if (i > -1) {
        this.items.splice(i, 1) //배열에서 해당 인덱스 아이템 제거 
      }
    }
  }
}
</script>

<template>
  <button @click="insert">Insert at random index</button>
  <button @click="reset">Reset</button>
  <button @click="shuffle">Shuffle</button>

  <TransitionGroup tag="ul" name="fade" class="container">
    <li v-for="item in items" class="item" :key="item">
      {{ item }}
      <button @click="remove(item)">x</button>
    </li>
  </TransitionGroup>
</template>

<style>
.container {
  position: relative;
  padding: 0;
  list-style-type: none;
}

.item {
  width: 100%;
  height: 30px;
  background-color: #f3f3f3;
  border: 1px solid #666;
  box-sizing: border-box;
}

/* 1. declare transition */
.fade-move,
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s cubic-bezier(0.55, 0, 0.1, 1);
}

/* 2. declare enter from and leave to state */
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: scaleY(0.01) translate(30px, 0);
}

/* 3. ensure leaving items are taken out of layout flow so that moving
      animations can be calculated correctly. */
.fade-leave-active {
  position: absolute;
}
</style>