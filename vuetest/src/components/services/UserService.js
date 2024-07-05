const USERS_KEY = 'users';

function getUsers() {
  const users = localStorage.getItem(USERS_KEY);
  return users ? JSON.parse(users) : [];
}

function saveUsers(users) {
  localStorage.setItem(USERS_KEY, JSON.stringify(users));
}

export function registerUser(id, password) {
  const users = getUsers();
  users.push({ id, password });
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

export function onlyAlphabet(e) {
  e.value = e.value.replace(/[^\\!-z]/gi,"");
}

