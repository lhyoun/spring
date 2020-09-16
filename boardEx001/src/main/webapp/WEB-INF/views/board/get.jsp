<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ include file="../include/header.jsp" %>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board Re gister
                        </div>
                        <!-- /.panel-heading -->
						<div class="panel-body">
						
								<div class="form-grop">
									<label>Bno</label>
									<input class="form-control" name="bno" value="${board.bno }" readonly>
								</div>
								
								<div class="form-grop">
									<label>Title</label>
									<input class="form-control" name="title" value="${board.title }" readonly>
								</div>
								
								<div class="form-grop">
									<label>content</label>
									<textarea class="form-control" rows="3" name="content" readonly>${board.content }</textarea>
								</div>
								
									<label>writer</label>
								<div class="form-grop">
									<input class="form-control" name="writer" value="${board.writer }" readonly>
								</div>
								
								<div class="form-grop">
									<label>regdate</label>
									<input class="form-control" name="regdate" value="${board.regdate }" readonly>
								</div>
								
								<div class="form-grop">
									<label>updatedate</label>
									<input class="form-control" name="updatedate" value="${board.updatedate }" readonly>
								</div>
								
								<br>
								<button data-oper='modify' class="btn btn-primary">Modify</button>
								<button data-oper='remove' class="btn btn-danger">Remove</button>
								<button data-oper='list' class="btn btn-default">List</button>
								
						</div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        
<form id="operForm">
	<input type="hidden" id="bno" name="bno" value="${board.bno}">
	<input type='hidden' name='pageNum' value='${cri.pageNum}'>
	<input type='hidden' name='amount' value='${cri.amount}'>
	<input type='hidden' name='type' value='${cri.type}'>
	<input type='hidden' name='keyword' value='${cri.keyword}'>
</form>

<script type="text/javascript">
$(document).ready(function(){
	var operForm=$("#operForm");
	
	$("button[data-oper='modify']").on("click",function(){
		operForm.attr("action", "/board/modify");
		operForm.attr("method", "get");
		operForm.submit();
	});
	
	$("button[data-oper='remove']").on("click",function(){
		operForm.attr("action", "/board/remove");
		operForm.attr("method", "get");
		operForm.submit();
	});
	
	$("button[data-oper='list']").on("click",function(){
		operForm.find("#bno").remove();
		operForm.attr("action", "/board/list");
		operForm.attr("method", "get");
		operForm.submit();
	});
});
</script>

<script type="text/javascript" src="/resources/js/reply.js"></script>

<script type="text/javascript">
	console.log("===============");
	console.log("JS TEST");
	var bnoValue = '<c:out value="${board.bno}"/>';
	//for replyService add test
	
	/*replyService.get(5,function(data){
		console.log(data);
	});*/
	
	/*replyService.update(
		{rno:5, bno:bnoValue, reply:"Modified reply"},
		function(result){
			alert("수정 성공");
	});*/
	
	/*replyService.remove(6, function(count){
		console.log(count);
		if(count=="reply delete success"){
			alert("삭제 성공");
		}
	}, function(err){
		alert("ERROR");
	});*/
	
	replyService.getList({bno:bnoValue,page:1},function(list){
		for(var i=0, len=list.length||0; i<len; i++){
			console.log(list[i]);
		}
	});
	/*
	replyService.add(
		{reply:"JS Test", replyer:"tester", bno:bnoValue} ,
		function(result){
		alert("RESULT: " + result);
	}
);*/
</script>



<%@ include file="../include/footer.jsp" %>