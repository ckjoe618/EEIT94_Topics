<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>EEIT94_第四組_貓狗大站</title>
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      body {
        display: flex;
        width: 100vw;
        height: 100vh;
        background-color: #f9f9f9;
      }
      .content {
        flex: 1;
        padding: 20px;
        overflow-y: auto;
      }
      .header {
        font-size: 24px;
        margin-bottom: 20px;
      }
      fieldset {
        width: 500px;
        border: 2px solid black;
        border-radius: 15px;
      }
      .st1 {
        width: 450px;
        padding-bottom: 10px;
        border-bottom: 1px solid gray;
        margin: 20px;
      }
      .t1 {
        width: 100px;
        float: left;
        text-align: right;
      }
      .sub {
        width: 450px;
        margin: 20px;
        text-align: center;
      }
      .div1 {
        width: 450px;
        margin: 10px;
        text-align: center;
      }
      .returnBtn {
        width: 50px;
        height: 30px;
        font-size: 16px;
        background-color: #fff4c1;
        border-radius: 10px;
        cursor: pointer;
      }
      .submitBtn,
      .resetBtn {
        background-color: #fff4c1;
        border-radius: 5px;
        cursor: pointer;
      }
      .returnBtn:hover,
      .submitBtn:hover,
      .resetBtn:hover {
        background-color: #ffe66f;
      }
    </style>
  </head>
  <body>
    <div class="content">
      <div class="header">會員資料</div>
      <hr />
      <form method="post" action="../MemberServlet">
        <input type="hidden" name="action" value="insertMember" />
        <fieldset>
          <legend>新增會員資料</legend>
          <div class="st1">
            <label for="name" class="t1">姓名:</label>
            <input type="text" name="name" size="10" required />
          </div>
          <div class="st1">
            <label for="gender" class="t1">性別:</label>
            <label
              ><input
                type="radio"
                name="gender"
                value="male"
                required
                checked
              />男</label
            >
            <label><input type="radio" name="gender" value="female" />女</label>
          </div>
          <div class="st1">
            <label for="idno" class="t1">身分證字號:</label>
            <input
              type="text"
              name="idno"
              maxlength="10"
              pattern="[a-z,A-Z]{1}[1-2,8-9]{1}\d{8}"
              required
            />
          </div>
          <div class="st1">
            <label for="email" class="t1">Email:</label>
            <input type="email" name="email" required />
          </div>
          <div class="st1">
            <label for="phone" class="t1">電話:</label>
            <input
              type="text"
              name="phone"
              maxlength="10"
              pattern="[0]{1}[9]{1}\d{8}"
              required
            />
          </div>
          <div class="st1">
            <label for="birthday" class="t1">生日:</label>
            <input type="date" name="birthday" required />
          </div>
          <div class="st1">
            <label for="account" class="t1">帳號:</label>
            <input
              type="text"
              name="account"
              maxlength="50"
              size="30"
              required
            />
          </div>
          <div class="st1">
            <label for="password" class="t1">密碼:</label>
            <input
              type="text"
              name="password"
              maxlength="20"
              size="20"
              required
            />
          </div>
          <div class="sub">
            <button class="submitBtn" type="submit">確認新增</button>
            <button class="resetBtn" type="reset">清除</button>
          </div>
        </fieldset>
      </form>
      <div class="div1">
        <button class="returnBtn">返回</button>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script>
      $(function () {
        let url = "<%= request.getContextPath() %>";

        $(".returnBtn").on("click", function () {
          window.location.href = url + "/member/Member.jsp";
        });
      });
    </script>
  </body>
</html>
