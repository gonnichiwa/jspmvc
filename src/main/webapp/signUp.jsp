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
<form action="signUp.bbs" method="post">
    <table>
        <tr>
            <td><label for="id">아이디</label></td>
            <td><input type="text" name="id" id="id"/></td>
        </tr>
        <tr>
            <td><label for="password">비번</label></td>
            <td><input type="text" name="password" id="password"/></td>
        </tr>
        <tr>
            <td><label for="passwordCheck">비번확인</label></td>
            <td><input type="text" name="passwordCheck" id="passwordCheck"/></td>
        </tr>
        <tr>
            <!-- javascript로 비번확인 체크 로직까지 넣어서 회원가입 요청 처리 -->
            <!-- 같으면 signUp.bbs 요청 날림 -->
            <!-- 다르면 alert('비번을 정확히 입력 바랍니다') -->
            <input type="submit" value="회원가입"/>
        </tr>
    </table>
</form>

</body>
</html>
