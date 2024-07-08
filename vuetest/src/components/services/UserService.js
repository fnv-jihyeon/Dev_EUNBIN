const USERS_KEY = 'users';

function getUsers() {
  const users = localStorage.getItem(USERS_KEY);
  return users ? JSON.parse(users) : [];
}

function saveUsers(users) {
  localStorage.setItem(USERS_KEY, JSON.stringify(users));
}

export function registerUser(id, password, name, number, email) {
  const users = getUsers();
  users.push({ id, password, name, number, email });
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