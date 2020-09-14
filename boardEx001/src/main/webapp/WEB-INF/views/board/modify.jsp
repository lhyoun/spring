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
							
								<div class="form-grop">
									<label>Bno</label>
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
								<button type="button" data-oper="list" class="btn btn-default">List Button</button>
								
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