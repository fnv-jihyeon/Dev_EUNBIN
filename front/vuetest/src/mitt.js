import mitt from 'mitt'; // EventBus는 뷰가 추천하는 공식적인 방법X. 
const emitter = mitt(); // mitt 라는 외부 라이브러리를 이용하여 EventBus 구현 
export default emitter; // Vue3에서는 mitt
