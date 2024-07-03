<template>
    <footer id="footer" class="footer__wrap section Nanum5" role="contentinfo">
      <div class="footer__inner container">
        <div class="footer__text">
          <h5>소개</h5>
          <p>
            주소 : 서울특별시 서초구 서초대로 77길 55 에이프로 스퀘어(워워크) 6층 A106 피네보㈜
          </p>

        </div>

        <div class="footer__right">
          <p>피네보_웹사이트 제작</p>
        </div>
      </div>
    </footer>
  </template>
  <style lang="scss">
  //footer__wrap
  .footer__wrap {
    .footer__inner {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      .footer__text {
        width: 30%;
        > h5 {
          font-size: 18px;
          line-height: 1.2;
          font-weight: 400;
          margin-bottom: 25px;
        }
        > p {
          font-size: 14px;
          line-height: 1.7;
          font-weight: 400;
          color: #909090;
          margin-bottom: 30px;
        }
        > ul {
          > li {
            display: inline;
            > a {
              display: inline-block;
              width: 45px;
              height: 45px;
              background-color: #fff;
              border-radius: 50%;
              transition: background-color 0.3s;
            }
            a:hover {
              background-color: #e0e9ff;
            }
            &:nth-child(2) a {
              background-position: -45px 0;
            }
            &:nth-child(3) a {
              background-position: -90px 0;
            }
            &:nth-child(4) a {
              background-position: -135px 0;
            }
          }
        }
      }

      .footer__right {
        width: 100%;
        padding: 30px;
        text-align: center;
        border-top: 1px solid #e5e5e5;
        margin-top: 50px;
        color: #808080;
        font-size: 14px;
      }
    }
  }
  </style>
  