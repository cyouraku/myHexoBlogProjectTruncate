<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String playerPath = basePath + "player";
String audioPath  = basePath + "audio";
String imagePath = basePath + "images";
session.setAttribute("basePath", basePath);
session.setAttribute("playerPath", playerPath);
session.setAttribute("audioPath", audioPath);
session.setAttribute("imagePath", imagePath);
%>
<!DOCTYPE html>
<html>
<title>HLS Player</title>
<head>
    <link rel="stylesheet" href="${playerPath}/mediaelementplayer.css" />
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
    <script src="//cdn.bootcss.com/vue/1.0.26/vue.js"></script>
    <script src="${playerPath}/mediaelement-and-player.js"></script>
<style>
.mejs-controls {
	display: none !important;
}
</style>
</head>
<body>
<div class="panel-group">
	<div class="panel panel-default">
		<div  class="panel-heading" id = "showTime" align="center" ></div>
	</div>
	<!-- Default panel contents -->
	<div class="panel panel-default">
	    <div class="row">
        <div class="col-md-3">
            <div class="panel-body" align="center">
				<table id="animal_tb" style="margin-top: 2px;" >
				<tbody>
					<tr>
						<td v-for="url in urlList"><input type="image" src="<%=imagePath%>/{{url.jpg}}" alt="{{url.name}}" v-on:click="play(url.link);" width="160" height="90" style="margin-left: 2px;"></td>
					</tr>
				</tbody>
                </table>
         	</div>
         </div>
         </div>
<%--       <p><audio id="music" autoplay="false" loop="false"><source type="audio/mp3" src="<%=audioPath%>/{{currentAudio}}" hidden="true"></audio></p> --%>
<%--       <p><embed id="music" src="<%=audioPath%>/{{currentAudio}}" autostart="false" loop="false" hidden="true"></embed></p> --%>
      <p><video id="music" autoplay="false" loop="false"><source type="audio/mp3" src="<%=audioPath%>/{{currentAudio}}" hidden="true"></video></p>
	</div>
	<div class="panel panel-default" >
		<div  class="panel-footer" align="center" >Designed by Tim.Zhang@2018</div>
	</div>
</div>
<script src="<%=basePath%>/static/js/audio.js" type="text/javascript"></script>
</body>
</html>