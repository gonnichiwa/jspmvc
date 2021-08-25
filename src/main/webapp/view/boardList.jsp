<%--
  Created by IntelliJ IDEA.
  User: keept
  Date: 2021-08-24
  Time: 오후 4:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- jstl -->
<html>
<head>
    <title>전체 글 목록 보기</title>
</head>
<body>
<!-- BoardListCmd.java에서 가져온 데이터를 어떻게 jsp파일에서 html 형태로 보여줄 것인가? -->
<c:forEach var="j" begin="1" end="3">
Item <c:out value="${j}"/><p>
</c:forEach>

</body>
</html>
