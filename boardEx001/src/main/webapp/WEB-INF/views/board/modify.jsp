<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ include file="../include/header.jsp" %>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board Register
                        </div>
                        <!-- /.panel-heading -->
						<div class="panel-body">
							<form role="form" action="/board/modify" method="post">
								
								<input type="hidden" name='pageNum' value="${cri.pageNum}">
								<input type="hidden" name='amount' value="${cri.amount}">
								<input type="hidden" name='type' value="${cri.type}">
								<input type="hidden" name='keyword' value="${cri.keyword}">
								
								<div class="form-grop">
									<label>B	no</label>
									<input class="form-control" name="bno" value="${board.bno}" readonly>
								</div>
								
								<div class="form-grop">
									<label>Title</label>
									<input class="form-control" name="title" value="${board.title}">
								</div>
								
								<div class="form-grop">
									<label>content</label>
									<textarea class="form-control" rows="3" name="content">${board.content}</textarea>
								</div>
								
								<div class="form-grop">
									<label>writer</label>
									<input class="form-control" name="writer" value="${board.writer}">
								</div>
								
								<button type="submit" data-oper="modify" class="btn btn-default">Modify Button</button>
								<button type="reset" data-oper="reset" class="btn btn-default">Reset Button</button>
								
							</form>
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
	var formObj=$("form");
	$("button[data-oper='list']").on("click",function(){
		formObj.attr("method","get");
		formObj.attr("action","/board/list");
		formObj.submit();
		//location.href="/board/list";
	});
});
</script>
<%@ include file="../include/footer.jsp" %>