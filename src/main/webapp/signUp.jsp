<%--
  Created by IntelliJ IDEA.
  User: keept
  Date: 2021-09-10
  Time: 오후 8:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입 화면</title>
</head>
<body>
<form action="signUp.bbs" method="post" name="reqSignUp">
    <table>
        <tr>
            <td><label for="id">아이디</label></td>
            <td><input type="text" name="id" id="id"/></td>
        </tr>
        <tr>
            <td><label for="password">비번</label></td>
            <td><input type="password" name="password" id="password"/></td>
        </tr>
        <tr>
            <td><label for="passwordCheck">비번확인</label></td>
            <td><input type="password" name="passwordCheck" id="passwordCheck"/></td>
        </tr>
</form>
        <tr>
            <button onclick="checkPassword()">회원가입</button>
        </tr>
    </table>
<script>
    function checkPassword() {
        const password = document.getElementById("password").value;
        const checkPassword = document.getElementById("passwordCheck").value;
        if(password === checkPassword) {
            reqSignUp.submit();
        } else {
            alert('패스워드 틀립니다. 패스워드와 패스워드 확인란에 같은 패스워드를 넣어주세요');
        }
    }
</script>

</body>
</html>
