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


			<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	<%-- <i class="fa fa-comments fa-fw">&nbsp;Reply&nbsp;[${board.replycount}]</i> --%>
                        	<i class="fa fa-comments fa-fw">&nbsp;Reply&nbsp;[<span class="replyCnt">${board.replycount}</span>]</i>
                        	<button id="addReplyBtn" class="btn btn-primary, btn-xs pull-right">New Reply</button>
            			</div>
            			<div class="panel-body">
            				<ul class="chat">
            					<!-- 여기에 댓글 리스트 출력 -->	
            				</ul>
            			</div>   
            			<div class="panel-footer">
            				
            			</div>             
                    </div>
                </div>
            </div>
            
            
			<!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Reply Modal</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                           		<label>Reply</label>
                           		<input class="form-controller" name="reply" value="reply">
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>Replyer</label>
                           		<input class="form-controller" name="replyer" value="replyer">
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>ReplyDate</label>
                           		<input class="form-controller" name="replydate" value="2020-01-01">
                           	</div>
                        </div>
                        <div class="modal-footer">
                        	<button id="modalModBtn" type="button" class="btn btn-warnig">Modify</button>
                        	<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
                            <button id="modalRegisterBtn" type="button" class="btn btn-primary">Register</button>
                            <button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
            
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
	var replyUL=$(".chat");
	
	function showList(page){
		alert("a");
		replyService.getList({bno:bnoValue,page: page|| 1 }, function(list) {
			var str="";
			if(list == null || list.length == 0){
				replyUL.html("");
				return;
			}
			$(".replyCnt").html(list.length);
			for (var i = 0, len = list.length || 0; i < len; i++) {
				str +="<li style='cursor:pointer' class='left clearfix' data-rno='"+list[i].rno+"'>";
				str +=" <div><div class='header'><strong class='primaryfont'>"+list[i].replyer+"</strong>";
				str +=" <small class='pull-right textmuted'>"+replyService.displayTime(list[i].replydate)+"</small></div>";
				str +=" <p>"+list[i].reply+"</p></div></li>";
			}
			replyUL.html(str);
		});//end function
	}//end showList
	
	showList(1);
	
	var modal=$(".modal");
	var modalInputReply=modal.find("input[name='reply']");
	var modalInputReplyer=modal.find("input[name='replyer']");
	var modalInputReplyDate=modal.find("input[name='replydate']");
	
	var modalModBtn=$("#modalModBtn");
	var modalRemoveBtn=$("#modalRemoveBtn");
	var modalRegisterBtn=$("#modalRegisterBtn");
	
	$("#modalCloseBtn").on("click",function(){
		modal.modal('hide');
	});
	
	$("#addReplyBtn").on("click",function(){
		modal.find("input").val("");
		modalInputReplyDate.closest("div").hide();
		modal.find("button[id!='modalCloseBtn']").hide();
		modalRegisterBtn.show();
		$(".modal").modal("show");
	});
	
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
	
	/*replyService.getList({bno:bnoValue,page:1},function(list){
		for(var i=0, len=list.length||0; i<len; i++){
			console.log(list[i]);
		}
	});*/
	
	
	/*replyService.add(
		{reply:"JS Test", replyer:"tester", bno:bnoValue} ,
		function(result){
		alert("RESULT: " + result);
	});*/
	
	modalRegisterBtn.on("click",function(e){
		var reply = {
			reply: modalInputReply.val(),
			replyer:modalInputReplyer.val(),
			bno:bnoValue
			};
		replyService.add(reply, function(result){
			alert(result);
			modal.find("input").val("");
			modal.modal("hide");
			showList(1);
		});
	});
	
	$(".chat").on("click", "li", function(e){
		var rno = $(this).data("rno");
		replyService.get(rno, function(reply){
			modalInputReply.val(reply.reply);
			modalInputReplyer.val(reply.replyer);
			modalInputReplyDate.val(replyService.displayTime(reply.replydate)).attr("readonly","readonly");
			modal.data("rno", reply.rno);	// modal에 표시는 안되지만 data를 지니고 있음
			modal.find("button[id !='modalCloseBtn']").hide();
			modalModBtn.show();
			modalRemoveBtn.show();
			$(".modal").modal("show");
		});
	});
	
	modalModBtn.on("click", function(e){
		var reply = {
			rno:modal.data("rno"), 
			reply: modalInputReply.val()
		};
		replyService.update(reply, function(result){
			alert(result);
			modal.modal("hide");
			showList(1);
		});
	});
	
	
</script>



<%@ include file="../include/footer.jsp" %>