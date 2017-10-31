<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="Xenon Boostrap Admin Panel" />
<meta name="author" content="" />

<title>投保单资料下载</title>
<%@ include file="../../main/jsp/top.jsp"%>

</head>

<body class="page-body skin-facebook">
	<div class="page-container">


		<div class="main-content">

			<%@ include file="../../main/jsp/nav.jsp"%>

			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="panel-body">
								<div class="form-group">
									<div>
									<h4>投保单资料下载：</h4>
									</div>
								<table class="table table-bordered" >
									<thead>
										<tr>
											<th>资料名称</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody class="contNoData">
										<tr>
											<td id="xzd1">MetLife_后期签署文件</td>
											<td>
												<button  class="btn btn-primary" id="d1">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd2">保单服务申请书-中英文版</td>
											<td>
												<button  class="btn btn-primary" id="d2">下载</button>
											</td>
										</tr>
										<tr>
											<td id="xzd3">保全客户FATCA合规声明及告知事项</td>
											<td>
												<button  class="btn btn-primary" id="d3">下载</button>
											</td>
										</tr>
										<tr>
											<td id="xzd4">贷款及还款申请书</td>
											<td>
												<button  class="btn btn-primary" id="d4">下载</button>
											</td>
										</tr>
										<tr>
											<td id="xzd5">非身故保险金分期领取申请书</td>
											<td>
												<button  class="btn btn-primary" id="d5">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd6">合同终止申请书-中英文版</td>
											<td>
												<button  class="btn btn-primary" id="d6">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd7">健康告知书</td>
											<td>
												<button  class="btn btn-primary" id="d7">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd8">理赔申请书</td>
											<td>
												<button  class="btn btn-primary" id="d8">下载</button>
											</td>
										</tr>
										
										
										<tr>
											<td id="xzd9">年金转换申请书</td>
											<td>
												<button  class="btn btn-primary" id="d9">下载</button>
											</td>
										</tr>
										
										
										<tr>
											<td id="xzd10">生存金给付申请书</td>
											<td>
												<button  class="btn btn-primary" id="d10">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd11">投资连结保险保全申请书(5个账户)汇丰专用</td>
											<td>
												<button  class="btn btn-primary" id="d11">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd12">MetLife_后期签署文件</td>
											<td>
												<button  class="btn btn-primary" id="d12">下载</button>
											</td>
										</tr>
										
										
										
										
									</tbody>
								</table>
										
									

								</div>
							</div>

						</div>
					</div>

					<%@ include file="../../main/jsp/footer.jsp"%>
				</div>

			</div>

		</div>
	</div>
	<%@ include file="../../main/jsp/bottom.jsp"%>


	<!-- Imported scripts on this page -->
	
	<script src="<%=request.getContextPath() %>/application/newContImageUpload/js/dataDownload2.js" 
		type="text/javascript" charset="utf-8">
	</script>
	
</body>
</html>