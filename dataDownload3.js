$(document).ready(function() {
	
	
	$("#d1").click(function(){
		var d1 = $("#xzd1").html();
		d1 = "MetLife_现场签署文件/"+d1+".pdf"
//		alert(d1);
		download(d1);
	});
	
	
	$("#d2").click(function(){
		var d2 = $("#xzd2").html();
		d2 = "MetLife_现场签署文件/"+d2+".pdf";
//		alert(d2);
		download(d2);
	});
	
	$("#d3").click(function(){
		var d3 = $("#xzd3").html();
		d3 = "MetLife_现场签署文件/"+d3 + ".xls";
//		alert(d3);
		download(d3);
	});
	
	$("#d4").click(function(){
		var d4 = $("#xzd4").html();
		d4 = "MetLife_现场签署文件/"+d4 + ".pdf";
//		alert(d4);
		download(d4);
	});
	
	
	$("#d5").click(function(){
		var d5 = $("#xzd5").html();
		d5 = "MetLife_现场签署文件/"+d5 + ".pdf";
//		alert(d5);
		download(d5);
	});
	
	$("#d6").click(function(){
		var d6 = $("#xzd6").html();
		d6 = "MetLife_现场签署文件/"+d6 + ".pdf";
//		alert(d6);
		download(d6);
	});
	
	$("#d7").click(function(){
		var d7 = $("#xzd7").html();
		d7 = "MetLife_现场签署文件/"+d7 + ".pdf";
//		alert(d7);
		download(d7);
	});
	
	$("#d8").click(function(){
		var d8 = $("#xzd8").html();
		d8 = "MetLife_现场签署文件/"+d8 + ".pdf";
//		alert(d8);
		download(d8);
	});
	
	
	
});

function download(id){
	var name = id;
	
//	id="D:/download/"+id;
	
	id="/home/ybt/downloadfiles/"+id;	
	$.ajax({
		url:path + "/contUploadController/download.do",// 后台请求URL地址
		type : "POST",
		data : {"path":id,"docName":name},
		success : function(data) {
			if(data.success==true){
				var url = "download.jsp?name="+encodeURI(encodeURI(id));
				window.location.href=url;
			}else{
				alert(data.msg);
			}
		},
		error : function() {
			alert("下载失败!");
		}
	});
	
};