<template>
    <header
      id="header"
      class="header__wrap Nanum5 bg-vue"
      role="heading"
      aria-level="1"
    >
      <div class="header__inner container">
        <h1 class="header__logo">
          <router-link to="/"><a>finevo</a> </router-link>
        </h1>
        <nav class="header__nav" role="navigation">
          <ul>
            <li>
              <router-link to="intro">회사 소개</router-link>
            </li>
            <li>
              <router-link to="service">서비스</router-link>
            </li>
            <li>
              <router-link to="serviceApplication ">서비스 신청</router-link>
            </li>
            <li>
              <router-link to="customerSupport">고객 지원</router-link>
            </li>
            <li>
              <router-link to="in">FINEVO IN</router-link>
            </li>
          </ul>
        </nav>
      </div>
      <router-view></router-view>

    </header>
  </template>
  <style lang="scss">
  // header__wrap
  .header__inner {
    text-align: center;
  
    .header__logo {
      width: 100%;
  
        a {
          font-family: Gothic;
          font-style: normal;
          font-weight: 500;
          text-decoration: none;
          color: black;

        }
      
    }
    .header__nav {
      width: 100%;
  
      ul {
        padding: 0 10%;
        display: flex;
        justify-content: space-between;
        list-style-type: none;
  
        li {
          a {
            display: inline-block;
            padding: 20px 10px;
            margin: 2px;
            position: relative;
            text-decoration: none;
            color: black;
  
            &::before {
              content: "";
              width: calc(100% - 20px);
              height: 2px;
              background-color: var(--color_body);
              position: absolute;
              left: 10px;
              bottom: 16px;
              transform: scaleX(0);
              transition: all 0.3s ease;
            }
  
            &:hover::before {
              transform: scaleX(1);
            }
          }
        }
      }
    }
  }
  </style>
  