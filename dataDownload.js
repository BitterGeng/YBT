$(document).ready(function() {
	
	
	$("#d1").click(function(){
		var d1 = $("#xzd1").html();
		d1 = "MetLife_产品辅助文件/MetLife_产品MetLife_产品辅助文件 (as Web Page).mht"
//		alert(d1);
		download(d1);
	});
	
	
	$("#d2").click(function(){
		var d2 = $("#xzd2").html();
		d2 = "MetLife_产品辅助文件/"+d2+".pdf";
//		alert(d2);
		download(d2);
	});
	
	$("#d3").click(function(){
		var d3 = $("#xzd3").html();
		d3 = "MetLife_产品辅助文件/"+d3 + ".pdf";
//		alert(d3);
		download(d3);
	});
	
	$("#d4").click(function(){
		var d4 = $("#xzd4").html();
		d4 = "MetLife_产品辅助文件/"+d4 + ".pdf";
//		alert(d4);
		download(d4);
	});
	
	
	$("#d5").click(function(){
		var d5 = $("#xzd5").html();
		d5 = "MetLife_产品辅助文件/"+d5 + ".pdf";
//		alert(d5);
		download(d5);
	});
	
	$("#d6").click(function(){
		var d6 = $("#xzd6").html();
		d6 = "MetLife_产品辅助文件/"+d6 + ".pdf";
//		alert(d6);
		download(d6);
	});
	
	$("#d7").click(function(){
		var d7 = $("#xzd7").html();
		d7 = "MetLife_产品辅助文件/"+d7 + ".pdf";
//		alert(d7);
		download(d7);
	});
	
	$("#d8").click(function(){
		var d8 = $("#xzd8").html();
		d8 = "MetLife_产品辅助文件/"+d8 + ".pdf";
//		alert(d8);
		download(d8);
	});
	
	
	$("#d9").click(function(){
		var d9 = $("#xzd9").html();
		d9 = "MetLife_产品辅助文件/"+d9 + ".pdf";
//		alert(d9);
		download(d9);
	});
	
	$("#d10").click(function(){
		var d10 = $("#xzd10").html();
		d10 = "MetLife_产品辅助文件/"+d10 + ".pdf";
//		alert(d10);
		download(d10);
	});
	
	$("#d11").click(function(){
		var d11 = $("#xzd11").html();
		d11 = "MetLife_产品辅助文件/"+d11 + ".pdf";
//		alert(d11);
		download(d11);
	});
	
	$("#d12").click(function(){
		var d12 = $("#xzd12").html();
		d12 = "MetLife_产品辅助文件/"+d12 + ".one";
//		alert(d12);
		download(d12);
	});
	
});

function download(id){
	var name = id;
	
//	id="D:/download/"+id;
	
	id="/home/ybt/downloadfiles/noncont/"+id;
	
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