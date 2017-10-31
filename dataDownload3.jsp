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
											<td id="xzd1">电话交易系统（IVR）申请书-中英文版</td>
											<td>
												<button  class="btn btn-primary" id="d1">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd2">附件三：关于“非居民金融账户涉税信息尽职调查管理办法（CRS）”正式下发的投保要求指引</td>
											<td>
												<button  class="btn btn-primary" id="d2">下载</button>
											</td>
										</tr>
										<tr>
											<td id="xzd3">人身保险新型产品客户风险测评问卷(非特定地区CN-EN20140401)</td>
											<td>
												<button  class="btn btn-primary" id="d3">下载</button>
											</td>
										</tr>
										<tr>
											<td id="xzd4">人身保险新型产品投保风险特别提示(上海以外地区老人投保新型产品适用)</td>
											<td>
												<button  class="btn btn-primary" id="d4">下载</button>
											</td>
										</tr>
										<tr>
											<td id="xzd5">网上交易系统（GSVP）申请书-中英文版</td>
											<td>
												<button  class="btn btn-primary" id="d5">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd6">新LOGO-HO-NB-300-1423-人身保险投保提示书_辽宁版模板（2017第二季度）</td>
											<td>
												<button  class="btn btn-primary" id="d6">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd7">新LOGO-HO-NB-300-1424-人身保险投保提示书_重庆版模板（2017第二季度）</td>
											<td>
												<button  class="btn btn-primary" id="d7">下载</button>
											</td>
										</tr>
										
										<tr>
											<td id="xzd8">新LOGO-HO-NB-300-1434-人身保险投保提示书-BXS汇丰中英文版模版（2017第二季度）</td>
											<td>
												<button  class="btn btn-primary" id="d8">下载</button>
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
	
	<script src="<%=request.getContextPath() %>/application/newContImageUpload/js/dataDownload3.js" 
		type="text/javascript" charset="utf-8">
	</script>
	
</body>
</html>