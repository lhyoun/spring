<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<table>
	<tr>
		<th>bno</th>
		<th>title</th>
		<th>writer</th>
		<th>regdate</th>
		<th>updatedate</th>
	</tr>
	<c:forEach items="${list }" var="board">
		<tr>
			<td>${board.bno}</td>
			<td>${board.title}</td>
			<td>${board.writer}</td>
			<td><fmt:formatDate value="${board.regdate}"/></td>
			<td><fmt:formatDate value="${board.updatedate}"/></td>
		</tr>
	</c:forEach>
</table>

</body>
</html>