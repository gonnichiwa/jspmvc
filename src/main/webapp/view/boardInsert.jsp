<%--
  Created by IntelliJ IDEA.
  User: keept
  Date: 2021-08-25
  Time: 오후 3:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 추가하기</title>
</head>
<body>
<table>
    <tr>
        <td colspan="2">글 추가하기</td>
    </tr>
    <tr>
        <td><label for="subject">제목</label></td>
        <td><input type="text" id="subject"/></td>
    </tr>
    <tr>
        <td><label for="author">작성자</label></td>
        <td><input type="text" id="author"/></td>
    </tr>
    <tr>
        <td><label for="content">내용</label></td>
        <td><input type="text" id="content"/></td>
    </tr>
    <tr>
        <!-- 취소 버튼을 누르면 boardList.bbs로 가도록 처리해 보시오. -->
        <td colspan="2">[글쓰기]<a href="../boardList.bbs">[취소]</a></td>
    </tr>
</table>
</body>
</html>
