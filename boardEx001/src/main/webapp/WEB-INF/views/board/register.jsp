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
							<form role="form" action="/board/register" method="post">
								<div class="form-grop">
									<label>Title</label>
									<input class="form-control" name="title">
								</div>
								
								<div class="form-grop">
									<label>content</label>
									<textarea class="form-control" rows="3" name="content"></textarea>
								</div>
								
								<div class="form-grop">
									<label>writer</label>
									<input class="form-control" name="writer">
								</div>
								
								<button type="submit" class="btn btn-default">Submit Button</button>
								<button type="reset" class="btn btn-default">Reset Button</button>
								
							</form>
						</div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
<%@ include file="../include/footer.jsp" %>