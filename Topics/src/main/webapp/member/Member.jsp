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
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        padding: 20px;
        overflow-y: auto;
        width: 100%;
      }
      .sp1 {
        margin: 10px;
        font-weight: bold;
      }
      #memberTable {
        border: 1px solid black;
        border-collapse: collapse;
      }
      #memberTable thead tr {
        background-color: #bebebe;
      }
      #memberTable th,
      #memberTable td {
        border: 1px solid black;
        padding: 8px;
        text-align: center;
      }
      .getAllBtn,
      .addBtn {
        margin: 10px 0;
        width: 80px;
        height: 30px;
        font-size: 16px;
        background-color: #fff4c1;
        border-radius: 10px;
        cursor: pointer;
      }
      .returnBtn {
        width: 50px;
        height: 30px;
        font-size: 16px;
        background-color: #fff4c1;
        border-radius: 10px;
        cursor: pointer;
      }
      .getBtn,
      .clearBtn {
        background-color: #fff4c1;
        border-radius: 5px;
        cursor: pointer;
      }
      .submitBtn,
      .resetBtn {
        background-color: #fff4c1;
        border-radius: 5px;
        cursor: pointer;
      }
      .getAllBtn:hover,
      .getBtn:hover,
      .addBtn:hover,
      .returnBtn:hover,
      .submitBtn:hover,
      .resetBtn:hover,
      .clearBtn:hover {
        background-color: #ffe66f;
      }
      #main-form {
        display: flex;
        flex-wrap: wrap;
        margin: 10px;
      }
      .field-container {
        margin: 10px;
      }
      .div1 {
        margin: 10px;
      }
      .div2 {
        width: 450px;
        margin: 10px;
        text-align: center;
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
    </style>
  </head>
  <body>
    <div class="content">
      <form id="main-form">
        <div class="field-container">
          <label for="memberId">會員編號:</label>
          <input type="text" name="memberId" placeholder="請輸入" />
        </div>
        <span class="sp1">或</span>
        <div class="field-container">
          <label for="memberId">帳號:</label>
          <input type="text" name="account" placeholder="請輸入" />
        </div>
        <span class="sp1">或</span>
        <div class="field-container">
          <label for="memberId">身分證:</label>
          <input type="text" name="idno" placeholder="請輸入" />
        </div>
        <div class="field-container">
          <button class="getBtn">查詢</button>
        </div>
        <div class="field-container">
          <button type="reset" class="clearBtn">清除</button>
        </div>
      </form>
      <div>
        <button class="getAllBtn">查詢全部</button>
        <button class="addBtn">新增會員</button>
      </div>
      <div class="div1"></div>
    </div>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script>
      $(function () {
        let url = "<%= request.getContextPath() %>";

        getMember();

        $(".getBtn").on("click", function (e) {
          e.preventDefault(); // 防止表單提交
          let memberId = $("input[name='memberId']").val();
          let account = $("input[name='account']").val();
          let idno = $("input[name='idno']").val();
          console.log('memberId' + memberId); // 驗證
          console.log('account' + account); // 驗證
          console.log('idno' + idno); // 驗證

          getMemberBy(memberId, account, idno);
          $("#main-form")[0].reset();
          $("input[name='memberId']").removeAttr("disabled");
          $("input[name='account']").removeAttr("disabled");
          $("input[name='idno']").removeAttr("disabled");
        });

        $(".clearBtn").on("click", function () {
          $("#main-form")[0].reset();
          $("input[name='memberId']").removeAttr("disabled");
          $("input[name='account']").removeAttr("disabled");
          $("input[name='idno']").removeAttr("disabled");
        });

        $(".getAllBtn").on("click", function () {
          getMember();
        });

        $(document).on("click", ".addBtn", function () {
          alert("新增會員"); // 測試 alert 是否觸發
          let servletUrl = url + "/member/InsertMember.jsp";
          window.location.href = servletUrl;
        });

        $(document).on("click", ".editBtn", function () {
          let memberId = $(this).data("id");
          alert("你點選的會員ID為：" + memberId); // 測試 alert 是否觸發
          updateMember(memberId);
        });

        $(document).on("click", ".deleteBtn", function () {
          let memberId = $(this).data("id");
          if (confirm("確定要刪除這位會員嗎？")) {
            $.ajax({
              url: url + "/MemberServlet",
              type: "POST",
              data: { action: "deleteMemberById", memberId: memberId },
              success: function () {
                alert("會員已刪除！");
                $("#row-" + memberId).remove(); // 前端即時移除該行
              },
              error: function () {
                alert("刪除失敗！");
              },
            });
          }
        });

        $(document).on("click", ".returnBtn", function () {
          getMember();
        });

        function getMember() {
          $.ajax({
            url: url + "/MemberServlet",
            type: "POST",
            data: {
              action: "getMember",
            },
            dataType: "json",
            success: function (list) {
              console.log(list); // 驗證
              if (list.length === 0) {
                $(".div1").empty();
                $(".div1").append("<p>沒有找到符合條件的會員資料。</p>");
                return;
              }

              $(".div1").empty();
              $(".div1").append(
                `<table id="memberTable">
                    <thead></thead>
                    <tbody></tbody>
                </table>`
              );

              let thead = ` <tr>
                                <th>會員編號</th>
                                <th>姓名</th>
                                <th>性別</th>
                                <th>身分證</th>
                                <th>Email</th>
                                <th>電話</th>
                                <th>生日</th>
                                <th>帳號</th>
                                <th>密碼</th>
                                <th>創建日期</th>
                                <th>活耀狀態</th>
                                <th>編輯</th>
                            </tr>`;
              $("#memberTable thead").append(thead);

              let row = "";
              $.each(list, function (index, member) {
                row += `<tr id="row-\${member.memberId}">
                            <td>\${member.memberId}</td>
                            <td>\${member.name}</td>
                            <td>\${member.gender}</td>
                            <td>\${member.idno}</td>
                            <td>\${member.email}</td>
                            <td>\${member.phone}</td>
                            <td>\${member.birthday}</td>
                            <td>\${member.account}</td>
                            <td>\${member.password}</td>
                            <td>\${member.createAccount}</td>
                            <td>\${member.active}</td>
                            <td>
                            <button class="editBtn" data-id="\${member.memberId}">修改</button>
                            <button class="deleteBtn" data-id="\${member.memberId}">刪除</button>
                            </td>
                        </tr>`;
              });
              $("#memberTable tbody").append(row);
              console.log(row); // 驗證
            },
            error: function () {
              alert("無法取得會員資料！");
            },
          });
        }

        function getMemberBy(memberId, account, idno) {
          $.ajax({
            url: url + "/MemberServlet",
            type: "POST",
            data: {
              action: "getMemberBy",
              memberId: memberId,
              account: account,
              idno: idno,
            },
            dataType: "json",
            success: function (member) {
              console.log(member); // 驗證
              if (member.memberId === 0) {
                $(".div1").empty();
                $(".div1").append("<p>沒有找到符合條件的會員資料。</p>");
                return;
              }
              $(".div1").empty();
              $(".div1").append(
                `<table id="memberTable">
                    <thead></thead>
                    <tbody></tbody>
                </table>`
              );

              let thead = ` <tr>
                                <th>會員編號</th>
                                <th>姓名</th>
                                <th>性別</th>
                                <th>身分證</th>
                                <th>Email</th>
                                <th>電話</th>
                                <th>生日</th>
                                <th>帳號</th>
                                <th>密碼</th>
                                <th>創建日期</th>
                                <th>活耀狀態</th>
                                <th>編輯</th>
                            </tr>`;
              $("#memberTable thead").append(thead);

              let row = `<tr id="row-\${member.memberId}">
                            <td>\${member.memberId}</td>
                            <td>\${member.name}</td>
                            <td>\${member.gender}</td>
                            <td>\${member.idno}</td>
                            <td>\${member.email}</td>
                            <td>\${member.phone}</td>
                            <td>\${member.birthday}</td>
                            <td>\${member.account}</td>
                            <td>\${member.password}</td>
                            <td>\${member.createAccount}</td>
                            <td>\${member.active}</td>
                            <td>
                            <button class="editBtn" data-id="\${member.memberId}">修改</button>
                            <button class="deleteBtn" data-id="\${member.memberId}">刪除</button>
                            </td>
                        </tr>`;
              $("#memberTable tbody").append(row);
              console.log(row); // 驗證
            },
            error: function () {
              alert("無法取得會員資料！");
            },
          });
        }

        function updateMember(memberId) {
          $.ajax({
            url: url + "/MemberServlet",
            type: "POST",
            data: {
              action: "getMemberBy",
              memberId: memberId,
            },
            dataType: "json",
            success: function (member) {
              console.log(member); // 驗證

              $(".div1").empty();

              let updateForm = `<form method="post" action="../MemberServlet">
                                  <input type="hidden" name="action" value="updateMemberById" />
                                  <fieldset>
                                    <legend>修改會員資料</legend>
                                    <input type="hidden" name="memberId" value="\${
                                      member.memberId
                                    }" />
                                    <div class="st1">
                                      <label for="name" class="t1">姓名:</label>
                                      <input
                                        type="text"
                                        name="name"
                                        size="10"
                                        value="\${member.name}"
                                        required
                                      />
                                    </div>
                                    <div class="st1">
                                      <label for="gender" class="t1">性別:</label>
                                      <input
                                        type="text"
                                        name="gender"
                                        value="\${member.gender}"
                                        required
                                      />
                                    </div>
                                    <div class="st1">
                                      <label for="idno" class="t1">身分證字號:</label>
                                      <input
                                        type="text"
                                        name="idno"
                                        maxlength="10"
                                        pattern="[a-z,A-Z]{1}[1-2,8-9]{1}\\d{8}"
                                        value="\${member.idno}"
                                        required
                                      />
                                    </div>
                                    <div class="st1">
                                      <label for="email" class="t1">Email:</label>
                                      <input type="email" name="email" value="\${
                                        member.email
                                      }" required />
                                    </div>
                                    <div class="st1">
                                      <label for="phone" class="t1">電話:</label>
                                      <input
                                        type="text"
                                        name="phone"
                                        maxlength="10"
                                        pattern="[0]{1}[9]{1}\\d{8}"
                                        value="\${member.phone}"
                                        required
                                      />
                                    </div>
                                    <div class="st1">
                                      <label for="birthday" class="t1">生日:</label>
                                      <input
                                        type="date"
                                        name="birthday"
                                        value="\${member.birthday}"
                                        required
                                      />
                                    </div>
                                    <div class="st1">
                                      <label for="account" class="t1">帳號:</label>
                                      <input
                                        type="text"
                                        name="account"
                                        maxlength="50"
                                        size="30"
                                        value="\${member.account}"
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
                                        value="\${member.password}"
                                        required
                                      />
                                    </div>
                                    <div class="st1">
                                      <label for="isActive" class="t1">活耀狀態:</label>
                                      <label
                                        ><input type="radio" name="isActive" value="1" required
                                        \${
                                          member.active ? "checked" : ""
                                        } />開啟</label
                                      >
                                      <label
                                        ><input type="radio" name="isActive" value="0" required
                                        \${
                                          !member.active ? "checked" : ""
                                        } />凍結</label
                                      >
                                    </div>
                                    <div class="sub">
                                      <button class="submitBtn" type="submit">確認修改</button>
                                      <button class="resetBtn" type="reset">清除</button>
                                    </div>
                                  </fieldset>
                                </form>
                                <div class="div2">
                                  <button class="returnBtn">返回</button>
                                </div>`;
              $(".div1").append(updateForm);
            },
            error: function () {
              alert("無法取得會員資料！");
            },
          });
        }

        $("input[name='memberId']").on("change", function () {
          $("input[name='account']").attr("disabled", true);
          $("input[name='idno']").attr("disabled", true);
        });
        $("input[name='account']").on("change", function () {
          $("input[name='memberId']").attr("disabled", true);
          $("input[name='idno']").attr("disabled", true);
        });
        $("input[name='idno']").on("change", function () {
          $("input[name='memberId']").attr("disabled", true);
          $("input[name='account']").attr("disabled", true);
        });
      });
    </script>
  </body>
</html>
