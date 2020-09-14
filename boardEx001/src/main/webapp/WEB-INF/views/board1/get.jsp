<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="modify" method="post">
		<table>
			<tr>
				<th>번호</th>
				<td><input type="text" name="bno" value="${board.bno}" readonly></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" value="${board.title}"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="5" cols="50" name="content">${board.content}</textarea></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="writer" value="${board.writer}"></td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${board.regdate}"</td>
			</tr>
			<tr>
				<th>수정일</th>
				<td>${board.updatedate}</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="수정">
					<input type="button" value="삭제"
					onclick="location.href='remove?bno=${board.bno}'"> <input
					type="reset" value="취소"></td>
			</tr>
		</table>
	</form>
</body>
</html>