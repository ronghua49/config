<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>qrcodeTest</title>
</head>
<body>
	<!-- 本地生成二维码 -->
	<p id="qrcode"></p>
	<!-- 在线生成二维码 -->
	<img
		src="https://api.qrserver.com/v1/create-qr-code/?data=HelloWorld&size=100x100"
		alt="加载二维码信息" title="加载二维码信息"></img>

	<script src="/static/plugins/qrcodejs-master/jquery.min.js"></script>
	<script src="/static/plugins/qrcodejs-master/qrcode.min.js"></script>
	<script>
		$(function() {
			var qrcode = new QRCode('qrcode', {
				text : "http://baqizi.cc",
				width : 100,
				height : 100,
				colorDark : '#000000',
				colorLight : '#ffffff',
				correctLevel : QRCode.CorrectLevel.H,
			});
			qrcode.clear();//清除二维码信息
			qrcode.makeCode('https://www.baidu.com');//添加新内容  
		});
	</script>

</body>
</html>