const USERS_KEY = 'users';
const LOGGED_IN_USER_KEY = 'loggedInUser'; // 현재 로그인된 사용자 키

function getUsers() {
  const users = localStorage.getItem(USERS_KEY);
  return users ? JSON.parse(users) : [];
}

function saveUsers(users) {
  localStorage.setItem(USERS_KEY, JSON.stringify(users));
}

export function registerUser(id, password, name, tel, email) {
  const users = getUsers();
  users.push({ id, password, name, tel, email });
  saveUsers(users);
}

export function authenticateUser(id, password) {
  const users = getUsers();
  return users.find(user => user.id === id && user.password === password);
}

export function deleteUser(id) {
  let users = getUsers();
  users = users.filter(user => user.id !== id);
  saveUsers(users);
}

export function getAllUsers() {
  return getUsers();
}

/*
아이디 중복 체크 작성 필요 - 기존에 null 은 제출되지 않도록 설계. 그부분을 여기서 또 다룰 필요X 
export function searchSameId(id){
  let users = getUsers();
  return users.find(user => user.id === id);
}


export function searchSameId(id){
  if(user => user.id === id){
    alert("사용 가능한 아이디입니다");
    return;
  }
    else{
      alert("다른 아이디를 사용해주세요");
    }
  }
};
*/

export function checkDuplicateId(id) {
  const users = getUsers();
  return users.some(user => user.id === id);
}

export function filterUsers(users, query) {
  query = query.toLowerCase();
  return users.filter(user => { // 초기에 오류났던 부분. 각 속성이 정의되어있는지 확인하고 toLowerCase를 호출해야함 
    const id = user.id ? user.id.toLowerCase() : '';
    const password = user.password ? user.password.toLowerCase() : '';
    const name = user.name ? user.name.toLowerCase() : '';
    const tel = user.tel ? user.tel.toLowerCase() : '';
    const email = user.email ? user.email.toLowerCase() : '';

    return (
      id.includes(query) ||
      password.includes(query) ||
      name.includes(query) ||
      tel.includes(query) ||
      email.includes(query)
    );
  });
}

/*
export function refreshUsers() { 
  this.users = getAllUsers();
  this.filteredUsers = this.users;
}
  */

export function getUser() {
  const user = localStorage.getItem(LOGGED_IN_USER_KEY); // 수정필요 - 마이페이지에서 로그인했던 마지막 정보로 저장되는 중. LoginVeiw에서의 로그인으로 수정 필요
  return user ? JSON.parse(user) : null;
}

export function updateUser(updatedUser) {
  let users = getUsers();
  const index = users.findIndex(user => user.id === updatedUser.id);
  if (index !== -1) { // "!=""와 달리 "!=="는 타입까지 비교. 즉 같지 않다의 엄격한 비교
    users[index] = updatedUser;
    saveUsers(users);
    localStorage.setItem(LOGGED_IN_USER_KEY, JSON.stringify(updatedUser));
    return true;
  }
  return false;
}