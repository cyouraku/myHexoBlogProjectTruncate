
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cssPath = basePath + "static/css";
session.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>WEB VOD APP DEMO</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/grid-template.css" />
    <link href="<%=basePath%>static/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/bootstrap-theme.css" rel="stylesheet">
    <script src="<%=basePath%>static/js/jquery-3.3.1.min.js"></script>
    <script src="<%=basePath%>static/js/bootstrap-4.0.0.js"></script>
    <script src="<%=basePath%>static/js/vue.js"></script>
</head>
<body>
<div class="panel-group">
	<div class="panel panel-default">
		<div  class="panel-heading" id = "showTime" align="center" ></div>
	</div>

<div class="panel panel-default">
	<div class="row">
		<div class="panel panel-default">
			<div class="row">
				<div class="col-md-3">
					<div class="panel-body" align="center">
						<div id="demo" align="center">
							<p>{{ message }}</p>
							<p></p>
							<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
							<div id="main" style="height:400px"></div>
							<p></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

	<div class="panel panel-default" >
		<div  class="panel-footer" align="center" >Designed by Tim.Zhang@2018</div>
	</div>
</div>
<!-- ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script src="<%=basePath%>static/js/eChartsBarDemo.js" type="text/javascript"></script>
</body>
</html>