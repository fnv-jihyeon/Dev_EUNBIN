<template>
    <footer id="footer" class="footer__wrap section Nanum5" role="contentinfo">
      <div class="footer__inner container">
        <div class="footer__text">
          <h5>Finevo</h5>
          <p>
            주소 : 서울특별시 서초구 서초대로 77길 55 에이프로 스퀘어(워워크) 6층 A106 피네보㈜
          </p>

        </div>
        <div class="footer__right">
          <p>Copyright© 2024 Finevo Inc All Rights Reserved.</p>
        </div>
      </div>
    </footer>
  </template>
  <style lang="scss">
  //footer__wrap
  
  .page-wrapper {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }
  
  .main-content {
    flex: 1;
  }
  
  .footer__wrap {
    width: 100%;
    margin-top: auto;
    bottom: 0px;
    position: relative; //(본문 - 푸터) 겹치는 문제 해결을 위해 추가 
    min-height: 100; //(본문 - 푸터) 겹치는 문제 해결을 위해 추가 

    .footer__inner {
      display: flex;
      flex-direction: column;
      align-items: center;
      flex-wrap: wrap;
      padding: 30px 0; // added padding for space
  
      .footer__text, .footer__right {
        width: 100%;
        text-align: center;
      }
  
      .footer__text {
        margin-bottom: 10px;
        > h5 {
          font-size: 15px;
          line-height: 1.2;
          font-weight: 400;
          margin-bottom: 10px;
        }
        > p {
          font-size: 12px;
          line-height: 0.7;
          font-weight: 400;
          color: #909090;
          margin-bottom: 5px;
        }
      }
  
      .footer__right {
        padding: 5px 0;
        border-top: 1px solid #e5e5e5;
        color: #b7b6b6;
        font-size: 10px;
      }
    }
  }
   
  </style>
  