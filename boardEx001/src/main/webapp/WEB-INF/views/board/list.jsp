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
										<th>조회수</th>
									</tr>
								</thead>
								<c:forEach items="${list}" var="board">
									<tr>
										<td>${board.bno }</td>
			                          	<td><a class="move" href="${board.bno}">${board.title }&nbsp;[${board.replycount}]</a></td>
			                          	<td>${board.writer }</td>
			                          	<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			                          	<td><fmt:formatDate value="${board.updatedate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			                          	<td>${board.readcount }</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						
						<form id='searchForm' action="/board/list" method='get'>
	 						<select name='type'>
	 							<!-- <option value="asd">--</option> -->
	 							<!-- selected가 선택하라는 동사의 의미 -->
								<option value="" <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
								<option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
								<option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>내용</option>
								<option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
								<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/>>제목 or 내용</option>
								<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}"/>>제목 or 작성자</option>
								<option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}"/>>제목 or 작성자 or 내용</option>
							</select> 
							<input type="text" name="keyword" value="${pageMaker.cri.keyword}">
							<input type="hidden" name="pageNum" value="1">
							<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
							<button class="btn btn-default">Search</button>
						</form>
						  
						  
						  
                        <!-- /.panel-body -->
                        
                        <%-- <div class="pull-right">
                        	<ul class="pagination">
                        		<c:if test="${pageMaker.prev}">
	                        		<li class="pagination_button previous"><a href="/board/list?pageNum=${pageMaker.endPage-1}&amount=10">Previous</a></li>
                        		</c:if>
                        		<c:forEach var="i" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                        			<li class='paginate_button'>
                        				<a href="/board/list?pageNum=${i}&amount=10">${i}</a>
                        			</li>
                        		</c:forEach>
                        		<c:if test="${pageMaker.next}">
									<li class="pagination_button next"><a href="/board/list?pageNum=${pageMaker.endPage+1}&amount=10">Next</a></li>                        		
                        		</c:if>
                        	</ul>
                        </div> --%>
                        
                    	<div class='pull-right'>
	                        <ul class="pagination">
	                        	<c:if test="${pageMaker.prev}">
									<li class="paginate_button previous"><a
									href="${pageMaker.startPage -1}">Previous</a></li>
								</c:if>
		
								<c:forEach var="num" begin="${pageMaker.startPage}"
									end="${pageMaker.endPage}">
									<li class='paginate_button ${pageMaker.cri.pageNum == num ? "active":""}'>
										<a href="${num}">${num}</a>
									</li>
								</c:forEach>
		
								<c:if test="${pageMaker.next}">
									<li class="paginate_button next"><a
										href="${pageMaker.endPage +1 }">Next</a></li>
								</c:if>
	                        </ul>
                        </div>
                        
                        <form id='actionForm' action="/board/list" method='get'>
							<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
							<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
							<input type='hidden' name='type' value='${pageMaker.cri.type}'>
							<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
						</form>
						
                     	<!-- Modal -->
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                    </div>
                                    <div class="modal-body">
										complete
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
                                <!-- /.modal-content -->
                            </div>
                            <!-- /.modal-dialog -->
                            
                        </div>
                        <!-- /.modal -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
<script type="text/javascript">
/*$(document).ready(function(){
	
	var result='<c:out value="${result}"/>';
	checkMoadl(result);
	
	function checkModal(result){
		if(result==''||history.state){
			return;
		}
		if(parseInt(result)>0){
			$(".modal-body").html('게시글'+parseInt(result)+'번이 등록되었습니다');
		}
		$("#myModal").modal("show");
	}
	
	$("#regBtn").on("click",function(){
		self.location="/board/register";
	});
});*/
/*$(document).ready(function(){
	
	var result='<c:out value="${result}"/>';
	checkModal(result);
	
	history.replaceState({}, null, null);
	
	function checkModal(result){
		if(result==''|| history.state){
			return;
		}else if{
			$(".modal-body").html("게시글 "+reslut);
			
		}
		//if(parseInt(result)>0){
		//	$(".modal-body").html('게시글 '+parseInt(result)+"번이 등록되었습니다.");
		//	history.replaceState({}, null, null);
		//}
		$("#myModal").modal("show");
	}
	
	$("#regBtn").on("click",function(){
		self.location="/board/register";
	});
	
});*/
$(document).ready(function(){
	//alert("history.state = "+$(history.state));
	//alert("history.state = "+history.state);
	
	var result='<c:out value="${result}"/>'
	checkModal(result);
	
	history.replaceState({},null,null);
	
	function checkModal(result){
		if(result==''||history.state){
			return;
		}else{
			$(".modal-body").html('게시글 '+result);
		}
	//	if(parseInt(result)>0){
	//		$(".modal-body").html('게시글 '+parseInt(result)+"번이 등록되었습니다.")
	//	}
		$("#myModal").modal("show");
	}
	
	$("#regBtn").on("click",function(){
		self.location="/board/register";
	});
	
	var actionForm = $("#actionForm");
	$(".paginate_button a").on("click",function(e) {
		e.preventDefault();	// a tag click block
		//console.log('click');
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();
	});
	
	$(".move").on("click",function(e){
		e.preventDefault();	// a tag click block
		actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
		actionForm.attr("action","/board/get");
		actionForm.submit();
	});
	
	var searchForm =$("#searchForm");
	$("#searchForm button").on("click", function(e){
		e.preventDefault();
		// alert(searchForm.find("option:selected").val());
		// selected는 만든 변수가 아니고 선택된 메뉴라는 뜻 
		if(!searchForm.find("option:selected").val()){
			alert("검색종류를 선택하세요");
			return false;
		}
		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하세요");
			return false;
		}
		searchForm.submit();
	});
});
</script>
<%@ include file="../include/footer.jsp" %>