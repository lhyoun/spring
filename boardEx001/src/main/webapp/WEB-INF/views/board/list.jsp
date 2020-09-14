<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../include/header.jsp" %>

            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board List</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board List
                            <button id="regBtn" type="button" class="btn btn-xs pull-right">
                            	New Board
                            </button>
                        </div>
                        <!-- /.panel-heading -->
						<div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>#번호</th>
										<th>제목</th>
										<th>작성자</th>
										<th>작성일</th>
										<th>수정일</th>
									</tr>
								</thead>
								<c:forEach items="${list}" var="board">
									<tr>
										<td>${board.bno }</td>
			                          	<td><a href="">${board.title }</a></td>
			                          	<td>${board.writer }</td>
			                          	<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			                          	<td><fmt:formatDate value="${board.updatedate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
									</tr>
								</c:forEach>
							</table>
						</div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
<script type="text/javascript">
$(document).ready(function(){
	$("#regBtn").on("click",function(){
		self.location="/board/register";
	});
});
</script>
<%@ include file="../include/footer.jsp" %>